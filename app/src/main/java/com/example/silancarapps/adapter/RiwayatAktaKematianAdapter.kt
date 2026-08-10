package com.example.silancarapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.silancarapps.data.local.PengajuanAktaKematian
import com.example.silancarapps.databinding.ItemRiwayatBinding
import com.example.silancarapps.databinding.ItemRiwayatBinding.inflate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RiwayatAktaKematianAdapter(
    private var list: List<PengajuanAktaKematian>,
    private val onDelete: (PengajuanAktaKematian) -> Unit
) : RecyclerView.Adapter<RiwayatAktaKematianAdapter.ViewHolder>() {

    class ViewHolder (val binding : ItemRiwayatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with (holder.binding) {
            tvJenisLayanan.text = item.jenisLayanan
            tvNamaPemohon.text = "Nama: ${item.nama}"
            tvStatus.text = item.status

            val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            tvTanggal.text = sdf.format(Date(item.tanggal))

            btnDelete.setOnClickListener { onDelete(item) }
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateDate(newList: List<PengajuanAktaKematian>) {
     list = newList
     notifyDataSetChanged()
    }
}