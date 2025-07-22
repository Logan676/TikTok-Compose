package com.puskal.cameramedia

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import androidx.navigation.navArgument
import android.net.Uri
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.puskal.core.DestinationRoute
import com.puskal.core.DestinationRoute.FORMATTED_VIDEO_EDIT_ROUTE
import com.puskal.core.DestinationRoute.FORMATTED_VIDEO_TRIM_ROUTE
import com.puskal.core.DestinationRoute.PassedKey
import com.puskal.cameramedia.edit.VideoEditScreen
import com.puskal.cameramedia.edit.VideoTrimScreen

/**
 * Created by Puskal Khadka on 4/2/2023.
 */
@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.cameraMediaNavGraph(navController: NavController) {
    composable(route = DestinationRoute.CAMERA_ROUTE) {
        CameraMediaScreen(navController)
    }
    bottomSheet(route = DestinationRoute.CHOOSE_SOUND_ROUTE) {
        com.puskal.cameramedia.sound.ChooseSoundScreen(navController)
    }
    composable(
        route = FORMATTED_VIDEO_EDIT_ROUTE,
        arguments = listOf(navArgument(PassedKey.VIDEO_URI) { type = NavType.StringType })
    ) { backStackEntry ->
        val argUri = backStackEntry.arguments
            ?.getString(PassedKey.VIDEO_URI)
            ?.let { Uri.decode(it) } ?: ""
        val videoUri by backStackEntry
            .savedStateHandle
            .getStateFlow(PassedKey.VIDEO_URI, argUri)
            .collectAsState()
        VideoEditScreen(
            videoUri = videoUri,
            onClickBack = { navController.navigateUp() },
            onTrimVideo = { encoded ->
                navController.navigate(
                    DestinationRoute.VIDEO_TRIM_ROUTE + "/" + Uri.encode(encoded)
                )
            },
            enableFilters = true
        )
    }

    composable(
        route = FORMATTED_VIDEO_TRIM_ROUTE,
        arguments = listOf(navArgument(PassedKey.VIDEO_URI) { type = NavType.StringType })
    ) { backStackEntry ->
        val uri = backStackEntry.arguments
            ?.getString(PassedKey.VIDEO_URI)
            ?.let { Uri.decode(it) } ?: ""
        VideoTrimScreen(
            videoUri = uri,
            onCancel = { navController.navigateUp() },
            onSave = { output ->
                navController.previousBackStackEntry?.savedStateHandle?.set(PassedKey.VIDEO_URI, output)
                navController.navigateUp()
            }
        )
    }
}