package com.app.freightCyber.presentation.home_dashboard.compliance.work_and_rest_changes

import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentWorkRestChangesBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WorkRestChangesFragment : BaseFragment<FragmentWorkRestChangesBinding>() {

    private  var dataItems=  ArrayList<DummyData>()
    private val viewModel: WorkRestChangesFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_work_rest_changes
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        dataItems = ArrayList()
        dataItems.add(
          DummyData(
                "Work",
                "Date & Time",
                "Location",
                "Odometer Value",
                "Work & Rest option"
            )
        )
        dataItems.add(
          DummyData(
                "Rest",
                "Date & Time",
                "Location",
                "Odometer Value",
                "Work & Rest option"
            )
        )
        dataItems.add(
          DummyData(
                "Work",
                "Date & Time",
                "Location",
                "Odometer Value",
                "Work & Rest option"
            )
        )
        dataItems.add(
          DummyData(
                "Rest",
                "Date & Time",
                "Location",
                "Odometer Value",
                "Work & Rest option"
            )
        )
        dataItems.add(
          DummyData(
                "Work",
                "Date & Time",
                "Location",
                "Odometer Value",
                "Work & Rest option"
            )
        )
        dataItems.add(
            DummyData(
                "Work",
                "Date & Time",
                "Location",
                "Odometer Value",
                "Work & Rest option"
            )
        )
        dataItems.add(
            DummyData(
                "Work",
                "Date & Time",
                "Location",
                "Odometer Value",
                "Work & Rest option"
            )
        )
        setUpTableRowData()
    }

    private fun setUpTableRowData() {
        val tableLayout = binding.tableLayout
        for ((index, item) in dataItems.withIndex()) {
            val tableRow = TableRow(requireContext())
            /***/
            val textViewSN = TextView(requireContext())
            textViewSN.setTextAppearance(R.style.TextView_Medium)
            textViewSN.setTextColor(ContextCompat.getColor(requireContext() , R.color.grey))
            textViewSN.text = item.time
            textViewSN.setPadding(20, 20, 20, 30)
            /***/
            val textViewDate = TextView(requireContext())
            textViewDate.text = item.workOrRestOption
            textViewDate.setPadding(20, 20, 20, 30)
            textViewDate.setTextAppearance(R.style.TextView_Medium)
            textViewDate.setTextColor(ContextCompat.getColor(requireContext() , R.color.grey))
            textViewDate.setOnClickListener {
                HelperUtils.openDatePickerDialog(requireContext() , textViewDate)
            }
            /***/
            val textViewWork = TextView(requireContext())
            textViewWork.text = item.periodTime
            textViewWork.setPadding(20, 20, 20, 30)
            textViewWork.setTextAppearance(R.style.TextView_Medium)
            textViewWork.setTextColor(ContextCompat.getColor(requireContext() , R.color.grey))
            /***/
            val textViewPeriodOfTime= TextView(requireContext())
            textViewPeriodOfTime.text = item.workOrRest
            textViewPeriodOfTime.setPadding(20, 20, 20, 30)
            textViewPeriodOfTime.setTextAppearance(R.style.TextView_Medium)
            textViewPeriodOfTime.setTextColor(ContextCompat.getColor(requireContext() , R.color.grey))
            /***/
            val textViewMinor= TextView(requireContext())
            textViewMinor.text = item.potential
            textViewMinor.setPadding(20, 20, 20, 30)
            textViewMinor.setTextAppearance(R.style.TextView_Medium)
            textViewMinor.setTextColor(ContextCompat.getColor(requireContext() , R.color.grey))
            /*** Add TextViews to TableRow ***/
            tableRow.addView(textViewSN)
            tableRow.addView(textViewDate)
            tableRow.addView(textViewWork)
            tableRow.addView(textViewPeriodOfTime)
            tableRow.addView(textViewMinor)
            /*** Add TableRow to TableLayout ***/
            tableLayout.addView(tableRow)
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }
            }
        })
    }

    data class DummyData(
        val time : String ,val workOrRestOption : String , val periodTime : String ,val workOrRest : String , val potential : String
    )
}