package g58008.mobg5.ui.view_model

import androidx.lifecycle.ViewModel
import g58008.mobg5.ui.AppUiState
import kotlinx.coroutines.flow.MutableStateFlow

class AppViewModel : ViewModel() {

    companion object {

        /**
         * Marks a variable as volatile, ensuring atomic reads and writes.
         *
         * In multithreaded environments, the use of {@code @Volatile} guarantees
         * that operations on the marked variable are atomic. This prevents
         * inconsistencies when the variable is accessed or modified by different threads.
         */
        @Volatile
        private var instance: AppViewModel? = null

        fun getInstance(): AppViewModel {
            return instance ?: synchronized(this) {
                instance ?: AppViewModel().also { instance = it }
            }
        }
    }

    var uiState = MutableStateFlow(AppUiState())
}

