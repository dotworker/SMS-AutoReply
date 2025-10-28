package de.freestyler.smsauto_reply

import android.content.Context
import android.content.SharedPreferences

object AutoReplySettings {

    private const val PREF_NAME = "AutoReplySettings"
    private const val KEY_ENABLED = "auto_reply_enabled"
    private const val KEY_KEYWORD = "keyword"
    private const val KEY_REPLY_MESSAGE = "reply_message"
    private const val KEY_ALLOWED_NUMBERS = "allowed_numbers" // NEU
    private const val KEY_DELAY_ENABLED = "delay_enabled" // NEU

    fun saveSettings(
        context: Context,
        enabled: Boolean,
        keyword: String,
        replyMessage: String,
        allowedNumbers: String,
        delayEnabled: Boolean // NEU
    ) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putBoolean(KEY_ENABLED, enabled)
            putString(KEY_KEYWORD, keyword)
            putString(KEY_REPLY_MESSAGE, replyMessage)
            putString(KEY_ALLOWED_NUMBERS, allowedNumbers)
            putBoolean(KEY_DELAY_ENABLED, delayEnabled) // NEU
            apply()
        }
    }

    fun isAutoReplyEnabled(context: Context): Boolean {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_ENABLED, false)
    }

    fun getKeyword(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_KEYWORD, "") ?: ""
    }

    fun getReplyMessage(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_REPLY_MESSAGE, context.getString(R.string.default_reply_message)) ?: context.getString(R.string.default_reply_message)
    }

    // NEU: Erlaubte Nummern abrufen
    fun getAllowedNumbers(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_ALLOWED_NUMBERS, "") ?: ""
    }
    // NEU: Delay-Einstellung abrufen
    fun isDelayEnabled(context: Context): Boolean {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_DELAY_ENABLED, false)
    }
}
