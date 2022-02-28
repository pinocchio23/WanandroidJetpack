package op.github.common.support

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Author: pk
 * Time: 2022/2/8  9:23 上午
 * Description:   单事件响应的liveData，只有一个接收者能接收到信息，可以避免不必要的业务的场景中的事件消费通知
 * 只有调用call的时候，observer才能收到通知
 */
open class SingleLiveData<T> : MutableLiveData<T>() {

    companion object {
        private const val TAG = "SingleLiveData"
    }

    private val mPending = AtomicBoolean(false)

    override fun setValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }


    @MainThread
    fun call() {
        value = null
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if (hasActiveObservers()) {
            Log.w(TAG, "多个观察者存在的时候，只会有一个被通知到数据更新")
        }

        super.observe(owner, Observer {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

}