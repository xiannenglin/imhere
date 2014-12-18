package org.xiaoxiancai.imhere.activity;

import org.xiaoxiancai.imhere.client.Client;
import org.xiaoxiancai.imhere.client.ClientFactory;
import org.xiaoxiancai.imhere.common.protos.business.LocateRequestProtos.LocateRequest;
import org.xiaoxiancai.imhere.common.protos.business.LocateResponseProtos.LocateResponse;
import org.xiaoxiancai.imhere.common.protos.business.LocationProtos.Location;
import org.xiaoxiancai.imhere.utils.ImHereConstants;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

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

/**
 * 展示位置
 */
public class LocationActivity extends Activity {

	private LocationClient locationClient;

	private ClientFactory clientFactory;

	private Client imHereClient;

	private static final String serverHost = "120.24.222.133";

	private static final int serverPort = 18080;

	private int userId;

	private TextView locationText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		clientFactory = ClientFactory.getInstance();
		super.onCreate(savedInstanceState);
		Context context = getApplicationContext();
		SDKInitializer.initialize(context);
		setContentView(R.layout.activity_location);

		MapView mapView = (MapView) findViewById(R.id.mapView);
		locationText = (TextView) findViewById(R.id.locationText);
		BaiduMap baiduMap = mapView.getMap();
		baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		baiduMap.setMyLocationEnabled(true);
		locationClient = initLocationClient(context, baiduMap);
		imHereClient = clientFactory.getDefaultClient(serverHost, serverPort);
		startLocationClient(locationClient, baiduMap);
	}

	/**
	 * 初始化LocationClient
	 * 
	 * @param context
	 * @return
	 */
	private LocationClient initLocationClient(Context context, BaiduMap baiduMap) {
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
		locationClient.registerLocationListener(new ImHereLocationListener(
				baiduMap));
		return locationClient;
	}

	/**
	 * 开始定位
	 * 
	 * @param locationClient
	 * @param baiduMap
	 */
	private void startLocationClient(LocationClient locationClient,
			BaiduMap baiduMap) {
		locationClient.start();
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

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					// .direction(100)
					.latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			baiduMap.setMyLocationData(locData);
			BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
					.fromResource(R.drawable.ic_launcher);
			MyLocationConfiguration config = new MyLocationConfiguration(
					LocationMode.FOLLOWING, true, mCurrentMarker);
			baiduMap.setMyLocationConfigeration(config);
			// 当不需要定位图层时关闭定位图层
			// baiduMap.setMyLocationEnabled(false);

			// 向服务器发送位置信息 TODO
			try {
				String msg = location.getLocType() + ","
						+ location.getLatitude() + ","
						+ location.getLongitude();
//				locationText.setText(msg);
				LocateRequest locateRequest = createLocateRequest(location);
//				locationText.setText(locateRequest.toString());
				LocateResponse response = imHereClient.locate(locateRequest);
				locationText.setText("response = " + response.toString());
			} catch (Exception e) {
				e.printStackTrace();
				locationText.setText(imHereClient + ";" + e.getMessage() + ";" + e.getCause());
			}
		}

		private LocateRequest createLocateRequest(BDLocation location) {
			Location.Builder protoLocationBuilder = Location.newBuilder();
			// TODO
			protoLocationBuilder.setUserId(1);
			protoLocationBuilder.setLocType(location.getLocType());
			protoLocationBuilder.setLatitude(location.getLatitude());
			protoLocationBuilder.setLongitude(location.getLongitude());
			protoLocationBuilder.setSpeed(location.getSpeed());
			LocateRequest.Builder builder = LocateRequest.newBuilder();
			builder.setCurrentLocation(protoLocationBuilder.build());
			return builder.build();
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}
}
