package org.xiaoxiancai.imhere.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewTestActivity extends ListActivity {

	private String[] items = { "One", "Two", "Three", "Four", "Five", "Six",
			"Serven", "Eight" };
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_test_1);
		// setListAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, items));

		// setListAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_single_choice, items));
		// getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		// setListAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_multiple_choice, items));
		// getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		// setListAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_checked, items));
		// getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		// button = (Button) findViewById(R.id.ok);

		String[] contries = getResources().getStringArray(
				R.array.countries_array);
		setListAdapter(new ArrayAdapter<String>(this,
				R.layout.activity_listview_test_2, contries));
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(ListViewTestActivity.this,
						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		button.setText(items[position]); // position是点击的item的序号，从0开始。
	}

}
