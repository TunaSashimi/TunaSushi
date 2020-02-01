package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

import com.tuna.R;

import static com.tunasushi.tool.PaintTool.initTunaTextPaint;
import static com.tunasushi.tool.PaintTool.tunaPaint;


/**
 * @author Tunasashimi
 * @date 10/30/15 16:58
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TScratch extends TView{

	private Paint tunaScratchCoverPaint;

	private Bitmap tunaScratchCoverBitmap, tunaScratchSrcBitmap;

	//
	private int tunaScratchTouchX, tunaScratchTouchY;

	//
	private String tunaScratchText;
	//
	private int tunaScratchTextSize;
	private int tunaScratchTextColor;

	private int tunaScratchCoverColor;
	private float tunaScratchCoverStrokeWidth;

	private float tunaScratchRadius;

	private volatile boolean tunaScratchComplete;

	public interface onTunaScratchCompleteListener{
		void onTunaScratchComplete();
	}

	private onTunaScratchCompleteListener onTunaScratchCompleteListener;

	public onTunaScratchCompleteListener getOnTunaScratchCompleteListener(){
		return onTunaScratchCompleteListener;
	}

	public void setOnTunaScratchCompleteListener(onTunaScratchCompleteListener onTunaScratchCompleteListener){
		this.onTunaScratchCompleteListener = onTunaScratchCompleteListener;
	}

	public TScratch(Context context) {
		this(context, null);
	}

	public TScratch(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TScratch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		tunaTag = TScratch.class.getSimpleName();

		//
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TScratch);
		int tunaScratchSrcId = typedArray.getResourceId(R.styleable.TScratch_scratchSrc, -1);
		if (tunaScratchSrcId > -1) {
			tunaScratchSrcBitmap = BitmapFactory.decodeResource(getResources(), tunaScratchSrcId);
		}

		tunaScratchRadius = (int) typedArray.getDimension(R.styleable.TScratch_scratchRadius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
		tunaScratchCoverColor = typedArray.getColor(R.styleable.TScratch_scratchCoverColor, Color.parseColor("#c0c0c0"));

		tunaScratchCoverStrokeWidth = (int) typedArray.getDimension(R.styleable.TScratch_scratchCoverStrokeWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
		tunaScratchText = typedArray.getString(R.styleable.TScratch_scratchText);

		tunaScratchTextSize = (int) typedArray.getDimension(R.styleable.TScratch_scratchTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 22, getResources().getDisplayMetrics()));
		tunaScratchTextColor = typedArray.getColor(R.styleable.TScratch_scratchTextColor, Color.TRANSPARENT);

		typedArray.recycle();

		//
		initTunaPath();
		initTunaRect();

		tunaScratchCoverPaint = new Paint();
	}

	public void setTunaScratchText(String tunaScratchText){
		this.tunaScratchText = tunaScratchText;
		tunaPaint.getTextBounds(tunaScratchText, 0, tunaScratchText.length(), tunaRect);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom){
		super.onLayout(changed, left, top, right, bottom);

		//
		tunaScratchCoverBitmap = Bitmap.createBitmap(tunaWidth, tunaHeight, Config.ARGB_8888);
		tunaCanvas = new Canvas(tunaScratchCoverBitmap);

		//
		tunaScratchCoverPaint.setColor(tunaScratchCoverColor);
		tunaScratchCoverPaint.setAntiAlias(true);
		tunaScratchCoverPaint.setDither(true);
		// 设置结合处的样子, Miter为锐角, Round为圆弧, BEVEL为直线。
		tunaScratchCoverPaint.setStrokeJoin(Paint.Join.ROUND);
		// 设置笔刷类型
		tunaScratchCoverPaint.setStrokeCap(Paint.Cap.ROUND);
		tunaScratchCoverPaint.setStyle(Style.FILL);
		tunaScratchCoverPaint.setStrokeWidth(tunaScratchCoverStrokeWidth);

		//
		initTunaTextPaint(Style.FILL, tunaScratchTextColor, tunaScratchTextSize, Align.LEFT);
		tunaPaint.getTextBounds(tunaScratchText, 0, tunaScratchText.length(), tunaRect);

		tunaCanvas.drawRoundRect(new RectF(0, 0, tunaWidth, tunaHeight), tunaScratchRadius, tunaScratchRadius, tunaScratchCoverPaint);
		tunaCanvas.drawBitmap(tunaScratchSrcBitmap, null, new Rect(0, 0, tunaWidth, tunaHeight), null);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event){
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				tunaScratchTouchX = x;
				tunaScratchTouchY = y;
				tunaPath.moveTo(tunaScratchTouchX, tunaScratchTouchY);
				break;
			case MotionEvent.ACTION_MOVE:
				int dx = Math.abs(x - tunaScratchTouchX);
				int dy = Math.abs(y - tunaScratchTouchY);
				if (dx > 3 || dy > 3) {
					tunaPath.lineTo(x, y);
				}
				tunaScratchTouchX = x;
				tunaScratchTouchY = y;
				break;
			case MotionEvent.ACTION_UP:
				if (!tunaScratchComplete) {
					measurePixels();
				}
				break;
			default:
				break;
		}
		if (!tunaScratchComplete) {
			invalidate();
		}
		return true;
	}

	private void measurePixels(){
		new Thread(mRunnable).start();
	}

	private Runnable mRunnable = new Runnable(){
		@Override
		public void run(){
			int w = getWidth();
			int h = getHeight();
			float wipeArea = 0;
			float totalArea = w * h;
			Bitmap bitmap = tunaScratchCoverBitmap;
			int[] mPixels = new int[w * h];
			// 获得bitmap上所有信息
			bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					int index = i + j * w;
					if (mPixels[index] == 0) {
						wipeArea++;
					}
				}
			}

			if (wipeArea > 0 && totalArea > 0) {
				int percent = (int) (wipeArea * 100 / totalArea);
				if (percent > 60) {
					tunaScratchComplete = true;
					postInvalidate();
				}
			}
		}
	};

	@Override
	protected void onDraw(Canvas canvas){
		canvas.drawText(tunaScratchText, (tunaWidth >> 1) - tunaRect.width() / 2, (tunaHeight >> 1) + tunaRect.height() / 2, tunaPaint);

		if (!tunaScratchComplete) {
			tunaScratchCoverPaint.setStyle(Style.STROKE);
			tunaScratchCoverPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
			tunaCanvas.drawPath(tunaPath, tunaScratchCoverPaint);
			canvas.drawBitmap(tunaScratchCoverBitmap, 0, 0, null);
		}

		if (tunaScratchComplete) {
			if (onTunaScratchCompleteListener != null) {
				onTunaScratchCompleteListener.onTunaScratchComplete();
			}
		}
	}
}
