package com.example.sneakershop

import android.app.Application
import com.example.sneakershop.data.SneakerDatabase

class SneakerApp : Application() {

    val database by lazy { SneakerDatabase.getDatabase(this) }
}
