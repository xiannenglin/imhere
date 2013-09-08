package org.xiaoxiancai.imhere.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SpinnerTestActivity extends Activity {

	private TextView spinnerLabel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spinner_test);
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		// 第二个参数表示spinner没有展开前的UI类型
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.countries_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinnerLabel = (TextView) findViewById(R.id.spinner_label);
		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener(spinnerLabel));
	}
	
	private class MyOnItemSelectedListener implements OnItemSelectedListener {

		private TextView textView;
		public MyOnItemSelectedListener(TextView textView) {
			this.textView = textView;
		}
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			String str = parent.getItemAtPosition(position).toString();
			Toast.makeText(SpinnerTestActivity.this, "你选择的是" + str,
					Toast.LENGTH_SHORT).show();
			textView.setText(str);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	}
}
