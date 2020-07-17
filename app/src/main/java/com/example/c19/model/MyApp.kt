package com.example.c19.model

import android.app.Application
import android.content.Context


/**
 * Allows access to Context
 * Got this idea from SO: https://stackoverflow.com/questions/54075649/access-application-context-in-companion-object-in-kotlin
 *
 * I saw that this can lead to memory leaks in some cases, but the linter *will* warn
 * if we reference this with a static and cause that to happen. I couldn't find a way
 * to access Context to write files in the data model without this.
 *
 */
class MyApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any

        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context: Context = MyApp.applicationContext()
    }
}
