package com.example.dynamictabview.Fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamictabview.FetchData
import com.example.dynamictabview.Model.Model
import com.example.dynamictabview.R
import com.example.dynamictabview.ViewModel.NewsViewModel
import com.example.dynamictabview.adapter.NewsAdapter


class DynamicFragment : Fragment() {

    private var textView : TextView?= null
    private var recyclerView : RecyclerView? = null
    private var newsAdapter : NewsAdapter? = null

    fun newInstance(position: Int, journalName: String): DynamicFragment {

        val fragment = DynamicFragment()
        val args = Bundle()
        args.putInt("someInt", position)
        args.putString("name",journalName)
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.layout_fragment,container,false)
        val name = arguments?.getString("name")

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView?.layoutManager = layoutManager

        newsAdapter = NewsAdapter()

        val noteViewModel =  ViewModelProviders.of(this).get(NewsViewModel::class.java)
        noteViewModel.getAllWorldNews(name,activity?.applicationContext)

        noteViewModel?.liveData?.observe(this, Observer<ArrayList<Model>> { t -> newsAdapter?.setWorldNews(t) })
        recyclerView?.adapter = newsAdapter

        return view
    }
}