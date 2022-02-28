package op.github.home.ui

import androidx.paging.ExperimentalPagingApi
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import op.github.common.base.BaseFragment
import op.github.home.R
import op.github.home.adapter.viewpager.HomePageAdapter
import op.github.home.databinding.FragmentHomeBinding

/**
 * Author: pk
 * Time: 2022/2/9  5:18 下午
 * Description:
 */
@ExperimentalPagingApi
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var homePageAdapter: HomePageAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        homePageAdapter = HomePageAdapter(this)
        mBinding?.vpHome?.adapter = homePageAdapter

        mBinding?.run {
            TabLayoutMediator(homeTabLayout, vpHome) { tab, position ->
                when (position) {
                    0 -> tab.text = "每日一问"
                    1 -> tab.text = "首页"
                    2 -> tab.text = "广场"
                }
            }.attach()
            vp_home.currentItem = 1
        }
    }
}