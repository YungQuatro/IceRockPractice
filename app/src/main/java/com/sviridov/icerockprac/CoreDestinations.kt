package com.sviridov.icerockprac

sealed interface CoreDestinations {
    object Authentication : CoreDestinations
    object RepositoriesList : CoreDestinations
    data class RepositoryDetails(val repo: String) : CoreDestinations
}