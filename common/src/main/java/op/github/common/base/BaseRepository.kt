package op.github.common.base

import op.github.common.network.BaseResp
import op.github.common.network.DataState
import op.github.common.network.net.StateLiveData

/**
 * Author: pk
 * Time: 2022/2/8  10:40 上午
 * Description:
 */
private const val TAG = "BaseRepository"

open class BaseRepository {

    suspend fun <T : Any> executeResp(
        block: suspend () -> BaseResp<T>,
        stateLiveData: StateLiveData<T>
    ) {
        var baseResp = BaseResp<T>()
        try {
            baseResp.dataState = DataState.STATE_LOADING
            //开始请求数据
            val invoke = block.invoke()
            baseResp = invoke
            if (baseResp.errorCode == 0) {
                //请求成功，判断数据是否为空，
                //因为数据有多种类型，需要自己设置类型进行判断
                if (baseResp.data == null || baseResp.data is List<*> && (baseResp.data as List<*>).size == 0) {
                    baseResp.dataState = DataState.STATE_EMPTY
                } else {
                    baseResp.dataState = DataState.STATE_SUCCESS
                }
            } else {
                baseResp.dataState = DataState.STATE_FAILED
            }
        } catch (e: Exception) {
            baseResp.dataState = DataState.STATE_ERROR
            baseResp.error = e
        } finally {
            stateLiveData.postValue(baseResp)
        }
    }
}