package com.sviridov.icerockprac

interface ILanguageColorsRepository {
    /**
     * @param hex - hex-color string
     * @return int-color.
     */
    fun getLangColor(language: String): Int?
}