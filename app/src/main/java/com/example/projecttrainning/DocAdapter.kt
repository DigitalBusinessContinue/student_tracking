package com.example.projecttrainning

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttrainning.`class`.Doc

class DocAdapter(private val docs: List<Doc>) : RecyclerView.Adapter<DocAdapter.ViewHolder>() {

    var docUID = ""

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public val txvname: TextView = itemView.findViewById(R.id.txv_name)
        public val btnopen: Button = itemView.findViewById(R.id.btn_open)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerlayout_doc, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doc = docs[position]
        holder.txvname.text = doc.name

        holder.btnopen.setOnClickListener {


            val context = it.context
            val url = doc.link
            val i = Intent(Intent.ACTION_VIEW,Uri.parse(url))

            ContextCompat.startActivity(context,i,null)
        }
    }

    override fun getItemCount(): Int {
        return docs.size
    }
}
