package com.example.c19

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.example.c19.model.CovidHistEntity
import com.example.c19.model.CovidHistManager
import com.example.c19.presenter.ComparePresenterImpl
import com.example.c19.view.CompareView
import com.example.c19.view.MyXAxisValueFormatter
import com.example.c19.view.Utils
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CompareFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompareFragment :
    Fragment(),
    CompareView,
    AdapterView.OnItemSelectedListener,
    View.OnKeyListener,
    View.OnClickListener {

    private val _comparePresenter = ComparePresenterImpl(CovidHistManager(), this)
    private var _datasetType = "confirmed"
    private var _covidEntities1: List<CovidHistEntity> = listOf()
    private var _covidEntities2: List<CovidHistEntity> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_compare, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.compare)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val button = view?.findViewById<Button>(R.id.compare_button)
        button?.setOnClickListener(this)

        val spinner = view?.findViewById<Spinner>(R.id.compare_spinner)
        spinner?.onItemSelectedListener = this
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CompareFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompareFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun drawComparisonChart(covidEntities1: List<CovidHistEntity>, covidEntities2: List<CovidHistEntity>) {
        _covidEntities1 = covidEntities1
        _covidEntities2 = covidEntities2

        val entityName1 = if (covidEntities1.isNotEmpty()) covidEntities1[0].name else ""
        val entityName2 = if (covidEntities2.isNotEmpty()) covidEntities2[0].name else ""

        val  referenceDate = Date(119, 0, 1)

        val entries1 = covidEntities1.map { covidHistEntity -> Entry(
            TimeUnit.DAYS.convert(
                Utils.parseDate(covidHistEntity.date)!!.time - referenceDate.time, TimeUnit.MILLISECONDS
            ).toFloat(),
            if (_datasetType == "confirmed") covidHistEntity.confirmed.toFloat() else covidHistEntity.deaths.toFloat()
        ) }

        val entries2 = covidEntities2.map { covidHistEntity -> Entry(
            TimeUnit.DAYS.convert(
                Utils.parseDate(covidHistEntity.date)!!.time - referenceDate.time, TimeUnit.MILLISECONDS
            ).toFloat(),
            if (_datasetType == "confirmed") covidHistEntity.confirmed.toFloat() else covidHistEntity.deaths.toFloat()
        ) }

        val dataSet1 = LineDataSet(entries1, if (_datasetType == "confirmed") "$entityName1 confirmed count" else "$entityName1 death count")
        val dataSet2 = LineDataSet(entries2, if (_datasetType == "confirmed") "$entityName2 confirmed count" else "$entityName2 death count")

        dataSet1.color = Color.BLACK
        dataSet1.valueTextColor  = Color.BLACK
        dataSet1.circleColors = listOf(Color.BLACK)

        val lineData = LineData(listOf(dataSet1, dataSet2))
        val chart = view?.findViewById<LineChart>(R.id.compare_chart)
        chart?.data = lineData
        chart?.description?.isEnabled = false

        // Configure axis
        chart?.xAxis?.valueFormatter = MyXAxisValueFormatter(referenceDate)
        chart?.xAxis?.granularity = 1f
        chart?.xAxis?.labelRotationAngle = 90f

        // Refresh chart
        chart?.invalidate()

        // Set title
        if (covidEntities1.isNotEmpty() && covidEntities2.isNotEmpty()) {
            view?.findViewById<TextView>(R.id.compare_title)?.text =
                """${covidEntities1[0].name}/${covidEntities2[0].name} - Comparison"""
        } else {
            view?.findViewById<TextView>(R.id.compare_title)?.text = "Nothing matches the search criterias"
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        _datasetType =  parent?.getItemAtPosition(position).toString()
        drawComparisonChart(_covidEntities1, _covidEntities2)
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if ((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            compare(view)
            return true
        }
        return false
    }

    private fun compare(view: View?) {
        val search1 = view?.findViewById<TextInputEditText>(R.id.compare1_search)
        val search2 = view?.findViewById<TextInputEditText>(R.id.compare2_search)
        _comparePresenter.getHistoricalData(search1?.text.toString(), search2?.text.toString())
        view?.findViewById<TextView>(R.id.compare_title)?.text =
            """Searching for ${search1?.text.toString()}/${search2?.text.toString()}..."""
    }

    override fun onClick(v: View?) {
        compare(view)
    }
}