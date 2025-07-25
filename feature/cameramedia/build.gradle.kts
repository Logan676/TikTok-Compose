plugins {
    id("plugin.android-common")
}


dependencies {
    COMMON_THEME
    COMMON_COMPOSABLE
    DOMAIN
    DATA
    CORE
    cameraXDependencies()
    media3Dependency()
    implementation(project(":feature:video-trimmer"))
    implementation(project(":feature:cameracapture"))
    implementation(project(":feature:mp4compose"))
}