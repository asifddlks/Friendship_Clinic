package dev.asifddlks.friendshipclinic.utils.extensions

import dev.asifddlks.bhaibhaiclinicApp.utils.extensions.getCurrentMethodName
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

fun JSONObject.hasOrNull(key: String): JSONObject? {
    return try {
        if (this.has(key) && !this.isNull(key)) {
            this
        } else {
            null
        }
    } catch (e: Exception) {
        Timber.e("${this.getCurrentMethodName()} : ${e.message}")
        null
    }

}

fun JSONObject.parseJsonArray(key: String): JSONArray? {
    return try {
        if (this.has(key) && !this.isNull(key)) {
            this.getJSONArray(key)
        } else {
            null
        }
    } catch (e: Exception) {
        Timber.e("${this.getCurrentMethodName()} : ${e.message}")
        null
    }
}

fun JSONObject.parseString(key: String): String? {
    return try {
        if (this.has(key) && !this.isNull(key)) {
            this.getString(key)
        } else {
            null
        }
    } catch (e: Exception) {
        Timber.e("${this.getCurrentMethodName()} : ${e.message}")
        null
    }
}

fun JSONObject.parseLong(key: String): Long? {
    return try {
        if (this.has(key) && !this.isNull(key)) {
            this.getLong(key)
        } else {
            null
        }
    } catch (e: Exception) {
        Timber.e("${this.getCurrentMethodName()} : ${e.message}")
        null
    }
}

fun JSONObject.parseInt(key: String): Int? {
    return try {
        if (this.has(key) && !this.isNull(key)) {
            this.getInt(key)
        } else {
            null
        }
    } catch (e: Exception) {
        Timber.e("${this.getCurrentMethodName()} : ${e.message}")
        null
    }
}

fun JSONObject.parseDouble(key: String): Double? {
    return try {
        if (this.has(key) && !this.isNull(key)) {
            this.getDouble(key)
        } else {
            null
        }
    } catch (e: Exception) {
        Timber.e("${this.getCurrentMethodName()} : ${e.message}")
        null
    }
}

fun JSONObject.parseBoolean(key: String): Boolean? {
    return try {
        if (this.has(key) && !this.isNull(key)) {
            this.getBoolean(key)
        } else {
            null
        }
    } catch (e: Exception) {
        Timber.e("${this.getCurrentMethodName()} : ${e.message}")
        null
    }
}