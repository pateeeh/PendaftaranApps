package com.example.pendaftaranapps

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pendaftaranapps.data.response.AddUpdateResponse
import com.example.pendaftaranapps.data.response.DataItem
import com.example.pendaftaranapps.data.retrofit.ApiConfig
import com.example.pendaftaranapps.databinding.ActivityAddUpdateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddUpdateActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddUpdateBinding
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val siswa = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra(EXTRA_DATA, DataItem::class.java)
        }else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<DataItem>(EXTRA_DATA)
        }

        if (siswa!=null){
            id = siswa.id!!.toInt()

            binding.btnSaveUpdate.text = getString(R.string.update)
            binding.btnDelet.visibility= View.VISIBLE
            binding.btnDelet.setOnClickListener{
                deleteSiswa(id)
            }

            binding.etNama.setText("${siswa.nama}")
            binding.etAlamat.setText("${siswa.alamat}")
            binding.etJenisKelamin.setText("${siswa.jenisKelamin}")
            binding.etAgama.setText("${siswa.agama}")
            binding.etAsalSekolah.setText("${siswa.sekolahAsal}")
        }else {
            binding.btnSaveUpdate.text = getString(R.string.save)
            binding.btnDelet.visibility = View.GONE
        }




        binding.btnSaveUpdate.setOnClickListener{
            val nama = binding.etNama.text.toString()
            val alamat = binding.etAlamat.text.toString()
            val jenisKelamin = binding.etJenisKelamin.text.toString()
            val agama = binding.etAgama.text.toString()
            val sekolahAsal = binding.etAsalSekolah.text.toString()

            if(nama.isEmpty()){
                binding.etNama.error = getString(R.string.error)
            }else if(alamat.isEmpty()){
                binding.etAlamat.error = getString(R.string.error)
            }else if(jenisKelamin.isEmpty()){
                binding.etJenisKelamin.error = getString(R.string.error)
            }else if(agama.isEmpty()){
                binding.etAgama.error = getString(R.string.error)
            }else if(sekolahAsal.isEmpty()){
                binding.etAsalSekolah.error = getString(R.string.error)
            }else{
                if (binding.btnSaveUpdate.text == getString(R.string.save)){
                    insertSiswa(nama, alamat, jenisKelamin, agama, sekolahAsal)
                }else if (binding.btnSaveUpdate.text == getString(R.string.update)){
                    updateSiswa(id,nama, alamat, jenisKelamin, agama,sekolahAsal)
                }
            }
        }
    }

    private fun deleteSiswa(id: Int) {
        showLoading(true)

        val client = ApiConfig.getApiService().deleteSiswa(id)
        client.enqueue(object : Callback<AddUpdateResponse>{
            override fun onFailure(call: Call<AddUpdateResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@AddUpdateActivity, "onFailure", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(
                call: Call<AddUpdateResponse>,
                response: Response<AddUpdateResponse>
            ) {
                showLoading(false)

                if (response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!= null){
                        Toast.makeText(this@AddUpdateActivity, "success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@AddUpdateActivity, MainActivity:: class.java))
                        finish()
                        }
                    }
                }

        })
    }

    private fun updateSiswa(
        id: Int,
        nama: String,
        alamat: String,
        jenisKelamin: String,
        agama: String,
        sekolahAsal: String
    ) {
        showLoading(true)

        val client = ApiConfig.getApiService().updateSiswa(id,nama, sekolahAsal, jenisKelamin, agama, alamat)
        client.enqueue(object : Callback<AddUpdateResponse>{
            override fun onResponse(
                call: Call<AddUpdateResponse>,
                response: Response<AddUpdateResponse>
            ) {
                showLoading(false)

                if (response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!= null){
                        Toast.makeText(this@AddUpdateActivity, "success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@AddUpdateActivity, MainActivity:: class.java))
                        finish()
                    }else{
                        Toast.makeText(this@AddUpdateActivity,"null", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@AddUpdateActivity, "failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddUpdateResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@AddUpdateActivity, "onFailure", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun insertSiswa(
        nama: String,
        alamat: String,
        jenisKelamin: String,
        agama: String,
        sekolahAsal: String
    ) {
        showLoading(true)

        val client = ApiConfig.getApiService().addSiswa(nama, alamat,jenisKelamin, agama, sekolahAsal)
        client.enqueue(object: Callback<AddUpdateResponse>{
            override fun onFailure(call: Call<AddUpdateResponse>, t: Throwable) {
                showLoading(true)
                Toast.makeText(this@AddUpdateActivity,"onFailure", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<AddUpdateResponse>,
                response: Response<AddUpdateResponse>
            ) {
                showLoading(false)

                if (response.isSuccessful) {
                    val responsBody = response.body()

                    if (responsBody != null) {
                        Toast.makeText(this@AddUpdateActivity, "success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@AddUpdateActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@AddUpdateActivity, "null", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(this@AddUpdateActivity,"failed", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}