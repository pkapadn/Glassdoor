/*
 * Copyright (c) 2025, Glassdoor Inc.
 *
 * Licensed under the Glassdoor Inc Hiring Assessment License.
 * You may not use this file except in compliance with the License.
 * You must obtain explicit permission from Glassdoor Inc before sharing or distributing this file.
 * Mention Glassdoor Inc as the source if you use this code in any way.
 */

package com.glassdoor.intern.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Immutable
@Parcelize
internal class HeaderUiModel(
    val title: String,
    val description: String,
    val timestamp: String,
    val items: List<ItemUiModel> = emptyList()
) : Parcelable {

    /** DONE: Define empty state */
    @IgnoredOnParcel
    val isEmpty: Boolean  get() = title.isBlank() && description.isBlank() && timestamp.isBlank() && items.isEmpty()
}
