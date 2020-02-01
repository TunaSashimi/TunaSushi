package com.tunasushi.tuna;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Property;
import android.util.TypedValue;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.tuna.R;

import static com.tunasushi.tool.DeviceTool.applyDimension;
import static com.tunasushi.tool.DeviceTool.getViewDisplayMetrics;
import static com.tunasushi.tool.PaintTool.initTunaPaint;
import static com.tunasushi.tool.PaintTool.initTunaTextPaint;
import static com.tunasushi.tool.ViewTool.getLinearGradient;


/**
 * @author Tunasashimi
 * @date 10/30/15 16:57
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TRipple extends TView{

	private float tunaRippleInnerCircleRadius;

	public float getTunaRippleInnerCircleRadius(){
		return tunaRippleInnerCircleRadius;
	}

	public void setTunaRippleInnerCircleRadius(float tunaRippleInnerCircleRadius){
		this.tunaRippleInnerCircleRadius = tunaRippleInnerCircleRadius;
	}

	private int tunaRippleInnerCircleColor;

	public int getTunaRippleInnerCircleColor(){
		return tunaRippleInnerCircleColor;
	}

	public void setTunaRippleInnerCircleColor(int tunaRippleInnerCircleColor){
		if (this.tunaRippleInnerCircleColor!=tunaRippleInnerCircleColor) {
			tunaRippleInnerCircleColorShader=null;
			this.tunaRippleInnerCircleColor = tunaRippleInnerCircleColor;
		}
	}
	
	//
	public void setTunaRippleInnerCircleColor(int tunaRippleInnerCircleColorGradientStart,int tunaRippleInnerCircleColorGradientEnd){
		this.tunaRippleInnerCircleColorGradientStart = tunaRippleInnerCircleColorGradientStart;
		this.tunaRippleInnerCircleColorGradientEnd = tunaRippleInnerCircleColorGradientEnd;
		
		setTunaRippleInnerCircleColor(
				tunaRippleInnerCircleAngle == Integer.MAX_VALUE ? 0 : tunaRippleInnerCircleAngle, 
						tunaRippleInnerCircleColorGradientStart, 
						tunaRippleInnerCircleColorGradientEnd);
	}
	
	public void setTunaRippleInnerCircleColor(int tunaRippleInnerCircleAngle,int tunaRippleInnerCircleColorGradientStart,int tunaRippleInnerCircleColorGradientEnd){
		this.tunaRippleInnerCircleColorGradientStart = tunaRippleInnerCircleColorGradientStart;
		this.tunaRippleInnerCircleColorGradientEnd = tunaRippleInnerCircleColorGradientEnd;
		
		tunaRippleInnerCircleColorShader = getLinearGradient(tunaWidth,tunaHeight,tunaRippleInnerCircleAngle, tunaRippleInnerCircleColorGradientStart, tunaRippleInnerCircleColorGradientEnd);
	}

	//
	private float tunaRippleOuterCircleRadius;

	public float getTunaRippleOuterCircleRadius(){
		return tunaRippleOuterCircleRadius;
	}

	public void setTunaRippleOuterCircleRadius(float tunaRippleOuterCircleRadius){
		this.tunaRippleOuterCircleRadius = tunaRippleOuterCircleRadius;
	}

	// delay
	private float tunaRippleOuterDelayCircleRadius;

	public float getTunaRippleOuterDelayCircleRadius(){
		return tunaRippleOuterDelayCircleRadius;
	}

	public void setTunaRippleOuterDelayCircleRadius(float tunaRippleOuterDelayCircleRadius){
		this.tunaRippleOuterDelayCircleRadius = tunaRippleOuterDelayCircleRadius;
	}

	// defer
	private float tunaRippleOuterDeferCircleRadius;

	public float getTunaRippleOuterDeferCircleRadius(){
		return tunaRippleOuterDeferCircleRadius;
	}

	public void setTunaRippleOuterDeferCircleRadius(float tunaRippleOuterDeferCircleRadius){
		this.tunaRippleOuterDeferCircleRadius = tunaRippleOuterDeferCircleRadius;
	}

	private int tunaRippleOuterCircleColor;

	public int getTunaRippleOuterCircleColor(){
		return tunaRippleOuterCircleColor;
	}

	public void setTunaRippleOuterCircleColor(int tunaRippleOuterCircleColor){
		
		if (this.tunaRippleOuterCircleColor!=tunaRippleOuterCircleColor) {
			
			tunaRippleOuterCircleColorShader=null;
			this.tunaRippleOuterCircleColor = tunaRippleOuterCircleColor;
		}
		
	}
	
	public void setTunaRippleOuterCircleColor(int tunaRippleOuterCircleColorGradientStart,int tunaRippleOuterCircleColorGradientEnd){
		setTunaRippleOuterCircleColor(
				tunaRippleOuterCircleAngle == Integer.MAX_VALUE ? 0 : tunaRippleOuterCircleAngle, 
				tunaRippleOuterCircleColorGradientStart, 
				tunaRippleOuterCircleColorGradientEnd);
	}
	
	public void setTunaRippleOuterCircleColor(int tunaRippleOuterCircleAngle,int tunaRippleOuterCircleColorGradientStart,int tunaRippleOuterCircleColorGradientEnd){
		this.tunaRippleOuterCircleColorGradientStart = tunaRippleOuterCircleColorGradientStart;
		this.tunaRippleOuterCircleColorGradientEnd = tunaRippleOuterCircleColorGradientEnd;
		
		tunaRippleOuterCircleColorShader = getLinearGradient(tunaWidth,tunaHeight,tunaRippleOuterCircleAngle, tunaRippleOuterCircleColorGradientStart, tunaRippleOuterCircleColorGradientEnd);
	}
	
	private float tunaRippleTextSize;

	public float getTunaRippleTextSize(){
		return tunaRippleTextSize;
	}

	public void setTunaRippleTextSize(float tunaRippleTextSize){
		this.tunaRippleTextSize = tunaRippleTextSize;
	}

	private int tunaRippleTextColor;

	public int getTunaRippleTextColor(){
		return tunaRippleTextColor;
	}

	public void setTunaRippleTextColor(int tunaRippleTextColor){
		this.tunaRippleTextColor = tunaRippleTextColor;
	}

	private String tunaRippleTextValue;

	public String getTunaRippleTextValue(){
		return tunaRippleTextValue;
	}

	public void setTunaRippleTextValue(String tunaRippleTextValue){
		this.tunaRippleTextValue = tunaRippleTextValue;
	}

	//
	private float tunaRippleTextDx;

	public float getTunaRippleTextDx(){
		return tunaRippleTextDx;
	}

	public void setTunaRippleTextDx(float tunaRippleTextDx){
		setTunaRippleTextDx(TypedValue.COMPLEX_UNIT_DIP, tunaRippleTextDx);
	}

	public void setTunaRippleTextDx(int unit, float tunaRippleTextDx){
		setTunaRippleTextDxRaw(applyDimension(unit, tunaRippleTextDx, getViewDisplayMetrics(this)));
	}

	private void setTunaRippleTextDxRaw(float tunaRippleTextDx){
		if (this.tunaRippleTextDx != tunaRippleTextDx) {
			this.tunaRippleTextDx = tunaRippleTextDx;
		}
	}

	//
	private float tunaRippleTextDy;

	public float getTunaRippleTextDy(){
		return tunaRippleTextDy;
	}

	public void setTunaRippleTextDy(float tunaRippleTextDy){
		setTunaRippleTextDy(TypedValue.COMPLEX_UNIT_DIP, tunaRippleTextDy);
	}

	public void setTunaRippleTextDy(int unit, float tunaRippleTextDy){
		setTunaRippleTextDyRaw(applyDimension(unit, tunaRippleTextDy, getViewDisplayMetrics(this)));
	}

	private void setTunaRippleTextDyRaw(float tunaRippleTextDy){
		if (this.tunaRippleTextDy != tunaRippleTextDy) {
			this.tunaRippleTextDy = tunaRippleTextDy;
		}
	}

	//
	private float tunaRippleTextFractionDx;

	public float getTunaRippleTextFractionDx(){
		return tunaRippleTextFractionDx;
	}

	public void setTunaRippleTextFractionDx(float tunaRippleTextFractionDx){
		this.tunaRippleTextFractionDx = tunaRippleTextFractionDx;
	}

	//
	private float tunaRippleTextFractionDy;

	public float getTunaRippleTextFractionDy(){
		return tunaRippleTextFractionDy;
	}

	public void setTunaRippleTextFractionDy(float tunaRippleTextFractionDy){
		this.tunaRippleTextFractionDy = tunaRippleTextFractionDy;
	}

	private int tunaRippleDuraction;

	public int getTunaRippleDuraction(){
		return tunaRippleDuraction;
	}

	public void setTunaRippleDuraction(int tunaRippleDuraction){
		this.tunaRippleDuraction = tunaRippleDuraction;
	}

	private int tunaRippleInnerCircleAngle;
	public int getTunaRippleInnerCircleAngle(){
		return tunaRippleInnerCircleAngle;
	}
	public void setTunaRippleInnerCircleAngle(int tunaRippleInnerCircleAngle){
		this.tunaRippleInnerCircleAngle = tunaRippleInnerCircleAngle;
		tunaRippleInnerCircleColorShader = getLinearGradient(tunaWidth,tunaHeight,tunaRippleInnerCircleAngle, tunaRippleInnerCircleColorGradientStart, tunaRippleInnerCircleColorGradientEnd);
	}
	
	private int tunaRippleOuterCircleAngle;
	public int getTunaRippleOuterCircleAngle(){
		return tunaRippleOuterCircleAngle;
	}
	public void setTunaRippleOuterCircleAngle(int tunaRippleOuterCircleAngle){
		this.tunaRippleOuterCircleAngle = tunaRippleOuterCircleAngle;
		tunaRippleOuterCircleColorShader = getLinearGradient(tunaWidth,tunaHeight,tunaRippleOuterCircleAngle, tunaRippleOuterCircleColorGradientStart, tunaRippleOuterCircleColorGradientEnd);
	}
	
	// tunaRippleInnerCircleColorGradientStart default tunaRippleInnerCircleColor
	private int tunaRippleInnerCircleColorGradientStart;

	public int getTunaRippleInnerCircleColorGradientStart(){
		return tunaRippleInnerCircleColorGradientStart;
	}

	public void setTunaRippleInnerCircleColorGradientStart(int tunaRippleInnerCircleColorGradientStart){
		this.tunaRippleInnerCircleColorGradientStart = tunaRippleInnerCircleColorGradientStart;
	}

	// tunaRippleInnerCircleColorGradientEnd default tunaRippleInnerCircleColor
	private int tunaRippleInnerCircleColorGradientEnd;

	public int getTunaRippleInnerCircleColorGradientEnd(){
		return tunaRippleInnerCircleColorGradientEnd;
	}

	public void setTunaRippleInnerCircleColorGradientEnd(int tunaRippleInnerCircleColorGradientEnd){
		this.tunaRippleInnerCircleColorGradientEnd = tunaRippleInnerCircleColorGradientEnd;
	}
	
	// tunaRippleOuterCircleColorGradientStart default tunaRippleOuterCircleColor
	private int tunaRippleOuterCircleColorGradientStart;

	public int getTunaRippleOuterCircleColorGradientStart(){
		return tunaRippleOuterCircleColorGradientStart;
	}

	public void setTunaRippleOuterCircleColorGradientStart(int tunaRippleOuterCircleColorGradientStart){
		this.tunaRippleOuterCircleColorGradientStart = tunaRippleOuterCircleColorGradientStart;
	}

	// tunaRippleOuterCircleColorGradientEnd default tunaRippleOuterCircleColor
	private int tunaRippleOuterCircleColorGradientEnd;

	public int getTunaRippleOuterCircleColorGradientEnd(){
		return tunaRippleOuterCircleColorGradientEnd;
	}

	public void setTunaRippleOuterCircleColorGradientEnd(int tunaRippleOuterCircleColorGradientEnd){
		this.tunaRippleOuterCircleColorGradientEnd = tunaRippleOuterCircleColorGradientEnd;
	}

	// tunaRippleInnerCircleColorShader default null
	private Shader tunaRippleInnerCircleColorShader;

	public Shader getTunaRippleInnerCircleColorShader(){
		return tunaRippleInnerCircleColorShader;
	}

	public void setTunaRippleInnerCircleColorShader(Shader tunaRippleInnerCircleColorShader){
		this.tunaRippleInnerCircleColorShader = tunaRippleInnerCircleColorShader;
	}

	// tunaRippleOuterCircleColorShader default null
	private Shader tunaRippleOuterCircleColorShader;

	public Shader getTunaRippleOuterCircleColorShader(){
		return tunaRippleOuterCircleColorShader;
	}

	public void setTunaRippleOuterCircleColorShader(Shader tunaRippleOuterCircleColorShader){
		this.tunaRippleOuterCircleColorShader = tunaRippleOuterCircleColorShader;
	}

	//
	private TimeInterpolator tunaRippleTimeInterpolator;

	public TimeInterpolator getTunaRippleTimeInterpolator(){
		return tunaRippleTimeInterpolator;
	}

	public void setTunaRippleTimeInterpolator(TimeInterpolator tunaRippleTimeInterpolator){
		this.tunaRippleTimeInterpolator = tunaRippleTimeInterpolator;
	}

	public enum TunaRippleTimeInterpolator {
		ACCELERATEDECELERATEINTERPOLATOR(0), ACCELERATEINTERPOLATOR(1), ANTICIPATEINTERPOLATOR(2), ANTICIPATEOVERSHOOTINTERPOLATOR(3), BOUNCEINTERPOLATOR(4), CYCLEINTERPOLATOR(5), DECELERATEINTERPOLATOR(
				6), LINEARINTERPOLATOR(7), OVERSHOOTINTERPOLATOR(8), ;
		final int nativeInt;

		TunaRippleTimeInterpolator(int ni){
			nativeInt = ni;
		}
	}

	//
	private static final TimeInterpolator[] tunaRippleTimeInterpolatorArray = { new AccelerateDecelerateInterpolator(), new AccelerateInterpolator(), new AnticipateInterpolator(),
			new AnticipateOvershootInterpolator(), new BounceInterpolator(), new CycleInterpolator(0), new DecelerateInterpolator(), new LinearInterpolator(),
			new OvershootInterpolator(), };

	public static TimeInterpolator[] getTunarippletimeinterpolatorarray(){
		return tunaRippleTimeInterpolatorArray;
	}

	//
	private float tunaRippleMaxRadius;
	private float tunaRippleDeltaRadius;
	private float tunaRippleAnimationCircleRadius;

	//
	private AnimatorSet tunaRippleAnimatorSet;

	public AnimatorSet getTunaRippleAnimatorSet(){
		return tunaRippleAnimatorSet;
	}

	public void setTunaRippleAnimatorSet(AnimatorSet tunaRippleAnimatorSet){
		this.tunaRippleAnimatorSet = tunaRippleAnimatorSet;
	}

	private Property<TRipple, Float> tunaRippleAnimationCircleRadiusProperty = new Property<TRipple, Float>(Float.class, "tunaRippleAnimationCircleRadiusProperty"){
		@Override
		public Float get(TRipple object){
			return object.tunaRippleAnimationCircleRadius;
		}

		@Override
		public void set(TRipple object, Float value){
			object.tunaRippleAnimationCircleRadius = value;
			// We need to draw three radius
			object.tunaRippleOuterCircleRadius = value;
			object.tunaRippleOuterDelayCircleRadius = value - tunaRippleDeltaRadius * 0.5f;
			object.tunaRippleOuterDeferCircleRadius = value - tunaRippleDeltaRadius * 1.0f;
			invalidate();
		}
	};

	public TRipple(Context context) {
		this(context, null);
	}

	public TRipple(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TRipple(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		tunaTag = TRipple.class.getSimpleName();

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TRipple);

		tunaRippleInnerCircleRadius = typedArray.getDimension(R.styleable.TRipple_rippleInnerCircleRadius, 0);
		tunaRippleInnerCircleColor = typedArray.getColor(R.styleable.TRipple_rippleInnerCircleColor, Color.TRANSPARENT);

		//
		tunaRippleAnimationCircleRadius = tunaRippleInnerCircleRadius;

		tunaRippleOuterCircleColor = typedArray.getColor(R.styleable.TRipple_rippleOuterCircleColor, tunaRippleInnerCircleColor);

		tunaRippleTextSize = typedArray.getDimension(R.styleable.TRipple_rippleTextSize, 0);
		tunaRippleTextColor = typedArray.getColor(R.styleable.TRipple_rippleTextColor, Color.TRANSPARENT);

		tunaRippleTextValue = typedArray.getString(R.styleable.TRipple_rippleTextValue);

		//
		tunaRippleTextDx = typedArray.getDimension(R.styleable.TRipple_rippleTextDx, 0);
		tunaRippleTextDy = typedArray.getDimension(R.styleable.TRipple_rippleTextDy, 0);
		tunaRippleTextFractionDx = typedArray.getFraction(R.styleable.TRipple_rippleTextFractionDx, 1, 1, 0);
		tunaRippleTextFractionDy = typedArray.getFraction(R.styleable.TRipple_rippleTextFractionDy, 1, 1, 0);
		
		//
		tunaRippleInnerCircleAngle = typedArray.getInt(R.styleable.TRipple_rippleInnerCircleAngle, Integer.MAX_VALUE);
		if (tunaRippleInnerCircleAngle != Integer.MAX_VALUE) {
			tunaRippleInnerCircleColorGradientStart = typedArray.getColor(R.styleable.TRipple_rippleInnerCircleColorGradientStart, tunaRippleInnerCircleColor);
			tunaRippleInnerCircleColorGradientEnd = typedArray.getColor(R.styleable.TRipple_rippleInnerCircleColorGradientEnd, tunaRippleInnerCircleColor);
		}

		tunaRippleOuterCircleAngle = typedArray.getInt(R.styleable.TRipple_rippleOuterCircleAngle, tunaRippleInnerCircleAngle);
		if (tunaRippleOuterCircleAngle != Integer.MAX_VALUE) {
			tunaRippleOuterCircleColorGradientStart = typedArray.getColor(R.styleable.TRipple_rippleOuterCircleColorGradientStart, tunaRippleInnerCircleColorGradientStart);
			tunaRippleOuterCircleColorGradientEnd = typedArray.getColor(R.styleable.TRipple_rippleOuterCircleColorGradientEnd, tunaRippleInnerCircleColorGradientEnd);
		}
		
		tunaRippleDuraction = typedArray.getInt(R.styleable.TRipple_rippleDuraction, 0);

		int tunaRippleTimeInterpolatorIndex = typedArray.getInt(R.styleable.TRipple_rippleTimeInterpolator, -1);
		if (tunaRippleTimeInterpolatorIndex > -1) {
			tunaRippleTimeInterpolator = tunaRippleTimeInterpolatorArray[tunaRippleTimeInterpolatorIndex];
		}
		typedArray.recycle();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom){
		super.onLayout(changed, left, top, right, bottom);

		tunaRippleMaxRadius = tunaWidth > tunaHeight ? tunaHeight >> 1 : tunaWidth >> 1;
		tunaRippleDeltaRadius = tunaRippleMaxRadius - tunaRippleInnerCircleRadius;

		if (tunaRippleInnerCircleRadius >= tunaRippleMaxRadius) {
			throw new IndexOutOfBoundsException("The content attribute tunaRippleInnerCircleRadius length must be less than half of the width or height");
		}

		tunaRippleTextDx += tunaWidth * tunaRippleTextFractionDx;
		tunaRippleTextDy += tunaHeight * tunaRippleTextFractionDy;

		if (tunaRippleInnerCircleAngle!=Integer.MAX_VALUE) {
			tunaRippleInnerCircleColorShader = getLinearGradient(tunaWidth,tunaHeight,tunaRippleInnerCircleAngle, tunaRippleInnerCircleColorGradientStart, tunaRippleInnerCircleColorGradientEnd);
		}
		
		if (tunaRippleOuterCircleAngle!=Integer.MAX_VALUE) {
			tunaRippleOuterCircleColorShader = getLinearGradient(tunaWidth,tunaHeight,tunaRippleOuterCircleAngle, tunaRippleOuterCircleColorGradientStart, tunaRippleOuterCircleColorGradientEnd);
		}
		
	}

	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);

		if (tunaRippleInnerCircleRadius > 0) {
			// The latter part of the judge to modify the contents added to initTunaPaint in
			canvas.drawCircle(tunaWidth >> 1, tunaHeight >> 1, tunaRippleInnerCircleRadius,
					initTunaPaint(Paint.Style.FILL, tunaRippleInnerCircleColor, tunaRippleInnerCircleColorShader));
		}

		// 255
		if (tunaRippleOuterCircleRadius > tunaRippleInnerCircleRadius && tunaRippleOuterCircleRadius < tunaRippleMaxRadius) {
			canvas.drawCircle(
					tunaWidth >> 1,
					tunaHeight >> 1,
					tunaRippleOuterCircleRadius,
					initTunaPaint(Paint.Style.FILL, tunaRippleOuterCircleColor, tunaRippleInnerCircleColorShader, (int) ((tunaRippleMaxRadius - tunaRippleOuterCircleRadius)
							/ tunaRippleDeltaRadius * 255)));
		}
		if (tunaRippleOuterDelayCircleRadius > tunaRippleInnerCircleRadius && tunaRippleOuterDelayCircleRadius < tunaRippleMaxRadius) {
			canvas.drawCircle(
					tunaWidth >> 1,
					tunaHeight >> 1,
					tunaRippleOuterDelayCircleRadius,
					initTunaPaint(Paint.Style.FILL, tunaRippleOuterCircleColor, tunaRippleOuterCircleColorShader, (int) ((tunaRippleMaxRadius - tunaRippleOuterDelayCircleRadius)
							/ tunaRippleDeltaRadius * 255)));
		}
		if (tunaRippleOuterDeferCircleRadius > tunaRippleInnerCircleRadius && tunaRippleOuterDeferCircleRadius < tunaRippleMaxRadius) {
			canvas.drawCircle(
					tunaWidth >> 1,
					tunaHeight >> 1,
					tunaRippleOuterDeferCircleRadius,
					initTunaPaint(Paint.Style.FILL, tunaRippleOuterCircleColor, tunaRippleOuterCircleColorShader, (int) ((tunaRippleMaxRadius - tunaRippleOuterDeferCircleRadius)
							/ tunaRippleDeltaRadius * 255)));
		}

		if (tunaRippleTextValue != null) {
			drawTunaText(canvas, tunaRippleTextValue, tunaWidth, (tunaWidth >> 1) + tunaRippleTextDx, (tunaHeight >> 1) + tunaRippleTextDy, 0, 0,
					initTunaTextPaint(Paint.Style.FILL, tunaRippleTextColor, tunaRippleTextSize, Align.CENTER));
		}
	}

	public void play(){
		tunaRippleAnimatorSet = new AnimatorSet();

		ObjectAnimator tunaRippleOuterCircleObjectAnimator = ObjectAnimator.ofFloat(this, tunaRippleAnimationCircleRadiusProperty, tunaRippleInnerCircleRadius,
				tunaRippleInnerCircleRadius + tunaRippleDeltaRadius * 3);

		tunaRippleOuterCircleObjectAnimator.setDuration(tunaRippleDuraction);
		tunaRippleAnimatorSet.playTogether(tunaRippleOuterCircleObjectAnimator);

		if (tunaRippleTimeInterpolator != null) {
			tunaRippleAnimatorSet.setInterpolator(tunaRippleTimeInterpolator);
		}
		tunaRippleAnimatorSet.start();
	}

}
