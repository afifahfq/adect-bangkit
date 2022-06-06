package com.example.adect.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adect.R
import com.example.adect.adapter.ListArticleAdapter
import com.example.adect.databinding.FragmentArticleBinding
import com.example.adect.models.Article
import com.example.adect.viewmodels.ArticleViewModel

class ArticleFragment : Fragment() {
    private lateinit var mLiveDataList: ArticleViewModel
    private lateinit var rvArticles: RecyclerView
    private var _binding: FragmentArticleBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_article, container, false)
        _binding = FragmentArticleBinding.inflate(inflater, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvArticles = view.findViewById(R.id.rv_articles)
        rvArticles.setHasFixedSize(true)

        mLiveDataList = ViewModelProvider(this)[ArticleViewModel::class.java]
        subscribe()
        mLiveDataList.getArticles()
    }

    private fun subscribe() {
        val statusObserver = Observer<Boolean> { aStatus ->
            showLoading(aStatus)
        }
        mLiveDataList.getStatus().observe(viewLifecycleOwner, statusObserver)

        val articlesObserver = Observer<ArrayList<Article>?> { aList ->
            showRecyclerList(aList)
        }
        mLiveDataList.getListArticles().observe(viewLifecycleOwner, articlesObserver)
    }

    private fun showRecyclerList(aList: ArrayList<Article>) {
        rvArticles.layoutManager = LinearLayoutManager(activity)

        val listArticleAdapter = ListArticleAdapter(aList)
        rvArticles.adapter = listArticleAdapter

        listArticleAdapter.setOnItemClickCallback(object : ListArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Article) {
//                val detailArticleIntent = Intent(context, DetailArticleActivity::class.java)
//                detailArticleIntent.putExtra(DetailArticleActivity.EXTRA_USER, data)

                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
                startActivity(browserIntent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            val progressBar: ProgressBar = requireView().findViewById(R.id.progressBar)
            progressBar.visibility = View.VISIBLE
        } else {
            val progressBar: ProgressBar = requireView().findViewById(R.id.progressBar)
            progressBar.visibility = View.GONE
        }
    }
}