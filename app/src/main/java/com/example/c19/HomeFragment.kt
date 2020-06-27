package com.example.c19

import UICard.CardAdapter
import UICard.CardModel
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var viewPager : ViewPager
    private lateinit var pagerAdapter : CardAdapter
    private lateinit var _activity : Activity

    override fun onAttach(activity: Activity) {
        this._activity = activity
        super.onAttach(_activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        val cards = mutableListOf<CardModel>()
        cards.add(
            CardModel("Global",9689411,
                3898161, 5249782,
                488959)
        )
        cards.add(
            CardModel(
                "Test",
                300,
                400,
                500,
                2
            )
        )
        viewPager = ViewPager(_activity)
        pagerAdapter = CardAdapter(cards, _activity)
        viewPager = viewPager.findViewById(R.id.cardViewPager)
        viewPager.setAdapter(pagerAdapter)

        viewPager.setPadding(130,0,130,0)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}