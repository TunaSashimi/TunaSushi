package com.tunasushi.view

import android.content.Context
import kotlin.jvm.JvmOverloads
import android.graphics.Typeface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.tunasushi.R
import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:52
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TDialog @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : TView(context, attrs, defStyle) {
    private val dialogWidth: Float
    private val dialogHeight: Float
    private val dialogBackground: Int
    private val dialogSurround: Int
    private val dialogStrokeWidth: Float
    private val dialogStrokeColor: Int
    private val dialogText: String?
    private val dialogTextSize: Float
    private val dialogTextColor: Int
    private val dialogTextDy: Float

    //
    var dialogTextStyle: Typeface? = null

    //
    private val dialogContent: String?
    private val dialogContentSize: Float
    private val dialogContentColor: Int
    private val dialogContentDy: Float

    //
    var dialogContentStyle: Typeface? = null
    private val dialogContentPaddingLeft: Float
    private val dialogContentPaddingRight: Float
    private val dialogChoiceBackgroundNormal: Int
    private val dialogChoiceBackgroundPress: Int
    private val dialogChoiceHeight: Float
    var dialogChoiceTextArray: Array<String>
    private val dialogChoiceSize: Float
    private var dialogChoiceColor = 0

    //Because there are only string-array and integer-array, it cannot be defined in xml
    var dialogChoiceColorArray: IntArray
    var dialogChoiceIndex: Int

    //
    private var dialogRadiusBottomLeft = 0f
    private var dialogRadiusBottomRight = 0f
    private var dialogChoiceLeft = 0
    private var dialogChoiceTop = 0
    private var dialogChoiceRight = 0
    private var dialogChoiceBottom = 0
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        //
        dx = (width - dialogWidth) / 2
        dy = (height - dialogHeight) / 2

        //
        share = dialogWidth / total
        //floatArray means dialogChoiceArrayCenterX
        dialogChoiceTop = (dialogHeight - dialogChoiceHeight + dialogStrokeWidth * 0.5f).toInt()
        dialogChoiceBottom = (dialogHeight - dialogStrokeWidth).toInt()
        for (i in 0 until total) {
            floatArray[i] = share * (i + 0.5f) + dx
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (dialogSurround != Color.TRANSPARENT) {
            canvas.drawColor(dialogSurround)
        }
        canvas.translate(dx, dy)

        //
        drawRectClassic(
            canvas,
            dialogWidth,
            dialogHeight,
            dialogBackground,
            dialogStrokeWidth,
            radius
        )

        //
        drawRectClassicStroke(
            canvas,
            dialogWidth,
            dialogHeight,
            dialogStrokeWidth,
            dialogStrokeColor,
            radius
        )

        //drawDialogText
        drawText(
            canvas,
            dialogText,
            dialogWidth,
            dialogWidth / 2,
            dialogTextSize * 0.5f + dialogTextDy, 0f, 0f,
            initTextPaint(
                Paint.Style.FILL,
                dialogTextColor,
                dialogTextSize,
                dialogTextStyle,
                Paint.Align.CENTER
            )
        )

        //drawDialogContent
        drawText(
            canvas,
            dialogContent,
            dialogWidth,
            dialogWidth / 2,
            dialogHeight / 2 + dialogContentDy,
            dialogContentPaddingLeft, dialogContentPaddingRight,
            initTextPaint(
                Paint.Style.FILL,
                dialogContentColor,
                dialogContentSize,
                dialogContentStyle,
                Paint.Align.CENTER
            )
        )

        //draw Choice
        for (i in 0 until total) {
            //Only one
            if (i == 0 && i == total - 1) {
                //draw choice separate line horizontal
                canvas.drawLine(
                    0f,
                    dialogHeight - dialogChoiceHeight,
                    dialogWidth,
                    dialogHeight - dialogChoiceHeight,
                    initPaint(Paint.Style.FILL, dialogStrokeColor, dialogStrokeWidth)
                )
                dialogRadiusBottomLeft = radius
                dialogRadiusBottomRight = radius
                dialogChoiceLeft = (share * i + dialogStrokeWidth).toInt()
                dialogChoiceRight = (share * (i + 1) - dialogStrokeWidth).toInt()

                //Left
            } else if (i == 0) {
                //draw choice separate line horizontal
                canvas.drawLine(
                    0f,
                    dialogHeight - dialogChoiceHeight,
                    dialogWidth,
                    dialogHeight - dialogChoiceHeight,
                    initPaint(Paint.Style.FILL, dialogStrokeColor, dialogStrokeWidth)
                )
                dialogRadiusBottomLeft = radius
                dialogRadiusBottomRight = 0f
                dialogChoiceLeft = (share * i + dialogStrokeWidth).toInt()
                dialogChoiceRight = (share * (i + 1) - dialogStrokeWidth / 2).toInt()

                //Right
            } else if (i == total - 1) {
                //draw choice separate line vertical
                canvas.drawLine(
                    share * i, dialogHeight - dialogChoiceHeight, share * i, dialogHeight,
                    initPaint(Paint.Style.FILL, dialogStrokeColor, dialogStrokeWidth)
                )
                dialogRadiusBottomLeft = 0f
                dialogRadiusBottomRight = radius
                dialogChoiceLeft = (share * i + dialogStrokeWidth / 2).toInt()
                dialogChoiceRight = (share * (i + 1) - dialogStrokeWidth).toInt()

                //Center
            } else {
                //draw choice separate line vertical
                canvas.drawLine(
                    share * i, dialogHeight - dialogChoiceHeight, share * i, dialogHeight,
                    initPaint(Paint.Style.FILL, dialogStrokeColor, dialogStrokeWidth)
                )
                dialogRadiusBottomLeft = 0f
                dialogRadiusBottomRight = 0f
                dialogChoiceLeft = (share * i + dialogStrokeWidth / 2).toInt()
                dialogChoiceRight = (share * (i + 1) - dialogStrokeWidth / 2).toInt()
            }

            //
            drawRectCustom(
                canvas,
                dialogChoiceLeft,
                dialogChoiceTop,
                dialogChoiceRight,
                dialogChoiceBottom,
                if (i == dialogChoiceIndex) if (isPress) dialogChoiceBackgroundPress else dialogChoiceBackgroundNormal else dialogChoiceBackgroundNormal,
                0f,
                dialogRadiusBottomLeft,
                0f,
                dialogRadiusBottomRight
            )

            //
            drawText(
                canvas,
                dialogChoiceTextArray[i],
                dialogWidth,
                share * (i + 0.5f),
                dialogHeight - dialogChoiceHeight * 0.5f, 0f, 0f,
                initTextPaint(
                    Paint.Style.FILL,
                    dialogChoiceColorArray[i],
                    dialogChoiceSize,
                    Paint.Align.CENTER
                )
            )
        }
        canvas.translate(-dx, -dy)
    }

    override fun setTouchXYRaw(touchX: Float, touchY: Float) {
        x = touchX
        y = touchY
        if (y >= dialogHeight - dialogChoiceHeight + dy && y <= dialogHeight - dialogChoiceHeight + dy + dialogChoiceHeight && x >= dx && x <= width - dx) {
            var distenceMin = dialogWidth
            for (i in 0 until total) {
                val centreDistance = Math.abs(x - floatArray[i])
                if (centreDistance < distenceMin) {
                    dialogChoiceIndex = i
                    distenceMin = centreDistance
                } else {
                    break
                }
            }
        } else {
            dialogChoiceIndex = -1
        }
        invalidate()
    }

    companion object {
        private val dialogTextStyleArray =
            intArrayOf(Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC)
        private val dialogContentStyleArray =
            intArrayOf(Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC)
    }

    init {
        tag = TDialog::class.java.simpleName
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TDialog)
        dialogBackground =
            typedArray.getColor(R.styleable.TDialog_dialogBackground, Color.TRANSPARENT)
        dialogSurround = typedArray.getColor(R.styleable.TDialog_dialogSurround, Color.TRANSPARENT)
        dialogWidth = typedArray.getDimension(R.styleable.TDialog_dialogWidth, 0f)
        require(dialogWidth > 0) { "The content attribute dialogWidth length must be greater than 0" }
        dialogHeight = typedArray.getDimension(R.styleable.TDialog_dialogHeight, 0f)
        require(dialogHeight > 0) { "The content attribute dialogHeight length must be greater than 0" }
        dialogStrokeWidth = typedArray.getDimension(R.styleable.TDialog_dialogStrokeWidth, 0f)
        dialogStrokeColor =
            typedArray.getColor(R.styleable.TDialog_dialogStrokeColor, Color.TRANSPARENT)
        dialogText = typedArray.getString(R.styleable.TDialog_dialogText)
        dialogTextSize =
            typedArray.getDimension(R.styleable.TDialog_dialogTextSize, textSizeDefault)
        dialogTextColor = typedArray.getColor(R.styleable.TDialog_dialogTextColor, textColorDefault)
        dialogTextDy = typedArray.getDimension(R.styleable.TDialog_dialogTextDy, 0f)
        val dialogTextStyleIndex = typedArray.getInt(R.styleable.TDialog_dialogTextStyle, 0)
        if (dialogTextStyleIndex >= 0) {
            dialogTextStyle =
                Typeface.create(Typeface.DEFAULT, dialogTextStyleArray[dialogTextStyleIndex])
        }
        dialogContent = typedArray.getString(R.styleable.TDialog_dialogContent)
        dialogContentSize =
            typedArray.getDimension(R.styleable.TDialog_dialogContentSize, textSizeDefault)
        dialogContentColor =
            typedArray.getColor(R.styleable.TDialog_dialogContentColor, textColorDefault)
        dialogContentDy = typedArray.getDimension(R.styleable.TDialog_dialogContentDy, 0f)
        val dialogContentStyleIndex = typedArray.getInt(R.styleable.TDialog_dialogContentStyle, 0)
        if (dialogContentStyleIndex >= 0) {
            dialogContentStyle =
                Typeface.create(Typeface.DEFAULT, dialogContentStyleArray[dialogContentStyleIndex])
        }
        dialogContentPaddingLeft =
            typedArray.getDimension(R.styleable.TDialog_dialogContentPaddingLeft, 0f)
        dialogContentPaddingRight =
            typedArray.getDimension(R.styleable.TDialog_dialogContentPaddingRight, 0f)
        dialogChoiceBackgroundNormal =
            typedArray.getColor(R.styleable.TDialog_dialogChoiceBackgroundNormal, Color.TRANSPARENT)
        dialogChoiceBackgroundPress = typedArray.getColor(
            R.styleable.TDialog_dialogChoiceBackgroundPress,
            dialogChoiceBackgroundNormal
        )
        val dialogChoiceTextArrayId =
            typedArray.getResourceId(R.styleable.TDialog_dialogChoiceValueArray, -1)
        if (dialogChoiceTextArrayId != -1) {
            dialogChoiceTextArray = typedArray.resources.getStringArray(dialogChoiceTextArrayId)
            total = dialogChoiceTextArray.size
            floatArray = FloatArray(total)
        } else {
            throw IllegalArgumentException("The content attribute require a property named dialogChoiceTextArray")
        }
        dialogChoiceHeight = typedArray.getDimension(R.styleable.TDialog_dialogChoiceHeight, 0f)
        if (dialogChoiceHeight <= 0) {
            throw IndexOutOfBoundsException("The content attribute dialogChoiceHeight length must be greater than 0")
        }

        //
        dialogChoiceSize =
            typedArray.getDimension(R.styleable.TDialog_dialogChoiceSize, textSizeDefault)

        // If dialogChoiceColorArray is set then dialogChoiceColor will be replaced!
        val dialogChoiceColorArrayId =
            typedArray.getResourceId(R.styleable.TDialog_dialogChoiceColorArray, -1)
        if (dialogChoiceColorArrayId != -1) {
            dialogChoiceColorArray = typedArray.resources.getIntArray(dialogChoiceColorArrayId)
        } else {
            dialogChoiceColor =
                typedArray.getColor(R.styleable.TDialog_dialogChoiceColor, textColorDefault)
            dialogChoiceColorArray = IntArray(total)
            for (i in 0 until total) {
                dialogChoiceColorArray[i] = dialogChoiceColor
            }
        }

        //
        dialogChoiceIndex = typedArray.getInt(R.styleable.TDialog_dialogChoiceIndex, -1)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        typedArray.recycle()
    }
}