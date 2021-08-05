package com.tunasushi.tool

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import com.tunasushi.tool.BitmapTool
import kotlin.jvm.JvmOverloads
import android.graphics.drawable.Drawable
import com.tunasushi.tool.SVGTool.SVG
import com.tunasushi.tool.SVGTool
import java.lang.Exception
import java.util.HashMap

/**
 * @author TunaSashimi
 * @date 2019-11-19 15:39
 * @Copyright 2019 TunaSashimi. All rights reserved.
 * @Description
 */
class BitmapTool {
    //
    fun decodeGifResource(id: Int): Movie {
        val stringId = id.toString()
        if (graphicsMap.containsKey(stringId)) {
            val `object` = graphicsMap[stringId]
            if (`object` != null && `object` is Movie) {
                return `object`
            }
        }
        return Movie.decodeStream(Resources.getSystem().openRawResource(id))
    }

    //
    fun decodeGraphicsResource(context: Context?, id: Int): Any {
        return decodeGraphicsResource(id, 1)
    }

    //
    fun decodeGraphicsResource(id: Int, inSampleSize: Int): Any {
        val stringId = id.toString()
        if (graphicsMap.containsKey(stringId)) {
            val `object` = graphicsMap[stringId]
            if (`object` != null) {
                return `object`
            }
        }
        val movie = decodeGifResource(id)
        return movie ?: decodeBitmapResource(id, inSampleSize)
    }

    //
    @JvmOverloads
    fun decodeBitmapFile(path: String, reqWidth: Int = 0, reqHeight: Int = 0): Bitmap {
        if (graphicsMap.containsKey(path)) {
            val `object` = graphicsMap[path]
            if (`object` != null && `object` is Bitmap) {
                return `object`
            }
        }
        val bitmap: Bitmap
        if (reqWidth == 0 || reqHeight == 0) {

            //Without image parameters to avoid OOM situation arising
            val bitmapFactoryOptions = BitmapFactory.Options()
            bitmapFactoryOptions.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, bitmapFactoryOptions)
            val bitmapSize = bitmapFactoryOptions.outWidth * bitmapFactoryOptions.outHeight
            if (bitmapSize > bitmapMaxSize) {
                bitmapFactoryOptions.inSampleSize =
                    Math.ceil((bitmapSize / bitmapMaxSize).toDouble())
                        .toInt()
                bitmapFactoryOptions.inJustDecodeBounds = false
                bitmap = BitmapFactory.decodeFile(path, bitmapFactoryOptions)
            } else {
                bitmap = BitmapFactory.decodeFile(path)
            }
        } else {
            val bitmapFactoryOptions = BitmapFactory.Options()
            bitmapFactoryOptions.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, bitmapFactoryOptions)
            bitmapFactoryOptions.inSampleSize =
                computeSampleSize(bitmapFactoryOptions, -1, reqWidth * reqHeight)
            bitmapFactoryOptions.inJustDecodeBounds = false
            bitmap = BitmapFactory.decodeFile(path, bitmapFactoryOptions)
        }
        graphicsMap[path] = bitmap
        return bitmap
    }

    //
    fun decodeGifFile(path: String): Movie {
        if (graphicsMap.containsKey(path)) {
            val `object` = graphicsMap[path]
            if (`object` != null && `object` is Movie) {
                return `object`
            }
        }
        return Movie.decodeFile(path)
    }

    //
    fun decodeGraphicsFile(path: String): Any {
        if (graphicsMap.containsKey(path)) {
            val `object` = graphicsMap[path]
            if (`object` != null) {
                return `object`
            }
        }
        val movie = Movie.decodeFile(path)
        return movie ?: decodeBitmapFile(path)
    }

    //
    fun decodeGraphicsFile(path: String, reqWidth: Int, reqHeight: Int): Any {
        if (graphicsMap.containsKey(path)) {
            val `object` = graphicsMap[path]
            if (`object` != null) {
                return `object`
            }
        }
        val movie = Movie.decodeFile(path)
        return movie ?: decodeBitmapFile(path, reqWidth, reqHeight)
    }

    //
    fun createImageThumbnail(filePath: String?): Bitmap? {
        var bitmap: Bitmap? = null
        val opts = BitmapFactory.Options()
        opts.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, opts)
        opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128)
        opts.inJustDecodeBounds = false
        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    companion object {
        var bitmapMaxSize = 1536
        var graphicsMap = HashMap<String, Any>()
        @JvmStatic
        fun drawableToBitmap(drawable: Drawable): Bitmap {
            val bitmapWidth = drawable.intrinsicWidth
            val bitmapHeight = drawable.intrinsicHeight
            val bitmapConfig =
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            //
            val bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, bitmapConfig)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, bitmapWidth, bitmapHeight)
            drawable.draw(canvas)
            return bitmap
        }

        //PNG
        //
        @JvmOverloads
        fun decodeBitmapResource(id: Int, inSampleSize: Int = 1): Bitmap {
            val stringId = id.toString()
            if (graphicsMap.containsKey(stringId)) {
                val `object` = graphicsMap[stringId]
                if (`object` != null && `object` is Bitmap) {
                    return `object`
                }
            }
            val bitmap: Bitmap
            if (inSampleSize > 1) {
                val bitmapFactoryOptions = BitmapFactory.Options()
                bitmapFactoryOptions.inSampleSize = inSampleSize
                bitmap =
                    BitmapFactory.decodeResource(Resources.getSystem(), id, bitmapFactoryOptions)
            } else {
                bitmap = BitmapFactory.decodeResource(Resources.getSystem(), id)
            }
            graphicsMap[stringId] = bitmap
            return bitmap
        }

        //ExifInterface supports JPEG and some RAW image formats only
        @JvmStatic
        @JvmOverloads
        fun decodeBitmapResource(resources: Resources?, id: Int, inSampleSize: Int = 1): Bitmap {
            val stringId = id.toString()
            if (graphicsMap.containsKey(stringId)) {
                val `object` = graphicsMap[stringId]
                if (`object` != null && `object` is Bitmap) {
                    return `object`
                }
            }
            val bitmap: Bitmap
            if (inSampleSize > 1) {
                val bitmapFactoryOptions = BitmapFactory.Options()
                bitmapFactoryOptions.inSampleSize = inSampleSize
                bitmap =
                    BitmapFactory.decodeResource(Resources.getSystem(), id, bitmapFactoryOptions)
            } else {
                bitmap = BitmapFactory.decodeResource(resources, id)
            }
            graphicsMap[stringId] = bitmap
            return bitmap
        }

        //
        fun computeSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            val initialSize = computeInitialSampleSize(options, reqWidth, reqHeight)
            var inSampleSize: Int
            if (initialSize <= 8) {
                inSampleSize = 1
                while (inSampleSize < initialSize) {
                    inSampleSize = inSampleSize shl 1
                }
            } else {
                inSampleSize = (initialSize + 7) / 8 * 8
            }
            return inSampleSize
        }

        //
        private fun computeInitialSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val w = options.outWidth.toDouble()
            val h = options.outHeight.toDouble()
            val lowerBound = if (reqHeight == -1) 1 else Math.ceil(Math.sqrt(w * h / reqHeight))
                .toInt()
            val upperBound = if (reqWidth == -1) 128 else Math.min(
                Math.floor(w / reqWidth),
                Math.floor(h / reqWidth)
            )
                .toInt()
            if (upperBound < lowerBound) {
                // return the larger one when there is no overlapping zone.
                return lowerBound
            }
            return if (reqHeight == -1 && reqWidth == -1) {
                1
            } else if (reqWidth == -1) {
                lowerBound
            } else {
                upperBound
            }
        }

        //
        fun getCustomRoundBitmap(
            sourceBitmap: Bitmap,
            radiusTopLeft: Float,
            radiusBottomLeft: Float,
            radiusTopRight: Float,
            radiusBottomRight: Float
        ): Bitmap {
            val roundCustomBitmap = Bitmap.createBitmap(
                sourceBitmap.width,
                sourceBitmap.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(roundCustomBitmap)
            val paint = Paint()
            paint.isAntiAlias = true
            val rect = Rect(0, 0, sourceBitmap.width, sourceBitmap.height)
            val rectF = RectF(rect)
            val path = Path()
            val radii = floatArrayOf(
                radiusTopLeft,
                radiusTopLeft,
                radiusTopRight,
                radiusTopRight,
                radiusBottomRight,
                radiusBottomRight,
                radiusBottomLeft,
                radiusBottomLeft
            )
            path.addRoundRect(rectF, radii, Path.Direction.CW)
            canvas.drawPath(path, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(sourceBitmap, rect, rect, paint)
            return roundCustomBitmap
        }

        //
        @JvmStatic
        fun getClassicRoundBitmap(sourceBitmap: Bitmap, radius: Float): Bitmap {
            val classicRoundBitmap = Bitmap.createBitmap(
                sourceBitmap.width,
                sourceBitmap.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(classicRoundBitmap)
            val paint = Paint()
            paint.isAntiAlias = true
            val rect = Rect(0, 0, sourceBitmap.width, sourceBitmap.height)
            val rectF = RectF(rect)
            canvas.drawRoundRect(rectF, radius, radius, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(sourceBitmap, rect, rect, paint)
            return classicRoundBitmap
        }

        //
        @JvmStatic
        fun getCircleBitmap(radius: Int): Bitmap {
            val bitmap = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = Color.BLACK
            canvas.drawCircle(
                (radius shr 1).toFloat(),
                (radius shr 1).toFloat(),
                (radius shr 1).toFloat(),
                paint
            )
            return bitmap
        }

        // SVG
        // The resource files placed in raw need to be accessed through Contex!
        @JvmStatic
        fun getSVGBitmap(context: Context, width: Int, height: Int, svgResourceId: Int): Bitmap {
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = Color.BLACK
            if (svgResourceId > 0) {
                val svg = SVGTool.getSVGFromInputStream(
                    context.resources.openRawResource(svgResourceId), width, height
                )
                canvas.drawPicture(svg.picture)
            } else {
                canvas.drawRect(RectF(0.0f, 0.0f, width.toFloat(), height.toFloat()), paint)
            }
            return bitmap
        }

        //
        fun getScaleBitmap(sourceBitmap: Bitmap, scale: Float): Bitmap {
            val matrix = Matrix()
            matrix.setScale(scale, scale)
            return Bitmap.createBitmap(
                sourceBitmap,
                0,
                0,
                sourceBitmap.width,
                sourceBitmap.height,
                matrix,
                false
            )
        }

        //
        @JvmStatic
        fun getAlphaBitmap(sourceBitmap: Bitmap, fraction: Float): Bitmap {
            var sourceBitmap = sourceBitmap
            val argb = IntArray(sourceBitmap.width * sourceBitmap.height)
            sourceBitmap.getPixels(
                argb,
                0,
                sourceBitmap.width,
                0,
                0,
                sourceBitmap.width,
                sourceBitmap.height
            )
            val number = (fraction * 255).toInt()
            for (i in argb.indices) {
                if (argb[i] != 0) {
                    argb[i] = number shl 24 or (argb[i] and 0x00FFFFFF)
                } else {
                    argb[i] = 0x00000000
                }
            }
            sourceBitmap = Bitmap.createBitmap(
                argb,
                sourceBitmap.width,
                sourceBitmap.height,
                Bitmap.Config.ARGB_8888
            )
            return sourceBitmap
        }

        // 旋转
        fun getRotateBitamp(sourceBitmap: Bitmap, degree: Float): Bitmap {
            // 获得Bitmap的高和宽
            val sourceBitmapWidth = sourceBitmap.width
            val sourceBitmapHeight = sourceBitmap.height
            // 产生resize后的Bitmap对象
            val matrix = Matrix()
            matrix.setRotate(
                degree,
                (sourceBitmapWidth shr 1).toFloat(),
                (sourceBitmapHeight shr 1).toFloat()
            ) //要旋转的角度
            return Bitmap.createBitmap(
                sourceBitmap,
                0,
                0,
                sourceBitmapWidth,
                sourceBitmapHeight,
                matrix,
                true
            )
        }

        // 翻转
        @JvmStatic
        fun getReverseBitmap(sourceBitmap: Bitmap, flag: Int): Bitmap {
            var floats: FloatArray? = null
            when (flag) {
                0 -> floats = floatArrayOf(-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f)
                1 -> floats = floatArrayOf(1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f)
            }
            if (floats != null) {
                val matrix = Matrix()
                matrix.setValues(floats)
                return Bitmap.createBitmap(
                    sourceBitmap,
                    0,
                    0,
                    sourceBitmap.width,
                    sourceBitmap.height,
                    matrix,
                    true
                )
            }
            return sourceBitmap
        }

        // 怀旧
        @JvmStatic
        fun getSepiaBitmap(sourceBitmap: Bitmap): Bitmap {
            val width = sourceBitmap.width
            val height = sourceBitmap.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            var pixColor = 0
            var pixR = 0
            var pixG = 0
            var pixB = 0
            var newR = 0
            var newG = 0
            var newB = 0
            val pixels = IntArray(width * height)
            sourceBitmap.getPixels(pixels, 0, width, 0, 0, width, height)
            for (i in 0 until height) {
                for (k in 0 until width) {
                    pixColor = pixels[width * i + k]
                    pixR = Color.red(pixColor)
                    pixG = Color.green(pixColor)
                    pixB = Color.blue(pixColor)
                    newR = (0.393 * pixR + 0.769 * pixG + 0.189 * pixB).toInt()
                    newG = (0.349 * pixR + 0.686 * pixG + 0.168 * pixB).toInt()
                    newB = (0.272 * pixR + 0.534 * pixG + 0.131 * pixB).toInt()
                    val newColor = Color.argb(
                        255,
                        if (newR > 255) 255 else newR,
                        if (newG > 255) 255 else newG,
                        if (newB > 255) 255 else newB
                    )
                    pixels[width * i + k] = newColor
                }
            }
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        }

        // 浮雕
        @JvmStatic
        fun getEmbossBitmap(sourceBitmap: Bitmap): Bitmap {
            val width = sourceBitmap.width
            val height = sourceBitmap.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            var pixR = 0
            var pixG = 0
            var pixB = 0
            var pixColor = 0
            var newR = 0
            var newG = 0
            var newB = 0
            val pixels = IntArray(width * height)
            sourceBitmap.getPixels(pixels, 0, width, 0, 0, width, height)
            var pos = 0
            var i = 1
            val length = height - 1
            while (i < length) {
                var k = 1
                val len = width - 1
                while (k < len) {
                    pos = i * width + k
                    pixColor = pixels[pos]
                    pixR = Color.red(pixColor)
                    pixG = Color.green(pixColor)
                    pixB = Color.blue(pixColor)
                    pixColor = pixels[pos + 1]
                    newR = Color.red(pixColor) - pixR + 127
                    newG = Color.green(pixColor) - pixG + 127
                    newB = Color.blue(pixColor) - pixB + 127
                    newR = Math.min(255, Math.max(0, newR))
                    newG = Math.min(255, Math.max(0, newG))
                    newB = Math.min(255, Math.max(0, newB))
                    pixels[pos] = Color.argb(255, newR, newG, newB)
                    k++
                }
                i++
            }
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        }

        // 底片
        @JvmStatic
        fun getBacksheetBitmap(sourceBitmap: Bitmap): Bitmap {
            // RGBA的最大值
            val MAX_VALUE = 255
            val width = sourceBitmap.width
            val height = sourceBitmap.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            var pixR = 0
            var pixG = 0
            var pixB = 0
            var pixColor = 0
            var newR = 0
            var newG = 0
            var newB = 0
            val pixels = IntArray(width * height)
            sourceBitmap.getPixels(pixels, 0, width, 0, 0, width, height)
            var pos = 0
            var i = 1
            val length = height - 1
            while (i < length) {
                var k = 1
                val len = width - 1
                while (k < len) {
                    pos = i * width + k
                    pixColor = pixels[pos]
                    pixR = Color.red(pixColor)
                    pixG = Color.green(pixColor)
                    pixB = Color.blue(pixColor)
                    newR = MAX_VALUE - pixR
                    newG = MAX_VALUE - pixG
                    newB = MAX_VALUE - pixB
                    newR = Math.min(MAX_VALUE, Math.max(0, newR))
                    newG = Math.min(MAX_VALUE, Math.max(0, newG))
                    newB = Math.min(MAX_VALUE, Math.max(0, newB))
                    pixels[pos] = Color.argb(MAX_VALUE, newR, newG, newB)
                    k++
                }
                i++
            }
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        }

        //光照,光照中心要坐标
        @JvmStatic
        fun getSunshineBitmap(sourceBitmap: Bitmap, centerX: Float, centerY: Float): Bitmap {
            val width = sourceBitmap.width
            val height = sourceBitmap.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            var pixR = 0
            var pixG = 0
            var pixB = 0
            var pixColor = 0
            var newR = 0
            var newG = 0
            var newB = 0
            val radius = Math.min(centerX, centerY)
            val strength = 150f // 光照强度 100~150
            val pixels = IntArray(width * height)
            sourceBitmap.getPixels(pixels, 0, width, 0, 0, width, height)
            var pos = 0
            var i = 1
            val length = height - 1
            while (i < length) {
                var k = 1
                val len = width - 1
                while (k < len) {
                    pos = i * width + k
                    pixColor = pixels[pos]
                    pixR = Color.red(pixColor)
                    pixG = Color.green(pixColor)
                    pixB = Color.blue(pixColor)
                    newR = pixR
                    newG = pixG
                    newB = pixB
                    // 计算当前点到光照中心的距离，平面座标系中求两点之间的距离
                    val distance = (Math.pow(
                        (centerY - i).toDouble(),
                        2.0
                    ) + Math.pow((centerX - k).toDouble(), 2.0)).toInt()
                    if (distance < radius * radius) {
                        // 按照距离大小计算增加的光照值
                        val result =
                            (strength * (1.0 - Math.sqrt(distance.toDouble()) / radius)).toInt()
                        newR = pixR + result
                        newG = pixG + result
                        newB = pixB + result
                    }
                    newR = Math.min(255, Math.max(0, newR))
                    newG = Math.min(255, Math.max(0, newG))
                    newB = Math.min(255, Math.max(0, newB))
                    pixels[pos] = Color.argb(255, newR, newG, newB)
                    k++
                }
                i++
            }
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        }

        @JvmStatic
        fun getSketchBitmap(sourceBitmap: Bitmap): Bitmap {
            var pos: Int
            var row: Int
            var col: Int
            var clr: Int
            val width = sourceBitmap.width
            val height = sourceBitmap.height
            val pixSrc = IntArray(width * height)
            val pixNvt = IntArray(width * height)
            // 先对图象的像素处理成灰度颜色后再取反
            sourceBitmap.getPixels(pixSrc, 0, width, 0, 0, width, height)
            row = 0
            while (row < height) {
                col = 0
                while (col < width) {
                    pos = row * width + col
                    pixSrc[pos] = (Color.red(pixSrc[pos]) + Color.green(
                        pixSrc[pos]
                    ) + Color.blue(pixSrc[pos])) / 3
                    pixNvt[pos] = 255 - pixSrc[pos]
                    col++
                }
                row++
            }
            // 对取反的像素进行高斯模糊, 强度可以设置，暂定为5.0
            gaussGray(pixNvt, 5.0, 5.0, width, height)
            // 灰度颜色和模糊后像素进行差值运算
            row = 0
            while (row < height) {
                col = 0
                while (col < width) {
                    pos = row * width + col
                    clr = pixSrc[pos] shl 8
                    clr /= 256 - pixNvt[pos]
                    clr = Math.min(clr, 255)
                    pixSrc[pos] = Color.rgb(clr, clr, clr)
                    col++
                }
                row++
            }
            val createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            createBitmap.setPixels(pixSrc, 0, width, 0, 0, width, height)
            return createBitmap
        }

        private fun gaussGray(
            psrc: IntArray,
            horz: Double,
            vert: Double,
            width: Int,
            height: Int
        ): Int {
            var horz = horz
            var vert = vert
            val dst: IntArray
            val src: IntArray
            val n_p: DoubleArray
            val n_m: DoubleArray
            val d_p: DoubleArray
            val d_m: DoubleArray
            val bd_p: DoubleArray
            val bd_m: DoubleArray
            val val_p: DoubleArray
            val val_m: DoubleArray
            var i: Int
            var j: Int
            var t: Int
            var k: Int
            var row: Int
            var col: Int
            var terms: Int
            val initial_p: IntArray
            val initial_m: IntArray
            var std_dev: Double
            val max_len = Math.max(width, height)
            var sp_p_idx: Int
            var sp_m_idx: Int
            var vp_idx: Int
            var vm_idx: Int
            val_p = DoubleArray(max_len)
            val_m = DoubleArray(max_len)
            n_p = DoubleArray(5)
            n_m = DoubleArray(5)
            d_p = DoubleArray(5)
            d_m = DoubleArray(5)
            bd_p = DoubleArray(5)
            bd_m = DoubleArray(5)
            src = IntArray(max_len)
            dst = IntArray(max_len)
            initial_p = IntArray(4)
            initial_m = IntArray(4)

            // 垂直方向
            if (vert > 0.0) {
                vert = Math.abs(vert) + 1.0
                std_dev = Math.sqrt(-(vert * vert) / (2 * Math.log(1.0 / 255.0)))
                // 初试化常量
                findConstants(n_p, n_m, d_p, d_m, bd_p, bd_m, std_dev)
                col = 0
                while (col < width) {
                    k = 0
                    while (k < max_len) {
                        val_p[k] = 0.0
                        val_m[k] = val_p[k]
                        k++
                    }
                    t = 0
                    while (t < height) {
                        src[t] = psrc[t * width + col]
                        t++
                    }
                    sp_p_idx = 0
                    sp_m_idx = height - 1
                    vp_idx = 0
                    vm_idx = height - 1
                    initial_p[0] = src[0]
                    initial_m[0] = src[height - 1]
                    row = 0
                    while (row < height) {
                        terms = if (row < 4) row else 4
                        i = 0
                        while (i <= terms) {
                            val_p[vp_idx] += n_p[i] * src[sp_p_idx - i] - d_p[i] * val_p[vp_idx - i]
                            val_m[vm_idx] += n_m[i] * src[sp_m_idx + i] - d_m[i] * val_m[vm_idx + i]
                            i++
                        }
                        j = i
                        while (j <= 4) {
                            val_p[vp_idx] += (n_p[j] - bd_p[j]) * initial_p[0]
                            val_m[vm_idx] += (n_m[j] - bd_m[j]) * initial_m[0]
                            j++
                        }
                        sp_p_idx++
                        sp_m_idx--
                        vp_idx++
                        vm_idx--
                        row++
                    }
                    transferGaussPixels(val_p, val_m, dst, 1, height)
                    t = 0
                    while (t < height) {
                        psrc[t * width + col] = dst[t]
                        t++
                    }
                    col++
                }
            }

            // 水平方向
            if (horz > 0.0) {
                horz = Math.abs(horz) + 1.0
                if (horz != vert) {
                    std_dev = Math.sqrt(-(horz * horz) / (2 * Math.log(1.0 / 255.0)))
                    // 初试化常量
                    findConstants(n_p, n_m, d_p, d_m, bd_p, bd_m, std_dev)
                }
                row = 0
                while (row < height) {
                    k = 0
                    while (k < max_len) {
                        val_p[k] = 0.0
                        val_m[k] = val_p[k]
                        k++
                    }
                    t = 0
                    while (t < width) {
                        src[t] = psrc[row * width + t]
                        t++
                    }
                    sp_p_idx = 0
                    sp_m_idx = width - 1
                    vp_idx = 0
                    vm_idx = width - 1
                    initial_p[0] = src[0]
                    initial_m[0] = src[width - 1]
                    col = 0
                    while (col < width) {
                        terms = if (col < 4) col else 4
                        i = 0
                        while (i <= terms) {
                            val_p[vp_idx] += n_p[i] * src[sp_p_idx - i] - d_p[i] * val_p[vp_idx - i]
                            val_m[vm_idx] += n_m[i] * src[sp_m_idx + i] - d_m[i] * val_m[vm_idx + i]
                            i++
                        }
                        j = i
                        while (j <= 4) {
                            val_p[vp_idx] += (n_p[j] - bd_p[j]) * initial_p[0]
                            val_m[vm_idx] += (n_m[j] - bd_m[j]) * initial_m[0]
                            j++
                        }
                        sp_p_idx++
                        sp_m_idx--
                        vp_idx++
                        vm_idx--
                        col++
                    }
                    transferGaussPixels(val_p, val_m, dst, 1, width)
                    t = 0
                    while (t < width) {
                        psrc[row * width + t] = dst[t]
                        t++
                    }
                    row++
                }
            }
            return 0
        }

        private fun findConstants(
            n_p: DoubleArray,
            n_m: DoubleArray,
            d_p: DoubleArray,
            d_m: DoubleArray,
            bd_p: DoubleArray,
            bd_m: DoubleArray,
            std_dev: Double
        ) {
            val div = Math.sqrt(2 * 3.141593) * std_dev
            val x0 = -1.783 / std_dev
            val x1 = -1.723 / std_dev
            val x2 = 0.6318 / std_dev
            val x3 = 1.997 / std_dev
            val x4 = 1.6803 / div
            val x5 = 3.735 / div
            val x6 = -0.6803 / div
            val x7 = -0.2598 / div
            var i: Int
            n_p[0] = x4 + x6
            n_p[1] =
                Math.exp(x1) * (x7 * Math.sin(x3) - (x6 + 2 * x4) * Math.cos(x3)) + Math.exp(x0) * (x5 * Math.sin(
                    x2
                ) - (2 * x6 + x4) * Math.cos(x2))
            n_p[2] =
                2 * Math.exp(x0 + x1) * ((x4 + x6) * Math.cos(x3) * Math.cos(x2) - x5 * Math.cos(x3) * Math.sin(
                    x2
                ) - x7 * Math.cos(x2) * Math.sin(x3)) + x6 * Math.exp(2 * x0) + (x4
                        * Math.exp(2 * x1))
            n_p[3] =
                Math.exp(x1 + 2 * x0) * (x7 * Math.sin(x3) - x6 * Math.cos(x3)) + Math.exp(x0 + 2 * x1) * (x5 * Math.sin(
                    x2
                ) - x4 * Math.cos(x2))
            n_p[4] = 0.0
            d_p[0] = 0.0
            d_p[1] = -2 * Math.exp(x1) * Math.cos(x3) - 2 * Math.exp(x0) * Math.cos(x2)
            d_p[2] =
                4 * Math.cos(x3) * Math.cos(x2) * Math.exp(x0 + x1) + Math.exp(2 * x1) + Math.exp(2 * x0)
            d_p[3] =
                -2 * Math.cos(x2) * Math.exp(x0 + 2 * x1) - 2 * Math.cos(x3) * Math.exp(x1 + 2 * x0)
            d_p[4] = Math.exp(2 * x0 + 2 * x1)
            i = 0
            while (i <= 4) {
                d_m[i] = d_p[i]
                i++
            }
            n_m[0] = 0.0
            i = 1
            while (i <= 4) {
                n_m[i] = n_p[i] - d_p[i] * n_p[0]
                i++
            }
            var sum_n_p: Double
            var sum_n_m: Double
            var sum_d: Double
            val a: Double
            val b: Double
            sum_n_p = 0.0
            sum_n_m = 0.0
            sum_d = 0.0
            i = 0
            while (i <= 4) {
                sum_n_p += n_p[i]
                sum_n_m += n_m[i]
                sum_d += d_p[i]
                i++
            }
            a = sum_n_p / (1.0 + sum_d)
            b = sum_n_m / (1.0 + sum_d)
            i = 0
            while (i <= 4) {
                bd_p[i] = d_p[i] * a
                bd_m[i] = d_m[i] * b
                i++
            }
        }

        private fun transferGaussPixels(
            src1: DoubleArray,
            src2: DoubleArray,
            dest: IntArray,
            bytes: Int,
            width: Int
        ) {
            var i: Int
            var j: Int
            var k: Int
            var b: Int
            val bend = bytes * width
            var sum: Double
            k = 0
            j = k
            i = j
            b = 0
            while (b < bend) {
                sum = src1[i++] + src2[j++]
                if (sum > 255) sum = 255.0 else if (sum < 0) sum = 0.0
                dest[k++] = sum.toInt()
                b++
            }
        }

        //
        @JvmStatic
        fun processBitmap(
            sourceBitmap: Bitmap,
            brightValue: Float,
            hueValue: Float,
            saturationValue: Float
        ): Bitmap {
            val createBitmap = Bitmap.createBitmap(
                sourceBitmap.width,
                sourceBitmap.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(createBitmap)

            //亮度
            val colorMatrixBright = ColorMatrix()
            colorMatrixBright.reset()

            //色相
            val colorMatrixHue = ColorMatrix()
            colorMatrixHue.reset()

            //饱和度
            val colorMatrixSaturation = ColorMatrix()
            colorMatrixSaturation.reset()

            //叠加
            val colorMatrixAll = ColorMatrix()
            colorMatrixAll.reset()
            colorMatrixBright.setRotate(0, hueValue) // 控制红色区在色轮上旋转的角度
            colorMatrixBright.setRotate(1, hueValue) // 控制绿色区在色轮上旋转的角度
            colorMatrixBright.setRotate(2, hueValue) // 控制蓝色区在色轮上旋转的角度

            // hueColor就是色轮旋转的角度,正值表示顺时针旋转,负值表示逆时针旋转,红、绿、蓝三分量按相同的比例,最后一个参数1表示透明度不做变化
            colorMatrixHue.setScale(brightValue, brightValue, brightValue, 1f)

            // saturation 饱和度值，最小可设为0，此时对应的是灰度图(也就是俗话的“黑白图”),为1表示饱和度不变，设置大于1，就显示过饱和
            colorMatrixSaturation.setSaturation(saturationValue)
            colorMatrixAll.postConcat(colorMatrixBright) // 效果叠加
            colorMatrixAll.postConcat(colorMatrixHue)
            colorMatrixAll.postConcat(colorMatrixSaturation) // 效果叠加
            val paint = Paint()
            paint.isAntiAlias = true
            paint.colorFilter = ColorMatrixColorFilter(colorMatrixAll) // 设置颜色变换效果
            canvas.drawBitmap(sourceBitmap, 0f, 0f, paint) // 将颜色变化后的图片输出到新创建的位图区
            // 返回新的位图，也即调色处理后的图片
            return createBitmap
        }
    }
}