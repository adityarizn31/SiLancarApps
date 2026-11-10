# 📱 SiLancar Apps - Sistem Pelayanan Administrasi Kependudukan

![Android](https://img.shields.io/badge/Platform-Android-green?logo=android)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple?logo=kotlin)
![Min SDK](https://img.shields.io/badge/Min%20SDK-24%20(Nougat)-blue)
![Target SDK](https://img.shields.io/badge/Target%20SDK-36-orange)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-brightgreen)

**SiLancar Apps** adalah aplikasi berbasis Android yang dirancang untuk mempermudah masyarakat dalam melakukan pengajuan dan pendaftaran administrasi kependudukan serta pencatatan sipil secara digital, cepat, transparan, dan efisien dari rumah.

---

## 🔄 Alur Pengajuan Layanan

```text
  [ User Login / Registrasi ]
              │
              ▼
  [ Pilih Layanan Administrasi ] ──► (KK, KTP, Akta, KIA, Surat Pindah, dll)
              │
              ▼
  [ Isi Form & Upload Dokumen ] ──► (Validasi NIK, No HP, Berkas Pendukung)
              │
              ▼
  [ Simpan ke Database (Room) ]
              │
              ▼
  [ Pantau Progress di Riwayat ] ──► (Stepper Status: Diterima ➔ Verifikasi ➔ Diproses ➔ Selesai)
```

---

## ✨ Fitur Utama

### 🔐 1. Autentikasi & Profil Pengguna
- **Registrasi & Login**: Keamanan akun pengguna dengan validasi kredensial.
- **Manajemen Profil**: Pengguna dapat melihat dan mengedit data pribadi.
- **Session Manager**: Penyimpanan sesi login yang aman menggunakan `SharedPreferences`.

### 📋 2. Layanan Administrasi & Pengajuan
Aplikasi menyediakan 7 jenis layanan administrasi kependudukan:
- 👨‍👩‍👧‍👦 **Pendaftaran Kartu Keluarga (KK)**
- 🆔 **Pendaftaran Kartu Tanda Penduduk (KTP)**
- 👶 **Pendaftaran Akta Kelahiran**
- ⚰️ **Pendaftaran Akta Kematian**
- 👦 **Pendaftaran Kartu Identitas Anak (KIA)**
- 📊 **Pelayanan Pemanfaatan Data**
- 🏠 **Pendaftaran Surat Pindah / Perpindahan Datang**

### 📜 3. Riwayat Pendaftaran
- **Daftar Riwayat**: Memantau seluruh pengajuan layanan yang telah dikirim oleh pengguna.
- **Hapus Pengajuan**: Menghapus data riwayat pengajuan dari database lokal.

### 📊 4. Detail Pengajuan & Stepper Status (Progress Tracker) *(Terbaru!)*
- **Status Stepper Visual**: Melacak posisi proses pengajuan secara real-time melalui 4 tahapan:
  1. `Diterima` (Pengajuan masuk antrean)
  2. `Verifikasi` (Pemeriksaan berkas & formulir)
  3. `Diproses` (Tahap penerbitan dokumen)
  4. `Selesai` (Dokumen siap diambil/diterbitkan)
- **Rincian Permohonan**: Menampilkan data detail pemohon (NIK, Nama, Alamat, No. HP, Data Spesifik) secara dinamis.
- **Cek Lampiran Dokumen**: Status kelengkapan berkas yang diunggah (`✓ Terlampir` / `✕ Tidak ada`).

### 📄 5. Validasi & Pengelolaan Dokumen
- Validasi otomatis pada setiap form (NIK 16 digit, Nomor HP 10-13 digit, field wajib).
- Pengelolaan lampiran dokumen/berkas pendaftaran (`FileUtils`).

---

## 📑 Daftar Layanan & Persyaratan Dokumen

| Jenis Layanan | Dokumen Persyaratan yang Diperlukan |
| :--- | :--- |
| **Pendaftaran KK** | KTP Suami & Istri, KK Suami & Istri |
| **Pendaftaran KTP** | Scan/Foto KTP Lama, Scan Kartu Keluarga |
| **Akta Kelahiran** | Surat Keterangan Lahir, KK, KTP Ayah & Ibu |
| **Akta Kematian** | KTP & KK Almarhum/ah, Akta Kelahiran Almarhum/ah, KTP Saksi |
| **Kartu Identitas Anak (KIA)** | Akta Kelahiran Anak, KK, KTP Ayah & Ibu, Pas Foto Anak |
| **Pemanfaatan Data** | Surat Permohonan Instansi, KTP Penanggung Jawab, Surat Tugas, Legalitas Instansi |
| **Surat Pindah / Datang** | Surat Pengantar Pindah, KTP Pemohon, Kartu Keluarga |

---

## 🛠️ Arsitektur & Teknologi

Aplikasi ini dibangun menggunakan praktik pengembangan Android modern:

- **Bahasa Pemrograman**: [Kotlin](https://kotlinlang.org/)
- **Arsitektur**: **MVVM (Model-View-ViewModel)** dengan Repository Pattern.
- **Database Lokal**: [Room Database](https://developer.android.com/training/data-storage/room) untuk penyimpanan data pengajuan dan autentikasi pengguna secara offline/lokal.
- **UI & Layout**:
  - **View Binding** untuk pengaksesan komponen UI yang efisien dan type-safe.
  - **Android Jetpack Navigation Component** untuk navigasi antar Fragment.
  - **Material Design Components** (Inputs, Buttons, Cards, RecyclerView).
- **Asynchronous Processing**: Kotlin Coroutines & LiveData / ViewModel.

---

## 📂 Struktur Proyek

```text
com.example.silancarapps
├── adapter/          # Adapter RecyclerView (List Layanan & Riwayat Pendaftaran)
├── data/
│   ├── local/        # Room Database (AppDatabase), Entity (User & Layanan), dan DAO
│   ├── model/        # Data Class Model (RiwayatPendaftaran, Pendaftaran)
│   └── repository/   # Repository Pattern untuk abstraksi data
├── ui/
│   ├── auth/         # Login & Register Activity / Fragment
│   ├── home/         # Home Fragment & Dashboard Layanan
│   ├── pelayanan/    # Fragment Formulir Pengajuan (KK, KTP, Akta, KIA, Surat Pindah, dll)
│   ├── profile/      # Fragment Profil & Edit Profil
│   ├── riwayat/      # RiwayatFragment & DetailRiwayatFragment (Progress Tracker Stepper)
│   ├── settings/     # Fragment Pengaturan
│   ├── splash/       # Splash Screen Activity
│   └── viewmodel/    # ViewModel & ViewModelFactory
└── utils/            # Helper utils (SessionManager, FileUtils, Form Validators)
```

---

## 🚀 Persyaratan Sistem & Spesifikasi

- **Min SDK**: 24 (Android 7.0 Nougat)
- **Target SDK**: 36 (Android 16)
- **JDK/Java Version**: Java 11 / Kotlin 2.x
- **Build System**: Gradle dengan Kotlin DSL (`build.gradle.kts`)

---

## ⚙️ Cara Menjalankan Proyek

1. **Clone repository ini**:
   ```bash
   git clone https://github.com/username/SiLancarApps.git
   cd SiLancarApps
   ```

2. **Buka di Android Studio**:
   - Buka Android Studio (versi Ladybug / Koala / Hedgehog atau yang lebih baru).
   - Pilih **Open** dan arahkan ke folder proyek `SiLancarApps`.

3. **Sync Gradle**:
   - Biarkan Android Studio mengunduh dependensi Gradle secara otomatis.

4. **Jalankan Aplikasi**:
   - Hubungkan perangkat fisik Android atau gunakan Android Emulator (API 24+).
   - Klik tombol **Run 'app'** (`Shift + F10`).

---

## 📞 Bantuan & Layanan Kontak

Pengguna yang membutuhkan bantuan teknis atau memiliki pertanyaan terkait pengajuan dapat menghubungi admin secara langsung melalui tombol **Bantuan WhatsApp (FAB)** pada aplikasi.

---

## 📝 Lisensi

Proyek ini dibuat untuk kebutuhan pengembangan aplikasi pelayanan kependudukan.
