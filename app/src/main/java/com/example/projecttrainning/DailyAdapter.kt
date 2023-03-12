package com.example.projecttrainning

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttrainning.`class`.Daily


class DailyAdapter(private val dailyList: List<Daily>) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDailyId: TextView = itemView.findViewById(R.id.txv_no)
        val textViewDailyDate: TextView = itemView.findViewById(R.id.txv_date)
        val textViewDailyReport: TextView = itemView.findViewById(R.id.txv_memo)
        val textViewDailyRemark: TextView = itemView.findViewById(R.id.txv_remark)
        val btnDailyDetail: Button = itemView.findViewById(R.id.btn_dailydetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerlayout_daily, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val daily = dailyList[position]
        holder.textViewDailyId.text = daily.dailyid
        holder.textViewDailyDate.text = daily.dailydate
        holder.textViewDailyReport.text = daily.dailyreport
        holder.textViewDailyRemark.text = daily.dailyremark

        holder.btnDailyDetail.setOnClickListener {
            val context = it.context
            val intent = Intent(context, DailyDetailActivity::class.java)
            intent.putExtra("dailyUID", daily.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dailyList.size
    }
}
