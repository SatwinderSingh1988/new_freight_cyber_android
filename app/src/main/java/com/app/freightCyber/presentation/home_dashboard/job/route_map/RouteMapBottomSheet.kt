package com.app.freightCyber.presentation.home_dashboard.job.route_map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.freightCyber.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RouteMapBottomSheet() : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.route_job_bottom_sheet_content, container, false)

}