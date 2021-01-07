package com.example.javacvimage2videodemo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.io.File
import java.util.*

class ImageToVideo02Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_to_video02)
        //val list = assets.list("img")
        val f = File("file:////android_asset/icon1.bmp","file:////android_asset/icon2.bmp");
        Log.d("ddebug","list --- ${f.name}")
        //test()
        object : Thread(){
            override fun run() {
                super.run()
                //AssetsUtil.copyAssetsDir2Phone(this@ImageToVideo02Activity,"img",externalCacheDir?.absolutePath + File.separator + "img")
                AssetsUtil.copyAssetsDir2Sdcard(this@ImageToVideo02Activity,"img",externalCacheDir?.absolutePath )//+ File.separator + "img"

            }
        }.start();
        Log.d("ddebug","externalCacheDir = ${externalCacheDir}")
    }
    fun start(v: View){
        createMp4()
    }
    fun createMp4(){
        val  imgPath = externalCacheDir?.absolutePath+File.separator+"img"
        val mp4SavePath = externalCacheDir?.absolutePath + File.separator + System.currentTimeMillis() + "test_abc.mp4"
        val width = 1600
        val height = 900
        //读取所有图片
        val file = File(imgPath)
        val files = file.listFiles()
        val imgMap: MutableMap<Int, File> = HashMap()
        var num = 0
        for (imgFile in files) {
            imgMap[num] = imgFile
            num++
        }
        Utils.createMp4(mp4SavePath, imgMap, width, height,
            object : Utils.CompleteListener{
                override fun onComplete(path: String?) {
                    runOnUiThread{
                        val tv: TextView = findViewById(R.id.tv)
                        tv.setText("录制已完成123：$path")
                    }
                }
            });

    }
    private fun test() {

        Log.d("ddebug","Environment.getDataDirectory() = ${Environment.getDataDirectory()}")
        Log.d("ddebug","Environment.getDownloadCacheDirectory() = ${Environment.getDownloadCacheDirectory()}")
        Log.d("ddebug","Environment.getRootDirectory() = ${Environment.getRootDirectory()}")
        Log.d("ddebug","Environment.getStorageDirectory() = ${Environment.getStorageDirectory()}")
        Log.d("ddebug","Environment.getExternalStorageDirectory() = ${Environment.getExternalStorageDirectory()}")
        Log.d("ddebug","Environment.getExternalStoragePublicDirectory() = ${Environment.getExternalStoragePublicDirectory("123")}")
        Log.d("ddebug","-----------------------------")
        Log.d("ddebug","externalCacheDir = ${externalCacheDir}")
        Log.d("ddebug","-----------------------------")
    }
}