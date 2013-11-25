package org.xiaoxiancai.imhere.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import org.xiaoxiancai.imhere.utils.ImHereConstants;

/**
 * 展示位置
 */
public class LocationActivity extends Activity {

	@SuppressWarnings("unused")
	private BMapManager bMapManager = null;

	private MapView mapView = null;

	private LocationClient locationClient = null;

	private MyLocationOverlay myLocationOverlay = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Application app = getApplication();
		locationClient = initLocationClient(app);
		bMapManager = initBMapManager(app);
		// 注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.activity_locate);
		mapView = (MapView) findViewById(R.id.mapView);
		myLocationOverlay = new MyLocationOverlay(mapView);
		startLocationClient(locationClient, mapView, myLocationOverlay);

	}

	/**
	 * 初始化BMapManager
	 * 
	 */
	private BMapManager initBMapManager(Application app) {
		BMapManager bMapManager = new BMapManager(app);
		bMapManager.init(ImHereConstants.IMHERE_MOBILE_KEY, null);
		return bMapManager;
	}

	/**
	 * 初始化LocationClient
	 */
	private LocationClient initLocationClient(Application app) {
		LocationClient locationClient = new LocationClient(app);
		locationClient.setAK(ImHereConstants.IMHERE_LOCATION_KEY);
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
			MapView mapView, MyLocationOverlay myLocationOverlay) {
		locationClient.start();
		locationClient.registerLocationListener(new ImHereLocationListener(
				mapView, myLocationOverlay));
	}

	/**
	 * 定位监控器
	 */
	class ImHereLocationListener implements BDLocationListener {
		private MapView mapView;

		private MyLocationOverlay myLocationOverlay;

		// 我的位置图层是否已添加到MapView
		boolean isMyLocationOverlayAdded = false;

		public ImHereLocationListener(MapView mapView,
				MyLocationOverlay myLocationOverlay) {
			this.mapView = mapView;
			this.myLocationOverlay = myLocationOverlay;
		}

		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();

			LocationData locationData = new LocationData();
			locationData.latitude = latitude;
			locationData.longitude = longitude;
			locationData.direction = location.getDerect();
			myLocationOverlay.setData(locationData);

			// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
			GeoPoint point = new GeoPoint((int) (latitude * 1E6),
					(int) (longitude * 1E6));

			myLocationOverlay.setLocationMode(LocationMode.NORMAL);
			if (!isMyLocationOverlayAdded) {
				initLocation(point);
				mapView.getOverlays().add(myLocationOverlay);
				myLocationOverlay.enableCompass();
				mapView.refresh();
				isMyLocationOverlayAdded = true;
			} else {
				// TODO
			}

		}

		/**
		 * 初始化我的位置
		 * 
		 */
		private void initLocation(GeoPoint point) {
			MapController mapController = mapView.getController();
			// 设置地图中心点
			mapController.setCenter(point);
			// 设置地图zoom级别
			mapController.setZoom(ImHereConstants.IMHERE_DEFAULT_ZOOM_LEVEL);
			mapController.enableClick(true);
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}
}
