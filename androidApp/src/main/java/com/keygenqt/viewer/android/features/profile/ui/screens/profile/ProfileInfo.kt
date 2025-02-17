/*
 * Copyright 2022 Vitaliy Zarubin
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
package com.keygenqt.viewer.android.features.profile.ui.screens.profile

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion.then
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keygenqt.viewer.android.R
import com.keygenqt.viewer.android.compose.base.AppImageUser
import com.keygenqt.viewer.android.compose.texts.*
import com.keygenqt.viewer.android.data.models.UserModel
import com.keygenqt.viewer.android.theme.AppTheme
import com.keygenqt.viewer.android.extensions.dateFormat
import com.keygenqt.viewer.extensions.toTimestamp
import com.keygenqt.viewer.utils.AppConstants

@Composable
fun ProfileInfo(
    model: UserModel,
    uriHandler: UriHandler? = null,
) {
    Column {
        Box(
            modifier = Modifier.height(300.dp),
        ) {
            AppImageUser(
                url = model.avatarUrl,
                modifier = Modifier.fillMaxSize(),
            )
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
                    .align(Alignment.BottomStart)
            ) {}

            TextTitleLarge(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart),
                maxLines = 1,
                text = model.name
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBlockCount(
                stringResource(id = R.string.profile_label_item_count_public_repos),
                vectorImage = Icons.Default.HomeRepairService,
                count = model.publicRepos
            )
            InfoBlockCount(
                stringResource(id = R.string.profile_label_item_count_followers),
                vectorImage = Icons.Default.Group,
                count = model.followers
            )
            InfoBlockCount(
                stringResource(id = R.string.profile_label_item_count_following),
                vectorImage = Icons.Default.GroupAdd,
                count = model.following
            )
        }

        Spacer(modifier = Modifier.size(6.dp))

        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Spacer(modifier = Modifier.size(20.dp))

            if (model.company != null) {
                InfoBlock(
                    label = stringResource(id = R.string.profile_label_company),
                    text = model.company
                )
            }

            if (model.blog != null) {
                InfoBlock(
                    label = stringResource(id = R.string.profile_label_blog),
                    text = model.blog,
                    onClick = {
                        uriHandler?.openUri(model.blog)
                    }
                )
            }

            if (model.location != null) {
                InfoBlock(
                    label = stringResource(id = R.string.profile_label_location),
                    text = model.location
                )
            }

            InfoBlock(
                label = stringResource(id = R.string.profile_label_created_at),
                text = model.createdAt.toTimestamp().dateFormat(AppConstants.DATE_FORMAT.SHORT)
            )

            if (model.bio != null) {
                InfoBlock(
                    label = stringResource(id = R.string.profile_label_bio),
                    text = model.bio
                )
            }

            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun InfoBlockCount(
    label: String,
    vectorImage: ImageVector,
    count: Int? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextLabelSmall(text = label.uppercase())
        Spacer(modifier = Modifier.size(2.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(26.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(top = 10.dp, bottom = 10.dp, start = 28.dp, end = 28.dp)
        ) {
            Icon(
                imageVector = vectorImage,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        TextLabelLarge(text = count.toString())
    }
}

@Composable
fun InfoBlock(
    label: String,
    text: String,
    onClick: (() -> Unit)? = null
) {
    if (text.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .let { modifier ->
                    onClick?.let {
                        then(modifier.clickable { onClick.invoke() })
                    } ?: run {
                        modifier
                    }
                }
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Spacer(modifier = Modifier.size(10.dp))

            TextLabelLarge(text = label)

            Spacer(modifier = Modifier.size(2.dp))

            if (onClick == null) {
                TextBodyMedium(text)
            } else {
                AppText(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, device = Devices.PIXEL_4)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PIXEL_4)
@Composable
private fun Preview() {
    AppTheme {
        ProfileInfo(UserModel.mock())
    }
}
