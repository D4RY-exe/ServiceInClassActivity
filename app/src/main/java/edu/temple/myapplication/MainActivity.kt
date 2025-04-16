package edu.temple.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

//modify ui to trigger things using a menu
//take callbacks into a function and call function?
//make start and stop buttons on ui

class MainActivity : AppCompatActivity() {

    private val prefsName = "countdown_prefs"
    private val savedCount = "saved_count"
    private val defaultCount = 100

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
        val prefs = getSharedPreferences(prefsName, MODE_PRIVATE)
        val savedCount = prefs.getInt(savedCount, -1)
        val wasPaused = prefs.getBoolean("was_paused", false)
        val startValue = if (wasPaused && savedCount > 0) savedCount else defaultCount

        val serviceIntent = Intent(this, TimerService::class.java)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)

        timerBinder?.start(startValue)
        Toast.makeText(this, "Start clicked", Toast.LENGTH_SHORT).show()
    }

    private fun onStopClicked() {
        timerBinder?.pause()

        if (timerBinder?.paused == true) {
            val currentValue = currentCount
            getSharedPreferences(prefsName, MODE_PRIVATE)
                .edit()
                .putInt(savedCount, currentValue)
                .putBoolean("was_paused", true)
                .apply()
            Toast.makeText(this, "Stop clicked", Toast.LENGTH_SHORT).show()
        }
    }
    private var currentCount = defaultCount

    private val timerHandler = Handler(Looper.getMainLooper()) { msg ->
        currentCount = msg.what
        findViewById<TextView>(R.id.textView).text = currentCount.toString()
        true
    }
    private var timerBinder: TimerService.TimerBinder? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            timerBinder = service as TimerService.TimerBinder
            timerBinder?.setHandler(timerHandler)
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            timerBinder = null
        }
    }


}