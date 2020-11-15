package com.example.bannerdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.banner.BannerAdapter
import com.example.banner.bean.DataEntry
import com.example.bannerdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private val responseEntries = mutableListOf<DataEntry>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getData()
    }

    private fun getData(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = Http.call("https://gank.io/api/v2/banners")
            val json = JSONObject(response).getJSONArray("data")
            for (i in 0 until json.length()) {
                val array = json.getJSONObject(i)
                responseEntries.add(DataEntry(array.getString("image"), array.getString("title"), array.getString("url")))
            }
            withContext(Dispatchers.Main){
                binding.mainBanner.setDataAndClickListener(responseEntries, object : BannerAdapter.BannerClickListener {
                    override fun onClick(view: View, data: DataEntry) {
                        val intent = Intent(this@MainActivity, WebActivity::class.java)
                        intent.putExtra("url", data.url)
                        startActivity(intent)
                    }
                })
                Log.d("TAG_02", "getData: $json")
            }
        }
    }

}