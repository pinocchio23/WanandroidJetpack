package op.github.common.loadsir

import com.kingja.loadsir.callback.Callback
import op.github.common.R

class ErrorCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.base_layout_error
    }
}