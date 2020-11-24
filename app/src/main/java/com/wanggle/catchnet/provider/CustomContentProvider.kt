package com.wanggle.catchnet.provider
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.text.TextUtils
import java.lang.IllegalArgumentException

class CustomContentProvider : ContentProvider() {
    private lateinit var sqLiteDatabase : SQLiteDatabase

    companion object {
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private const val authority = "com.wanggle.catchnet.provider.CustomContentProvider"
        private const val requestUriCode = 0

        init {
            uriMatcher.addURI(authority, DBOpenHelper.requestTable, requestUriCode)
        }
    }

    private fun getTableName(uri: Uri) : String {
        return DBOpenHelper.requestTable
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val tableName = getTableName(uri)
        if (TextUtils.isEmpty(tableName)) {
            throw IllegalArgumentException("Unsupported Uri$uri")
        }
        sqLiteDatabase.insert(tableName, null, values)
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val tableName = getTableName(uri)
        if (TextUtils.isEmpty(tableName)) {
            throw IllegalArgumentException("Unsupported Uri$uri")
        }

        return sqLiteDatabase.query(tableName, projection, selection, selectionArgs, null, null, sortOrder)
    }

    override fun onCreate(): Boolean {
        initProvider()
        return false
    }

    private fun initProvider() {
        sqLiteDatabase = DBOpenHelper(context).writableDatabase
        sqLiteDatabase.beginTransaction()

        sqLiteDatabase.setTransactionSuccessful()
        sqLiteDatabase.endTransaction()
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val tableName = getTableName(uri)
        if (TextUtils.isEmpty(tableName)) {
            throw IllegalArgumentException("Unsupported Uri$uri")
        }

        val row = sqLiteDatabase.update(tableName, values, selection, selectionArgs)
        if (row > 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }

        return row
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val tableName = getTableName(uri)
        if (TextUtils.isEmpty(tableName)) {
            throw IllegalArgumentException("Unsupported Uri$uri")
        }

        val count = sqLiteDatabase.delete(tableName, selection, selectionArgs)
        if (count > 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return count
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}