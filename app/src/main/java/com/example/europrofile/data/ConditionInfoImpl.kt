package com.example.europrofile.data

import com.example.europrofile.domain.ConditionInfo
import com.example.europrofile.ui.tabs.main.condition.CondTypeCard
import com.example.europrofile.ui.tabs.main.condition.Conditioner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.io.IOException
import javax.inject.Inject

class ConditionInfoImpl @Inject constructor() : ConditionInfo {

    private val url = "https://dantex.ru/products/home/onwall/"


    override suspend fun getConditionsList() : RequestResult<List<CondTypeCard>> = withContext(Dispatchers.IO) {
        try {

            val conditionsList = mutableListOf<CondTypeCard>()

            val document = Jsoup.connect(url).get()

            val elements = document.select("div[class=ctl-list]")

            for (item in 0 until elements.size){

                val listOfConditions = mutableListOf<Conditioner>()

                val title = elements.select("div[class=ctl-hdr]")
                    .select("div[class=side-right]")
                    .select("h2")
                    .select("span")
                    .eq(item)
                    .text()

                val conditionsBlock = elements.select("div[id=ctl-wrap$item]").select("table[class=ctl-table]").select("tbody").select("th")

                for (conditions in 0 until  conditionsBlock.size){
                    val name = conditionsBlock.select("th")
                        .select("a")
                        .eq(conditions)
                        .text()
                    val price = conditionsBlock.select("div[class=cur-price]")
                        .select("div[class=rub]")
                        .eq(conditions)
                        .text()
                    val link = conditionsBlock.select("th")
                        .select("a")
                        .eq(conditions)
                        .attr("href")

                    listOfConditions.add(Conditioner(listOf(), name, price, link))
                }


                conditionsList.add(
                    CondTypeCard(
                    title, listOfConditions
                )
                )

            }

            RequestResult.Success(conditionsList)

        } catch (e: IOException){
           RequestResult.Error(e)
        }
    }

}