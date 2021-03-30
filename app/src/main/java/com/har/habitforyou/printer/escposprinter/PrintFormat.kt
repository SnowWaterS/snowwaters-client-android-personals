package com.har.habitforyou.printer.escposprinter

import android.graphics.Bitmap

data class PrintFormat(
    val type: Int,
    val alignment: Int = ALIGNMENT_LEFT,
    val size: Int = SIZE_NORMAL,
    val isBold: Boolean = false,
    val isUnderLine: Boolean = false,
    val style: Int = STYLE_A
    ) {

    var data: String? = null
    var bitmap: Bitmap? = null
    var bitmapUrl: String? = null
    var feedCount: Int = 6
    var lineChar: String = "-"

    companion object {
        const val TYPE_TEXT = 0
        const val TYPE_BITMAP = 1
        const val TYPE_QRCODE = 2
        const val TYPE_BARCODE = 3
        const val TYPE_LINE = 5
        const val TYPE_LINEFEED = 6
        const val TYPE_CUTPAPER = 7
        const val ALIGNMENT_LEFT = 0
        const val ALIGNMENT_CENTER = 1
        const val ALIGNMENT_RIGHT = 2
        const val SIZE_SMALL = 0
        const val SIZE_NORMAL = 1
        const val SIZE_LARGE = 2
        const val SIZE_XLARGE = 3
        const val SIZE_XXLARGE = 4
        const val STYLE_A = 0
        const val STYLE_B = 1

        @JvmStatic
        @JvmOverloads
        fun createText(data: String?, alignment: Int = ALIGNMENT_LEFT, size: Int = SIZE_NORMAL, style: Int = STYLE_A, isBold: Boolean = false, isUnderLine: Boolean = false): PrintFormat {
            val printFormat = PrintFormat(TYPE_TEXT, alignment, size, isBold, isUnderLine, style)
            printFormat.data = data
            return printFormat
        }

        @JvmStatic
        fun createBitmap(data: Bitmap?): PrintFormat {
            val printFormat = PrintFormat(TYPE_BITMAP, ALIGNMENT_CENTER, SIZE_NORMAL)
            printFormat.bitmap = data
            return printFormat
        }

        @JvmStatic
        fun createBitmap(data: String?): PrintFormat {
            val printFormat = PrintFormat(TYPE_BITMAP, ALIGNMENT_CENTER, SIZE_NORMAL)
            printFormat.bitmapUrl = data
            return printFormat
        }


        @JvmStatic
        fun createQrcode(data: String?): PrintFormat {
            val printFormat = PrintFormat(TYPE_QRCODE, ALIGNMENT_CENTER, SIZE_NORMAL)
            printFormat.data = data
            return printFormat
        }

        @JvmStatic
        fun createBarcode(data: String?): PrintFormat {
            val printFormat = PrintFormat(TYPE_BARCODE, ALIGNMENT_CENTER, SIZE_NORMAL)
            printFormat.data = data
            return printFormat
        }

        @JvmStatic
        fun createCutPaper(response: Boolean): PrintFormat {
            return PrintFormat(TYPE_CUTPAPER)
        }

        @JvmStatic
        fun createLineFeed(feedCount: Int): PrintFormat {
            val printFormat = PrintFormat(TYPE_LINEFEED)
            printFormat.feedCount = feedCount
            return printFormat
        }

        @JvmStatic
        fun createLine(lineChar: String = "-"): PrintFormat {
            val printFormat = PrintFormat(TYPE_LINE)
            printFormat.lineChar = lineChar
            return printFormat

        }

    }
}