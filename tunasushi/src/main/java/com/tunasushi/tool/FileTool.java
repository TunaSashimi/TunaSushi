package com.tunasushi.tool;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author Tunasashimi
 * @date 2019-11-19 15:26
 * @Copyright 2019 TunaSashimi. All rights reserved.
 * @Description
 */
public class FileTool {

    //
    public static final String GRAPHICSTYPE_UNKNOWN = "UNKNOWN";
    public static final String GRAPHICSTYPE_GIF = "GIF";
    public static final String GRAPHICSTYPE_PNG = "PNG";
    public static final String GRAPHICSTYPE_JPG = "JPG";

    public static String getGraphicsType(File file) {
        String type = GRAPHICSTYPE_UNKNOWN;
        FileInputStream graphicsFile = null;
        byte[] b = new byte[10];
        int l = -1;
        try {
            graphicsFile = new FileInputStream(file);
            l = graphicsFile.read(b);
            graphicsFile.close();
        } catch (Exception e) {
            return type;
        }
        if (l == 10) {
            byte b0 = b[0];
            byte b1 = b[1];
            byte b2 = b[2];
            byte b3 = b[3];
            byte b6 = b[6];
            byte b7 = b[7];
            byte b8 = b[8];
            byte b9 = b[9];
            // GIF
            if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
                type = GRAPHICSTYPE_GIF;
                // PNG
            } else if (b1 == (byte) 'P' && b2 == (byte) 'N' && b3 == (byte) 'G') {
                type = GRAPHICSTYPE_PNG;
                // JPG
            } else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I' && b9 == (byte) 'F') {
                type = GRAPHICSTYPE_JPG;
            }
        }
        return type;
    }

    public static String getGraphicsType(String path) {
        String type = GRAPHICSTYPE_UNKNOWN;
        FileInputStream graphicsFile = null;
        byte[] b = new byte[10];
        int l = -1;
        try {
            graphicsFile = new FileInputStream(path);
            l = graphicsFile.read(b);
            graphicsFile.close();
        } catch (Exception e) {
            return type;
        }
        if (l == 10) {
            byte b0 = b[0];
            byte b1 = b[1];
            byte b2 = b[2];
            byte b3 = b[3];
            byte b6 = b[6];
            byte b7 = b[7];
            byte b8 = b[8];
            byte b9 = b[9];
            // GIF
            if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
                type = GRAPHICSTYPE_GIF;
                // PNG
            } else if (b1 == (byte) 'P' && b2 == (byte) 'N' && b3 == (byte) 'G') {
                type = GRAPHICSTYPE_PNG;
                // JPG
            } else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I' && b9 == (byte) 'F') {
                type = GRAPHICSTYPE_JPG;
            }
        }
        return type;
    }

}
