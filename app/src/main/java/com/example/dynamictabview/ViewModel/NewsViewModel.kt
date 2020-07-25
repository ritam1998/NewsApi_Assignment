package com.example.dynamictabview.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.dynamictabview.FetchData
import com.example.dynamictabview.Model.Model
import com.example.dynamictabview.ParticularDataListner
import org.json.JSONObject

class NewsViewModel(application: Application) : AndroidViewModel(application),ParticularDataListner {

    private val allDataList = ArrayList<Model>()
    private val context = getApplication<Application>().applicationContext
    private var fetchData: FetchData? = null

    private var model: Model? = null

    var liveData: MutableLiveData<ArrayList<Model>>? = null

    init {
        if (liveData == null) {
            liveData = MutableLiveData()
        }
    }

    override fun particularDataListner(worldNews: String) {

        val worldNewsJsonObject = JSONObject(worldNews ?: "")
        val newsContentjsonArray = worldNewsJsonObject.getJSONArray("articles")

        for (i in 0 until newsContentjsonArray.length()) {

            val getJsonNewsdata = newsContentjsonArray.getJSONObject(i)
            val nameOfJournal = newsContentjsonArray.getJSONObject(i).getJSONObject("source")

            model = Model(
                worldheadLine = getJsonNewsdata.getString("title"),
                worldNewsBody = getJsonNewsdata.getString("description"),
                worldNewsPostDate = getJsonNewsdata.getString("publishedAt"),
                worldImageView = getJsonNewsdata.getString("urlToImage"),
                nameOfJournal = nameOfJournal.getString("name"),
                newsContent = getJsonNewsdata.getString("content"),
                authorName = getJsonNewsdata.getString("author"),
                fullContentRead = getJsonNewsdata.getString("url"),
                isRead = false
            )
            allDataList?.add(model!!)
        }
        newsLiveData(allDataList)
    }
    fun getAllWorldNews(name : String?,context: Context?){

        fetchData = FetchData()
        fetchData?.particularNews(name,this)
    }

    fun newsLiveData(dataList: ArrayList<Model>) {
        liveData?.postValue(dataList)
    }
}