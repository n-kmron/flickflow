package g58008.mobg5.ui.view_model

import androidx.lifecycle.ViewModel
import g58008.mobg5.ui.AppUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG: String = "VIEW MODEL"

class AppViewModel : ViewModel() {

    val _uiState = MutableStateFlow(AppUiState())
    var uiState: StateFlow<AppUiState> = _uiState.asStateFlow()
}

