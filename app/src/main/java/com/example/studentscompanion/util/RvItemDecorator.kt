package com.example.studentscompanion.util

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class RvItemDecorator(private val spacingInPX: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.top = spacingInPX
    }
}