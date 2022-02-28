package op.github.home.api

import op.github.common.network.BasePagingResp
import op.github.common.network.BaseResp
import op.github.home.bean.ArticleData
import op.github.home.bean.BannerData
import op.github.home.bean.DailyQuestionData
import op.github.home.bean.SquareData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Author: pk
 * Time: 2022/2/8  5:25 下午
 * Description:
 */

interface HomeService {


    @GET("article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page") page: Int): BaseResp<BasePagingResp<List<ArticleData>>>


    @GET("banner/json")
    suspend fun getBanner(): BaseResp<List<BannerData>>

    @GET("wenda/list/{page}/json")
    suspend fun getDailyQuestion(@Path("page") page: Int): BaseResp<BasePagingResp<List<DailyQuestionData>>>

    @GET("user_article/list/{page}/json")
    suspend fun getSquareData(@Path("page") page: Int): BaseResp<BasePagingResp<List<SquareData>>>
}