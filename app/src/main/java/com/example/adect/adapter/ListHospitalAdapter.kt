package com.example.adect.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adect.R
import com.example.adect.models.Hospital

class ListHospitalAdapter(private val listHospitals: ArrayList<Hospital>) : RecyclerView.Adapter<ListHospitalAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListHospitalAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_hospital, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListHospitalAdapter.ListViewHolder, position: Int) {
        val (id, kode, nama, kota, provinsi, alamat, latitude, longitude, telp, jenis_faskes) = listHospitals[position]

        holder.tvNama.text = nama
        holder.tvJenis.text = jenis_faskes
        holder.tvAlamat.text = "Alamat : " + alamat
        holder.tvTelp.text = "Telepon : " + telp
        holder.tvKode.text = "Kode : " + kode

        if (jenis_faskes == "PUSKESMAS") {
            holder.tvJenis.setBackgroundColor(Color.parseColor("#EF5DA8"))
        } else if (jenis_faskes == "RUMAH SAKIT") {
            holder.tvJenis.setBackgroundColor(Color.parseColor("#5D5FEF"))
        } else if (jenis_faskes == "KLINIK") {
            holder.tvJenis.setBackgroundColor(Color.parseColor("#7879F1"))
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listHospitals[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listHospitals.size
    }

    class ListViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        var tvNama: TextView = itemView.findViewById(R.id.faskesNama)
        var tvJenis: TextView = itemView.findViewById(R.id.faskesJenis)
        var tvAlamat: TextView = itemView.findViewById(R.id.faskesAlamat)
        var tvTelp: TextView = itemView.findViewById(R.id.faskesTelp)
        var tvKode: TextView = itemView.findViewById(R.id.faskesKode)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Hospital)
    }
}