package com.example.petmatch

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PetApiService {

    @Headers("User-Agent: PetMatchAcademicApp/1.0")
    @GET("search")
    suspend fun buscarLocalizacao(
        @Query("q") localizacao: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 1
    ): List<LocalizacaoResponse>
}

data class LocalizacaoResponse(
    val display_name: String,
    val lat: String,
    val lon: String
)