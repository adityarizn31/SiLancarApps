package com.example.silancarapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.silancarapps.data.model.RiwayatSemua
import com.example.silancarapps.databinding.ItemRiwayatBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RiwayatSemuaAdapter(
    private var list: List<RiwayatSemua>,
    private val onDelete: (RiwayatSemua) -> Unit
) : RecyclerView.Adapter<RiwayatSemuaAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemRiwayatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRiwayatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            tvJenisLayanan.text = item.jenisLayanan
            tvNamaPemohon.text = "Nama: ${item.nama}"
            tvStatus.text = item.status
            
            val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            tvTanggal.text = sdf.format(Date(item.tanggal))

            btnDelete.setOnClickListener { onDelete(item) }
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<RiwayatSemua>) {
        list = newList
        notifyDataSetChanged()
    }
}
