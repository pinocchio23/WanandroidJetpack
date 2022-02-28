package op.github.jetpackkt

import android.content.Intent
import android.os.Bundle
import op.github.common.base.BaseActivity
import op.github.jetpackkt.databinding.ActivitySplashBinding

/**
 * Author: pk
 * Time: 2022/2/15  11:26 上午
 * Description:
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun getLayoutId(): Int = R.layout.activity_splash
}