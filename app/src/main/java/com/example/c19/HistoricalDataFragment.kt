package com.example.c19

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import com.example.c19.model.CovidHistEntity
import com.example.c19.model.CovidHistManager
import com.example.c19.presenter.HistoricalDataPresenterImpl
import com.example.c19.view.HistoricalDataView
import com.example.c19.view.MyXAxisValueFormatter
import com.example.c19.view.Utils
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_historical_data.*
import java.util.*
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoricalDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoricalDataFragment :
    Fragment(),
    HistoricalDataView,
    AdapterView.OnItemSelectedListener,
    View.OnKeyListener {
    // TODO: Rename and change types of parameters
    private val _historicalPresenter = HistoricalDataPresenterImpl(CovidHistManager(), this)
    private var _datasetType = "confirmed"
    private var _covidEntities: List<CovidHistEntity> = listOf()
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_historical_data, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.historical_data)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val spinner = view?.findViewById<Spinner>(R.id.spinner)
        spinner?.onItemSelectedListener = this

        val search = view?.findViewById<TextInputEditText>(R.id.chart_search)
        search?.setOnKeyListener(this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoricalDataFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoricalDataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun drawChart(covidEntities: List<CovidHistEntity>) {
        _covidEntities = covidEntities

        val  referenceDate = Date(119, 0, 1)

        val entries = covidEntities.map { covidHistEntity -> Entry(
            TimeUnit.DAYS.convert(
                Utils.parseDate(covidHistEntity.date)!!.time - referenceDate.time, TimeUnit.MILLISECONDS
            ).toFloat(),
            if (_datasetType == "confirmed") covidHistEntity.confirmed.toFloat() else covidHistEntity.deaths.toFloat()
        ) }

        val dataSet = LineDataSet(entries, if (_datasetType == "confirmed") "Confirmed count" else "Death count")
        dataSet.color = Color.BLACK
        dataSet.valueTextColor  = Color.BLACK
        dataSet.circleColors = listOf(Color.BLACK)

        val lineData = LineData(dataSet)
        val chart = view?.findViewById<LineChart>(R.id.chart)
        chart?.data = lineData

        // Configure axis
        chart?.xAxis?.valueFormatter = MyXAxisValueFormatter(referenceDate)
        chart?.xAxis?.granularity = 1f
        chart?.xAxis?.labelRotationAngle = 90f

        // Refresh chart
        chart?.invalidate()

        // Set title
        if (covidEntities.isNotEmpty()) {
            view?.findViewById<TextView>(R.id.chartTitle)?.text = covidEntities[0].name
        } else {
            view?.findViewById<TextView>(R.id.chartTitle)?.text = ""
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        _datasetType =  parent?.getItemAtPosition(position).toString()
        drawChart(_covidEntities)
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if ((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            val search = view?.findViewById<TextInputEditText>(R.id.chart_search)
            _historicalPresenter.getHistoricalData(search?.text.toString())
            return true
        }
        return false
    }
}