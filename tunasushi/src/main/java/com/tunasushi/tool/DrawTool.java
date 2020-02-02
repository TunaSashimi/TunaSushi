package com.tunasushi.tool;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;

import com.tunasushi.tuna.TView;

import java.util.List;

import static com.tunasushi.tool.PathTool.initPathRoundRect;
import static com.tunasushi.tuna.TView.generateMeasureList;
import static com.tunasushi.tuna.TView.initRectF;

/**
 * @author Tunasashimi
 * @date 2020-02-03 00:10
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class DrawTool {
    // 8
    public static void drawRectCustom(Canvas canvas, int width, int height, int fillColor, float radiusLeftTop, float radiusLeftBottom,
                                  float radiusRightTop, float radiusRightBottom) {

        drawRectCustom(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, 0, Color.TRANSPARENT, radiusLeftTop, radiusLeftBottom, radiusRightTop,
                radiusRightBottom);
    }

    // 10
    public static void drawRectCustom(Canvas canvas, int width, int height, int fillColor, float strokeWidth, int strokeColor, float radiusLeftTop, float radiusLeftBottom,
                                  float radiusRightTop, float radiusRightBottom) {

        drawRectCustom(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radiusLeftTop, radiusLeftBottom, radiusRightTop,
                radiusRightBottom);
    }

    // 10
    public static void drawRectCustom(Canvas canvas, int width, int height, Shader shader, float strokeWidth, int strokeColor, float radiusLeftTop, float radiusLeftBottom,
                                  float radiusRightTop, float radiusRightBottom) {

        drawRectCustom(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radiusLeftTop, radiusLeftBottom,
                radiusRightTop, radiusRightBottom);
    }

    // 14
    public static void drawRectCustom(Canvas canvas, int width, int height, int fillColor, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                  int strokeColor, float radiusLeftTop, float radiusLeftBottom, float radiusRightTop, float radiusRightBottom) {

        drawRectCustom(canvas, 0, 0, width, height, fillColor, null, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radiusLeftTop, radiusLeftBottom,
                radiusRightTop, radiusRightBottom);
    }

    // 14
    public static void drawRectCustom(Canvas canvas, int width, int height, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                  int strokeColor, float radiusLeftTop, float radiusLeftBottom, float radiusRightTop, float radiusRightBottom) {

        drawRectCustom(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radiusLeftTop,
                radiusLeftBottom, radiusRightTop, radiusRightBottom);
    }

    // 15
    public static void drawRectCustom(Canvas canvas, float left, float top, float right, float bottom, int fillColor, Shader shader, float shadowRadius, int shadowColor,
                                  float shadowDx, float shadowDy, float strokeWidth, int strokeColor, float radiusLeftTop, float radiusLeftBottom, float radiusRightTop, float radiusRightBottom) {

        float[] radii = {radiusLeftTop, radiusLeftTop, radiusRightTop, radiusRightTop, radiusRightBottom, radiusRightBottom, radiusLeftBottom, radiusLeftBottom};
        if (strokeWidth > 0) {
            canvas.drawPath(
                    initPathRoundRect(initRectF(left + strokeWidth * 0.5f, top + strokeWidth * 0.5f, right - strokeWidth * 0.5f, bottom - strokeWidth * 0.5f), radii,
                            Path.Direction.CW), PaintTool.initPaint(Paint.Style.STROKE, strokeColor, strokeWidth));
        }

        int radiiLength = radii.length;
        for (int i = 0; i < radiiLength; i++) {
            radii[i] -= strokeWidth;
            radii[i] = radii[i] >= 0 ? radii[i] : 0;
        }

        canvas.drawPath(
                initPathRoundRect(initRectF(left + strokeWidth, top + strokeWidth, right - strokeWidth, bottom - strokeWidth), radii, Path.Direction.CW),
                shader == null ? shadowRadius == 0 ? PaintTool.initPaint(fillColor) : PaintTool.initPaint(Paint.Style.FILL, fillColor, shadowRadius, shadowColor, shadowDx,
                        shadowDy) : shadowRadius == 0 ? PaintTool.initPaint(Paint.Style.FILL, shader) : PaintTool.initPaint(Paint.Style.FILL, shader, shadowRadius, shadowColor, shadowDx,
                        shadowDy));
    }

    // 5
    public static void drawRectClassic(Canvas canvas, float width, float height, int fillColor, float radius) {

        drawRectClassic(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, 0, Color.TRANSPARENT, radius);
    }

    // 7
    public static void drawRectClassic(Canvas canvas, float width, float height, int fillColor, float strokeWidth, int strokeColor, float radius) {

        drawRectClassic(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radius);
    }

    // 7
    public static void drawRectClassic(Canvas canvas, float width, float height, Shader shader, float strokeWidth, int strokeColor, float radius) {

        drawRectClassic(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radius);
    }

    // 9
    public static void drawRectClassic(Canvas canvas, float left, float top, float right, float bottom, int fillColor, float strokeWidth, int strokeColor, float radius) {

        drawRectClassic(canvas, left, top, right, bottom, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radius);
    }

    // 9
    public static void drawRectClassic(Canvas canvas, float left, float top, float right, float bottom, Shader shader, float strokeWidth, int strokeColor, float radius) {

        drawRectClassic(canvas, left, top, right, bottom, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radius);
    }

    // 11
    public static void drawRectClassic(Canvas canvas, float width, float height, int fillColor, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                   int strokeColor, float radius) {

        drawRectClassic(canvas, 0, 0, width, height, fillColor, null, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radius);
    }

    // 11
    public static void drawRectClassic(Canvas canvas, float width, float height, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                   int strokeColor, float radius) {

        drawRectClassic(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radius);
    }

    // 12
    public static void drawRectClassic(Canvas canvas, float left, float top, float right, float bottom, int fillColor, Shader shader, float shadowRadius, int shadowColor,
                                   float shadowDx, float shadowDy, float strokeWidth, int strokeColor, float radius) {

        if (strokeWidth > 0) {
            canvas.drawRoundRect(initRectF(left + strokeWidth * 0.5f, top + strokeWidth * 0.5f, right - strokeWidth * 0.5f, bottom - strokeWidth * 0.5f), radius, radius,
                    PaintTool.initPaint(Paint.Style.STROKE, strokeColor, strokeWidth));
        }

        canvas.drawRoundRect(
                initRectF(left + strokeWidth, top + strokeWidth, right - strokeWidth, bottom - strokeWidth),
                radius,
                radius,
                shader == null ? shadowRadius == 0 ? PaintTool.initPaint(fillColor) : PaintTool.initPaint(Paint.Style.FILL, fillColor, shadowRadius, shadowColor, shadowDx,
                        shadowDy) : shadowRadius == 0 ? PaintTool.initPaint(Paint.Style.FILL, shader) : PaintTool.initPaint(Paint.Style.FILL, shader, shadowRadius, shadowColor, shadowDx,
                        shadowDy));
    }


    // 6
    public static float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, Paint paint) {
        return drawText(canvas, string, width, centerX, centerY, 0, 0, paint, TView.TextGravity.ALL_CENTER, 1.0f, null);
    }

    // 8
    public static float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint) {
        return drawText(canvas, string, width, centerX, centerY, paddingLeft, paddingRight, paint, TView.TextGravity.ALL_CENTER, 1.0f, null);
    }

    // 9
    public static float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint,
                               float textRowSpaceRatio, List<Integer> valueMeasureList) {
        return drawText(canvas, string, width, centerX, centerY, paddingLeft, paddingRight, paint, TView.TextGravity.ALL_CENTER, textRowSpaceRatio, valueMeasureList);
    }

    // 10
    public static float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint,
                               TView.TextGravity textGravity, float textRowSpaceRatio, List<Integer> valueMeasureList) {

        if (valueMeasureList == null) {
            valueMeasureList = generateMeasureList(string, paint, width, paddingLeft, paddingRight);
        }

        float textMiddleRow = (valueMeasureList.size() + 1) * 0.5f;

        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // float baseline = (targetRectBottom + targetRectTop - fontMetrics.bottom - fontMetrics.top) * 0.5f;
        float baseline = centerY - fontMetrics.bottom * 0.5f - fontMetrics.top * 0.5f;
        int halfWordHeight = (fontMetrics.descent - fontMetrics.ascent) >> 1;

        //
        String drawString;
        float drawLineY;

        int valueMeasureListSize = valueMeasureList.size();
        for (int i = 0; i < valueMeasureListSize; i++) {
            drawLineY = baseline + (i + 1 - textMiddleRow) * paint.getTextSize() * textRowSpaceRatio;
            if (i == 0) {
                drawString = string.substring(0, valueMeasureList.get(i));
            } else {
                drawString = string.substring(valueMeasureList.get(i - 1), valueMeasureList.get(i));
            }

            float measureLength = paint.measureText(drawString);

            if (i != valueMeasureListSize - 1) {
                switch (textGravity) {
                    case ALL_CENTER:
                        canvas.drawText(drawString, centerX + (paddingLeft - paddingRight) * 0.5f, drawLineY, paint);
                        break;
                    case ALL_LEFT:
                        canvas.drawText(drawString, paddingLeft + measureLength * 0.5f, drawLineY, paint);
                        break;
                    case CENTER_LEFT:
                        canvas.drawText(drawString, centerX + (paddingLeft - paddingRight) * 0.5f, drawLineY, paint);
                        break;
                    case LEFT_CENTER:
                        canvas.drawText(drawString, paddingLeft + measureLength * 0.5f, drawLineY, paint);
                        break;
                    default:
                        break;
                }
            } else {
                float availableWidth = width - paddingLeft - paddingRight;
                switch (textGravity) {
                    case ALL_CENTER:
                        canvas.drawText(drawString, centerX, drawLineY, paint);
                        return new float[]{measureLength, measureLength * 0.5f, drawLineY - centerY - halfWordHeight};
                    case ALL_LEFT:
                        canvas.drawText(drawString, paddingLeft + measureLength * 0.5f, drawLineY, paint);
                        return new float[]{availableWidth, measureLength + paddingLeft - width * 0.5f, drawLineY - centerY - halfWordHeight};
                    case CENTER_LEFT:
                        canvas.drawText(drawString, paddingLeft + measureLength * 0.5f, drawLineY, paint);
                        return new float[]{availableWidth, measureLength + paddingLeft - width * 0.5f, drawLineY - centerY - halfWordHeight};
                    case LEFT_CENTER:
                        canvas.drawText(drawString, centerX, drawLineY, paint);
                        return new float[]{measureLength, measureLength * 0.5f, drawLineY - centerY - halfWordHeight};
                    default:
                        break;
                }
            }
        }
        return new float[]{width, 0, 0};
    }

    //
    public static void drawTextMark(
            int width,
            int height,
            Canvas canvas,
            float radius, Paint paint,
            float markRadius,
            float markDx, float markDy,
            float offsetX, float offsetY,
            String markText,
            int markTextColor,
            float markTextSize,
            List<Integer> valueMeasureList) {

        float cx = (width >> 1) + offsetX + markRadius + markDx;
        float cy = (height >> 1) + offsetY + markDy;

        canvas.drawCircle(cx, cy, radius, paint);
        if (markText != null) {
            // Because, drawText use the same method to clear the cache measureRowList
            drawText(canvas, markText, width, cx, cy, 0, 0,
                    PaintTool.initTextPaint(Paint.Style.FILL, markTextColor, markTextSize, Paint.Align.CENTER)
                    , 1, valueMeasureList);
        }
    }
}
