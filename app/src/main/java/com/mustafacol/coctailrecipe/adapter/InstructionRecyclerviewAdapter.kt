package com.mustafacol.coctailrecipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mustafacol.coctailrecipe.R

class InstructionRecyclerviewAdapter(private var instructionList: MutableList<String>) :
    RecyclerView.Adapter<InstructionRecyclerviewAdapter.InstructionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_instructions, parent, false)

        return InstructionViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        holder.bindView(instructionList[position])
    }

    override fun getItemCount(): Int {
        return instructionList.size
    }


    class InstructionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val instructionTxt: TextView = itemView.findViewById(R.id.cell_cocktail_instruction)

        fun bindView(instruction: String) {
            instructionTxt.text = instruction
        }
    }
}