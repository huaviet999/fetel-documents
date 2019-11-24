package com.example.admin.feteldocuments.Activitys

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.webkit.DownloadListener
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import com.example.admin.feteldocuments.R
import kotlinx.android.synthetic.main.activity_pdfview.*
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class PDFViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfview)
//        view_pdf.fromAsset("dsp.pdf").load()

       val url = intent.getStringExtra("PDFURL")
        web_view.settings.javaScriptEnabled = true
        web_view.settings.builtInZoomControls = true
        web_view.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }
        }
//        web_view.setDownloadListener(object  : DownloadListener{
//            override fun onDownloadStart(url: String?, userAgent: String?, contentDisposition: String?, mimetype: String?, contentLength: Long) {
//             val request = DownloadManager.Request(Uri.parse(url))
//                request.allowScanningByMediaScanner()
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) //Notify client once download is completed!
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Name of your downloadble file goes here, example: Mathematics II ")
//            val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//                dm.enqueue(request)
////                val i = Intent(Intent.ACTION_VIEW)
////                i.setData(Uri.parse(url))
////                Toast.makeText(this@PDFViewActivity,"Downloading File",Toast.LENGTH_SHORT).show()
////                startActivity(i)
//            }
//
//        })
        web_view.loadUrl(url)
    }


}
