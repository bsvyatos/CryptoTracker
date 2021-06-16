package com.example.cryptotracker.utils

import androidx.paging.PagingSource
import com.example.cryptotracker.models.CoinData
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class UtilsTest : TestCase() {
    private lateinit var pageList: List<PagingSource.LoadResult.Page<*, *>>
    private lateinit var listOfCoins: List<CoinData>

    @Before
    override fun setUp() {
        listOfCoins = (1..10).toList().map { CoinData(id = it) }
        val page = mock<PagingSource.LoadResult.Page<Int, CoinData>>()
        Mockito.`when`(page.data).thenReturn(listOfCoins)
        pageList = listOf(page, page)
    }

    @Test
    fun testGetFloat() {
        assert(Utils.getFloat("0.546") == 0.546f)
        assert(Utils.getFloat("-0.546") == -0.546f)
        assert(Utils.getFloat("%  -0.546c") == -0.546f)
        assert(Utils.getFloat("+  sdf-+0.546") == 0.546f)
        assert(Utils.getFloat("---+-0.546") == -0.546f)
    }

    @Test
    fun testNextPageValue() {
        assert(Utils.getNextPageValue(pageList) == 21)
    }

    @Test
    fun testCommaSeparatedIds() {
        assertEquals("1,2,3,4,5,6,7, 8, 9, 10", Utils.getCommaSeparatedIds(listOfCoins))
    }
}