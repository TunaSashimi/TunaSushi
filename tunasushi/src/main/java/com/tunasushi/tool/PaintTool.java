package com.tunasushi.tool;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;

/**
 * @author Tunasashimi
 * @date 2020-01-30 21:21
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class PaintTool {
    public static Paint tunaPaint;

    public static Paint getTunaPaint() {
        return tunaPaint;
    }

    public static void setTunaPaint(Paint paint) {
        tunaPaint = paint;
    }

    // 0
    public static Paint initTunaPaint() {
        if (tunaPaint == null) {
            return tunaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        } else {
            tunaPaint.reset();
            tunaPaint.clearShadowLayer();
            tunaPaint.setAntiAlias(true);
        }
        return tunaPaint;
    }

    // 1
    public static Paint initTunaPaint(int colorValue) {
        return initTunaPaint(Paint.Style.FILL, 0, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 2
    public static Paint initTunaPaint(Paint.Style style, int colorValue) {
        return initTunaPaint(style, 0, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 2
    public static Paint initTunaPaint(Paint.Style style, Shader shader) {
        return initTunaPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    public static Paint initTunaPaint(Paint.Style style, int colorValue, float strokeWidth) {
        return initTunaPaint(style, strokeWidth, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    public static Paint initTunaPaint(Paint.Style style, int colorValue, Shader shader) {
        return initTunaPaint(style, 0, colorValue, shader, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    public static Paint initTunaPaint(Paint.Style style, Shader shader, int alpha) {
        return initTunaPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    public static Paint initTunaPaint(Paint.Style style, int colorValue, Shader shader, int alpha) {
        return initTunaPaint(style, 0, colorValue, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    public static Paint initTunaPaint(Paint.Style style, int colorValue, float strokeWidth, int alpha) {
        return initTunaPaint(style, strokeWidth, colorValue, null, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    public static Paint initTunaPaint(Paint paint, float shadowRadius, float shadowDx, float shadowDy) {
        paint.clearShadowLayer();
        if (shadowRadius > 0) {
            paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, Color.WHITE);
        }
        return paint;
    }

    // 6
    public static Paint initTunaPaint(Paint.Style style, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        return initTunaPaint(style, 0, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
    }

    // 6
    public static Paint initTunaPaint(Paint.Style style, int colorValue, float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        return initTunaPaint(style, 0, colorValue, null, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
    }

    // 9
    public static Paint initTunaPaint(Paint.Style style, float strokeWidth, int colorValue, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, int alpha) {
        //
        initTunaPaint();
        //
        if (style != null) {
            tunaPaint.setStyle(style);
        }
        if (strokeWidth != 0) {
            tunaPaint.setStrokeWidth(strokeWidth);
        }

        // When the shadow color can not be set to transparent, but can not set
        if (shader == null) {
            tunaPaint.setColor(colorValue);
        } else {
            tunaPaint.setShader(shader);
        }

        if (shadowRadius != 0) {
            tunaPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
        }
        if (alpha != -1) {
            tunaPaint.setAlpha(alpha);
        }
        return tunaPaint;
    }


    // 1
    public static Paint initTunaTextPaint(float textSize) {
        return initTunaTextPaint(null, Color.WHITE, textSize, 0, Color.TRANSPARENT, 0, 0, null, null);
    }

    // 2
    public static Paint initTunaTextPaint(int textColor, float textSize) {
        return initTunaTextPaint(null, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, null, null);
    }

    // 3
    public static Paint initTunaTextPaint(int textColor, float textSize, Paint.Align align) {
        return initTunaTextPaint(null, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, null, align);
    }

    // 4
    public static Paint initTunaTextPaint(Paint.Style style, int textColor, float textSize, Paint.Align align) {
        return initTunaTextPaint(style, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, null, align);
    }

    // 5
    public static Paint initTunaTextPaint(Paint.Style style, int textColor, float textSize, Typeface typeFace, Paint.Align align) {
        return initTunaTextPaint(style, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, typeFace, align);
    }

    // 8
    public static Paint initTunaTextPaint(Paint.Style style, int colorValue, float textSize, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, Typeface typeFace, Paint.Align align) {
        //
        initTunaPaint();
        //
        if (style != null) {
            tunaPaint.setStyle(style);
        }

        tunaPaint.setColor(colorValue);

        if (textSize != 0) {
            tunaPaint.setTextSize(textSize);
        }
        if (shadowRadius != 0) {
            tunaPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
        }
        if (align != null) {
            tunaPaint.setTextAlign(align);
        }

        if (typeFace != null) {
            tunaPaint.setTypeface(typeFace);
        }

        return tunaPaint;
    }
}
