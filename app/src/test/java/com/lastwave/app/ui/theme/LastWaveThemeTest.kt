package com.lastwave.app.ui.theme

import androidx.compose.ui.graphics.Color
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LastWaveThemeTest {

    @Test
    fun lastWaveDarkScheme_usesBrandColors() {
        val scheme = Md3SchemeBuilder.buildLastWaveDarkScheme(amoled = false)

        assertThat(scheme.primary).isEqualTo(Color(0xFF00E5FF))
        assertThat(scheme.onPrimary).isEqualTo(Color(0xFF00363D))
        assertThat(scheme.surface).isEqualTo(Color(0xFF0B111E))
        assertThat(scheme.background).isEqualTo(Color(0xFF060A12))
    }

    @Test
    fun lastWaveDarkScheme_amoled_pureBlackSurfaces() {
        val scheme = Md3SchemeBuilder.buildLastWaveDarkScheme(amoled = true)

        assertThat(scheme.primary).isEqualTo(Color(0xFF00E5FF))
        assertThat(scheme.onPrimary).isEqualTo(Color(0xFF00363D))
        assertThat(scheme.background).isEqualTo(Color.Black)
        assertThat(scheme.surface).isEqualTo(Color.Black)
        assertThat(scheme.surfaceContainerLowest).isEqualTo(Color.Black)
        assertThat(scheme.surfaceContainerLow).isEqualTo(Color.Black)
    }

    @Test
    fun lastWaveLightScheme_usesBrandHighlights() {
        val scheme = Md3SchemeBuilder.buildLastWaveLightScheme()

        assertThat(scheme.onPrimaryContainer).isEqualTo(Color(0xFF001F24))
        assertThat(scheme.inversePrimary).isEqualTo(Color(0xFF00E5FF))
        assertThat(scheme.inverseSurface).isEqualTo(Color(0xFF2E3133))
    }
}
