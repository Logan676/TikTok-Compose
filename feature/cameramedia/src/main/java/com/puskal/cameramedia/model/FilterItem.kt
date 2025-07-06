package com.puskal.cameramedia.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.ColorMatrix

/**
 * Simple filter model holding a color matrix.
 */
data class FilterItem(
    val id: Int,
    @StringRes val nameRes: Int,
    val matrix: ColorMatrix
)
