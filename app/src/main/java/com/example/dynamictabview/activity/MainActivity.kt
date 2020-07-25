package com.example.dynamictabview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.dynamictabview.FetchData
import com.example.dynamictabview.GetAllDataListner
import com.example.dynamictabview.Model.JournalName
import com.example.dynamictabview.R
import com.example.dynamictabview.adapter.PagerAdapter
import com.google.android.material.tabs.TabLayout
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), GetAllDataListner {

    private var tabLayout : TabLayout?= null
    private var toolBar : Toolbar? = null
    private var viewPager : ViewPager? = null
    private var fetchData : FetchData? = null
    private var pagerAdapter : PagerAdapter? = null
    private var dataList : ArrayList<JournalName>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolBar = findViewById(R.id.toolbar2)
        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.frameLayout)

        fetchData = FetchData()
        fetchData?.fetchData(this)
    }

    override fun getAllDataListner(worldTrendingNews: String) {

        val worldNewsJsonObject = JSONObject(worldTrendingNews)
        val newsContentjsonArray = worldNewsJsonObject.getJSONArray("articles")
        getAllId(newsContentjsonArray)
    }

    private fun getAllId(newsContentjsonArray: JSONArray) {

        dataList = ArrayList()

        for(i in 0 until newsContentjsonArray.length()){

            val getJsonNewsdata = newsContentjsonArray.getJSONObject(i)
            val nameOfJournal = newsContentjsonArray.getJSONObject(i).getJSONObject("source")
            dataList?.add(
                JournalName(
                    journalName = nameOfJournal.getString(
                        "name"
                    )
                )
            )
        }

        val removeDuplicateName = removeDuplicateJournalName(dataList)

        for(newsName in removeDuplicateName.iterator()){
            Log.e("remove","${newsName.journalName}")
            tabLayout?.newTab()?.setText(newsName.journalName)?.let{ tabLayout?.addTab(it) }
        }

        pagerAdapter = PagerAdapter(
            supportFragmentManager,
            tabCount = tabLayout?.tabCount,
            journalName = removeDuplicateName
        )

        viewPager?.adapter = pagerAdapter
        viewPager?.offscreenPageLimit = 4

        viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout?.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { viewPager?.setCurrentItem(it) }
            }
        })
    }

    private fun removeDuplicateJournalName(dataList: ArrayList<JournalName>?): ArrayList<JournalName> {

        val newsNameList = ArrayList<JournalName>()
        if (dataList != null) {
            for(element in dataList){
                if(!newsNameList.contains(element)){
                    newsNameList.add(element)
                }
            }
        }
        return newsNameList
    }
}
