package com.swensun.swutils.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.R
import androidx.appcompat.app.AppCompatActivity

open class BaseAppCompatActivity: AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}