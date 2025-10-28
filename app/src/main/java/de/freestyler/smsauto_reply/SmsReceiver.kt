package de.freestyler.smsauto_reply

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log
import kotlin.random.Random

class SmsReceiver : BroadcastReceiver() {

    private val TAG = "SmsReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {

            if (!AutoReplySettings.isAutoReplyEnabled(context)) {
                return
            }

            val bundle: Bundle? = intent.extras
            bundle?.let {
                try {
                    val pdus = it["pdus"] as? Array<*>
                    pdus?.forEach { pdu ->
                        val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                        val senderNumber = smsMessage.displayOriginatingAddress
                        val messageBody = smsMessage.displayMessageBody

                        Log.d(TAG, "SMS empfangen von: $senderNumber, Nachricht: $messageBody")

                        // NEU: Prüfe sowohl Schlüsselwort als auch erlaubte Absender
                        val keyword = AutoReplySettings.getKeyword(context)
                        val hasKeyword = messageBody.contains(keyword, ignoreCase = true)
                        val isFromAllowedSender = isNumberAllowed(context, senderNumber)

                        if (hasKeyword && isFromAllowedSender) {
                            val replyMessage = AutoReplySettings.getReplyMessage(context)
                            val isDelayEnabled = AutoReplySettings.isDelayEnabled(context)

                            if (isDelayEnabled) {
                                // NEU: Zufällige Verzögerung zwischen 0-10 Sekunden
                                val delayMillis = Random.nextInt(0, 10001).toLong() // 0-10000ms
                                Log.d(TAG, "Auto-Reply wird mit ${delayMillis}ms Verzögerung gesendet")

                                Handler(Looper.getMainLooper()).postDelayed({
                                    sendAutoReply(senderNumber, replyMessage)
                                    Log.d(TAG, "Auto-Reply verzögert gesendet an: $senderNumber")
                                }, delayMillis)
                            } else {
                                // Sofort senden ohne Verzögerung
                                sendAutoReply(senderNumber, replyMessage)
                                Log.d(TAG, "Auto-Reply sofort gesendet an: $senderNumber")
                            }
                        } else {
                            if (!hasKeyword) {
                                Log.d(TAG, "Schlüsselwort nicht gefunden: $keyword")
                            }
                            if (!isFromAllowedSender) {
                                Log.d(TAG, "Absender nicht in erlaubter Liste: $senderNumber")
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Fehler beim Verarbeiten der SMS", e)
                }
            }
        }
    }
    // VERBESSERT: Exakte Nummernüberprüfung
    private fun isNumberAllowed(context: Context, senderNumber: String?): Boolean {
        if (senderNumber == null) return false

        val allowedNumbers = AutoReplySettings.getAllowedNumbers(context)

        // Wenn keine Nummern definiert sind, erlaube alle
        if (allowedNumbers.isEmpty()) return true

        // Normalisiere die Absender-Nummer
        val normalizedSender = normalizePhoneNumber(senderNumber)

        // Teile die erlaubten Nummern (getrennt durch Komma oder Semikolon)
        val numberList = allowedNumbers.split(",", ";")
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        // Prüfe exakte Übereinstimmung mit normalisierten Nummern
        return numberList.any { allowedNumber ->
            val normalizedAllowed = normalizePhoneNumber(allowedNumber)
            normalizedSender == normalizedAllowed
        }
    }

    // NEU: Funktion zur Normalisierung von Telefonnummern
    private fun normalizePhoneNumber(phoneNumber: String): String {
        // Entferne alle Leer- und Sonderzeichen außer +
        var normalized = phoneNumber.replace(Regex("[\\s\\-\\(\\)\\.]"), "")

        // Deutsche Nummern normalisieren
        when {
            // +49 Format beibehalten
            normalized.startsWith("+49") -> {
                // nichts ändern
            }
            // 0049 zu +49
            normalized.startsWith("0049") -> {
                normalized = "+49${normalized.substring(4)}"
            }
            // Deutsche 0-Vorwahl zu +49
            normalized.startsWith("0") && normalized.length > 3 -> {
                normalized = "+49${normalized.substring(1)}"
            }
            // Keine Vorwahl -> deutsche +49 hinzufügen
            !normalized.startsWith("+") && normalized.length >= 10 -> {
                normalized = "+49$normalized"
            }
        }

        Log.d(TAG, "Normalisierte Nummer: $phoneNumber -> $normalized")
        return normalized
    }

    private fun sendAutoReply(phoneNumber: String?, message: String?) {
        try {
            val smsManager = SmsManager.getDefault()
            if (phoneNumber != null && message != null) {
                smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Fehler beim Senden der Auto-Reply", e)
        }
    }
}
