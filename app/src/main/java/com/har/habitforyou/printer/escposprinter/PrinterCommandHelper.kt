package com.har.habitforyou.printer.escposprinter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.nowbusking.nowwaiting.nowwaiting.printer.escprinter.EposCommands
import java.io.File
import java.io.FileInputStream
import java.nio.charset.Charset
import kotlin.math.floor

object PrinterCommandHelper {

    private val maxLineCount = 42

    val SIZE_1 = EposCommands.getTextSize(0, 0)
    val SIZE_2 = EposCommands.getTextSize(0, 1)
    val SIZE_3 = EposCommands.getTextSize(0, 2)
    val SIZE_4 = EposCommands.getTextSize(0, 3)
    val SIZE_5 = EposCommands.getTextSize(0, 4)
    val SIZE_6 = EposCommands.getTextSize(0, 5)
    val SIZE_7 = EposCommands.getTextSize(0, 6)
    val SIZE_8 = EposCommands.getTextSize(0, 7)

    val SIZE_B_0 = EposCommands.getTextSize(0, 1)
    val SIZE_B_1 = EposCommands.getTextSize(1, 2)
    val SIZE_B_2 = EposCommands.getTextSize(2, 4)
    val SIZE_B_3 = EposCommands.getTextSize(3, 6)


    val LAST_LINE_FEED: ByteArray
        get() {
            var resultByteArray = byteArrayOf()
            for (i in 0 until 6) {
                resultByteArray += EposCommands.CTL_LF
            }

            return resultByteArray
        }

    private fun getAlignment(alignment: Int): ByteArray {
        return when (alignment) {
            PrintFormat.ALIGNMENT_CENTER -> EposCommands.TXT_ALIGN_CT
            PrintFormat.ALIGNMENT_RIGHT -> EposCommands.TXT_ALIGN_LT
            else -> EposCommands.TXT_ALIGN_LT
        }
    }

    private fun getStyle(style: Int): ByteArray {
        return when (style) {
            PrintFormat.STYLE_B -> EposCommands.TXT_FONT_B
            else -> EposCommands.TXT_FONT_A
        }
    }

    private fun getSize(size: Int = -1): ByteArray {
        return when (size) {
            PrintFormat.SIZE_NORMAL -> SIZE_B_0
            PrintFormat.SIZE_LARGE -> SIZE_B_1
            PrintFormat.SIZE_XLARGE -> SIZE_B_2
            PrintFormat.SIZE_XXLARGE -> SIZE_B_3
            else -> SIZE_1
        }
    }

    @JvmStatic
    fun printTestData(): ByteArray {
        val testTextMessage = "프린터 테스트입니다\n1234567890\nabcdefghijklmnopqrstuvwxyz\n!@#$%^&*()_+<>?:{}\n일이삼사오육칠팔구영"
        val euckrCharset = Charset.forName("euc_kr")
        val messageEUC_KR = testTextMessage.toByteArray(euckrCharset)
        return messageEUC_KR
    }

    @JvmStatic
    fun printData(printFormats: List<PrintFormat>): ByteArray {
        var value = byteArrayOf()

        printFormats.forEach {
            when (it.type) {
                PrintFormat.TYPE_CUTPAPER -> value += cutPaper()
                PrintFormat.TYPE_LINEFEED -> {
                    for (i in 0 until it.feedCount) {
                        value += lineFeed()
                    }
                }
                PrintFormat.TYPE_TEXT -> {
                    val alignment = getAlignment(it.alignment)
                    val size = getSize(it.size)
                    val style = getStyle(it.style)
                    val bold = if (it.isBold) EposCommands.TXT_BOLD_ON else EposCommands.TXT_BOLD_OFF
                    val underline = if (it.isUnderLine) EposCommands.TXT_UNDERL_ON else EposCommands.TXT_UNDERL_OFF
                    val euckrCharset = Charset.forName("euc_kr")
                    val data = it.data?.toByteArray(euckrCharset) ?: byteArrayOf()

                    value += alignment + size + style + bold + underline + data
                }
                PrintFormat.TYPE_LINE -> {
                    val alignment = getAlignment(it.alignment)
                    val size = getSize()
                    val style = getStyle(it.style)
                    val bold = if (it.isBold) EposCommands.TXT_BOLD_ON else EposCommands.TXT_BOLD_OFF
                    val underline = if (it.isUnderLine) EposCommands.TXT_UNDERL_ON else EposCommands.TXT_UNDERL_OFF
                    val data = getLine(it.lineChar).toByteArray()

                    value += alignment + size + style + bold + underline + data
                }
                PrintFormat.TYPE_QRCODE -> {
                    it.data?.let { qrUrl ->
                        value += getQrData(qrUrl)
                    }
                }
                PrintFormat.TYPE_BITMAP -> {
                    if (it.bitmap != null) {
                        it.bitmap?.let { bitmap ->
                            value += getBitmapData(bitmap)
                        }
                    } else if (it.bitmapUrl != null) {
                        it.bitmapUrl?.let { bitmapUrl ->
                            val options = BitmapFactory.Options()
                            options.inPreferredConfig = Bitmap.Config.ARGB_8888
                            options.inTargetDensity = 200
                            options.inDensity = 200

                            var bitmapImage: Bitmap? = null

                            val file = File(bitmapUrl)
                            if (file.exists()) {
                                val image = BitmapFactory.decodeStream(FileInputStream(bitmapUrl), null, options)
                                image?.let { image ->
                                    val multipier = 200.0 / image.width
                                    val matrix = Matrix()
                                    matrix.postScale(multipier.toFloat(), multipier.toFloat())
                                    bitmapImage = Bitmap.createScaledBitmap(image, (image.width * multipier).toInt(), (image.height * multipier).toInt(), true)
                                    // bitmapImage = image
                                }
                            }
                            bitmapImage?.let { image ->
                                value += getBitmapData(image)
                            }
                        }
                    }
                }
            }
        }
        return value
    }

    private fun getLine(lineType: String): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("\n")
        for (i in 0 until maxLineCount) {
            stringBuilder.append(lineType)
        }
        stringBuilder.append("\n")
        return stringBuilder.toString()
    }

    @JvmStatic
    fun initPrinter(): ByteArray {
        return EposCommands.HW_INIT
    }

    @JvmStatic
    fun lineFeed(): ByteArray {
        return EposCommands.CTL_LF
    }

    @JvmStatic
    fun cutPaper(): ByteArray {
        return LAST_LINE_FEED + EposCommands.PAPER_FULL_CUT
    }

    fun getQrSize(qrData: String): ByteArray {
        val qrLength = qrData.length + 3
        return byteArrayOf((qrLength % 256).toByte(), floor(qrLength / 256.0).toInt().toByte())
    }

    fun getQrData(data: String): ByteArray {
        val qrData = EposCommands.HW_INIT + EposCommands.QRCODDE_MODEL + EposCommands.QRCODDE_SIZE + EposCommands.QRCODDE_ERROR + EposCommands.QRCODDE_STORE_DATA_FIRST + getQrSize(data) + EposCommands.QRCODDE_STORE_DATA_LAST + data.toByteArray() + EposCommands.QRCODDE_PRINT + EposCommands.QRCODDE_TRANSMIT
        return qrData

    }

    fun getBitmapSize(data: Bitmap): ByteArray {
        val width = data.width
        return byteArrayOf((width % 256).toByte(), floor(width / 256.0).toInt().toByte())
    }


    fun getBitmapData(data: Bitmap): ByteArray {
        val width = data.width
        val height = data.height

        var result = byteArrayOf()
        result += EposCommands.LINE_SPACE_24
        for (j in 0 until height step 24) {
            result = result + EposCommands.SELECT_GRAY_BIT_IMAGE_MODE + getBitmapSize(data)
            for (i in 0 until width) {
                var slice = 0
                for (yy in j until j + 24 step 3) {
                    if (yy + 2 >= height) continue
                    val pixel1 = data.getPixel(i, yy)
                    val value1 = (0.299 * Color.red(pixel1) + 0.587 * Color.green(pixel1) + 0.114 * Color.blue(pixel1))
                    val pixel2 = data.getPixel(i, yy + 1)
                    val value2 = (0.299 * Color.red(pixel2) + 0.587 * Color.green(pixel2) + 0.114 * Color.blue(pixel2))
                    val pixel3 = data.getPixel(i, yy + 1)
                    val value3 = (0.299 * Color.red(pixel3) + 0.587 * Color.green(pixel3) + 0.114 * Color.blue(pixel3))
                    val value = ((value1 + value2 + value3) / 3).toInt()
                    if (value < 100) {
                        slice = (slice or (1 shl (7 - (yy - j) / 3)))
                    }
                }
                result += byteArrayOf(slice.toByte())
            }
            result += EposCommands.CTL_LF
        }
        result += EposCommands.HW_INIT
        return result
    }

    fun textToImageEncode(value: String): Bitmap? {
        val bitMatrix: BitMatrix = try {
            MultiFormatWriter().encode(
                value,
                BarcodeFormat.QR_CODE,
                200, 200, null
            )
        } catch (illegalargumentexception: IllegalArgumentException) {
            return null
        }
        val bitMatrixWidth: Int = bitMatrix.width
        val bitMatrixHeight: Int = bitMatrix.height
        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)
        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth
            for (x in 0 until bitMatrixWidth) {
                pixels[offset + x] = if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, 200, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }
}