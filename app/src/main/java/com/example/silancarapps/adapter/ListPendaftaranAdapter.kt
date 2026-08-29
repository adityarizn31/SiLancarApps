package com.example.silancarapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.silancarapps.R
import androidx.recyclerview.widget.RecyclerView
import com.example.silancarapps.data.model.Pendaftaran

class ListPendaftaranAdapter(
    private val listPendaftaran: List<Pendaftaran>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ListPendaftaranAdapter.ListViewHolder>() {

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPelayanan: ImageView = view.findViewById(R.id.imgPelayanan)
        val tvPelayanan: TextView = view.findViewById(R.id.tvPelayanan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listPendaftaran[position]
        holder.imgPelayanan.setImageResource(item.image)
        holder.tvPelayanan.text = item.title
        
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int = listPendaftaran.size
}
