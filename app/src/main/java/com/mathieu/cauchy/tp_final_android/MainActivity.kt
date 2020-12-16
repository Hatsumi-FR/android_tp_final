package com.mathieu.cauchy.tp_final_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mathieu.cauchy.tp_final_android.city.CityActivity
import com.mathieu.cauchy.tp_final_android.view.FirebaseLoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainButtonActivityFirebase.setOnClickListener { goToFirebase() }

    }
    fun goToListView(view: View) {
        val intent = Intent(this, CityActivity::class.java)
        startActivity(intent)
    }
    private fun goToFirebase() {
        startActivity(Intent(this, FirebaseLoginActivity::class.java))
    }
}