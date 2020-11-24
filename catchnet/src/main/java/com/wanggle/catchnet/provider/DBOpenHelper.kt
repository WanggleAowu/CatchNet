package com.wanggle.catchnet.provider
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBOpenHelper(context: Context?) : SQLiteOpenHelper(context, "people.db", null, 1) {
    companion object {
        const val id = "_id"
        const val requestTable = "test_request3"
        const val request = "request";
        const val response = "response"
        const val interfaceName = "interfaceName"
        const val header = "header"
        const val requestTime = "requestTime"
        const val url = "url"
        const val requestMethod = "requestMethod"
        const val responseCode = "responseCode"//请求结果状态，用于区分是否请求成功
    }

    private val createRequestTable =
        "create table $requestTable (_id integer primary key autoincrement, " +
                "$request text, $response text, $interfaceName text, " +
                "$header text, $requestTime text, $url text, " +
                "$requestMethod text, $responseCode integer)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createRequestTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}