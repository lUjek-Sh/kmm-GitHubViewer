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
package com.keygenqt.viewer.android.features.other.ui.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keygenqt.response.extensions.success
import com.keygenqt.viewer.android.BuildConfig
import com.keygenqt.viewer.android.base.viewModel.ViewModelStates
import com.keygenqt.viewer.android.extensions.withTransaction
import com.keygenqt.viewer.android.features.other.ui.screens.signIn.SignInScreen
import com.keygenqt.viewer.android.services.apiService.AppApiService
import com.keygenqt.viewer.android.services.dataService.AppDataService
import com.keygenqt.viewer.android.services.dataService.impl.SecurityModelDataService
import com.keygenqt.viewer.android.utils.AppHelper.getDynamicLinks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * [ViewModel] for [SignInScreen]
 */
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val apiService: AppApiService,
    private val dataService: AppDataService
) : ViewModelStates() {

    /**
     * Generate url for open in browser
     *
     * @param login login user
     */
    fun signIn(
        login: String,
    ) {
        setAction()
        viewModelScope.launch {
            delay(100)
            setSuccess(
                Uri.Builder().apply {
                    scheme("https")
                    authority("github.com")
                    appendPath("login")
                    appendPath("oauth")
                    appendPath("authorize")
                    appendQueryParameter("login", login)
                    appendQueryParameter("state", login)
                    appendQueryParameter("redirect_uri", getDynamicLinks("/oauth"))
                    appendQueryParameter("allow_signup", false.toString())
                    appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
                    build()
                }.toString()
            )
        }
    }

    fun signInCode(
        login: String,
        code: String,
    ) {
        queryLaunch {
            apiService.oauthCode(login = login, code = code)
                .success { model ->
                    // save security tokens
                    dataService.withTransaction<SecurityModelDataService> {
                        clearSecurityModel()
                        insertSecurityModel(model)
                    }
                }
        }
    }
}