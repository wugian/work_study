package com.xgimi.dressingshow.activity;

import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

public class ZoomListenter implements OnTouchListener {
	private int mode = 0;
	float oldDist;
	//float textSize = 0;
	LayoutParams laParams;
	ImageView imageView = null;

	public ZoomListenter(ImageView imageView) {
		this.imageView = imageView;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		imageView = (ImageView) v;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mode = 1;
			break;
		case MotionEvent.ACTION_UP:
			mode = 0;
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mode -= 1;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			mode += 1;
			break;

		case MotionEvent.ACTION_MOVE:
			if (mode >= 2) {
				float newDist = spacing(event);
				if (newDist > oldDist + 1) {
					zoom(newDist / oldDist);
					oldDist = newDist;
				}
				if (newDist < oldDist - 1) {
					zoom(newDist / oldDist);
					oldDist = newDist;
				}
			}
			break;
		}
		return true;
	}

	private void zoom(float f) {
		laParams = (LayoutParams) imageView.getLayoutParams();
		laParams.height = (int) (laParams.height * f);
		laParams.width = (int) (laParams.width * f);
		imageView.setLayoutParams(laParams);
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}
}
