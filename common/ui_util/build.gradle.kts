import com.ku_stacks.ku_ring.buildlogic.dsl.setNameSpace

plugins {
    kuring("view")
    kuring("compose")
}

android {
    setNameSpace("ui_util")
}

dependencies {
    implementation(projects.data.domain)
    implementation(projects.common.designsystem)
    implementation(libs.bundles.compose.interop)
}
