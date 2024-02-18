/*******************************************************************************
 * Copyright TVL. 2023. All rights reserved.
 * Last modified by asifAhmed on 6/23/23, 11:32 AM
 ******************************************************************************/

package dev.asifddlks.bhaibhaiclinicApp.utils.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by
 * Asif Ahmed
 * Senior Software Engineer, TVL
 *23/8/22 - .
 */

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)

