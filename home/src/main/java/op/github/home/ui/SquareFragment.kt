package op.github.home.ui

import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collectLatest
import op.github.common.base.BaseFragment
import op.github.home.R
import op.github.home.adapter.paging.SquarePagingAdapter
import op.github.home.databinding.FragmentSquareBinding
import op.github.home.viewmodel.ArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * @date：2021/5/20
 * @author pk
 * @instruction：首页广场
 */
@ExperimentalPagingApi
class SquareFragment : BaseFragment<FragmentSquareBinding>() {

    private val mViewModel: ArticleViewModel by viewModel()

    override fun initData() {
        val pagingAdapter = SquarePagingAdapter()
        mBinding?.rvSquare?.adapter = pagingAdapter

        lifecycleScope.launchWhenCreated {
            mViewModel.squarePagingFlow().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
        initListener(pagingAdapter)
    }

    private fun initListener(pagingAdapter: SquarePagingAdapter) {
        //下拉刷新
        mBinding?.swipeLayout?.setOnRefreshListener { pagingAdapter.refresh() }
        lifecycleScope.launchWhenCreated {
            pagingAdapter.loadStateFlow.collectLatest {
                //根据Paging的请求状态收缩刷新view
                mBinding?.swipeLayout?.isRefreshing = it.refresh is LoadState.Loading
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_square
    }


}