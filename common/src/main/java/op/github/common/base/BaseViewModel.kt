package op.github.common.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import op.github.common.support.SingleLiveData

/**
 * Author: pk
 * Time: 2022/2/7  6:03 下午
 * Description:
 */
private const val TAG = "BaseViewModel"
open class BaseViewModel : ViewModel(){

    val loadingLiveData = SingleLiveData<Boolean>()

    val errorLiveData = SingleLiveData<Throwable>()

    fun launch(
        block: suspend () -> Unit,
        error: suspend (Throwable) -> Unit,
        complete: suspend () -> Unit
    ){
        loadingLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            }catch (e: Exception){
                Log.d(TAG, "launch: error")
                error(e)
            }finally {
                complete()
            }
        }
    }
}