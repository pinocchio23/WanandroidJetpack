package op.github.jetpackkt

import android.app.Application
import android.content.Context
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDex
import androidx.paging.ExperimentalPagingApi
import com.alibaba.android.arouter.launcher.ARouter
import com.kingja.loadsir.core.LoadSir
import op.github.common.loadsir.EmptyCallback
import op.github.common.loadsir.ErrorCallback
import op.github.common.loadsir.LoadingCallback
import op.github.common.utils.AppHelper
import op.github.home.di.moduleHome
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.logging.Level

/**
 * Author: pk
 * Time: 2022/2/15  10:39 上午
 * Description:
 */

@ExperimentalPagingApi
class MainApp: Application() {

    private val modules = arrayListOf(
        moduleHome
    )

    override fun onCreate() {
        super.onCreate()
        initARouter()
        initLoadSir()
        initKoin()
        AppHelper.init(this.applicationContext)
    }

    //koin
    private fun initKoin() {
        startKoin {
            androidLogger(org.koin.core.logger.Level.NONE)
            androidContext(this@MainApp)
            modules(modules)
        }
    }

    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(LoadingCallback())
            .addCallback(EmptyCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }

    private fun initARouter() {
        ARouter.init(this)
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}