package org.xiaoxiancai.imhere.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class AutoCompleteTextViewTestActivity extends Activity {

	private TextView textView;

	private AutoCompleteTextView edit;

	private String[] items = { "lorem", "ipsum", "dolor", "sit", "amet",
			"consectetuer", "adipiscing", "elit", "morbi", "vel", "ligula",
			"vitae", "arcu", "aliquet", "mollis", "etiam", "vel", "erat",
			"placerat", "ante", "porttitor", "sodales", "pellentesque",
			"augue", "purus" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autocompletetextview_test);
		textView = (TextView) findViewById(R.id.input);
		edit = (AutoCompleteTextView) findViewById(R.id.editAuto);
		edit.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, items));

		edit.addTextChangedListener(new MyTextWatcher(textView));
	}

	private class MyTextWatcher implements TextWatcher {

		private TextView textView;

		public MyTextWatcher(TextView textView) {
			this.textView = textView;
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			textView.setText(edit.getText());
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void afterTextChanged(Editable s) {
		}

	}
}
