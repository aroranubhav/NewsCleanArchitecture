package com.maxi.newscleanarchitecture.data.remote.utils

object ApiConstants {

    object BaseUrls {

        const val BASE_URL = "https://newsapi.org/v2/"
    }

    object EndPoints {

        const val TOP_HEADLINES = "top-headlines"
    }

    object QueryParams {

        const val COUNTRY = "country"
        const val LANGUAGE = "language"
        const val DEFAULT_COUNTRY = "us"
        const val DEFAULT_LANGUAGE = "en"
    }

    object Headers {
        const val API_KEY = "X-Api-Key"
        const val USER_AGENT = "Sudo-Maxi"
    }

}