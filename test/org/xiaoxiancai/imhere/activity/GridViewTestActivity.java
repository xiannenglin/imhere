/*
 * Licensed to the Sakai Foundation (SF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The SF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.xiaoxiancai.imhere.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 *
 */
public class GridViewTestActivity extends Activity implements
		OnItemClickListener, OnItemSelectedListener {

	private TextView textView;
	private String[] items = { "lorem", "ipsum", "dolor", "sit", "amet",
			"hello", "me", "elit", "morbi", "vel", "ligula", "vitae", "arcu",
			"aliquet", "mollis", "etiam", "vel", "erat", "placerat", "ante",
			"hi", "sodales", "test", "augue", "purus" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_gridview_test_1);
		setContentView(R.layout.activity_gridview_test_2);
		textView = (TextView) findViewById(R.id.textView);
		// GridView gridView = (GridView) findViewById(R.id.grid);
		GridView gridView = (GridView) findViewById(R.id.gridview);
		// gridView.setAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, items));

		// gridView.setAdapter(new FunnyTextViewAdapter(this,
		// android.R.layout.simple_list_item_1, items));

		// gridView.setAdapter(new FunnyButtonAdapter(this,
		// android.R.layout.simple_list_item_1, items));
		// gridView.setOnItemSelectedListener(this);
		gridView.setAdapter(new ImageAdapter(this));
		gridView.setOnItemClickListener(this);
//		gridView.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		textView.setText("Selected:" + items[position]);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		textView.setText("");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		textView.setText("Clicked:" + items[position]);
	}

	private class FunnyTextViewAdapter extends ArrayAdapter<String> {
		private String[] items;
		private Context context;

		public FunnyTextViewAdapter(Context context, int textViewResourceId,
				String[] items) {
			super(context, textViewResourceId, items);
			this.context = context;
			this.items = items;
		}

		/**
		 * 重写getView()，对每个单元的内容以及UI格式进行描述
		 * 如果我们不使用TextView，则我们必须通过getView()对每一个gridview单元进行描述。
		 * 这些单元可以是Button，ImageView，在这里我们使用Button和TextView分别作测试。 重写override
		 * getView(int, View, ViewGroup)，返回任何我们所希望的view。
		 * 
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView label = (TextView) convertView;
			if (label == null) {
				convertView = new TextView(context);
				label = (TextView) convertView;
			}
			label.setText(position + ":" + items[position]);
			return label;
		}
	}

	private class FunnyButtonAdapter extends ArrayAdapter<String> {

		private Context context;
		private String[] items;

		public FunnyButtonAdapter(Context context, int textViewResourceId,
				String[] items) {
			super(context, textViewResourceId, items);
			this.items = items;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Button button = (Button) convertView;
			if (button == null) {
				button = new Button(context);
			}
			button.setText(position + ":" + items[position]);
			return button;
		}
	}

	private class ImageAdapter extends BaseAdapter {

		private Context context;

		private int[] imageIds = new int[] { R.drawable.venice_1,
				R.drawable.venice_2, R.drawable.venice_3, R.drawable.venice_4,
				R.drawable.venice_5, R.drawable.venice_6, R.drawable.venice_7,
				R.drawable.venice_8, R.drawable.venice_9, R.drawable.venice_10 };

		public ImageAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return imageIds.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = null;
			if (convertView == null) {
				imageView = new ImageView(context);
				// 设置View的height和width：这样保证无论image原来的尺寸，每个图像将重新适合这个指定的尺寸。
				imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			} else {
				imageView = (ImageView) convertView;
			}
			imageView.setImageResource(imageIds[position]);
			return imageView;
		}

	}
}
