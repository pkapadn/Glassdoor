/*
 * Copyright (c) 2025, Glassdoor Inc.
 *
 * Licensed under the Glassdoor Inc Hiring Assessment License.
 * You may not use this file except in compliance with the License.
 * You must obtain explicit permission from Glassdoor Inc before sharing or distributing this file.
 * Mention Glassdoor Inc as the source if you use this code in any way.
 */

package com.glassdoor.intern.presentation.ui.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.glassdoor.intern.presentation.theme.InternTheme
import com.glassdoor.intern.utils.previewParameterProviderOf
import kotlinx.coroutines.delay

/**
 * DONE: Define how long the error message will be displayed
 */
private const val SHOW_ERROR_MESSAGE_DURATION_IS_MILLIS: Long = 3000L

@Composable
internal fun ErrorMessageComponent(
    errorMessage: String?,
    hideErrorMessageAction: () -> Unit,
    modifier: Modifier = Modifier,
) = Crossfade(
    modifier = modifier,
    targetState = errorMessage,
    label = "ErrorMessageComponent",
) { state ->
    if (!state.isNullOrEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.errorContainer)
                .padding(InternTheme.dimensions.normal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(InternTheme.dimensions.normal),
                text = state,
            )

            LaunchedEffect(key1 = errorMessage) {
                delay(SHOW_ERROR_MESSAGE_DURATION_IS_MILLIS)
                hideErrorMessageAction()
            }
        }
    }
}

@Preview
@Composable
private fun ErrorMessageComponentPreview(
    @PreviewParameter(ErrorMessageComponentPreviewParameterProvider::class) errorMessage: String?
) = InternTheme {
    ErrorMessageComponent(
        errorMessage = errorMessage,
        hideErrorMessageAction = { },
    )
}

private class ErrorMessageComponentPreviewParameterProvider :
    PreviewParameterProvider<String?> by previewParameterProviderOf(null, "Error message")
