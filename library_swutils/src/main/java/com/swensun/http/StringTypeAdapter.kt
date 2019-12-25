package com.swensun.http

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class StringTypeAdapter : TypeAdapter<String>() {
    override fun write(out: JsonWriter?, value: String?) {
        try {
            if (value == null) {
                out?.nullValue()
                return
            }
            out?.value(value)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun read(`in`: JsonReader?): String {
        try {
            if (`in`?.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return ""
            }
            return `in`?.nextString() ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}