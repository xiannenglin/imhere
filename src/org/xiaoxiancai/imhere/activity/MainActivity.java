package org.xiaoxiancai.imhere.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 启动Activity
 */
public class MainActivity extends Activity {

	private Button startButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startButton = (Button) findViewById(R.id.btn_start);

		startButton.setOnClickListener(new StartButtonOnClickListener(this));

	}

	class StartButtonOnClickListener implements OnClickListener {

		private Context context;

		public StartButtonOnClickListener(Context context) {
			this.context = context;
		}

		public void onClick(View v) {
			Intent intent = new Intent(context, LocationActivity.class);
			startActivity(intent);
		}
	}

}
