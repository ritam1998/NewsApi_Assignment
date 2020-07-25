package com.example.dynamictabview

import android.util.Log
import com.example.dynamictabview.Fragment.DynamicFragment
import com.example.dynamictabview.NewsApi.Api
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class FetchData {

    private var BASE_URL = "http://newsapi.org/v2/"
    private var API_KEY = "a8a3e9daff5c4c08b605c76c7182cf1c"
    private var COUNTRY = "us"
    private var BUSINESS = "business"

    var retrofit = Retrofit.Builder()

    fun fetchData(getAllDataListner: GetAllDataListner){

        retrofit.baseUrl(BASE_URL)
        retrofit.addConverterFactory(GsonConverterFactory.create(Gson()))

        val postTrendingNewsApi = retrofit.build().create(Api::class.java)

       /* callApi.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Failed", "${t.message}")
            }
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (!response.isSuccessful) {
                    Log.e("Load Failed", "${response.message()}")
                }
                val getAllData = response.body()?.toString()
                Log.e("sucess","${getAllData}")
                if (getAllData != null) {
                    getAllDataListner?.getAllDataListner(getAllData)
                }
            }
        })*/

        val callAllTrendingNewsPosts = postTrendingNewsApi.getAllTrendingNews(COUNTRY,BUSINESS,API_KEY)

        callAllTrendingNewsPosts.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Failed","${t.message}")
            }
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (!response.isSuccessful) {
                    Log.e("EROR", " ${response.code()}")
                } else {
                    val getAllPostData = response.body().toString()
                    Log.e("All Trending", "${getAllPostData}")

                    getAllDataListner.getAllDataListner(worldTrendingNews = getAllPostData)
                }
            }
        })
    }

    fun particularNews(journalName: String?,particularDataListner: ParticularDataListner){

        Log.e("jounalNews","$journalName")
        retrofit.baseUrl(BASE_URL)
        retrofit.addConverterFactory(GsonConverterFactory.create(Gson()))

        val newsData = retrofit.build().create(Api :: class.java)
        val getNewsData = journalName?.let { newsData.getParticularNews(it,COUNTRY,BUSINESS,API_KEY) }

        getNewsData?.enqueue(object : Callback<JsonObject>{
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Data Loading Failed","${t.message}")
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try{
                    if(!response.isSuccessful){
                        Log.e("ERROR","${response.code()}")
                    }else{
                        val getNewsData = response.body().toString()
                        Log.e("getData","$getNewsData")
                        particularDataListner.particularDataListner(getNewsData)
                    }
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        })
    }
}
interface GetAllDataListner{
    fun getAllDataListner(worldTrendingNews : String)
}
interface ParticularDataListner{
    fun particularDataListner(worldNews : String)
}