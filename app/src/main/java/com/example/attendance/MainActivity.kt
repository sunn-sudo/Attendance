package com.example.attendance

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var buttonStartWork = findViewById<Button>(R.id.button_start_work)
        buttonStartWork.setOnClickListener(){
            AlertDialog.Builder(this)
                    .setTitle("タイトル")
                    .setMessage("メッセージ")
                    .setPositiveButton("OK", { dialog, which ->
                        // TODO:Yesが押された時の挙動
                    })
                    .setNegativeButton("No", { dialog, which ->
                        // TODO:Noが押された時の挙動
                    })
                    .setNeutralButton("その他", { dialog, which ->
                        // TODO:その他が押された時の挙動
                    })
                    .setIcon(android.R.drawable.ic_input_add)
                    .show()
        }
    }
}