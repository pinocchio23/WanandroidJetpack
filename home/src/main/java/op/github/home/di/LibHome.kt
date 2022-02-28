package op.github.home.di

import androidx.paging.ExperimentalPagingApi
import op.github.common.network.net.RetrofitManager
import op.github.home.api.HomeService
import op.github.home.dao.db.AppDatabase
import op.github.home.repo.HomeRepo
import op.github.home.viewmodel.ArticleViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Author: pk
 * Time: 2022/2/15  10:49 上午
 * Description:
 */
@ExperimentalPagingApi
val moduleHome = module {
    single {
        RetrofitManager.initRetrofit().getService(HomeService::class.java)
    }

    single {
        AppDatabase.get(androidApplication())
    }

    single {
        HomeRepo(get(),get())
    }

    viewModel { ArticleViewModel(get()) }
}