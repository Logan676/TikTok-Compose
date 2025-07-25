package com.puskal.core

import com.puskal.core.DestinationRoute.PassedKey.USER_ID
import com.puskal.core.DestinationRoute.PassedKey.VIDEO_INDEX

/**
 * Created by Puskal Khadka on 3/19/2023.
 */
object DestinationRoute {
    const val HOME_SCREEN_ROUTE = "home_screen_route"
    const val COMMENT_BOTTOM_SHEET_ROUTE = "comment_bottom_sheet_route"
    const val CREATOR_PROFILE_ROUTE = "creator_profile_route"

    const val CREATOR_VIDEO_ROUTE = "creator_video_route"
    const val FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE =
        "$CREATOR_VIDEO_ROUTE/{$USER_ID}/{$VIDEO_INDEX}"

    const val INBOX_ROUTE = "inbox_route"
    const val MY_PROFILE_ROUTE = "my_profile_route"
    const val FRIENDS_ROUTE = "friends_route"
    const val CAMERA_ROUTE = "camera_route"
    const val CHOOSE_SOUND_ROUTE = "choose_sound_route"

    const val VIDEO_EDIT_ROUTE = "video_edit_route"
    const val FORMATTED_VIDEO_EDIT_ROUTE = "$VIDEO_EDIT_ROUTE/{${PassedKey.VIDEO_URI}}"

    const val VIDEO_TRIM_ROUTE = "video_trim_route"
    const val FORMATTED_VIDEO_TRIM_ROUTE = "$VIDEO_TRIM_ROUTE/{${PassedKey.VIDEO_URI}}"

    const val POST_VIDEO_ROUTE = "post_video_route"
    const val FORMATTED_POST_VIDEO_ROUTE = "$POST_VIDEO_ROUTE/{${PassedKey.VIDEO_URI}}"

    const val AUTHENTICATION_ROUTE = "authentication_route"
    const val LOGIN_OR_SIGNUP_WITH_PHONE_EMAIL_ROUTE = "login_signup_phone_email_route"

    const val SETTING_ROUTE="setting_route"

    object PassedKey {
        const val USER_ID = "user_id"
        const val VIDEO_INDEX = "video_index"
        const val VIDEO_URI = "video_uri"
    }
}

