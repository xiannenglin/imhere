package org.xiaoxiancai.imhere.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;

import org.xiaoxiancai.imhere.utils.ImHereConstants;

/**
 * 展示位置
 */
public class LocationActivity extends Activity {

	private LocationClient locationClient = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context context = getApplicationContext();

		SDKInitializer.initialize(context);

		locationClient = initLocationClient(context);
		setContentView(R.layout.activity_location);

		MapView mapView = (MapView) findViewById(R.id.mapView);
		BaiduMap baiduMap = mapView.getMap();
		baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		baiduMap.setMyLocationEnabled(true);
		startLocationClient(locationClient, baiduMap);
	}

	/**
	 * 初始化LocationClient
	 */
	private LocationClient initLocationClient(Context context) {
		LocationClient locationClient = new LocationClient(context);
		locationClient.setAK(ImHereConstants.IMHERE_MOBILE_KEY);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setPriority(LocationClientOption.GpsFirst);
		option.setProdName(ImHereConstants.IMHERE_PROD_NAME);
		option.setScanSpan(10000);
		option.setAddrType("all");
		locationClient.setLocOption(option);
		return locationClient;
	}

	/**
	 * 开始定位
	 * 
	 * @param locationClient
	 */
	private void startLocationClient(LocationClient locationClient,
			BaiduMap baiduMap) {
		locationClient.start();
		locationClient.registerLocationListener(new ImHereLocationListener(
				baiduMap));
		if (locationClient.isStarted()) {
			locationClient.requestLocation();
		}
	}

	/**
	 * 定位监控器
	 */
	class ImHereLocationListener implements BDLocationListener {

		private BaiduMap baiduMap;

		public ImHereLocationListener(BaiduMap baiduMap) {
			this.baiduMap = baiduMap;
		}

		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
			System.out.println(getLocationInfo(location));
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					// .direction(100)
					.latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			baiduMap.setMyLocationData(locData);
			System.out.println(baiduMap.isBuildingsEnabled());
			BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
					.fromResource(R.drawable.ic_launcher);
			MyLocationConfiguration config = new MyLocationConfiguration(
					LocationMode.FOLLOWING, true, mCurrentMarker);
			baiduMap.setMyLocationConfigeration(config);
			// 当不需要定位图层时关闭定位图层
			// baiduMap.setMyLocationEnabled(false);
		}

		/**
		 * 获取位置信息
		 */
		private String getLocationInfo(BDLocation location) {
			StringBuilder buffer = new StringBuilder();
			buffer.append("latitude = ").append(location.getLatitude())
					.append("\n");
			buffer.append("longitude = ").append(location.getLongitude())
					.append("\n");
			buffer.append("direct = ").append(location.getDerect())
					.append("\n");
			buffer.append("city = ").append(location.getCity()).append("\n");
			buffer.append("province = ").append(location.getProvince())
					.append("\n");
			buffer.append("street = ").append(location.getStreet())
					.append("\n");
			return buffer.toString();
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}
}
