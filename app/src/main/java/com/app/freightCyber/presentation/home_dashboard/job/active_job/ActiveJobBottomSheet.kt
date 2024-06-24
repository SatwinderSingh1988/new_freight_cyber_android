package com.app.freightCyber.presentation.home_dashboard.job.active_job

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.freightCyber.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ActiveJobBottomSheet() : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.active_job_bottom_sheet_content, container, false)

}