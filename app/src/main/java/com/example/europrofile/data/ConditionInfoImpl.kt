package com.example.europrofile.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.europrofile.domain.ConditionInfo
import com.example.europrofile.domain.WebPageData
import com.example.europrofile.ui.tabs.main.condition.CondTypeCard
import com.example.europrofile.ui.tabs.main.condition.Conditioner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import javax.inject.Inject

class ConditionInfoImpl @Inject constructor() : ConditionInfo {

    private val url = "https://dantex.ru/products/home/onwall/"

    private val _condLiveData : MutableLiveData<List<CondTypeCard>> = MutableLiveData()
    val conditionLiveData : LiveData<List<CondTypeCard>> = _condLiveData

    override suspend fun getConditionsList() : RequestResult<List<CondTypeCard>> = withContext(Dispatchers.IO) {
        try {

            val conditionsList = mutableListOf<CondTypeCard>()
            val document = Jsoup.connect(url).get()
            val elements = document.select("div[class=ctl-list]")

            for (item in 0 until elements.size){

                val linkArray = mutableListOf<String>()
                val listOfConditions = mutableListOf<Conditioner>()

                val title = elements.select("div[class=ctl-hdr]")
                    .select("div[class=side-right]")
                    .select("h2")
                    .select("span")
                    .eq(item)
                    .text()

                val conditionsBlock = elements.select("div[id=ctl-wrap$item]").select("table[class=ctl-table]").select("tbody").select("tr")

                for (conditions in 0 until  conditionsBlock.size){

                    val price = conditionsBlock.eq(conditions).select("td")
                        .eq(3)
                        .select("div[class=cur-price]")
                        .select("div[class=rub]")
                        .text()

                    if (price.isEmpty()){
                        continue
                    }

                    val name = conditionsBlock.select("th")
                        .select("a")
                        .eq(conditions)
                        .text()

                    val link =
                        "https://dantex.ru" +
                        conditionsBlock.select("th").eq(conditions)
                        .select("a")
                        .attr("href")

                    if (linkArray.isEmpty()){
                        linkArray.addAll(getImages(link))
                    }

                    listOfConditions.add(Conditioner(linkArray, name, price, link))
                }

                conditionsList.add(
                    CondTypeCard(
                    title, listOfConditions
                )
                )


/*                withContext(Dispatchers.Main){
                    _condLiveData.value = _condLiveData.value.orEmpty() + CondTypeCard(
                        title, listOfConditions
                    )
                }*/

            }

            RequestResult.Success(conditionsList)

        } catch (e: IOException){
           RequestResult.Error(e)
        }
    }

    private fun getImages(link: String) : List<String>  {
        val list = mutableListOf<String>()
        val document = Jsoup.connect(link).get()

        val block = document.select("div[class=prod-thumb]").select("a")

        for (item in 0 until block.size) {

            val imgLink = buildString {
                append("https://dantex.ru")
                append(
                block.select("a")
                    .eq(item)
                    .attr("href")
                )
            }

            list.add(imgLink)

        }

        return list
    }

    override suspend fun getPage(url : String, imgList: List<String>) : RequestResult<WebPageData> = withContext(Dispatchers.IO) {
        try {

            val hashMap : LinkedHashMap<String, String> = linkedMapOf()

            val doc: Document = Jsoup.connect(url).get()

            val title: String = doc.select("div[class=main-header]").select("h1").text()

            val productTable = doc.select("div[class=prod-block]").select("div[class=prod-value]").select("dl")
            Log.i("QWERTY", productTable.toString())

            for (list in 0 until productTable.size){
                val key = productTable.select("dt").eq(list).text()
                val value = productTable.select("dd").eq(list).text()

                hashMap[key] = value
            }

            val description: String = doc.select("div[class=prod-wide-block content]").text()

            val price = doc.select("div[class=price-block]").select("div[class=rub]").text()

            RequestResult.Success(WebPageData(title, imgList, hashMap, price, description))

        } catch (e: IOException){
            RequestResult.Error(e)
        }
    }

}