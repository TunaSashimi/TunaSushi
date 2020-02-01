package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import com.tuna.R;

import static com.tunasushi.tool.DeviceTool.applyDimension;
import static com.tunasushi.tool.DeviceTool.getViewDisplayMetrics;
import static com.tunasushi.tool.ViewTool.getLinearGradient;
import static com.tunasushi.tuna.TView.initTunaMatrix;


/**
 * @author Tunasashimi
 * @date 10/30/15 16:55
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TLayout extends RelativeLayout{

	protected String tunaLayoutTag = TLayout.class.getSimpleName();

	protected int tunaLayoutWidth, tunaLayoutHeight;
	protected int tunaLayoutLayer;
	protected Paint tunaLayoutPaint;
	//
	//
	protected Path tunaLayoutPath;
	
	protected Matrix tunaLayoutMatrix;
	protected RectF tunaLayoutRectF;
	
	// tunaLayoutBackgroundNormal in onLayout if tunaBackgroundNormal transparent and have drawn the case of default white shadow
	private int tunaLayoutBackgroundNormal;
	public int getTunaBackgroundNormal(){
		return tunaLayoutBackgroundNormal;
	}
	public void setTunaBackgroundNormal(int tunaLayoutBackgroundNormal){
		this.tunaLayoutBackgroundNormal = tunaLayoutBackgroundNormal;
	}
	
	private int tunaLayoutBackgroundNormalAngle;
	public int getTunaLayoutBackgroundNormalAngle(){
		return tunaLayoutBackgroundNormalAngle;
	}
	public void setTunaLayoutBackgroundNormalAngle(int tunaLayuotBackgroundNormalAngle){
		this.tunaLayoutBackgroundNormalAngle = tunaLayuotBackgroundNormalAngle;
	}
	
	// tunaLayoutBackgroundNormalGradientStart default tunaLayoutBackgroundNormal
	private int tunaLayoutBackgroundNormalGradientStart;
	public int getTunaLayoutBackgroundNormalGradientStart(){
		return tunaLayoutBackgroundNormalGradientStart;
	}
	public void setTunaBackgroundNormalGradientStart(int tunaLayoutBackgroundNormalGradientStart){
		this.tunaLayoutBackgroundNormalGradientStart = tunaLayoutBackgroundNormalGradientStart;
	}

	// tunaLayoutBackgroundNormalGradientEnd default tunaBackgroundNormal
	private int tunaLayoutBackgroundNormalGradientEnd;
	public int getTunaBackgroundNormalGradientEnd(){
		return tunaLayoutBackgroundNormalGradientEnd;
	}
	public void setTunaBackgroundNormalGradientEnd(int tunaLayoutBackgroundNormalGradientEnd){
		this.tunaLayoutBackgroundNormalGradientEnd = tunaLayoutBackgroundNormalGradientEnd;
	}
	
	// tunaLayoutBackgroundNormalShader default null
	private Shader tunaLayoutBackgroundNormalShader;
	public Shader getTunaLayoutBackgroundNormalShader(){
		return tunaLayoutBackgroundNormalShader;
	}
	public void setTunaLayoutBackgroundNormalShader(Shader tunaLayoutBackgroundNormalShader){
		this.tunaLayoutBackgroundNormalShader = tunaLayoutBackgroundNormalShader;
	}

	//
	private float tunaLayoutBackgroundNormalShadowRadius;
	public float getTunaLayoutBackgroundNormalShadowRadius(){
		return tunaLayoutBackgroundNormalShadowRadius;
	}
	public void setTunaLayoutBackgroundNormalShadowRadius(float tunaLayoutBackgroundNormalShadowRadius){
		setTunaLayoutBackgroundNormalShadowRadius(TypedValue.COMPLEX_UNIT_DIP, tunaLayoutBackgroundNormalShadowRadius);
	}
	public void setTunaLayoutBackgroundNormalShadowRadius(int unit, float tunaLayoutBackgroundNormalShadowRadius){
		setTunaLayoutBackgroundNormalShadowRadiusRaw(applyDimension(unit, tunaLayoutBackgroundNormalShadowRadius, getViewDisplayMetrics(this)));
	}
	private void setTunaLayoutBackgroundNormalShadowRadiusRaw(float tunaLayoutBackgroundNormalShadowRadius){
		if (this.tunaLayoutBackgroundNormalShadowRadius != tunaLayoutBackgroundNormalShadowRadius) {
			this.tunaLayoutBackgroundNormalShadowRadius = tunaLayoutBackgroundNormalShadowRadius;
			invalidate();
		}
	}
	
	//
	private int tunaLayoutBackgroundNormalShadowColor;
	public int getTunaLayoutBackgroundNormalShadowColor(){
		return tunaLayoutBackgroundNormalShadowColor;
	}
	public void setTunaLayoutBackgroundNormalShadowColor(int tunaLayoutBackgroundNormalShadowColor){
		this.tunaLayoutBackgroundNormalShadowColor = tunaLayoutBackgroundNormalShadowColor;
	}
	
	//
	private float tunaLayoutBackgroundNormalShadowDx;
	public float getTunaLayoutBackgroundNormalShadowDx(){
		return tunaLayoutBackgroundNormalShadowDx;
	}
	public void setTunaLayoutBackgroundNormalShadowDx(float tunaLayoutBackgroundNormalShadowDx){
		setTunaLayoutBackgroundNormalShadowDx(TypedValue.COMPLEX_UNIT_DIP, tunaLayoutBackgroundNormalShadowDx);
	}
	public void setTunaLayoutBackgroundNormalShadowDx(int unit, float tunaLayoutBackgroundNormalShadowDx){
		setTunaLayoutBackgroundNormalShadowDxRaw(applyDimension(unit, tunaLayoutBackgroundNormalShadowDx, getViewDisplayMetrics(this)));
	}
	private void setTunaLayoutBackgroundNormalShadowDxRaw(float tunaLayoutBackgroundNormalShadowDx){
		if (this.tunaLayoutBackgroundNormalShadowDx != tunaLayoutBackgroundNormalShadowDx) {
			this.tunaLayoutBackgroundNormalShadowDx = tunaLayoutBackgroundNormalShadowDx;
			invalidate();
		}
	}
	
	//
	private float tunaLayoutBackgroundNormalShadowDy;
	public float getTunaLayoutBackgroundNormalShadowDy(){
		return tunaLayoutBackgroundNormalShadowDy;
	}
	public void setTunaLayoutBackgroundNormalShadowDy(float tunaLayoutBackgroundNormalShadowDy){
		setTunaLayoutBackgroundNormalShadowDy(TypedValue.COMPLEX_UNIT_DIP, tunaLayoutBackgroundNormalShadowDy);
	}
	public void setTunaLayoutBackgroundNormalShadowDy(int unit, float tunaLayoutBackgroundNormalShadowDy){
		setTunaLayoutBackgroundNormalShadowDyRaw(applyDimension(unit, tunaLayoutBackgroundNormalShadowDy, getViewDisplayMetrics(this)));
	}
	private void setTunaLayoutBackgroundNormalShadowDyRaw(float tunaLayoutBackgroundNormalShadowDy){
		if (this.tunaLayoutBackgroundNormalShadowDy != tunaLayoutBackgroundNormalShadowDy) {
			this.tunaLayoutBackgroundNormalShadowDy = tunaLayoutBackgroundNormalShadowDy;
			invalidate();
		}
	}
	
	//
	private Bitmap tunaLayoutSrcNormal;
	public Bitmap getTunaLayoutBitmapSrcNormal(){
		return tunaLayoutSrcNormal;
	}
	public void setTunaLayoutBitmapSrcNormal(Bitmap tunaLayoutSrcNormal){
		this.tunaLayoutSrcNormal = tunaLayoutSrcNormal;
	}
	
	//
	private float tunaLayoutSrcNormalShadowRadius;
	public float getTunaLayoutBitmapSrcNormalShadowRadius(){
		return tunaLayoutSrcNormalShadowRadius;
	}
	public void setTunaLayoutBitmapSrcNormalShadowRadius(float tunaLayoutSrcNormalShadowRadius){
		setTunaLayoutBitmapSrcNormalShadowRadius(TypedValue.COMPLEX_UNIT_DIP, tunaLayoutSrcNormalShadowRadius);
	}
	public void setTunaLayoutBitmapSrcNormalShadowRadius(int unit, float tunaLayoutSrcNormalShadowRadius){
		setTunaLayoutBitmapSrcNormalShadowRadiusRaw(applyDimension(unit, tunaLayoutSrcNormalShadowRadius, getViewDisplayMetrics(this)));
	}
	private void setTunaLayoutBitmapSrcNormalShadowRadiusRaw(float tunaLayoutSrcNormalShadowRadius){
		if (this.tunaLayoutSrcNormalShadowRadius != tunaLayoutSrcNormalShadowRadius) {
			this.tunaLayoutSrcNormalShadowRadius = tunaLayoutSrcNormalShadowRadius;
			invalidate();
		}
	}
	
	//
	private float tunaLayoutSrcNormalShadowDx;
	public float getTunaLayoutBitmapSrcNormalShadowDx(){
		return tunaLayoutSrcNormalShadowDx;
	}
	public void setTunaLayoutBitmapSrcNormalShadowDx(float tunaLayoutSrcNormalShadowDx){
		setTunaLayoutBitmapSrcNormalShadowDx(TypedValue.COMPLEX_UNIT_DIP, tunaLayoutSrcNormalShadowDx);
	}
	public void setTunaLayoutBitmapSrcNormalShadowDx(int unit, float tunaLayoutSrcNormalShadowDx){
		setTunaLayoutBitmapSrcNormalShadowDxRaw(applyDimension(unit, tunaLayoutSrcNormalShadowDx, getViewDisplayMetrics(this)));
	}
	private void setTunaLayoutBitmapSrcNormalShadowDxRaw(float tunaLayoutSrcNormalShadowDx){
		if (this.tunaLayoutSrcNormalShadowDx != tunaLayoutSrcNormalShadowDx) {
			this.tunaLayoutSrcNormalShadowDx = tunaLayoutSrcNormalShadowDx;
			invalidate();
		}
	}
	
	//
	private float tunaLayoutSrcNormalShadowDy;
	public float getTunaLayoutBitmapSrcNormalShadowDy(){
		return tunaLayoutSrcNormalShadowDy;
	}
	public void setTunaLayoutBitmapSrcNormalShadowDy(float tunaLayoutSrcNormalShadowDy){
		setTunaLayoutBitmapSrcNormalShadowDy(TypedValue.COMPLEX_UNIT_DIP, tunaLayoutSrcNormalShadowDy);
	}
	public void setTunaLayoutBitmapSrcNormalShadowDy(int unit, float tunaLayoutSrcNormalShadowDy){
		setTunaLayoutBitmapSrcNormalShadowDyRaw(applyDimension(unit, tunaLayoutSrcNormalShadowDy, getViewDisplayMetrics(this)));
	}
	private void setTunaLayoutBitmapSrcNormalShadowDyRaw(float tunaLayoutSrcNormalShadowDy){
		if (this.tunaLayoutSrcNormalShadowDy != tunaLayoutSrcNormalShadowDy) {
			this.tunaLayoutSrcNormalShadowDy = tunaLayoutSrcNormalShadowDy;
			invalidate();
		}
	}
	
	// attention tunaLayoutPorterDuffXfermode default 0 instead of -1!
	protected PorterDuffXfermode tunaLayoutPorterDuffXfermode;
	public enum TunaLayoutPorterDuffXfermode {
		Layout_SRC_IN(0), Layout_SRC_OUT(1), ;
		final int nativeInt;
		TunaLayoutPorterDuffXfermode(int ni){
			nativeInt = ni;
		}
	}
	private static final Mode[] tunaLayoutPorterDuffXfermodeArray = { Mode.SRC_IN, Mode.SRC_OUT, };
	public PorterDuffXfermode getTunaLayoutPorterDuffXfermode(){
		return tunaLayoutPorterDuffXfermode;
	}
	public void setTunaLayoutPorterDuffXfermode(TunaLayoutPorterDuffXfermode tunaLayoutXfermode){
		this.tunaLayoutPorterDuffXfermode = new PorterDuffXfermode(tunaLayoutPorterDuffXfermodeArray[tunaLayoutXfermode.nativeInt]);
	}
	
	// tunaLayoutStrokeWidthNormal default 0
	private float tunaLayoutStrokeWidthNormal;
	public float getTunaLayoutStrokeWidthNormal(){
		return tunaLayoutStrokeWidthNormal;
	}
	public void setTunaLayoutStrokeWidthNormal(float tunaLayoutStrokeWidthNormal){
		setTunaLayoutStrokeWidthNormal(TypedValue.COMPLEX_UNIT_DIP, tunaLayoutStrokeWidthNormal);
	}
	public void setTunaLayoutStrokeWidthNormal(int unit, float tunaLayoutStrokeWidthNormal){
		setTunaLayoutStrokeWidthNormalRaw(applyDimension(unit, tunaLayoutStrokeWidthNormal, getViewDisplayMetrics(this)));
	}
	private void setTunaLayoutStrokeWidthNormalRaw(float tunaLayoutStrokeWidthNormal){
		if (this.tunaLayoutStrokeWidthNormal != tunaLayoutStrokeWidthNormal) {
			this.tunaLayoutStrokeWidthNormal = tunaLayoutStrokeWidthNormal;
			invalidate();
		}
	}
	
	// tunaLayoutStrokeColorNormal default transparent
	private int tunaLayoutStrokeColorNormal;
	public int getTunaLayoutStrokeColorNormal(){
		return tunaLayoutStrokeColorNormal;
	}
	public void setTunaLayoutStrokeColorNormal(int tunaLayoutStrokeColorNormal){
		this.tunaLayoutStrokeColorNormal = tunaLayoutStrokeColorNormal;
	}
	
	// tunaLayoutRadius default 0
	private float tunaLayoutRadius;
	public float getTunaLayoutRadius(){
		return tunaLayoutRadius;
	}
	public void setTunaLayoutRadius(float tunaLayoutRadius){
		setTunaLayoutRadius(TypedValue.COMPLEX_UNIT_DIP, tunaLayoutRadius);
	}
	public void setTunaLayoutRadius(int unit, float tunaLayoutRadius){
		setTunaLayoutRadiusRaw(applyDimension(unit, tunaLayoutRadius, getViewDisplayMetrics(this)));
	}
	private void setTunaLayoutRadiusRaw(float tunaLayoutRadius){
		if (this.tunaLayoutRadius != tunaLayoutRadius) {
			this.tunaLayoutRadius = tunaLayoutRadius;
			invalidate();
		}
	}
	
	// tunaLayoutRadiusLeftTop default 0
	private float tunaLayoutRadiusLeftTop;
	public float getTunaLayoutRadiusLeftTop(){
		return tunaLayoutRadiusLeftTop;
	}
	public void setTunaLayoutRadiusLeftTop(float tunaLayoutRadiusLeftTop){
		setTunaLayoutRadiusLeftTop(TypedValue.COMPLEX_UNIT_DIP, tunaLayoutRadiusLeftTop);
	}
	public void setTunaLayoutRadiusLeftTop(int unit, float tunaLayoutRadiusLeftTop){
		setTunaLayoutRadiusLeftTopRaw(applyDimension(unit, tunaLayoutRadiusLeftTop, getViewDisplayMetrics(this)));
	}
	private void setTunaLayoutRadiusLeftTopRaw(float tunaLayoutRadiusLeftTop){
		if (this.tunaLayoutRadiusLeftTop != tunaLayoutRadiusLeftTop) {
			this.tunaLayoutRadiusLeftTop = tunaLayoutRadiusLeftTop;
			invalidate();
		}
	}
	
	// tunaLayoutRadiusLeftBottom default 0
	private float tunaLayoutRadiusLeftBottom;
	public float getTunaLayoutRadiusLeftBottom(){
		return tunaLayoutRadiusLeftBottom;
	}
	public void setTunaLayoutRadiusLeftBottom(float tunaLayoutRadiusLeftBottom){
		setTunaLayoutRadiusLeftBottom(TypedValue.COMPLEX_UNIT_DIP, tunaLayoutRadiusLeftBottom);
	}
	public void setTunaLayoutRadiusLeftBottom(int unit, float tunaLayoutRadiusLeftBottom){
		setTunaLayoutRadiusLeftBottomRaw(applyDimension(unit, tunaLayoutRadiusLeftBottom, getViewDisplayMetrics(this)));
	}
	private void setTunaLayoutRadiusLeftBottomRaw(float tunaLayoutRadiusLeftBottom){
		if (this.tunaLayoutRadiusLeftBottom != tunaLayoutRadiusLeftBottom) {
			this.tunaLayoutRadiusLeftBottom = tunaLayoutRadiusLeftBottom;
			invalidate();
		}
	}
	
	// tunaLayoutRadiusRightTop default 0
	private float tunaLayoutRadiusRightTop;
	public float getTunaLayoutRadiusRightTop(){
		return tunaLayoutRadiusRightTop;
	}
	public void setTunaLayoutRadiusRightTop(float tunaLayoutRadiusRightTop){
		setTunaLayoutRadiusRightTop(TypedValue.COMPLEX_UNIT_DIP, tunaLayoutRadiusRightTop);
	}
	public void setTunaLayoutRadiusRightTop(int unit, float tunaLayoutRadiusRightTop){
		setTunaLayoutRadiusRightTopRaw(applyDimension(unit, tunaLayoutRadiusRightTop, getViewDisplayMetrics(this)));
	}
	private void setTunaLayoutRadiusRightTopRaw(float tunaLayoutRadiusRightTop){
		if (this.tunaLayoutRadiusRightTop != tunaLayoutRadiusRightTop) {
			this.tunaLayoutRadiusRightTop = tunaLayoutRadiusRightTop;
			invalidate();
		}
	}
	
	// tunaLayoutRadiusRightBottom default 0
	private float tunaLayoutRadiusRightBottom;
	public float getTunaLayoutRadiusRightBottom(){
		return tunaLayoutRadiusRightBottom;
	}
	public void setTunaLayoutRadiusRightBottom(float tunaLayoutRadiusRightBottom){
		setTunaLayoutRadiusRightBottom(TypedValue.COMPLEX_UNIT_DIP, tunaLayoutRadiusRightBottom);
	}
	public void setTunaLayoutRadiusRightBottom(int unit, float tunaLayoutRadiusRightBottom){
		setTunaLayoutRadiusRightBottomRaw(applyDimension(unit, tunaLayoutRadiusRightBottom, getViewDisplayMetrics(this)));
	}
	private void setTunaLayoutRadiusRightBottomRaw(float tunaLayoutRadiusRightBottom){
		if (this.tunaLayoutRadiusRightBottom != tunaLayoutRadiusRightBottom) {
			this.tunaLayoutRadiusRightBottom = tunaLayoutRadiusRightBottom;
			invalidate();
		}
	}
	
	public TLayout(Context context){
		this(context, null);
	}

	public TLayout(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}

	public TLayout(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		
		//By ViewGroup default, it is set to WILL_NOT_DRAW, which is from a performance consideration, so that, onDraw will not be called. 
		//If we want a ViweGroup important onDraw method, there are two methods: 
		//1, in the constructor function to set a color, such as # 00000000
		//.2, in the constructor function, call setWillNotDraw (false), remove it WILL_NOT_DRAW flag.
		
		setWillNotDraw(false);
		
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TLayout, 0, defStyle);
		
		tunaLayoutBackgroundNormal = typedArray.getColor(R.styleable.TLayout_layoutBackgroundNormal, Color.TRANSPARENT);
		
		//
		tunaLayoutBackgroundNormalAngle = typedArray.getInt(R.styleable.TLayout_layoutBackgroundNormalAngle, Integer.MAX_VALUE);
		if (tunaLayoutBackgroundNormalAngle != Integer.MAX_VALUE) {
			tunaLayoutBackgroundNormalGradientStart = typedArray.getColor(R.styleable.TLayout_layoutBackgroundNormalGradientStart, tunaLayoutBackgroundNormal);
			tunaLayoutBackgroundNormalGradientEnd = typedArray.getColor(R.styleable.TLayout_layoutBackgroundNormalGradientEnd, tunaLayoutBackgroundNormal);
			
		}
		
		//
		tunaLayoutBackgroundNormalShadowRadius = typedArray.getDimension(R.styleable.TLayout_layoutBackgroundNormalShadowRadius, 0);
		if (tunaLayoutBackgroundNormalShadowRadius > 0) {
			tunaLayoutBackgroundNormalShadowColor = typedArray.getColor(R.styleable.TLayout_layoutBackgroundNormalShadowColor, Color.TRANSPARENT);
			tunaLayoutBackgroundNormalShadowDx = typedArray.getDimension(R.styleable.TLayout_layoutBackgroundNormalShadowDx, 0);
			tunaLayoutBackgroundNormalShadowDy = typedArray.getDimension(R.styleable.TLayout_layoutBackgroundNormalShadowDy, 0);
		}
		
		//
		int tunaLayoutSrcNormalId = typedArray.getResourceId(R.styleable.TLayout_layoutSrcNormal, -1);
		if (tunaLayoutSrcNormalId != -1) {

			// tunaLayoutXfermodeIndex default PorterDuff.Mode.SRC_IN
			int tunaLayoutXfermodeIndex = typedArray.getInt(R.styleable.TView_porterDuffXfermode, 0);
			tunaLayoutPorterDuffXfermode = new PorterDuffXfermode(tunaLayoutPorterDuffXfermodeArray[tunaLayoutXfermodeIndex]);

			//
			tunaLayoutSrcNormal = BitmapFactory.decodeResource(getResources(), tunaLayoutSrcNormalId);

			//
			tunaLayoutSrcNormalShadowRadius = typedArray.getDimension(R.styleable.TLayout_layoutSrcNormalShadowRadius, 0);
			if (tunaLayoutSrcNormalShadowRadius > 0) {
				tunaLayoutSrcNormalShadowDx = typedArray.getDimension(R.styleable.TLayout_layoutSrcNormalShadowDx, 0);
				tunaLayoutSrcNormalShadowDy = typedArray.getDimension(R.styleable.TLayout_layoutSrcNormalShadowDy, 0);
			}
		}

		//
		tunaLayoutStrokeWidthNormal = typedArray.getDimension(R.styleable.TLayout_layoutStrokeWidthNormal, 0);
		tunaLayoutStrokeColorNormal = typedArray.getColor(R.styleable.TLayout_layoutStrokeColorNormal, Color.TRANSPARENT);
		
		//
		tunaLayoutRadius = typedArray.getDimension(R.styleable.TLayout_layoutRadius, 0);
		tunaLayoutRadiusLeftTop = typedArray.getDimension(R.styleable.TLayout_layoutRadiusLeftTop, tunaLayoutRadius);
		tunaLayoutRadiusLeftBottom = typedArray.getDimension(R.styleable.TLayout_layoutRadiusLeftBottom, tunaLayoutRadius);
		tunaLayoutRadiusRightTop = typedArray.getDimension(R.styleable.TLayout_layoutRadiusRightTop, tunaLayoutRadius);
		tunaLayoutRadiusRightBottom = typedArray.getDimension(R.styleable.TLayout_layoutRadiusRightBottom, tunaLayoutRadius);
		
		typedArray.recycle();

	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b){
		super.onLayout(changed, l, t, r, b);
		
		tunaLayoutWidth = getWidth();
		tunaLayoutHeight = getHeight();
		
		if (tunaLayoutSrcNormal != null || tunaLayoutSrcNormalShadowRadius > 0  || tunaLayoutBackgroundNormalShadowRadius > 0 || tunaLayoutBackgroundNormalAngle != Integer.MAX_VALUE ) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		
		if ((tunaLayoutSrcNormal != null || tunaLayoutBackgroundNormalShadowRadius > 0 ) && tunaLayoutBackgroundNormal == Color.TRANSPARENT) {
			tunaLayoutBackgroundNormal = Color.WHITE;
		}
		
		//
		if (tunaLayoutBackgroundNormalAngle != Integer.MAX_VALUE) {
			tunaLayoutBackgroundNormalShader = getLinearGradient(tunaLayoutWidth, tunaLayoutHeight, tunaLayoutBackgroundNormalAngle, tunaLayoutBackgroundNormalGradientStart, tunaLayoutBackgroundNormalGradientEnd);
		}
		
		//
		int tunaLayoutSrcNormalWidthRaw = 0, tunaLayoutSrcNormalHeightRaw = 0;
		if (tunaLayoutSrcNormal != null) {
			tunaLayoutSrcNormalWidthRaw = tunaLayoutSrcNormal.getWidth();
			tunaLayoutSrcNormalHeightRaw = tunaLayoutSrcNormal.getHeight();

			initTunaMatrix(tunaLayoutMatrix,(tunaLayoutWidth - tunaLayoutSrcNormalShadowRadius * 2f - tunaLayoutBackgroundNormalShadowRadius * 2f - tunaLayoutBackgroundNormalShadowDx * 2f) / tunaLayoutSrcNormalWidthRaw,
					(tunaLayoutHeight - tunaLayoutSrcNormalShadowRadius * 2f - tunaLayoutBackgroundNormalShadowRadius * 2f - tunaLayoutBackgroundNormalShadowDy * 2f) / tunaLayoutSrcNormalHeightRaw);
		}

	}
	
	@Override
	protected void onDraw(Canvas canvas){
		
		boolean needSaveLayer = (tunaLayoutSrcNormal != null );
		if (needSaveLayer) {
			// draw the src/dst example into our offscreen bitmap
			tunaLayoutLayer = canvas.saveLayer(0, 0, tunaLayoutWidth, tunaLayoutHeight, null);
		}
//		
		drawTunaRectCustom(canvas, tunaLayoutBackgroundNormalShadowRadius + tunaLayoutBackgroundNormalShadowDx, tunaLayoutBackgroundNormalShadowRadius + tunaLayoutBackgroundNormalShadowDy,
				tunaLayoutWidth - tunaLayoutBackgroundNormalShadowRadius - tunaLayoutBackgroundNormalShadowDx, tunaLayoutHeight - tunaLayoutBackgroundNormalShadowRadius
						- tunaLayoutBackgroundNormalShadowDy,  tunaLayoutBackgroundNormal,
				tunaLayoutBackgroundNormalShader,
				tunaLayoutBackgroundNormalShadowRadius,
				tunaLayoutBackgroundNormalShadowColor,
				tunaLayoutBackgroundNormalShadowDx,
				tunaLayoutBackgroundNormalShadowDy, 
				tunaLayoutStrokeWidthNormal, 
				tunaLayoutStrokeColorNormal, tunaLayoutRadiusLeftTop, tunaLayoutRadiusLeftBottom, tunaLayoutRadiusRightTop, tunaLayoutRadiusRightBottom);
		
		// draw tunaLayoutBitmap
		if (needSaveLayer) {
			tunaLayoutPaint.setXfermode(tunaLayoutPorterDuffXfermode);

			canvas.translate(tunaLayoutBackgroundNormalShadowDx * 2f + tunaLayoutSrcNormalShadowRadius - tunaLayoutSrcNormalShadowDx,
					tunaLayoutBackgroundNormalShadowDy * 2f + tunaLayoutSrcNormalShadowRadius - tunaLayoutSrcNormalShadowDy);
			canvas.drawBitmap(
					tunaLayoutSrcNormal,
					tunaLayoutMatrix,
					initTunaPaint(tunaLayoutPaint, tunaLayoutSrcNormalShadowRadius,
							tunaLayoutSrcNormalShadowDx, tunaLayoutSrcNormalShadowDy));
			canvas.translate( -tunaLayoutBackgroundNormalShadowDx * 2f - tunaLayoutSrcNormalShadowRadius + tunaLayoutSrcNormalShadowDx,
					 -tunaLayoutBackgroundNormalShadowDy * 2f - tunaLayoutSrcNormalShadowRadius + tunaLayoutSrcNormalShadowDy);

			tunaLayoutPaint.setXfermode(null);
			
		}
	}
	
	
	// 10
	protected void drawTunaRectCustom(Canvas canvas, int width, int height, int fillColor, float strokeWidth, int strokeColor, float radiusLeftTop, float radiusLeftBottom,
			float radiusRightTop, float radiusRightBottom){

		drawTunaRectCustom(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radiusLeftTop, radiusLeftBottom, radiusRightTop,
				radiusRightBottom);
	}

	// 10
	protected void drawTunaRectCustom(Canvas canvas, int width, int height, Shader shader, float strokeWidth, int strokeColor, float radiusLeftTop, float radiusLeftBottom,
			float radiusRightTop, float radiusRightBottom){

		drawTunaRectCustom(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radiusLeftTop, radiusLeftBottom,
				radiusRightTop, radiusRightBottom);
	}

	// 14
	protected void drawTunaRectCustom(Canvas canvas, int width, int height, int fillColor, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
			int strokeColor, float radiusLeftTop, float radiusLeftBottom, float radiusRightTop, float radiusRightBottom){

		drawTunaRectCustom(canvas, 0, 0, width, height, fillColor, null, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radiusLeftTop, radiusLeftBottom,
				radiusRightTop, radiusRightBottom);
	}

	// 14
	protected void drawTunaRectCustom(Canvas canvas, int width, int height, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
			int strokeColor, float radiusLeftTop, float radiusLeftBottom, float radiusRightTop, float radiusRightBottom){

		drawTunaRectCustom(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radiusLeftTop,
				radiusLeftBottom, radiusRightTop, radiusRightBottom);
	}

	// 15
	protected  void drawTunaRectCustom(Canvas canvas, float left, float top, float right, float bottom, int fillColor, Shader shader, float shadowRadius, int shadowColor,
			float shadowDx, float shadowDy, float strokeWidth, int strokeColor, float radiusLeftTop, float radiusLeftBottom, float radiusRightTop, float radiusRightBottom){

		float[] radii = { radiusLeftTop, radiusLeftTop, radiusRightTop, radiusRightTop, radiusRightBottom, radiusRightBottom, radiusLeftBottom, radiusLeftBottom };
		if (strokeWidth > 0) {
			canvas.drawPath(
					initTunaPathRoundRect(initTunaRectF(left + strokeWidth * 0.5f, top + strokeWidth * 0.5f, right - strokeWidth * 0.5f, bottom - strokeWidth * 0.5f), radii,
							Direction.CW), initTunaPaint(Style.STROKE, strokeColor, strokeWidth));
		}

		int radiiLength = radii.length;
		for (int i = 0; i < radiiLength; i++) {
			radii[i] -= strokeWidth;
			radii[i] = radii[i] >= 0 ? radii[i] : 0;
		}

		canvas.drawPath(
				initTunaPathRoundRect(initTunaRectF(left + strokeWidth, top + strokeWidth, right - strokeWidth, bottom - strokeWidth), radii, Direction.CW),
				shader == null ? shadowRadius == 0 ? initTunaPaint(Style.FILL, fillColor) : initTunaPaint(Style.FILL, fillColor, shadowRadius, shadowColor, shadowDx,
						shadowDy) : shadowRadius == 0 ? initTunaPaint(Style.FILL, shader) : initTunaPaint(Style.FILL, shader, shadowRadius, shadowColor, shadowDx,
						shadowDy));
	}
	
	//
	protected RectF initTunaRectF(float left, float top, float right, float bottom){
		if (tunaLayoutRectF == null) {
			tunaLayoutRectF = new RectF(left, top, right, bottom);
		}
		tunaLayoutRectF.set(left, top, right, bottom);
		return tunaLayoutRectF;
	}
	
	//
	// 0
	protected Paint initTunaPaint(){
		if (tunaLayoutPaint == null) {
			return tunaLayoutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		} else {
			tunaLayoutPaint.reset();

			tunaLayoutPaint.setShader(null);
			tunaLayoutPaint.clearShadowLayer();

			tunaLayoutPaint.setAntiAlias(true);
		}
		return tunaLayoutPaint;
	}

	// 1
	protected Paint initTunaPaint(int alpha){
		return initTunaPaint(null, 0, Color.TRANSPARENT, null, 0, Color.TRANSPARENT, 0, 0, alpha);
	}

	// 2
	protected Paint initTunaPaint(Style style, int colorValue){
		return initTunaPaint(style, 0, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);

	}

	// 3
	protected Paint initTunaPaint(Style style, int colorValue, int alpha){
		return initTunaPaint(style, 0, colorValue, null, 0, Color.TRANSPARENT, 0, 0, alpha);
	}
	
	// 4
	protected Paint initTunaPaint(Style style, int colorValue, Shader shader,int alpha){
		return initTunaPaint(style, 0, colorValue, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
	}
	
	// 3
	protected Paint initTunaPaint(Style style, int colorValue, Shader shader){
		return initTunaPaint(style, 0, colorValue, shader, 0, Color.TRANSPARENT, 0, 0, -1);
	}
	
	// 2
	protected Paint initTunaPaint(Style style, Shader shader){
		return initTunaPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, -1);
	}
	
	// 3
	protected Paint initTunaPaint(Style style, Shader shader,int alpha){
		return initTunaPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
	}


	// 3
	protected Paint initTunaPaint(Style style, int colorValue, float strokeWidth){
		return initTunaPaint(style, strokeWidth, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
	}

	// 4
	protected Paint initTunaPaint(Style style, int colorValue, float strokeWidth, int alpha){
		return initTunaPaint(style, strokeWidth, colorValue, null, 0, Color.TRANSPARENT, 0, 0, alpha);
	}

	// 6
	protected Paint initTunaPaint(Style style, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy){
		return initTunaPaint(style, 0, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
	}

	// 6
	protected Paint initTunaPaint(Style style, int colorValue, float shadowRadius, int shadowColor, float shadowDx, float shadowDy){
		return initTunaPaint(style, 0, colorValue, null, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
	}

	// 9
	protected Paint initTunaPaint(Style style, float strokeWidth, int colorValue, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, int alpha){
		//
		initTunaPaint();
		//
		if (style != null) {
			tunaLayoutPaint.setStyle(style);
		}
		if (strokeWidth != 0) {
			tunaLayoutPaint.setStrokeWidth(strokeWidth);
		}

		// When the shadow color can not be set to transparent, but can not set
		if (shader == null) {
			tunaLayoutPaint.setColor(colorValue);
		} else {
			tunaLayoutPaint.setShader(shader);
		}

		if (shadowRadius != 0) {
			tunaLayoutPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
		}
		if (alpha != -1) {
			tunaLayoutPaint.setAlpha(alpha);
		}
		return tunaLayoutPaint;
	}

	// 1
	protected Paint initTunaPaint(float textSize){
		return initTunaPaint(null, Color.TRANSPARENT, textSize, 0, Color.TRANSPARENT, 0, 0, null);
	}

	// 4
	protected Paint initTunaPaint(Style style, int colorValue, float textSize, Align align){
		return initTunaPaint(style, colorValue, textSize, 0, Color.TRANSPARENT, 0, 0, align);
	}

	// 8
	protected Paint initTunaPaint(Style style, int colorValue, float textSize, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, Align align){
		//
		initTunaPaint();
		//
		if (style != null) {
			tunaLayoutPaint.setStyle(style);
		}

		tunaLayoutPaint.setColor(colorValue);

		if (textSize != 0) {
			tunaLayoutPaint.setTextSize(textSize);
		}
		if (shadowRadius != 0) {
			tunaLayoutPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
		}
		if (align != null) {
			tunaLayoutPaint.setTextAlign(align);
		}
		return tunaLayoutPaint;
	}

	// 4
	protected Paint initTunaPaint(Paint paint, float shadowRadius, float shadowDx, float shadowDy){
		paint.clearShadowLayer();
		if (shadowRadius > 0) {
			paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, Color.WHITE);
		}
		return paint;
	}

	protected Path initTunaPathRoundRect(RectF rect, float[] radii, Direction dir){
		if (tunaLayoutPath == null) {
			tunaLayoutPath = new Path();
		} else {
			tunaLayoutPath.reset();
		}
		// This setting will cause the following message appears reading xml
		// The graphics preview in the layout editor may not be accurate:
		// Different corner sizes are not supported in Path.addRoundRect.
		// (Ignore for this session)
		tunaLayoutPath.addRoundRect(rect, radii, dir);
		return tunaLayoutPath;
	}
	
}
