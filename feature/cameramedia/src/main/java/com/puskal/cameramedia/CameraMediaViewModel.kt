package com.puskal.cameramedia

import androidx.compose.ui.graphics.ColorMatrix
import androidx.lifecycle.viewModelScope
import com.puskal.cameramedia.model.FilterItem
import com.puskal.core.base.BaseViewModel
import com.puskal.domain.cameramedia.GetTemplateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Puskal Khadka on 3/15/2023.
 */
@HiltViewModel
class CameraMediaViewModel @Inject constructor(
    private val getTemplateUseCase: GetTemplateUseCase
) : BaseViewModel<ViewState, CameraMediaEvent>() {

    val filters = listOf(
        FilterItem(0, R.string.filter_normal, ColorMatrix()),
        FilterItem(1, R.string.filter_mono, ColorMatrix().apply { setToSaturation(0f) }),
        FilterItem(2, R.string.filter_sepia, ColorMatrix(floatArrayOf(
            0.393f, 0.769f, 0.189f, 0f, 0f,
            0.349f, 0.686f, 0.168f, 0f, 0f,
            0.272f, 0.534f, 0.131f, 0f, 0f,
            0f,     0f,     0f,     1f, 0f
        )))
    )

    init {
        getTemplates()
    }

    override fun onTriggerEvent(event: CameraMediaEvent) {
        when (event) {
            CameraMediaEvent.EventFetchTemplate -> getTemplates()
            is CameraMediaEvent.ChangeFilter -> {
                val current = viewState.value ?: ViewState()
                updateState(current.copy(selectedFilterId = event.filterId))
            }
        }
    }

    private fun getTemplates() {
        viewModelScope.launch {
            getTemplateUseCase().collect {
                updateState((viewState.value ?: ViewState()).copy(templates = it))
            }
        }
    }


}