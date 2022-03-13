package com.example.mymineSweeper

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymineSweeper.MineGridRecyclerAdapter.MineTileViewHolder

class MineGridRecyclerAdapter(private var cells: List<Cell>, private val listener: OnCellClickListener) : RecyclerView.Adapter<MineTileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MineTileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cell, parent, false)
        return MineTileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MineTileViewHolder, position: Int) {
        holder.bind(cells[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    fun setCells(cells: List<Cell>) {
        this.cells = cells
        notifyDataSetChanged()
    }

    inner class MineTileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var valueTextView: TextView = itemView.findViewById(R.id.item_cell_value)
        @SuppressLint("ResourceAsColor") fun bind(cell: Cell) {
            //itemView.setBackgroundColor(R.color.silver)
            itemView.setBackgroundColor(Color.rgb(192,192,192))
            itemView.setOnClickListener { listener.cellClick(cell) }
            if (cell.isRevealed) {
                if (cell.value == Cell.BOMB) {
                    valueTextView.setText(R.string.bomb)
                }
                else if (cell.value == Cell.BLANK) {
                    valueTextView.text = ""
                    itemView.setBackgroundColor(Color.WHITE)
                }
                else {
                    valueTextView.text = cell.value.toString()
                    if (cell.value == 1) {
                        valueTextView.setTextColor(Color.BLUE)
                    }
                    else if (cell.value == 2) {
                        valueTextView.setTextColor(Color.GREEN)
                    }
                    else if (cell.value == 3) {
                        valueTextView.setTextColor(Color.RED)
                    }
                    else if (cell.value == 4){
                        valueTextView.setTextColor(R.color.violet)
                    }
                }
            }
            else if (cell.isFlagged) {
                valueTextView.setText(R.string.flag)
            }
        }

    }
}