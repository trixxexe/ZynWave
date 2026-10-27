package com.lastwave.app.playback

import android.content.Context
import android.media.audiofx.BassBoost
import android.media.audiofx.Virtualizer
import android.util.Log
import androidx.media3.common.C
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

/**
 * State representing ZynWave's hardware-accelerated audio enhancement pipeline.
 */
data class ZynWaveBoosterState(
    val enabled: Boolean = false,
    val bassStrength: Int = 0, // 0 to 1000
    val virtualizerStrength: Int = 0, // 0 to 1000
    val presetName: String = "Custom",
    val smartFadeOut: Boolean = true,
    val stopAtEndOfSong: Boolean = false,
)

/**
 * ZynWave Audio Enhancer & Spatializer.
 * Bridges Android's native DSP AudioFX BassBoost and 3D Virtualizer onto ExoPlayer's
 * active audio session.
 */
@Singleton
class ZynWaveAudioBooster @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val prefs = context.getSharedPreferences("zynwave_audio_booster", Context.MODE_PRIVATE)
    private var currentSessionId = C.AUDIO_SESSION_ID_UNSET
    private var bassBoost: BassBoost? = null
    private var virtualizer: Virtualizer? = null

    private val _state = MutableStateFlow(
        ZynWaveBoosterState(
            enabled = prefs.getBoolean("booster_enabled", false),
            bassStrength = prefs.getInt("bass_strength", 0),
            virtualizerStrength = prefs.getInt("virtualizer_strength", 0),
            presetName = prefs.getString("preset_name", "Custom") ?: "Custom",
            smartFadeOut = prefs.getBoolean("smart_fade_out", true),
            stopAtEndOfSong = prefs.getBoolean("stop_at_end_of_song", false),
        )
    )
    val state: StateFlow<ZynWaveBoosterState> = _state.asStateFlow()

    fun attachSession(sessionId: Int) {
        if (sessionId == C.AUDIO_SESSION_ID_UNSET) return
        currentSessionId = sessionId
        rebuildEffects()
    }

    fun setEnabled(enabled: Boolean) {
        _state.update { it.copy(enabled = enabled) }
        prefs.edit().putBoolean("booster_enabled", enabled).apply()
        applyEffects()
    }

    fun setBassStrength(strength: Int) {
        val clamped = strength.coerceIn(0, 1000)
        _state.update {
            it.copy(
                bassStrength = clamped,
                enabled = if (clamped > 0 || it.virtualizerStrength > 0) true else it.enabled,
                presetName = "Custom"
            )
        }
        prefs.edit()
            .putInt("bass_strength", clamped)
            .putBoolean("booster_enabled", _state.value.enabled)
            .putString("preset_name", "Custom")
            .apply()
        applyEffects()
    }

    fun setVirtualizerStrength(strength: Int) {
        val clamped = strength.coerceIn(0, 1000)
        _state.update {
            it.copy(
                virtualizerStrength = clamped,
                enabled = if (clamped > 0 || it.bassStrength > 0) true else it.enabled,
                presetName = "Custom"
            )
        }
        prefs.edit()
            .putInt("virtualizer_strength", clamped)
            .putBoolean("booster_enabled", _state.value.enabled)
            .putString("preset_name", "Custom")
            .apply()
        applyEffects()
    }

    fun setSmartFadeOut(enabled: Boolean) {
        _state.update { it.copy(smartFadeOut = enabled) }
        prefs.edit().putBoolean("smart_fade_out", enabled).apply()
    }

    fun setStopAtEndOfSong(enabled: Boolean) {
        _state.update { it.copy(stopAtEndOfSong = enabled) }
        prefs.edit().putBoolean("stop_at_end_of_song", enabled).apply()
    }

    fun applyPreset(preset: String) {
        val (bass, virt) = when (preset) {
            "Bass Punch" -> 750 to 200
            "3D Spatializer" -> 250 to 800
            "Cyber Beats" -> 650 to 650
            "Vocal Clarity" -> 100 to 400
            "Clean / Off" -> 0 to 0
            else -> 0 to 0
        }
        val isEnabled = (bass > 0 || virt > 0)
        _state.update {
            it.copy(
                enabled = isEnabled,
                bassStrength = bass,
                virtualizerStrength = virt,
                presetName = preset,
            )
        }
        prefs.edit()
            .putBoolean("booster_enabled", isEnabled)
            .putInt("bass_strength", bass)
            .putInt("virtualizer_strength", virt)
            .putString("preset_name", preset)
            .apply()
        applyEffects()
    }

    private fun rebuildEffects() {
        releaseEffects()
        if (currentSessionId == C.AUDIO_SESSION_ID_UNSET) return
        try {
            bassBoost = BassBoost(0, currentSessionId).apply {
                if (strengthSupported) {
                    setStrength(_state.value.bassStrength.toShort())
                }
                enabled = _state.value.enabled && _state.value.bassStrength > 0
            }
        } catch (e: Throwable) {
            Log.w("ZynWaveAudioBooster", "Unable to create BassBoost on session $currentSessionId", e)
        }
        try {
            virtualizer = Virtualizer(0, currentSessionId).apply {
                if (strengthSupported) {
                    setStrength(_state.value.virtualizerStrength.toShort())
                }
                enabled = _state.value.enabled && _state.value.virtualizerStrength > 0
            }
        } catch (e: Throwable) {
            Log.w("ZynWaveAudioBooster", "Unable to create Virtualizer on session $currentSessionId", e)
        }
    }

    private fun applyEffects() {
        val st = _state.value
        try {
            bassBoost?.let { bb ->
                if (bb.strengthSupported) {
                    bb.setStrength(st.bassStrength.toShort())
                }
                bb.enabled = st.enabled && st.bassStrength > 0
            }
        } catch (e: Throwable) {
            Log.w("ZynWaveAudioBooster", "Failed to apply BassBoost strength", e)
        }
        try {
            virtualizer?.let { virt ->
                if (virt.strengthSupported) {
                    virt.setStrength(st.virtualizerStrength.toShort())
                }
                virt.enabled = st.enabled && st.virtualizerStrength > 0
            }
        } catch (e: Throwable) {
            Log.w("ZynWaveAudioBooster", "Failed to apply Virtualizer strength", e)
        }
    }

    private fun releaseEffects() {
        runCatching {
            bassBoost?.enabled = false
            bassBoost?.release()
        }
        bassBoost = null
        runCatching {
            virtualizer?.enabled = false
            virtualizer?.release()
        }
        virtualizer = null
    }
}
