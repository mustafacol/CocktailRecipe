package com.mustafacol.coctailrecipe

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class SwipeToDeleteCallback(context: Context, private val direction: Int) :
    ItemTouchHelper.SimpleCallback(0, direction) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_24)
    private val favoriteIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_star_24)
    private val intrinsicWidth =
        if (direction == ItemTouchHelper.LEFT) deleteIcon?.intrinsicWidth else favoriteIcon?.intrinsicWidth
    private val intrinsicHeight =
        if (direction == ItemTouchHelper.LEFT) deleteIcon?.intrinsicHeight else favoriteIcon?.intrinsicHeight
    private val background = ColorDrawable()
    private val deleteBackgroundColor = Color.parseColor("#f44336")
    private val favoriteBackgroundColor = Color.parseColor("#7f9aff")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        if (viewHolder?.adapterPosition == 10) return 0
        return super.getMovementFlags(recyclerView, viewHolder)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(
                c,
                itemView.right + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat()
            )
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }
        if (direction == ItemTouchHelper.LEFT) {
            // Draw the red delete background
            background.color = deleteBackgroundColor
            background.setBounds(
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom
            )
            background.draw(c)

            // Calculate position of delete icon
            val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2
            val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
            val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth!!
            val deleteIconRight = itemView.right - deleteIconMargin
            val deleteIconBottom = deleteIconTop + intrinsicHeight

            // Draw the delete icon
            deleteIcon?.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
            deleteIcon?.draw(c)
        } else {
            background.color = favoriteBackgroundColor
            background.setBounds(
                itemView.left + dX.toInt(),
                itemView.top,
                itemView.left,
                itemView.bottom
            )
            background.draw(c)

            // Calculate position of delete icon
            val favoriteIconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2
            val favoriteIconMargin = (itemHeight - intrinsicHeight) / 2
            val favoriteIconLeft = itemView.left + favoriteIconMargin + intrinsicWidth!!
            val favoriteIconRight = itemView.left + favoriteIconMargin
            val favoriteIconBottom = favoriteIconTop + intrinsicHeight

            // Draw the delete icon
            favoriteIcon?.setBounds(
                favoriteIconLeft,
                favoriteIconTop,
                favoriteIconRight,
                favoriteIconBottom
            )
            favoriteIcon?.draw(c)
        }


        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }

}