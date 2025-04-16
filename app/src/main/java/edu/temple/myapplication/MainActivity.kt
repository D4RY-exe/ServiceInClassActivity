package edu.temple.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast

//modify ui to trigger things using a menu
//take callbacks into a function and call function?
//make start and stop buttons on ui

class MainActivity : AppCompatActivity() {

    private val PREFS_NAME = "countdown_prefs"
    private val KEY_SAVED_COUNT = "saved_count"
    private val DEFAULT_COUNT = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.startButton).setOnClickListener {
onStartClicked()
        }
        
        findViewById<Button>(R.id.stopButton).setOnClickListener {
onStopClicked()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_start -> { onStartClicked() }
            R.id.action_stop -> { onStopClicked() }
            else -> return false
    }
        return true
    }
    private fun onStartClicked() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val savedCount = prefs.getInt(KEY_SAVED_COUNT, -1)
        val startValue = if (savedCount > 0) savedCount else DEFAULT_COUNT

        val serviceIntent = Intent(this, TimerService::class.java)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)

        timerBinder?.start(startValue)
        Toast.makeText(this, "Start clicked", Toast.LENGTH_SHORT).show()
    }

    private fun onStopClicked() {
        timerBinder?.pause()
        val currentValue = currentCount
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().putInt(KEY_SAVED_COUNT, currentValue).apply()
        Toast.makeText(this, "Stop clicked", Toast.LENGTH_SHORT).show()
    }
}