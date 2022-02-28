package op.github.common.base

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import op.github.common.widget.LoadingDialog

/**
 * Author: pk
 * Time: 2022/2/7  5:04 下午
 * Description:
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    private lateinit var mLoadingDialog: LoadingDialog

    var mBinding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLoadingDialog = LoadingDialog(this, false)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
    }

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun getLayoutId(): Int

    fun showLoading(){
        mLoadingDialog.showDialog(this, false)
    }

    fun dismissLoading(){
        mLoadingDialog.dismissDialog()
    }

    /**
     * 设置toolbar名称
     */
    protected fun setToolbarTitle(view: TextView, title: String) {
        view.text = title
    }

    /**
     * 设置toolbar返回按键图片
     */
    protected fun setToolbarBackIcon(view: ImageView, id: Int) {
        view.setBackgroundResource(id)
    }


}