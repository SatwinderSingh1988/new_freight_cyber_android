package com.app.freightCyber.presentation.home_dashboard.compliance.add_annotation

import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentAddAnnotationsTwoBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAnnotationTwoFragment : BaseFragment<FragmentAddAnnotationsTwoBinding>() {

    private val viewModel: AddAnnotationTwoFragmentVM by viewModels()

    private  var dataItems=  ArrayList<DummyData>()


    override fun getLayoutResource(): Int {
        return R.layout.fragment_add_annotations_two
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        dataItems = ArrayList()
        dataItems.add(
           DummyData(
                "Time reference",
                "Date & Time",
                "Intercept place",
                "--------"
            )
        )
        dataItems.add(
           DummyData(
               "Time reference",
                "Date & Time",
                "Intercept place",
                "--------"
            )
        )
        dataItems.add(
           DummyData(
               "Time reference",
                "Date & Time",
                "Intercept place",
                "--------"
            )
        )
        dataItems.add(
           DummyData(
               "Time reference",
                "Date & Time",
                "Intercept place",
                "--------"
            )
        )
        dataItems.add(
            DummyData(
                "Time reference",
                "Date & Time",
                "Intercept place",
                "--------"
            )
        )
        dataItems.add(
            DummyData(
                "Time reference",
                "Date & Time",
                "Intercept place",
                "--------"
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
            textViewSN.text = item.annotationFlag
            textViewSN.setPadding(20, 20, 20, 30)
            /***/
            val textViewDate = TextView(requireContext())
            textViewDate.text = item.timeOfIntercept
            textViewDate.setPadding(20, 20, 20, 30)
            textViewDate.setTextAppearance(R.style.TextView_Medium)
            textViewDate.setTextColor(ContextCompat.getColor(requireContext() , R.color.grey))
            textViewDate.setOnClickListener {
                HelperUtils.openDatePickerDialog(requireContext() , textViewDate)
            }
            /***/
            val textViewWork = TextView(requireContext())
            textViewWork.text = item.location
            textViewWork.setPadding(20, 20, 20, 30)
            textViewWork.setTextAppearance(R.style.TextView_Medium)
            textViewWork.setTextColor(ContextCompat.getColor(requireContext() , R.color.grey))
            /***/
            val textViewPeriodOfTime= TextView(requireContext())
            textViewPeriodOfTime.text = item.annotations
            textViewPeriodOfTime.setPadding(20, 20, 20, 30)
            textViewPeriodOfTime.setTextAppearance(R.style.TextView_Medium)
            textViewPeriodOfTime.setTextColor(ContextCompat.getColor(requireContext() , R.color.grey))
            /***/
            /*** Add TextViews to TableRow ***/
            tableRow.addView(textViewSN)
            tableRow.addView(textViewDate)
            tableRow.addView(textViewWork)
            tableRow.addView(textViewPeriodOfTime)
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
                R.id.imgEdit -> {
                  findNavController().navigate(R.id.addAnnotationFragment)
                }
            }
        })
    }

    data class DummyData(
        val annotationFlag : String ,val timeOfIntercept : String , val location : String ,val annotations : String
    )
}