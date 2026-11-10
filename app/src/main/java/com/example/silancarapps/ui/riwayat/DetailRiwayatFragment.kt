package com.example.silancarapps.ui.riwayat

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.silancarapps.R
import com.example.silancarapps.data.local.PengajuanAktaKelahiran
import com.example.silancarapps.data.local.PengajuanAktaKematian
import com.example.silancarapps.data.local.PengajuanKIA
import com.example.silancarapps.data.local.PengajuanKK
import com.example.silancarapps.data.local.PengajuanKTP
import com.example.silancarapps.data.local.PengajuanPelayananPemanfaatanData
import com.example.silancarapps.data.local.PengajuanSuratPindah
import com.example.silancarapps.databinding.FragmentDetailRiwayatBinding
import com.example.silancarapps.ui.viewmodel.PendaftaranViewModel
import com.example.silancarapps.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailRiwayatFragment : Fragment() {

    private var _binding: FragmentDetailRiwayatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PendaftaranViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var pengajuanId: Int = 0
    private var jenisLayanan: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pengajuanId = it.getInt(ARG_ID, 0)
            jenisLayanan = it.getString(ARG_JENIS_LAYANAN, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadDetailData()
    }

    private fun loadDetailData() {
        viewLifecycleOwner.lifecycleScope.launch {
            when {
                jenisLayanan.contains("Kartu Keluarga", ignoreCase = true) || jenisLayanan.contains("KK", ignoreCase = true) -> {
                    val item = viewModel.getKKById(pengajuanId)
                    item?.let { displayKK(it) }
                }
                jenisLayanan.contains("Kartu Tanda Penduduk", ignoreCase = true) || jenisLayanan.contains("KTP", ignoreCase = true) -> {
                    val item = viewModel.getKTPById(pengajuanId)
                    item?.let { displayKTP(it) }
                }
                jenisLayanan.contains("Akta Kelahiran", ignoreCase = true) -> {
                    val item = viewModel.getAktaKelahiranById(pengajuanId)
                    item?.let { displayAktaKelahiran(it) }
                }
                jenisLayanan.contains("Akta Kematian", ignoreCase = true) -> {
                    val item = viewModel.getAktaKematianById(pengajuanId)
                    item?.let { displayAktaKematian(it) }
                }
                jenisLayanan.contains("Kartu Identitas Anak", ignoreCase = true) || jenisLayanan.contains("KIA", ignoreCase = true) -> {
                    val item = viewModel.getKIAById(pengajuanId)
                    item?.let { displayKIA(it) }
                }
                jenisLayanan.contains("Pemanfaatan Data", ignoreCase = true) -> {
                    val item = viewModel.getPelayananPemanfaatanDataById(pengajuanId)
                    item?.let { displayPemanfaatanData(it) }
                }
                jenisLayanan.contains("Pindah", ignoreCase = true) -> {
                    val item = viewModel.getSuratPindahById(pengajuanId)
                    item?.let { displaySuratPindah(it) }
                }
            }
        }
    }

    private fun updateHeader(title: String, id: Int, tanggal: Long, status: String) {
        binding.tvJenisLayanan.text = title
        binding.tvNoRegistrasi.text = "#REG-${1000 + id}"
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        binding.tvTanggal.text = "Tanggal Pengajuan: ${sdf.format(Date(tanggal))}"
        binding.tvStatusBadge.text = status

        updateStepper(status)
    }

    private fun updateStepper(status: String) {
        val activeColor = ContextCompat.getColor(requireContext(), R.color.primary)
        val activeGreen = Color.parseColor("#10B981")
        val inactiveLineColor = Color.parseColor("#CBD5E1")

        val stepLevel = when (status.lowercase(Locale.ROOT)) {
            "verifikasi", "verifikasi berkas" -> 2
            "diproses", "sedang diproses" -> 3
            "selesai", "disetujui" -> 4
            else -> 1 // Default "Menunggu" / "Diterima"
        }

        // Step 1
        binding.step1Circle.setBackgroundResource(R.drawable.bg_stepper_circle_active)
        binding.step1Label.setTextColor(activeColor)

        // Step 2
        if (stepLevel >= 2) {
            binding.step2Circle.setBackgroundResource(R.drawable.bg_stepper_circle_active)
            binding.step2Label.setTextColor(activeColor)
            binding.lineStep1to2.setBackgroundColor(activeGreen)
        } else {
            binding.step2Circle.setBackgroundResource(R.drawable.bg_stepper_circle_inactive)
            binding.lineStep1to2.setBackgroundColor(inactiveLineColor)
        }

        // Step 3
        if (stepLevel >= 3) {
            binding.step3Circle.setBackgroundResource(R.drawable.bg_stepper_circle_active)
            binding.step3Label.setTextColor(activeColor)
            binding.lineStep2to3.setBackgroundColor(activeGreen)
        } else {
            binding.step3Circle.setBackgroundResource(R.drawable.bg_stepper_circle_inactive)
            binding.lineStep2to3.setBackgroundColor(inactiveLineColor)
        }

        // Step 4
        if (stepLevel >= 4) {
            binding.step4Circle.setBackgroundResource(R.drawable.bg_stepper_circle_active)
            binding.step4Label.setTextColor(activeColor)
            binding.lineStep3to4.setBackgroundColor(activeGreen)
        } else {
            binding.step4Circle.setBackgroundResource(R.drawable.bg_stepper_circle_inactive)
            binding.lineStep3to4.setBackgroundColor(inactiveLineColor)
        }

        // Set status text description based on step level
        binding.tvStatusDescription.text = when (stepLevel) {
            1 -> "Pengajuan Anda telah berhasil dikirim dan sedang menunggu antrean verifikasi oleh petugas."
            2 -> "Berkas pengajuan Anda sedang diverifikasi oleh petugas administrasi. Mohon pastikan data sudah sesuai."
            3 -> "Permohonan Anda sedang dalam tahap pemrosesan dan penerbitan dokumen resmi."
            4 -> "Selamat! Pengajuan Anda telah selesai diproses. Silakan periksa atau ambil dokumen fisik sesuai arahan."
            else -> "Pengajuan sedang dalam proses."
        }
    }

    private fun displayKK(item: PengajuanKK) {
        updateHeader(item.jenisLayanan, item.id, item.tanggal, item.status)
        clearDetails()

        addDetailRow("Nama Pemohon", item.nama)
        addDetailRow("NIK Suami", item.nikSuami)
        addDetailRow("NIK Istri", item.nikIstri)
        addDetailRow("No KK Suami", item.noKKSuami)
        addDetailRow("No KK Istri", item.noKKIstri)
        addDetailRow("No. Telepon / HP", item.noHp)
        addDetailRow("Alamat Dominasi", item.alamat)

        addDocumentRow("KTP Suami", item.docKTPSuami)
        addDocumentRow("KTP Istri", item.docKTPIstri)
        addDocumentRow("KK Suami", item.docKKSuami)
        addDocumentRow("KK Istri", item.docKKIstri)
    }

    private fun displayKTP(item: PengajuanKTP) {
        updateHeader(item.jenisLayanan, item.id, item.tanggal, item.status)
        clearDetails()

        addDetailRow("Nama Lengkap", item.nama)
        addDetailRow("NIK", item.nik)
        addDetailRow("No KK", item.noKK)
        addDetailRow("No. HP / WhatsApp", item.noHp)
        addDetailRow("Alamat Lengkap", item.alamat)

        addDocumentRow("Foto/Scan KTP Lama", item.docKTP)
        addDocumentRow("Foto/Scan KK", item.docKK)
    }

    private fun displayAktaKelahiran(item: PengajuanAktaKelahiran) {
        updateHeader(item.jenisLayanan, item.id, item.tanggal, item.status)
        clearDetails()

        addDetailRow("Nama Anak", item.namaAnak)
        addDetailRow("Jenis Kelamin", item.jenisKelamin)
        addDetailRow("Tempat, Tgl Lahir", "${item.tempatLahir}, ${item.tanggalLahir}")
        addDetailRow("Waktu Lahir", item.waktuLahir)
        addDetailRow("Anak Ke", item.anakKe)
        addDetailRow("Berat & Panjang", "${item.beratBayi} kg / ${item.panjangBayi} cm")
        addDetailRow("Nama & NIK Ayah", "${item.namaAyah} (${item.nikAyah})")
        addDetailRow("Nama & NIK Ibu", "${item.namaIbu} (${item.nikIbu})")

        addDocumentRow("Surat Keterangan Lahir", item.docSuratLahir)
        addDocumentRow("Dokumen Kartu Keluarga", item.docKK)
        addDocumentRow("KTP Ayah", item.docKTPAyah)
        addDocumentRow("KTP Ibu", item.docKTPIbu)
    }

    private fun displayAktaKematian(item: PengajuanAktaKematian) {
        updateHeader(item.jenisLayanan, item.id, item.tanggal, item.status)
        clearDetails()

        addDetailRow("Nama Almarhum/ah", item.namaAlm)
        addDetailRow("NIK Almarhum/ah", item.nikAlm)
        addDetailRow("No KK Almarhum/ah", item.noKKAlm)
        addDetailRow("No Akta Almarhum/ah", item.noAktaAlm)
        addDetailRow("Nama Saksi", item.namaSaksi)
        addDetailRow("NIK Saksi", item.nikSaksi)
        addDetailRow("No. HP Saksi", item.noHpSaksi)
        addDetailRow("Alamat Saksi", item.alamatSaksi)

        addDocumentRow("KTP Almarhum/ah", item.docKTPAlm)
        addDocumentRow("KK Almarhum/ah", item.docKKAlm)
        addDocumentRow("Akta Kelahiran Almarhum/ah", item.docAktaKelahiran)
        addDocumentRow("KTP Saksi", item.docKTPSaksi)
    }

    private fun displayKIA(item: PengajuanKIA) {
        updateHeader(item.jenisLayanan, item.id, item.tanggal, item.status)
        clearDetails()

        addDetailRow("Nama Lengkap Anak", item.namaLengkapAnak)
        addDetailRow("NIK Anak", item.nikAnak)
        addDetailRow("Tempat, Tgl Lahir", "${item.tempatLahirAnak}, ${item.tanggalLahirAnak}")
        addDetailRow("Jenis Kelamin", item.jenisKelaminAnak)
        addDetailRow("Nama & NIK Ayah", "${item.namaAyah} (${item.nikAyah})")
        addDetailRow("Nama & NIK Ibu", "${item.namaIbu} (${item.nikIbu})")

        addDocumentRow("Akta Kelahiran Anak", item.docAktaKelahiran)
        addDocumentRow("Kartu Keluarga", item.docKK)
        addDocumentRow("KTP Ayah", item.docKTPAyah)
        addDocumentRow("KTP Ibu", item.docKTPIbu)
        addDocumentRow("Pas Foto Anak", item.docPasFotoAnak)
    }

    private fun displayPemanfaatanData(item: PengajuanPelayananPemanfaatanData) {
        updateHeader(item.jenisLayanan, item.id, item.tanggal, item.status)
        clearDetails()

        addDetailRow("Nama Instansi", item.namaInstansi)
        addDetailRow("Jenis Instansi", item.jenisInstansi)
        addDetailRow("Email Instansi", item.emailInstansi)
        addDetailRow("No HP Instansi", item.noHpInstansi)
        addDetailRow("Alamat Instansi", item.alamatInstansi)
        addDetailRow("Penanggung Jawab", "${item.namaPenanggungJawab} (${item.jabatanPenanggungJawab})")
        addDetailRow("NIK Penanggung Jawab", item.nikPenanggungJawab)
        addDetailRow("Tujuan Pemanfaatan", item.tujuanPemanfaatanData)
        addDetailRow("Data Dibutuhkan", item.dataDibutuhkan)
        addDetailRow("Keterangan", item.keterangan)

        addDocumentRow("Surat Permohonan Instansi", item.docSuratPermohonanInstansi)
        addDocumentRow("KTP Penanggung Jawab", item.docKTPPenanggungJawab)
        addDocumentRow("Surat Tugas", item.docSuratTugas)
        addDocumentRow("Legalitas Instansi", item.docLegalitasInstansi)
    }

    private fun displaySuratPindah(item: PengajuanSuratPindah) {
        updateHeader(item.jenisLayanan, item.id, item.tanggal, item.status)
        clearDetails()

        addDetailRow("Nama Pemohon", item.nama)
        addDetailRow("NIK Pemohon", item.nik)
        addDetailRow("No KK Pemohon", item.noKK)
        addDetailRow("No HP Pemohon", item.noHp)
        addDetailRow("Daerah Asal", "${item.alamatAsal}, ${item.desaAsal}, ${item.kecamatanAsal}")
        addDetailRow("Daerah Tujuan", "${item.alamatTujuan}, ${item.desaTujuan}, ${item.kecamatanTujuan}")

        addDocumentRow("Surat Pengantar Pindah", item.docSuratPindah)
        addDocumentRow("KTP Pemohon", item.docKTP)
        addDocumentRow("Kartu Keluarga", item.docKK)
    }

    private fun clearDetails() {
        binding.containerDetailFields.removeAllViews()
        binding.containerDocuments.removeAllViews()
    }

    private fun addDetailRow(label: String, value: String?) {
        val displayValue = if (value.isNull_or_Empty()) "-" else value
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 8, 0, 8)
        }

        val tvLabel = TextView(requireContext()).apply {
            text = label
            textSize = 12f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
        }

        val tvValue = TextView(requireContext()).apply {
            text = displayValue
            textSize = 14f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.text_primary))
            setPadding(0, 2, 0, 0)
        }

        layout.addView(tvLabel)
        layout.addView(tvValue)
        binding.containerDetailFields.addView(layout)
    }

    private fun String?.isNull_or_Empty(): Boolean {
        return this.isNullOrBlank()
    }

    private fun addDocumentRow(docName: String, path: String?) {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 10, 0, 10)
        }

        val tvDocName = TextView(requireContext()).apply {
            text = docName
            textSize = 14f
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.text_primary))
        }

        val tvDocStatus = TextView(requireContext()).apply {
            if (!path.isNullOrBlank()) {
                text = "✓ Terlampir"
                setTextColor(Color.parseColor("#10B981"))
            } else {
                text = "✕ Tidak ada"
                setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
            }
            textSize = 13f
        }

        layout.addView(tvDocName)
        layout.addView(tvDocStatus)
        binding.containerDocuments.addView(layout)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_ID = "arg_id"
        const val ARG_JENIS_LAYANAN = "arg_jenis_layanan"

        fun newInstance(id: Int, jenisLayanan: String): DetailRiwayatFragment {
            val fragment = DetailRiwayatFragment()
            val args = Bundle().apply {
                putInt(ARG_ID, id)
                putString(ARG_JENIS_LAYANAN, jenisLayanan)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
