package op.github.jetpackkt

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.alibaba.android.arouter.facade.annotation.Route
import op.github.common.base.BaseActivity
import op.github.common.ktx.setupWithNavController
import op.github.common.support.Constants
import op.github.jetpackkt.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

@Route(path = Constants.PATH_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var currentNavController: LiveData<NavController>? = null

    override fun initData(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    /**
     * navigation绑定BottomNavigationView
     */
    private fun setupBottomNavigationBar() {
        val navGraphIds =
            listOf(R.navigation.navi_home)

        val controller = mBinding?.navView?.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        controller?.observe(this, Observer{
            it.addOnDestinationChangedListener { controller, destination, arguments ->
                run {
                    val id = destination.id
                }
            }
        })

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

}