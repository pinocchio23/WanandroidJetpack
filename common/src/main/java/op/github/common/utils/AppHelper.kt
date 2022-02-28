package op.github.common.utils

import android.content.Context

/**
 * Author: pk
 * Time: 2022/2/15  11:11 上午
 * Description:
 */
object AppHelper {
    lateinit var mContext: Context

    fun init(context: Context){
        this.mContext = context
    }
}