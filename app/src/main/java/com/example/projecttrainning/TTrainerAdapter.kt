package com.example.projecttrainning

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttrainning.`class`.Trainer

class TTrainerAdapter(private val trainers: List<Trainer>) : RecyclerView.Adapter<TTrainerAdapter.ViewHolder>() {

    var trainerUID = ""
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public val txvTrainerCompany: TextView = itemView.findViewById(R.id.txv_trainercompany)
        public val txvTrainerService: TextView = itemView.findViewById(R.id.txv_trainerservice)
        public val btnTrainerDetail: Button = itemView.findViewById(R.id.btn_trainerdetail)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerlayout_trainer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trainer = trainers[position]
        holder.txvTrainerCompany.text = trainer.trainercompany
        holder.txvTrainerService.text = trainer.trainerservice

        holder.btnTrainerDetail.setOnClickListener {
            val context = it.context
            val intent = Intent(context, TCompanyDetailActivity::class.java)
            intent.putExtra("trainerUID", trainer.id)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return trainers.size
    }
}
