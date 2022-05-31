package com.sviridov.icerockprac

import android.content.Context
import android.graphics.Color
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class LanguageColorsRepository(
    context: Context
) : ILanguageColorsRepository {
    private val _colorsDict = context.resources.openRawResource(R.raw.language_colors).bufferedReader().use {
        it.readText()
    }.let {
        Json.decodeFromString<Map<String, String?>>(it)
    }

    override fun getLangColor(language: String): Int?
    = _colorsDict[language]?.let { Color.parseColor(it) }
}