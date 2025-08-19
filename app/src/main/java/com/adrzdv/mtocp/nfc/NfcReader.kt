package com.adrzdv.mtocp.nfc

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.util.Log
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import kotlin.math.min

class NfcReader {

    private val SELECT_AID_COMMAND = byteArrayOf(
        0x00.toByte(), 0xA4.toByte(), 0x04.toByte(), 0x00.toByte(),
        0x07.toByte(),
        0xF0.toByte(), 0x12.toByte(), 0x34.toByte(), 0x56.toByte(),
        0x78.toByte(), 0x90.toByte(), 0x00.toByte()
    )

    private val INS_READ_CHUNK = 0xB0.toByte()
    private val INS_ACK = 0xB1.toByte()

    fun enableReaderMode(activity: Activity, onJsonReceived: (String) -> Unit) {
        val flags = NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK
        val callback = NfcAdapter.ReaderCallback { tag: Tag? ->
            Log.d("NfcReader", "Tag discovered: $tag")
            if (tag == null) return@ReaderCallback
            var iso: IsoDep? = null
            try {
                iso = IsoDep.get(tag)
                iso?.connect()

                val selectResp = iso?.transceive(SELECT_AID_COMMAND)
                if (selectResp == null || selectResp.size < 6) return@ReaderCallback

                val status1 = selectResp[selectResp.size - 2]
                val status2 = selectResp[selectResp.size - 1]
                if (!(status1 == 0x90.toByte() && status2 == 0x00.toByte())) return@ReaderCallback

                val headerLen = selectResp.size - 2
                if (headerLen < 5) return@ReaderCallback
                val header = selectResp.copyOfRange(0, headerLen)
                val totalLen = ByteBuffer.wrap(header.copyOfRange(0, 4)).int
                val chunkSize = header[4].toInt() and 0xFF
                if (totalLen <= 0 || chunkSize <= 0) return@ReaderCallback

                val parts = (totalLen + chunkSize - 1) / chunkSize
                val out = ByteArray(totalLen)
                var filled = 0
                var success = true
                val maxRetries = 3

                for (index in 0 until parts) {
                    var attempt = 0
                    var chunkRead: ByteArray? = null

                    while (attempt < maxRetries && chunkRead == null) {
                        attempt++
                        try {
                            val p1 = ((index shr 8) and 0xFF).toByte()
                            val p2 = (index and 0xFF).toByte()
                            val readApdu =
                                byteArrayOf(0x00.toByte(), INS_READ_CHUNK, p1, p2, 0x00.toByte())
                            val r = iso.transceive(readApdu)
                            if (r == null || r.size < 2) continue
                            val st1 = r[r.size - 2]
                            val st2 = r[r.size - 1]
                            if (st1 == 0x90.toByte() && st2 == 0x00.toByte()) {
                                chunkRead = r.copyOfRange(0, r.size - 2)
                            }
                        } catch (_: Exception) {
                            // игнорируем, чтобы retry
                        }
                    }

                    if (chunkRead == null) {
                        success = false
                        break
                    }

                    val copyLen = min(chunkRead.size, totalLen - filled)
                    System.arraycopy(chunkRead, 0, out, filled, copyLen)
                    filled += copyLen
                }

                if (!success || filled != totalLen) return@ReaderCallback

                val ackApdu =
                    byteArrayOf(0x00.toByte(), INS_ACK, 0x00.toByte(), 0x00.toByte(), 0x00.toByte())
                val ackResp = iso.transceive(ackApdu)
                if (ackResp != null && ackResp.size >= 2 &&
                    ackResp[ackResp.size - 2] == 0x90.toByte() && ackResp[ackResp.size - 1] == 0x00.toByte()
                ) {
                    val json = String(out, StandardCharsets.UTF_8)
                    onJsonReceived(json)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    iso?.close()
                } catch (_: Exception) {
                }
            }
        }

        activity.runOnUiThread {
            val adapter = NfcAdapter.getDefaultAdapter(activity)
            adapter?.enableReaderMode(activity, callback, flags, null)
        }
    }

    fun disableReaderMode(activity: Activity) {
        val adapter = NfcAdapter.getDefaultAdapter(activity)
        adapter?.disableReaderMode(activity)
    }
}