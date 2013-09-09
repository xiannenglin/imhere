package org.xiaoxiancai.imhere.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

public class RatingBarTestActivity extends Activity {

	private RatingBar ratingBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ratingbar_test);
		ratingBar = (RatingBar) findViewById(R.id.ratingbar_1);
		ratingBar.setOnRatingBarChangeListener(new MyOnRatingBarChangeListener(this));
	}
	
	@SuppressLint("ShowToast")
	private class MyOnRatingBarChangeListener implements OnRatingBarChangeListener {

		private Context context;
		
		public MyOnRatingBarChangeListener(Context context) {
			this.context = context;
		}


		public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
			System.out.println("rating = " + rating);
			System.out.println("num stars = " + ratingBar.getNumStars());
//			Toast.makeText(context, ratingBar.getNumStars(), Toast.LENGTH_SHORT);
//			Toast.makeText(context, String.valueOf(rating), Toast.LENGTH_SHORT);
		}
		
	}
}
