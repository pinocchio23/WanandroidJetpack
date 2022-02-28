package op.github.home.adapter.viewpager

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.paging.ExperimentalPagingApi
import androidx.viewpager2.adapter.FragmentStateAdapter
import op.github.home.ui.ArticleFragment
import op.github.home.ui.DailyQuestionFragment
import op.github.home.ui.SquareFragment

private const val TAG = "HomePageAdapter"

/**
 * Author: pk
 * Time: 2022/2/9  5:29 下午
 * Description:
 */
@ExperimentalPagingApi
class HomePageAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "createFragment: $position")
        return when(position){
            0 -> DailyQuestionFragment()
            1 -> ArticleFragment()
            else -> SquareFragment()
        }
    }
}