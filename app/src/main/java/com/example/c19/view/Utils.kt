package com.example.c19.view

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Utilities to be used by the Views and the Presenters
 * @author Rodrigo Iturralde
 */
class Utils {
    companion object Utils {
        fun parseDate(date: String): Date? {
            val parser1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val parser2 = SimpleDateFormat("yyyy-MM-dd")
            var parsedTime: Date
            try {
                return parser1.parse(date)
            } catch (e: ParseException) {
                return parser2.parse(date)
            }
        }
    }
}