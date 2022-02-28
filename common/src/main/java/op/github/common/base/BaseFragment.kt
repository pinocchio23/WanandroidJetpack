package op.github.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.kingja.loadsir.core.LoadService
import op.github.common.R
import op.github.common.databinding.BaseFragmentLayoutBinding
import op.github.common.support.Constants
import op.github.common.utils.SpUtils
import op.github.common.widget.LoadingDialog

/**
 * Author: pk
 * Time: 2022/2/9  3:33 下午
 * Description:
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    var mBinding: T? = null
    private lateinit var mContext: Context
    private lateinit var mLoadingDialog: LoadingDialog
    private lateinit var loadService: LoadService<Any>
    private lateinit var mBaseContainBinding: BaseFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBaseContainBinding = DataBindingUtil.inflate(inflater, R.layout.base_fragment_layout, container, false)
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        mBaseContainBinding.baseContainer.addView(mBinding?.root)
        return mBaseContainBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoadingDialog = LoadingDialog(view.context, false)
        initData()
    }

    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
    }

    abstract fun getLayoutId(): Int

    /**
     * show 加载中
     */
    private fun showLoading(){
        mLoadingDialog.showDialog(mContext, false)
    }

    private var time: Long = 0
    private var oldMsg: String? = null

    /**
     * 相同msg 只显示一个
     */
    fun showToast(msg: String) {
        if (msg != oldMsg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
            time = System.currentTimeMillis()
        } else {
            if (System.currentTimeMillis() - time > 2000) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
                time = System.currentTimeMillis()
            }
        }
        oldMsg = msg
    }

    protected fun isLogin(): Boolean {
        val userName = SpUtils.getString(Constants.SP_KEY_USER_INFO_NAME)
        if (userName == null || userName.isEmpty()) {
            return false
        }
        return true
    }
}