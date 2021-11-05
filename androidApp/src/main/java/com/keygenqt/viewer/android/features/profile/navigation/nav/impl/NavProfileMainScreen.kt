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
package com.keygenqt.viewer.android.features.profile.navigation.nav.impl

import com.keygenqt.routing.NavScreen
import com.keygenqt.viewer.android.features.profile.ui.screens.profileMain.ProfileMainScreen

/**
 * Routing for [ProfileMainScreen]
 */
object NavProfileMainScreen {
    val profileMainScreen = object : NavScreen {
        override val route: String = "NavProfileMainScreen"
    }
}