package com.thibaultcharriere.leboncointest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thibaultcharriere.leboncointest.ui.album.AlbumListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, AlbumListFragment.newInstance())
                .commitNow()
        }
    }
}