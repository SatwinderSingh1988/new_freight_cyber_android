package com.app.freightCyber.presentation.home_dashboard.compliance.comp_data

import androidx.recyclerview.widget.GridLayoutManager

class CenterLastItem(private val spanCount: Int, private val itemCount: Int) :
    GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        // Check if it's the last item
        return if (position == itemCount - 1) {
            // If it's the last item, make it span all columns
            spanCount
        } else {
            // Otherwise, let it span only one column
            1
        }
    }
}