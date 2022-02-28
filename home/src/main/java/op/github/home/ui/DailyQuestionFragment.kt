package op.github.home.ui

import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collectLatest
import op.github.common.base.BaseFragment
import op.github.common.widget.FooterAdapter
import op.github.home.R
import op.github.home.adapter.paging.DailyQuestionPagingAdapter
import op.github.home.databinding.FragmentDailyQuestionBinding
import op.github.home.viewmodel.ArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @date：2021/5/13
 * @author fuusy
 * @instruction：首页每日一问Fragment
 */
@ExperimentalPagingApi
class DailyQuestionFragment : BaseFragment<FragmentDailyQuestionBinding>() {
    private val mViewModel: ArticleViewModel by viewModel()

    private val dailyPagingAdapter = DailyQuestionPagingAdapter()

    override fun initData() {
        initRecyclerview()
        initListener()
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launchWhenCreated {
            mViewModel.dailyQuestionPagingFlow().collectLatest {
                dailyPagingAdapter.submitData(it)
            }
        }
    }

    private fun initListener() {
        mBinding?.swipeLayout?.setOnRefreshListener {
            dailyPagingAdapter.refresh()
        }
        lifecycleScope.launchWhenCreated {
            dailyPagingAdapter.loadStateFlow.collectLatest {
                mBinding?.swipeLayout?.isRefreshing = it.refresh is LoadState.Loading
            }
        }
    }

    private fun initRecyclerview() {
        mBinding?.rvDailyQuestion?.adapter = dailyPagingAdapter.withLoadStateFooter(
            FooterAdapter {
                dailyPagingAdapter.retry()
            })

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_daily_question
    }

}