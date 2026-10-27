package com.lastwave.app.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.lastwave.app.playback.MusicPlayer
import com.lastwave.app.playback.ZynWaveAudioBooster
import kotlin.math.roundToInt

@Composable
fun ZynWaveStudioDialog(
    musicPlayer: MusicPlayer,
    booster: ZynWaveAudioBooster,
    onDismiss: () -> Unit,
) {
    val playerState by musicPlayer.state.collectAsState()
    val boosterState by booster.state.collectAsState()
    val scrollState = rememberScrollState()

    var speed by remember(playerState.speed) { mutableFloatStateOf(playerState.speed) }
    var pitch by remember(playerState.pitch) { mutableFloatStateOf(playerState.pitch) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            tonalElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.GraphicEq,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "ZynWave Studio",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "DSP Booster & Tempo Engine",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Filled.Close, contentDescription = "Close")
                    }
                }

                Spacer(Modifier.height(18.dp))

                // SECTION 1: HARDWARE BASS & 3D SPATIALIZER
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Filled.Waves, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Text("Hardware Audio Booster", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                            }
                            Switch(
                                checked = boosterState.enabled,
                                onCheckedChange = { booster.setEnabled(it) }
                            )
                        }

                        Spacer(Modifier.height(10.dp))

                        // Sound Profiles
                        Text("DSP Sound Profile", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf("Bass Punch", "3D Spatializer", "Cyber Beats", "Clean / Off").forEach { p ->
                                val selected = boosterState.presetName == p
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHighest,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { booster.applyPreset(p) }
                                ) {
                                    Text(
                                        text = p,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
                                        maxLines = 1
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(14.dp))

                        // Bass Boost Slider
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Deep Bass Boost", style = MaterialTheme.typography.bodyMedium)
                            Text("${(boosterState.bassStrength / 10)}%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Slider(
                            value = boosterState.bassStrength.toFloat(),
                            onValueChange = { booster.setBassStrength(it.roundToInt()) },
                            valueRange = 0f..1000f,
                            enabled = boosterState.enabled
                        )

                        Spacer(Modifier.height(8.dp))

                        // 3D Spatializer Slider
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("3D Virtualizer Surround", style = MaterialTheme.typography.bodyMedium)
                            Text("${(boosterState.virtualizerStrength / 10)}%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Slider(
                            value = boosterState.virtualizerStrength.toFloat(),
                            onValueChange = { booster.setVirtualizerStrength(it.roundToInt()) },
                            valueRange = 0f..1000f,
                            enabled = boosterState.enabled
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))

                // SECTION 2: TEMPO & SPEED CONTROLLER
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Filled.Speed, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Text("Playback Speed & Pitch", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                            }
                            TextButton(onClick = {
                                speed = 1.0f
                                pitch = 1.0f
                                musicPlayer.setPlaybackParameters(1.0f, 1.0f)
                            }) {
                                Text("Reset")
                            }
                        }

                        // Speed Slider
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Speed: ${String.format("%.2f", speed)}x", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                text = when {
                                    speed < 0.9f -> "Slowed"
                                    speed > 1.2f -> "Fast"
                                    else -> "Normal"
                                },
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Slider(
                            value = speed,
                            onValueChange = {
                                speed = (it * 20).roundToInt() / 20f
                                musicPlayer.setSpeed(speed)
                            },
                            valueRange = 0.5f..2.0f,
                            steps = 29
                        )

                        Spacer(Modifier.height(6.dp))

                        // Quick Presets
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                "0.85x (Slow)" to 0.85f,
                                "1.0x" to 1.0f,
                                "1.15x" to 1.15f,
                                "1.25x (Nightcore)" to 1.25f,
                                "1.5x" to 1.5f
                            ).forEach { (label, targetSpeed) ->
                                val active = (speed - targetSpeed).let { it in -0.02f..0.02f }
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHighest,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            speed = targetSpeed
                                            musicPlayer.setSpeed(targetSpeed)
                                        }
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (active) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 2.dp),
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(14.dp))

                // SECTION 3: SMART SLEEP SETTINGS
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Filled.Bedtime, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Text("Smart Sleep Timer Options", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        }

                        Spacer(Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text("30-Second Gentle Fade-Out", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                                Text("Slowly ducks volume before sleep timer expires", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(
                                checked = boosterState.smartFadeOut,
                                onCheckedChange = { booster.setSmartFadeOut(it) }
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text("End of Song Mode", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                                Text("Wait for active song to finish before pausing", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(
                                checked = boosterState.stopAtEndOfSong,
                                onCheckedChange = { booster.setStopAtEndOfSong(it) }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(18.dp))

                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Done", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
