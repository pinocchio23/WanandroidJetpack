package op.github.home.repo

import androidx.paging.PagingConfig
import op.github.home.api.HomeService
import op.github.home.dao.db.AppDatabase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import op.github.common.base.BaseRepository
import op.github.common.network.net.StateLiveData
import op.github.home.bean.ArticleData
import op.github.home.bean.BannerData
import op.github.home.bean.DailyQuestionData
import op.github.home.bean.SquareData
import op.github.home.repo.data.DailyQuestionPagingSource
import op.github.home.repo.data.SquarePagingDataSource
import op.github.home.repo.inDb.ArticleRemoteMediator

/**
 * Author: pk
 * Time: 2022/2/8  5:19 下午
 * Description:
 */

private const val TAG = "HomeRepo"

@ExperimentalPagingApi
class HomeRepo(private val service: HomeService, private val db: AppDatabase) : BaseRepository() {
    private var mArticleType: Int = 0

    companion object {
        private const val PAGE_SIZE = 10
        val config = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = 5,
            initialLoadSize = 10,
            enablePlaceholders = false,
            maxSize = PAGE_SIZE * 3
        )
    }

    /**
     * 请求首页banner
     */
    suspend fun getBanner(bannerLiveData: StateLiveData<List<BannerData>>) {
        executeResp({ service.getBanner() }, bannerLiveData)
    }

    private val pagingSourceFactory = {
        db.articleDao().queryLocalArticle(mArticleType)
    }

    /**
     * 请求首页文章，
     * Room+network进行缓存
     */
    fun getHomeArticle(articleType: Int): Flow<PagingData<ArticleData>> {
        mArticleType = articleType
        return Pager(
            config = config,
            remoteMediator = ArticleRemoteMediator(service, db, 1),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    /**
     * 请求每日一问
     */
    fun getDailyQuestion(): Flow<PagingData<DailyQuestionData>> {

        return Pager(config) {
            DailyQuestionPagingSource(service)
        }.flow
    }

    /**
     * 请求广场数据
     */
    fun getSquareData(): Flow<PagingData<SquareData>> {
        return Pager(config) {
            SquarePagingDataSource(service)
        }.flow
    }


}