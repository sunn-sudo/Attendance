package com.example.attendance

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

    private val dbName: String = "AttendanceDB"
    private val tableName: String = "Attendance"
    private val dbVersion: Int = 1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // リスト宣言
        var listView = findViewById<ListView>(R.id.list_view)
        val array: ArrayList<String> = ArrayList()

        // DB設定
        val dbHelper = SampleDBHelper(applicationContext, dbName, null, dbVersion)

        // 初期表示時にデータを取得する
        try {
            val selectDatabase = dbHelper.readableDatabase
            val sql = "select id, name, date from $tableName"
            val cursor = selectDatabase.rawQuery(sql, null)

            if (cursor.count > 0) {
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    var array_text = cursor.getString(2) + ":" + cursor.getString(1);
                    array.add(array_text);
                    cursor.moveToNext()
                }
            }
            // ListView に取得したデータを表示する
            var adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, array);
            listView.setAdapter(adapter);

        } catch (exception: Exception) {
            Log.e("selectData", exception.toString());
        }

        // 出勤ボタンイベント設定
        var buttonStartWork = findViewById<Button>(R.id.button_start_work)
        // 退勤ボタンイベント設定
        var buttonEndWork = findViewById<Button>(R.id.button_end_work)
        // 登録用
        val insertDatabase = dbHelper.writableDatabase
        // 日付フォーマットの指定
        val dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

        buttonStartWork.setOnClickListener() {
            AlertDialog.Builder(this)
                    .setTitle("出勤登録")
                    .setMessage("出勤登録をしますか")
                    .setPositiveButton("OK") { dialog, which ->
                        // TODO:Yesが押された時の挙動
                        try {
                            val dateAndtime: LocalDateTime = LocalDateTime.now()
                            val dateAndtimeDtf: String = dateAndtime.format(dtf).toString()
                            val values = ContentValues()
                            values.put("name", "【出勤】");
                            values.put("date", dateAndtimeDtf);

                            // データ登録
                            insertDatabase.insertOrThrow(tableName, null, values)

                            // ListViewに追加
                            array.add("$dateAndtimeDtf:【出勤】");
                            listView.setAdapter(null);
                            var adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, array);
                            listView.setAdapter(adapter);

                        } catch (exception: Exception) {
                            Log.e("insertData", exception.toString())
                        }

                    }
                    .setNegativeButton("No", { dialog, which ->
                        // TODO:Noが押された時の挙動
                    })
                    .show()
        }

        buttonEndWork.setOnClickListener() {
            AlertDialog.Builder(this)
                    .setTitle("退勤登録")
                    .setMessage("退勤登録をしますか")
                    .setPositiveButton("OK") { dialog, which ->
                        // TODO:Yesが押された時の挙動
                        try {
                            val dateAndtime: LocalDateTime = LocalDateTime.now()
                            val dateAndtimeDtf: String = dateAndtime.format(dtf).toString()
                            val values = ContentValues()
                            values.put("name", "【退勤】");
                            values.put("date", dateAndtimeDtf);

                            // データ登録
                            insertDatabase.insertOrThrow(tableName, null, values)

                            // ListViewに追加
                            array.add("$dateAndtimeDtf:【退勤】");
                            listView.setAdapter(null);
                            var adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, array);
                            listView.setAdapter(adapter);

                        } catch (exception: Exception) {
                            Log.e("insertData", exception.toString())
                        }

                    }
                    .setNegativeButton("No", { dialog, which ->
                        // TODO:Noが押された時の挙動
                    })
                    .show()
        }
    }

    private class SampleDBHelper(context: Context, databaseName: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, databaseName, factory, version) {

        // DB作成時に起動する
        override fun onCreate(database: SQLiteDatabase?) {
            database?.execSQL("create table if not exists Attendance (id integer primary key, name text, date text)");
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        }

    }
}
