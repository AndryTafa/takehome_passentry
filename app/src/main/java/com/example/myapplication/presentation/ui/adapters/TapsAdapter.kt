package com.example.myapplication.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Tap

class TapsAdapter(private val items: List<Tap>) : RecyclerView.Adapter<TapsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tapInfoText = view.findViewById<TextView>(R.id.tapInfoText)

        @SuppressLint("SetTextI18n")
        fun bind(tap: Tap) {
            val displayText = "Tapped At: ${tap.tappedAt}\n" +
                    "Status: ${tap.status}\n" +
                    "Reader ID: ${tap.readerId}"
            tapInfoText.text = displayText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tap, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
