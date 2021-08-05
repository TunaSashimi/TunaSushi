package com.tunasushi.tool

import java.io.File
import java.io.FileInputStream
import java.lang.Exception

/**
 * @author TunaSashimi
 * @date 2019-11-19 15:26
 * @Copyright 2019 TunaSashimi. All rights reserved.
 * @Description
 */
object FileTool {
    //
    const val GRAPHICSTYPE_UNKNOWN = "UNKNOWN"
    const val GRAPHICSTYPE_GIF = "GIF"
    const val GRAPHICSTYPE_PNG = "PNG"
    const val GRAPHICSTYPE_JPG = "JPG"
    fun getGraphicsType(file: File?): String {
        var type = GRAPHICSTYPE_UNKNOWN
        var graphicsFile: FileInputStream? = null
        val b = ByteArray(10)
        var l = -1
        try {
            graphicsFile = FileInputStream(file)
            l = graphicsFile.read(b)
            graphicsFile.close()
        } catch (e: Exception) {
            return type
        }
        if (l == 10) {
            val b0 = b[0]
            val b1 = b[1]
            val b2 = b[2]
            val b3 = b[3]
            val b6 = b[6]
            val b7 = b[7]
            val b8 = b[8]
            val b9 = b[9]
            // GIF
            if (b0 == 'G'.toByte() && b1 == 'I'.toByte() && b2 == 'F'.toByte()) {
                type = GRAPHICSTYPE_GIF
                // PNG
            } else if (b1 == 'P'.toByte() && b2 == 'N'.toByte() && b3 == 'G'.toByte()) {
                type = GRAPHICSTYPE_PNG
                // JPG
            } else if (b6 == 'J'.toByte() && b7 == 'F'.toByte() && b8 == 'I'.toByte() && b9 == 'F'.toByte()) {
                type = GRAPHICSTYPE_JPG
            }
        }
        return type
    }

    fun getGraphicsType(path: String?): String {
        var type = GRAPHICSTYPE_UNKNOWN
        var graphicsFile: FileInputStream? = null
        val b = ByteArray(10)
        var l = -1
        try {
            graphicsFile = FileInputStream(path)
            l = graphicsFile.read(b)
            graphicsFile.close()
        } catch (e: Exception) {
            return type
        }
        if (l == 10) {
            val b0 = b[0]
            val b1 = b[1]
            val b2 = b[2]
            val b3 = b[3]
            val b6 = b[6]
            val b7 = b[7]
            val b8 = b[8]
            val b9 = b[9]
            // GIF
            if (b0 == 'G'.toByte() && b1 == 'I'.toByte() && b2 == 'F'.toByte()) {
                type = GRAPHICSTYPE_GIF
                // PNG
            } else if (b1 == 'P'.toByte() && b2 == 'N'.toByte() && b3 == 'G'.toByte()) {
                type = GRAPHICSTYPE_PNG
                // JPG
            } else if (b6 == 'J'.toByte() && b7 == 'F'.toByte() && b8 == 'I'.toByte() && b9 == 'F'.toByte()) {
                type = GRAPHICSTYPE_JPG
            }
        }
        return type
    }
}