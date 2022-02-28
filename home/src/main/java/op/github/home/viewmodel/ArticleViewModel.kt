package op.github.home.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import op.github.common.base.BaseViewModel
import op.github.common.network.net.StateLiveData
import op.github.home.bean.ArticleData
import op.github.home.bean.BannerData
import op.github.home.bean.DailyQuestionData
import op.github.home.bean.SquareData
import op.github.home.repo.HomeRepo

/**
 * Author: pk
 * Time: 2022/2/8  4:44 下午
 * Description:
 */

private const val TAG = "ArticleViewModel"

@ExperimentalPagingApi
class ArticleViewModel(private val repo: HomeRepo) : BaseViewModel() {
    val bannerLiveData = StateLiveData<List<BannerData>>()

    fun loadBanner(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getBanner(bannerLiveData)
        }
    }

    /**
     * 请求首页文章数据
     */
    fun articlePagingFlow(): Flow<PagingData<ArticleData>> =
        repo.getHomeArticle(1).cachedIn(viewModelScope)


    /**
     * 请求每日一问数据
     */
    fun dailyQuestionPagingFlow(): Flow<PagingData<DailyQuestionData>> =
        repo.getDailyQuestion().cachedIn(viewModelScope)

    /**
     * 查询广场数据
     */
    fun squarePagingFlow(): Flow<PagingData<SquareData>> =
        repo.getSquareData().cachedIn(viewModelScope)

}