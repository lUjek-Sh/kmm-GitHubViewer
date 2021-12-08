/*
 * Copyright 2021 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.keygenqt.viewer.android.base.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keygenqt.response.ResponseResult
import com.keygenqt.response.extensions.error
import com.keygenqt.response.extensions.errorUnknownHost
import com.keygenqt.response.extensions.success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

open class ViewModelStates : ViewModel() {

    /**
     * [MutableStateFlow] for state
     */
    private val _state: MutableStateFlow<ViewModelState> = MutableStateFlow(ViewModelState.Start)

    /**
     * [StateFlow] for [_state]
     */
    val state: StateFlow<ViewModelState> get() = _state.asStateFlow()

    /**
     * Set state Stop
     */
    fun setStop() {
        _state.value = ViewModelState.Stop
    }

    /**
     * Set state Action
     */
    fun setAction() {
        _state.value = ViewModelState.Action
    }

    /**
     * Set state Error
     */
    fun setError(message: String) {
        _state.value = ViewModelState.Error(message)
    }

    /**
     * Set state exception Error
     */
    fun setError(exception: Exception) {
        _state.value = ViewModelState.Error(exception.message ?: "Exception error")
    }

    /**
     * Set state Success
     */
    fun <T> setSuccess(data: T) {
        _state.value = ViewModelState.Success(data)
    }

    /**
     * Launch query only loading
     */
    fun <T> queryLaunch(
        predicate: suspend () -> ResponseResult<T>
    ) {
        setAction()
        viewModelScope.launch {
            predicate()
                .success(::setSuccess)
                .error(::setError)
                .error { Timber.e(it) }
                .errorUnknownHost(::setError)
        }
    }
}
