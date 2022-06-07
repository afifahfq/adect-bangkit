package com.example.adect.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.adect.api.ApiConfig
import com.example.adect.models.Article
import com.example.adect.models.ArticlesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel: ViewModel() {
    val loadingStatus = MutableLiveData<Boolean>()
    val mArticles = MutableLiveData<ArrayList<Article>>()
    val articles = ArrayList<Article>()

    fun getArticles() {
        articles.clear()
        mArticles.postValue(articles)
        loadingStatus.postValue(true)

        val client = ApiConfig.getApiService().getArticles()
        client.enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                val responseBody = response.body()
                if (responseBody!!.status == "ok" && responseBody.totalResults!! > 0) {
                    val result = responseBody.articles!!
                    for (article in result) {
                        val desc = article!!.description!!.replace("\\<(.*?)\\>".toRegex(), "")

                        val curr = Article(
                            article.author.toString(),
                            article.title.toString(),
                            desc,
                            article.url.toString(),
                            article.urlToImage.toString(),
                            article.publishedAt.toString(),
                            article.content.toString()
                        )
                        articles.add(curr)
                    }
                }
                mArticles.postValue(articles)
                loadingStatus.postValue(false)
            }
            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.e("ArticleViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getListArticles(): LiveData<ArrayList<Article>?> {
        return mArticles
    }

    fun getStatus(): MutableLiveData<Boolean> {
        return loadingStatus
    }
}