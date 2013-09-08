package org.xiaoxiancai.imhere.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class ProgressBarTestActivity extends Activity {

	private ProgressBar rectangleProgressBar, circleProgressBar;
	private Button mButton;

	protected static final int STOP = 0x10000;
	protected static final int NEXT = 0x10001;
	private int iCount = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progressbar_test);
		// findView By id
		rectangleProgressBar = (ProgressBar) findViewById(R.id.rectangleProgressBar);
		circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);
		mButton = (Button) findViewById(R.id.button);

		rectangleProgressBar.setIndeterminate(false);
		circleProgressBar.setIndeterminate(false);

		mButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {

				rectangleProgressBar.setVisibility(View.VISIBLE);
				circleProgressBar.setVisibility(View.VISIBLE);

				rectangleProgressBar.setMax(100);
				rectangleProgressBar.setProgress(0);
				circleProgressBar.setProgress(0);

				// 创建一个线程,每秒步长为5增加,到100%时停止
				Thread mThread = new Thread(new Runnable() {

					public void run() {

						for (int i = 0; i < 20; i++) {
							try {
								iCount = (i + 1) * 5;
								Thread.sleep(1000);
								if (i == 19) {
									Message msg = new Message();
									msg.what = STOP;
									mHandler.sendMessage(msg);
									break;
								} else {
									Message msg = new Message();
									msg.what = NEXT;
									mHandler.sendMessage(msg);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					}
				});
				mThread.start();
			}
		});
	}

	// 定义一个Handler
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STOP:
				rectangleProgressBar.setVisibility(View.GONE);
				circleProgressBar.setVisibility(View.GONE);
				Thread.currentThread().interrupt();
				break;
			case NEXT:
				if (!Thread.currentThread().isInterrupted()) {
					System.out.println("set progress....");
					rectangleProgressBar.setProgress(iCount);
					circleProgressBar.setProgress(iCount);
				}
				break;
			}
		}
	};
}
