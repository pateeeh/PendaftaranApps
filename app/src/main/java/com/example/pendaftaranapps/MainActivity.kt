package com.example.pendaftaranapps

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pendaftaranapps.data.response.DataItem
import com.example.pendaftaranapps.data.response.ListSiswaResponse
import com.example.pendaftaranapps.data.retrofit.ApiConfig
import com.example.pendaftaranapps.databinding.ActivityMainBinding
import com.example.pendaftaranapps.ui.ListSiswaAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    companion object{
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rcListSiswa.layoutManager = layoutManager
        // membuat instance dari DividerItemDecoration untuk menambahkan garis pemisah antar item di RecyclerView.
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rcListSiswa.addItemDecoration(itemDecoration)

        findAllSiswa()

        binding.fabAdd.setOnClickListener{
            startActivity(Intent(this, AddUpdateActivity::class.java))
        }


    }


    // method untuk memanggil data siswa dari API
    private fun findAllSiswa() {
        showLoading(true)

        // membuat permintaan jaringan menggunakan Retrofit.
        // ApiConfig.getApiService(): Ini memanggil fungsi getApiService() dari kelas ApiConfig.

        // getAllSiswa() adalah fungsi yang kita definisikan di interface ApiService untuk
        // mengambil daftar semua siswa.

        val client = ApiConfig.getApiService().getAllSiswa()

        // client.enqueue(): Adalah metode dari objek Call yang digunakan untuk
        // menjalankan permintaan jaringan secara asynchronous.
        client.enqueue(object : Callback<ListSiswaResponse> {
            override fun onResponse(
                call: Call<ListSiswaResponse>,
                response: Response<ListSiswaResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        setSiswaData(responseBody.data)
                    }
                }else{
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListSiswaResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })

    }

    private fun setSiswaData(dataSiswa: List<DataItem>) {
        val adapter = ListSiswaAdapter()
        adapter.submitList(dataSiswa)
        binding.rcListSiswa.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}