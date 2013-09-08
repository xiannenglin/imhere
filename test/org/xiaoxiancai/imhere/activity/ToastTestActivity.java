package org.xiaoxiancai.imhere.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ToastTestActivity extends Activity implements OnClickListener  {

	private Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_toast_test);
		findViewById(R.id.btnSimpleToast).setOnClickListener(this);
		findViewById(R.id.btnSimpleToastWithCustomPosition).setOnClickListener(
				this);
		findViewById(R.id.btnSimpleToastWithImage).setOnClickListener(this);
		findViewById(R.id.btnCustomToast).setOnClickListener(this);
		findViewById(R.id.btnRunToastFromOtherThread).setOnClickListener(this);
	}

	public void showToast() {
		handler.post(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), "我来自其他线程！",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void onClick(View v) {
		Toast toast = null;
		switch (v.getId()) {
		case R.id.btnSimpleToast:
			Toast.makeText(getApplicationContext(), "默认Toast样式",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnSimpleToastWithCustomPosition:
			toast = Toast.makeText(getApplicationContext(), "自定义位置Toast",
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			break;
		case R.id.btnSimpleToastWithImage:
			toast = Toast.makeText(getApplicationContext(), "带图片的Toast",
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			LinearLayout toastView = (LinearLayout) toast.getView();
			ImageView imageCodeProject = new ImageView(getApplicationContext());
			imageCodeProject.setImageResource(R.drawable.ic_launcher);
			toastView.addView(imageCodeProject, 0);
			toast.show();
			break;
//		case R.id.btnCustomToast:
//			LayoutInflater inflater = getLayoutInflater();
//			View layout = inflater.inflate(R.layout.custom,
//					(ViewGroup) findViewById(R.id.llToast));
//			ImageView image = (ImageView) layout
//					.findViewById(R.id.tvImageToast);
//			image.setImageResource(R.drawable.icon);
//			TextView title = (TextView) layout.findViewById(R.id.tvTitleToast);
//			title.setText("Attention");
//			TextView text = (TextView) layout.findViewById(R.id.tvTextToast);
//			text.setText("完全自定义Toast");
//			toast = new Toast(getApplicationContext());
//			toast.setGravity(Gravity.RIGHT | Gravity.TOP, 12, 40);
//			toast.setDuration(Toast.LENGTH_LONG);
//			toast.setView(layout);
//			toast.show();
//			break;
		case R.id.btnRunToastFromOtherThread:
			new Thread(new Runnable() {
				public void run() {
					showToast();
				}
			}).start();
			break;
		}
	}

}
