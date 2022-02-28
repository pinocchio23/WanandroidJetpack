package op.github.home.ui

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collectLatest
import op.github.common.base.BaseFragment
import op.github.common.network.BaseResp
import op.github.common.network.DataState
import op.github.common.widget.FooterAdapter
import op.github.home.R
import op.github.home.adapter.paging.ArticleMultiPagingAdapter
import op.github.home.bean.BannerData
import op.github.home.databinding.FragmentArticleBinding
import op.github.home.viewmodel.ArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


private const val TAG = "ArticleFragment"

/**
 * Author: pk
 * Time: 2022/2/9  5:49 下午
 * Description: 首页文章列表Fragment，包含banner和热门文章列表
 */
@ExperimentalPagingApi
class ArticleFragment : BaseFragment<FragmentArticleBinding>() {

    private val mViewModel: ArticleViewModel by viewModel()

    private var mArticlePagingAdapter =
        ArticleMultiPagingAdapter()

    override fun initData() {
        initListener()

        mBinding?.rvHomeArticle?.adapter = mArticlePagingAdapter.withLoadStateFooter(
            FooterAdapter {
                //重新请求
                mArticlePagingAdapter.retry()
            }
        )

        mViewModel.loadBanner()
        mViewModel.bannerLiveData.observe(this
        ) { t ->

            /**
             * Called when the data is changed.
             * @param t  The new data
             */
            if (t?.dataState == DataState.STATE_SUCCESS) {
                t.data?.let {
                    mArticlePagingAdapter.addBannerList(it)
                    mArticlePagingAdapter.notifyItemChanged(0)
                }
            }
        }

        //请求首页文章列表
        lifecycleScope.launchWhenCreated {
            mViewModel.articlePagingFlow().collectLatest {
                mArticlePagingAdapter.submitData(it)
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_article

    private fun initListener() {

        //下拉刷新
        mBinding?.swipeLayout?.setOnRefreshListener {
            mViewModel.loadBanner()
            mArticlePagingAdapter.refresh()
        }

        //监听paging数据刷新状态
        lifecycleScope.launchWhenCreated {
            mArticlePagingAdapter.loadStateFlow.collectLatest {
                Log.d(TAG, "initListener: $it")
                mBinding?.swipeLayout?.isRefreshing = it.refresh is LoadState.Loading
            }

        }
    }

}