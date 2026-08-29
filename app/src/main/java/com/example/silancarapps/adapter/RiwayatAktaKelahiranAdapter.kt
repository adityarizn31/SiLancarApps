package com.example.silancarapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.silancarapps.data.local.PengajuanAktaKelahiran
import com.example.silancarapps.databinding.ItemRiwayatBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RiwayatAktaKelahiranAdapter(
    private var list : List<PengajuanAktaKelahiran>,
    private val onDelete: (PengajuanAktaKelahiran) -> Unit
) : RecyclerView.Adapter<RiwayatAktaKelahiranAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemRiwayatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRiwayatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = list[position]
        with (holder.binding) {
            tvJenisLayanan.text = item.jenisLayanan
            tvNamaPemohon.text = "Nama: ${item.namaAnak}"
            tvStatus.text = item.status

            val sdf = SimpleDateFormat("dd MM yyyy, KK:mm", Locale.getDefault())
            tvTanggal.text = sdf.format(Date(item.tanggal))

            btnDelete.setOnClickListener { onDelete(item) }
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<PengajuanAktaKelahiran>) {
        list = newList
        notifyDataSetChanged()
    }
}