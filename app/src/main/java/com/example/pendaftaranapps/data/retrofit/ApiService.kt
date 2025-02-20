package com.example.pendaftaranapps.data.retrofit

import com.example.pendaftaranapps.data.response.AddUpdateResponse
import retrofit2.Call
import com.example.pendaftaranapps.data.response.ListSiswaResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("api-list-siswa.php")
    fun getAllSiswa(): Call<ListSiswaResponse>

    @FormUrlEncoded
    @POST("api-add-siswa.php")
    fun addSiswa(
        @Field("nama") nama: String,
        @Field("sekolah_asal") sekolahAsal: String,
        @Field("jenis_kelamin") jenisKelamin: String,
        @Field("agama") agama: String,
        @Field("alamat") alamat: String
    ): Call<AddUpdateResponse>

    @FormUrlEncoded
    @POST("api-update-siswa.php")
    fun updateSiswa(
        @Query("id") id: Int,
        @Field("nama") nama: String,
        @Field("sekolah_asal") sekolahAsal: String,
        @Field("jenis_kelamin") jenisKelamin: String,
        @Field("agama") agama: String,
        @Field("alamat") alamat: String
    ): Call<AddUpdateResponse>

    @POST("api-delete-siswa.php")
    fun deleteSiswa(
        @Query("id") id: Int
    ): Call<AddUpdateResponse>
}