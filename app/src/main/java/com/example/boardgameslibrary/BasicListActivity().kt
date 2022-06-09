package com.example.boardgameslibrary

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

abstract class BasicListActivity(): AppCompatActivity(){
    protected fun addField(linearLayout: LinearLayout, text: String, weight: Float){
        val contentParam = LinearLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT,
            weight
        )

        val view = TextView(this)
        view.text = text
        view.textSize = 16f
        view.textAlignment = View.TEXT_ALIGNMENT_CENTER
        view.layoutParams = contentParam
        linearLayout.addView(view)
    }

    protected fun addImage(linearLayout: LinearLayout, imageURL: String, weight: Float){
        val imageView = ImageView(this)
        var icon: Bitmap? = null
        var isDone = 0
        Thread {
            icon = getBitmapFromURL(imageURL)
            if(icon == null){
                imageView.setImageResource(R.drawable.ac) //TODO: zmien zdjecie
                isDone = 1
            } else {
                imageView.setImageBitmap(icon)
                isDone = 1
            }
        }.start()
        var i = 0
        while (isDone==0 && i<100){
            Thread.sleep(10)
            i++
        }
        val imageParam = LinearLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT,
            weight
        )
        imageView.layoutParams = imageParam
        linearLayout.addView(imageView)
    }

    protected fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url
                .openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}