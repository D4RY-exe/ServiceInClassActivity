package edu.temple.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast

//modify ui to trigger things using a menu
//take callbacks into a function and call function?

class MainActivity : AppCompatActivity() {

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
        Toast.makeText(this, "Start clicked", Toast.LENGTH_SHORT).show()
    }

    private fun onStopClicked() {
        Toast.makeText(this, "Stop clicked", Toast.LENGTH_SHORT).show()
    }
}