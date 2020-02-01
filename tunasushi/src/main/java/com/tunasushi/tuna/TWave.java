package com.tunasushi.tuna;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:59
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TWave extends TView{

	// 波纹颜色
	private static final int WAVE_PAINT_COLOR = 0x880000aa;
	// y = Asin(wx+b)+h
	private static final float STRETCH_FACTOR_A = 20;
	private static final int OFFSET_Y = 0;
	// 第一条水波移动速度
	private static final int TRANSLATE_X_SPEED_ONE = 7;
	// 第二条水波移动速度
	private static final int TRANSLATE_X_SPEED_TWO = 5;
	private float mCycleFactorW;

	private int mTotalWidth, mTotalHeight;
	private float[] mYPositions;
	private float[] mResetOneYPositions;
	private float[] mResetTwoYPositions;
	private int mXOffsetSpeedOne;
	private int mXOffsetSpeedTwo;
	private int mXOneOffset;
	private int mXTwoOffset;

	private Paint mWavePaint;
	private DrawFilter mDrawFilter;

    public TWave(Context context) {
        this(context, null);
    }

    public TWave(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TWave(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

		tunaTag = TWave.class.getSimpleName();

		// 将dp转化为px，用于控制不同分辨率上移动速度基本一致
		mXOffsetSpeedOne = dipToPx(context, TRANSLATE_X_SPEED_ONE);
		mXOffsetSpeedTwo = dipToPx(context, TRANSLATE_X_SPEED_TWO);

		// 初始绘制波纹的画笔
		mWavePaint = new Paint();
		// 去除画笔锯齿
		mWavePaint.setAntiAlias(true);
		// 设置风格为实线
		mWavePaint.setStyle(Style.FILL);
		// 设置画笔颜色
		mWavePaint.setColor(WAVE_PAINT_COLOR);
		mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

	}

	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
//		System.out.println("waveOnDraw");
		
//		正弦水波纹效果，每次在ondraw的时候其实可以不用重新都拷贝2个数组，直接通过mYPositions利用相应的偏移量就可以获得两个波浪的数据，说简单点就是可以不使用
//		mResetOneYPositions和mResetTwoYPositions直接通过mYPositions获取两个波浪数据，从而节约每次拷贝的时间提高性能。总体来说博主的文章还是非常棒的。
		
		// 正余弦函数方程为：
		// y = Asin(wx+b)+h ，这个公式里：w影响周期，A影响振幅，h影响y位置，b为初相；

		// 从canvas层面去除绘制时锯齿
		canvas.setDrawFilter(mDrawFilter);
		resetPositonY();
		for (int i = 0; i < mTotalWidth; i++) {

			// 减400只是为了控制波纹绘制的y的在屏幕的位置，大家可以改成一个变量，然后动态改变这个变量，从而形成波纹上升下降效果
			// 绘制第一条水波纹
			canvas.drawLine(i, mTotalHeight - mResetOneYPositions[i] - 400, i, mTotalHeight, mWavePaint);

			// 绘制第二条水波纹
			canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - 400, i, mTotalHeight, mWavePaint);
		}

		// 改变两条波纹的移动点
		mXOneOffset += mXOffsetSpeedOne;
		mXTwoOffset += mXOffsetSpeedTwo;

		// 如果已经移动到结尾处，则重头记录
		if (mXOneOffset >= mTotalWidth) {
			mXOneOffset = 0;
		}
		if (mXTwoOffset > mTotalWidth) {
			mXTwoOffset = 0;
		}

		// 引发view重绘，一般可以考虑延迟20-30ms重绘，空出时间片
		postInvalidate();

	}

	private void resetPositonY(){
		// mXOneOffset代表当前第一条水波纹要移动的距离
		int yOneInterval = mYPositions.length - mXOneOffset;
		// 使用System.arraycopy方式重新填充第一条波纹的数据
		System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
		System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

		int yTwoInterval = mYPositions.length - mXTwoOffset;
		System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0, yTwoInterval);
		System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
		// 记录下view的宽高
		mTotalWidth = w;
		mTotalHeight = h;
		// 用于保存原始波纹的y值
		mYPositions = new float[mTotalWidth];
		// 用于保存波纹一的y值
		mResetOneYPositions = new float[mTotalWidth];
		// 用于保存波纹二的y值
		mResetTwoYPositions = new float[mTotalWidth];

		// 将周期定为view总宽度
		mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);

		// 根据view总宽度得出所有对应的y值
		for (int i = 0; i < mTotalWidth; i++) {
			mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
		}
	}

	static public int getScreenWidthPixels(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	static public int dipToPx(Context context, int dip){
		return (int) (dip * getScreenDensity(context) + 0.5f);
	}

	static public float getScreenDensity(Context context){
		try {
			DisplayMetrics dm = new DisplayMetrics();
			((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
			return dm.density;
		} catch (Exception e) {
			return DisplayMetrics.DENSITY_DEFAULT;
		}
	}

}
