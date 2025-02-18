package com.example.pendaftaranapps.data.retrofit

import retrofit2.Call
import com.example.pendaftaranapps.data.response.ListSiswaResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api-list-siswa.php")
    fun getAllSiswa(): Call<ListSiswaResponse>


}