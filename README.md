<div align="center">

# ⚡ ZynWave

**Next-Gen Hi-Fi YouTube Music Streaming & Offline Engine with Hardware DSP, Real-Time Synced Lyrics & Liquid Glass UI for Android.**

<p align="center">
  <a href="https://github.com/trixxexe/ZynWave/stargazers">
    <img src="https://img.shields.io/github/stars/trixxexe/ZynWave?style=for-the-badge&color=00E5FF&labelColor=090D16" alt="Stars" />
  </a>
  <a href="https://github.com/trixxexe/ZynWave/network/members">
    <img src="https://img.shields.io/github/forks/trixxexe/ZynWave?style=for-the-badge&color=B5179E&labelColor=090D16" alt="Forks" />
  </a>
  <a href="https://github.com/trixxexe/ZynWave/blob/main/LICENSE">
    <img src="https://img.shields.io/badge/License-GPL%20v3.0-00E5FF?style=for-the-badge&logo=gnu&logoColor=white&labelColor=090D16" alt="GPL v3" />
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white&labelColor=090D16" alt="Platform" />
  </a>
  <a href="https://instagram.com/ritam.zip">
    <img src="https://img.shields.io/badge/Instagram-@ritam.zip-E4405F?style=for-the-badge&logo=instagram&logoColor=white&labelColor=090D16" alt="Instagram" />
  </a>
</p>

<p align="center">
  <strong>Developer:</strong> Ritam &bull; 
  <strong>Instagram:</strong> <a href="https://instagram.com/ritam.zip">@ritam.zip</a> &bull; 
  <strong>Inspired by:</strong> <a href="https://github.com/Clash-Projects/LastWave-Native">LastWave (Clash-Projects)</a>
</p>

</div>

---

## 🌟 Overview

**ZynWave** is a state-of-the-art native Android music client powered by the global YouTube Music catalog, designed for audiophiles and power listeners who demand uncompromising audio fidelity, kinetic visuals, and granular DSP control.

Built with **Jetpack Compose (Material 3 Expressive)** and **AndroidX Media3 ExoPlayer**, ZynWave pairs effortless ad-free streaming with cutting-edge features: a **Hardware Audio Booster & 3D Spatializer**, a **Tempo & Pitch Studio** for on-the-fly Slowed / Nightcore effects, a **Smart Sleep Timer with smooth volume fade-out**, and an ultra-fast **Offline Downloader** saving tagged audio straight to `Music/ZynWave`.

---

## 👑 What Makes ZynWave Unique?

| Feature | Description |
| :--- | :--- |
| 🎚️ **ZynWave DSP Audio Booster** | Real-time hardware-accelerated **Deep Bass Boost** (0–100%) and **3D Virtualizer Surround Sound** (0–100%) with tuned acoustic profiles (*Bass Punch, 3D Spatializer, Cyber Beats, Vocal Clarity*). |
| ⚡ **Tempo & Pitch Studio** | Fine-tune playback speed from **0.50x to 2.00x** and shift pitch in real-time. Includes 1-tap presets for *Slowed + Reverb (0.85x)*, *Nightcore (1.25x)*, and *Podcast Pace (1.35x)*. |
| 🌙 **Smart Sleep Timer & Fade-Out** | Gentle **30-second volume ducking** curve before sleep timer shutdown, plus **End of Current Song** mode so your music never halts mid-track. |
| 🎨 **ZynWave Cyber Obsidian Theme** | Signature Electric Cyan (`#00E5FF`) and Cyber Violet (`#B5179E`) aesthetic with pure black OLED AMOLED mode and translucent Liquid Glass backdrops. |
| 💾 **Enhanced Offline Downloader** | Direct downloads stored neatly under `Music/ZynWave` with full ID3/Vorbis tag injection, high-resolution album art, and synchronized `.lrc` lyrics files. |
| 🚀 **Multi-Engine Audio Fallback** | Multi-client stream extraction combining InnerTube, InnerTubeX, and NewPipe for instant playback with zero buffer drops. |

---

## ✨ Core Features

- 🎧 **High-Fidelity Audio Streaming**: Stream millions of tracks, albums, artists, and playlists from YouTube Music in high-bitrate Opus and AAC with zero advertisements.
- 🎤 **Synchronized Karaoke Lyrics**: Millisecond-accurate animated karaoke lyrics powered by LRCLIB with fluid physics motions and `.lrc` offline export.
- 📻 **Universal Last.fm Scrobbler**: Native OS session tracking scrobbling every track to your Last.fm profile with zero battery impact.
- 🎨 **Dynamic Material You & Liquid Glass UI**: Themes that adapt to album artwork or your system wallpaper, accompanied by frosted glass depth and floating controls.
- 🎛️ **15-Band Parametric Equalizer**: Precision frequency sculpting with AutoEQ headphone profiles and custom tuning.
- 🚗 **Android Auto & Lockscreen Media**: Seamless in-car dashboard controls and modern lockscreen notification surfaces.
- 💬 **Discord Rich Presence**: Showcase your currently playing song and artist directly on your Discord profile.

---

## 👨‍💻 Developer & Credits

- **Lead Developer**: **Ritam**
- **Instagram**: [@ritam.zip](https://instagram.com/ritam.zip)
- **Repository**: [github.com/trixxexe/ZynWave](https://github.com/trixxexe/ZynWave)
- **Inspiration & Lineage**: Inspired by and built upon the open-source foundation of [LastWave](https://github.com/Clash-Projects/LastWave-Native) by Clash-Projects.

---

## 🛠️ Building & Installation

### Option 1: Automated GitHub Actions Build (Recommended)
This repository includes a pre-configured GitHub Actions workflow (`.github/workflows/build.yml`) that automatically builds release and raw APKs.
1. Star or fork **[trixxexe/ZynWave](https://github.com/trixxexe/ZynWave)**.
2. Under the **Actions** tab, trigger **Build ZynWave APK** or push a new commit/tag.
3. Download the built APKs from the workflow **Artifacts** section or **Releases**.

### Option 2: Local Build
```bash
# Clone the repository
git clone https://github.com/trixxexe/ZynWave.git
cd ZynWave

# Build the Release APK
./gradlew assembleRelease

# The signed APK will be generated at:
# app/build/outputs/apk/release/app-release.apk
```

---

## ⚖️ Open Source & License

ZynWave is released under the **GNU General Public License v3.0 (GPL-3.0)** in full compliance with community open source standards.

- You are free to run, study, share, and modify this software under the terms of GPLv3.
- Derivative works must remain open source under the same license.
- See the full [LICENSE](LICENSE) file for details.

### Disclaimer
*ZynWave is an independent open-source project developed for educational and research purposes. It is not affiliated with, endorsed by, or sponsored by Google LLC, YouTube, YouTube Music, Spotify, Apple Inc., or Last.fm. All trademarks belong to their respective owners.*
