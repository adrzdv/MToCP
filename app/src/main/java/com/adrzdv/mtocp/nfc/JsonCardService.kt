package com.adrzdv.mtocp.nfc

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import kotlin.math.min

class JsonCardService : HostApduService() {

    companion object {
        @Volatile
        var jsonToSend: String = "{}"

        @Volatile
        var onTransferComplete: (() -> Unit)? = null
    }

    private val SELECT_AID_COMMAND = byteArrayOf(
        0x00.toByte(), 0xA4.toByte(), 0x04.toByte(), 0x00.toByte(),
        0x07.toByte(),
        0xF0.toByte(), 0x12.toByte(), 0x34.toByte(), 0x56.toByte(),
        0x78.toByte(), 0x90.toByte(), 0x00.toByte()
    )

    private val STATUS_OK = byteArrayOf(0x90.toByte(), 0x00.toByte())
    private val STATUS_FAIL = byteArrayOf(0x6F.toByte(), 0x00.toByte())
    private val DEFAULT_CHUNK_SIZE = 200

    @Volatile
    private var lastSentBytes: ByteArray? = null

    @OptIn(ExperimentalStdlibApi::class)
    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        Log.d("JsonCardService", "processCommandApdu called with: ${commandApdu?.toHexString()}")
        if (commandApdu == null) return STATUS_FAIL

        if (commandApdu.contentEquals(SELECT_AID_COMMAND)) {
            val data = jsonToSend.toByteArray(StandardCharsets.UTF_8)
            lastSentBytes = data
            val totalLen = data.size
            val header = ByteBuffer.allocate(4 + 1)
                .putInt(totalLen)
                .put(DEFAULT_CHUNK_SIZE.toByte())
                .array()
            return header + STATUS_OK
        }

        if (commandApdu.size >= 4) {
            val ins = commandApdu[1].toInt() and 0xFF
            when (ins) {
                0xB0 -> {
                    if (commandApdu.size < 5) return STATUS_FAIL
                    val p1 = commandApdu[2].toInt() and 0xFF
                    val p2 = commandApdu[3].toInt() and 0xFF
                    val index = (p1 shl 8) or p2
                    val bytes = lastSentBytes ?: return STATUS_FAIL
                    val chunkSize = DEFAULT_CHUNK_SIZE
                    val offset = index * chunkSize
                    if (offset >= bytes.size) return STATUS_FAIL
                    val end = min(offset + chunkSize, bytes.size)
                    val part = bytes.copyOfRange(offset, end)
                    return part + STATUS_OK
                }

                0xB1 -> {
                    lastSentBytes = null
                    onTransferComplete?.invoke()
                    onTransferComplete = null
                    return STATUS_OK
                }

                else -> return STATUS_FAIL
            }
        }
        return STATUS_FAIL
    }

    override fun onDeactivated(reason: Int) {
        lastSentBytes = null
    }
}