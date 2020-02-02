package com.tunasushi.tool;

import android.graphics.Path;
import android.graphics.RectF;

/**
 * @author Tunasashimi
 * @date 2020-02-02 19:49
 * @Copyright 2020 Sashimi. All rights reserved.
 * @Description
 */
public class PathTool {
    //
    public static Path path;

    public static Path getPath() {
        return path;
    }

    public static Path initPath() {
        if (path == null) {
            path = new Path();
        } else {
            path.reset();
        }
        return path;
    }

    public static Path initPathMoveTo(float x, float y) {
        if (path == null) {
            path = new Path();
        } else {
            path.reset();
        }
        path.moveTo(x, y);
        return path;
    }

    public static Path initPathLineTo(float x, float y) {
        if (path == null) {
            path = new Path();
        } else {
            path.reset();
        }
        path.lineTo(x, y);
        return path;
    }

    public static Path initPathArc(RectF oval, float startAngle, float sweepAngle) {
        if (path == null) {
            path = new Path();
        } else {
            path.reset();
        }
        path.addArc(oval, startAngle, sweepAngle);
        return path;
    }

    public static Path initPathRoundRect(RectF rect, float[] radii, Path.Direction dir) {
        if (path == null) {
            path = new Path();
        } else {
            path.reset();
        }
        // This setting will cause the following message appears reading xml
        // The graphics preview in the layout editor may not be accurate:
        // Different corner sizes are not supported in Path.addRoundRect.
        // (Ignore for this session)
        path.addRoundRect(rect, radii, dir);
        return path;
    }
}
