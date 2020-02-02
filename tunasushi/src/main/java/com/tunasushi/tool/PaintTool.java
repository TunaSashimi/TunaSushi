package com.tunasushi.tool;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;

/**
 * @author Tunasashimi
 * @date 2020-01-30 21:21
 * @Copyright 2020 Sashimi. All rights reserved.
 * @Description
 */
public class PaintTool {
    public static Paint paint;
    public static Paint getPaint() {
        return paint;
    }
    public static void setPaint(Paint paint) {
        PaintTool.paint = paint;
    }

    // 0
    public static Paint initPaint() {
        if (paint == null) {
            return paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        } else {
            paint.reset();
            paint.clearShadowLayer();
            paint.setAntiAlias(true);
        }
        return paint;
    }

    // 1
    public static Paint initPaint(int colorValue) {
        return initPaint(Paint.Style.FILL, 0, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 2
    public static Paint initPaint(Paint.Style style, int colorValue) {
        return initPaint(style, 0, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 2
    public static Paint initPaint(Paint.Style style, Shader shader) {
        return initPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    public static Paint initPaint(Paint.Style style, int colorValue, float strokeWidth) {
        return initPaint(style, strokeWidth, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    public static Paint initPaint(Paint.Style style, int colorValue, Shader shader) {
        return initPaint(style, 0, colorValue, shader, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    public static Paint initPaint(Paint.Style style, Shader shader, int alpha) {
        return initPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    public static Paint initPaint(Paint.Style style, int colorValue, Shader shader, int alpha) {
        return initPaint(style, 0, colorValue, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    public static Paint initPaint(Paint.Style style, int colorValue, float strokeWidth, int alpha) {
        return initPaint(style, strokeWidth, colorValue, null, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    public static Paint initPaint(Paint paint, float shadowRadius, float shadowDx, float shadowDy) {
        paint.clearShadowLayer();
        if (shadowRadius > 0) {
            paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, Color.WHITE);
        }
        return paint;
    }

    // 6
    public static Paint initPaint(Paint.Style style, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        return initPaint(style, 0, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
    }

    // 6
    public static Paint initPaint(Paint.Style style, int colorValue, float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        return initPaint(style, 0, colorValue, null, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
    }

    // 9
    public static Paint initPaint(Paint.Style style, float strokeWidth, int colorValue, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, int alpha) {
        //
        initPaint();
        //
        if (style != null) {
            paint.setStyle(style);
        }
        if (strokeWidth != 0) {
            paint.setStrokeWidth(strokeWidth);
        }

        // When the shadow color can not be set to transparent, but can not set
        if (shader == null) {
            paint.setColor(colorValue);
        } else {
            paint.setShader(shader);
        }

        if (shadowRadius != 0) {
            paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
        }
        if (alpha != -1) {
            paint.setAlpha(alpha);
        }
        return paint;
    }


    // 1
    public static Paint initTextPaint(float textSize) {
        return initTextPaint(null, Color.WHITE, textSize, 0, Color.TRANSPARENT, 0, 0, null, null);
    }

    // 2
    public static Paint initTextPaint(int textColor, float textSize) {
        return initTextPaint(null, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, null, null);
    }

    // 3
    public static Paint initTextPaint(int textColor, float textSize, Paint.Align align) {
        return initTextPaint(null, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, null, align);
    }

    // 4
    public static Paint initTextPaint(Paint.Style style, int textColor, float textSize, Paint.Align align) {
        return initTextPaint(style, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, null, align);
    }

    // 5
    public static Paint initTextPaint(Paint.Style style, int textColor, float textSize, Typeface typeFace, Paint.Align align) {
        return initTextPaint(style, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, typeFace, align);
    }

    // 8
    public static Paint initTextPaint(Paint.Style style, int colorValue, float textSize, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, Typeface typeFace, Paint.Align align) {
        //
        initPaint();
        //
        if (style != null) {
            paint.setStyle(style);
        }

        paint.setColor(colorValue);

        if (textSize != 0) {
            paint.setTextSize(textSize);
        }
        if (shadowRadius != 0) {
            paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
        }
        if (align != null) {
            paint.setTextAlign(align);
        }

        if (typeFace != null) {
            paint.setTypeface(typeFace);
        }

        return paint;
    }
}
