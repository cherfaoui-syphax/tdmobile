package com.example.tdmobile.qrcode

import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tdmobile.databinding.Activity2Binding
import com.example.tdmobile.databinding.ActivityQrcodeBinding
import com.google.android.material.navigation.NavigationView
import com.google.zxing.WriterException
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem

/*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class qrcode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
    }
}

 */


class qrcode : AppCompatActivity() {
    // variables for imageview, edittext,
    // button, bitmap and qrencoder.
    private var qrCodeIV: ImageView? = null
    private var dataEdt: EditText? = null
    private var generateQrBtn: Button? = null
    var bitmap: Bitmap? = null
    var qrgEncoder: QRGEncoder? = null

    private lateinit var binding: ActivityQrcodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initializing all variables.
        qrCodeIV = binding.idIVQrcode
        dataEdt = binding.idEdt
        generateQrBtn = binding.idBtnGenerateQR


        // initializing onclick listener for button.
        generateQrBtn!!.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(dataEdt!!.getText().toString())) {

                // if the edittext inputs are empty then execute
                // this method showing a toast message.
                Toast.makeText(
                    this,
                    "Enter some text to generate QR Code",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // below line is for getting
                // the windowmanager service.
                val manager = getSystemService(WINDOW_SERVICE) as WindowManager

                // initializing a variable for default display.
                val display = manager.defaultDisplay

                // creating a variable for point which
                // is to be displayed in QR Code.
                val point = Point()
                display.getSize(point)

                // getting width and
                // height of a point
                val width = point.x
                val height = point.y

                // generating dimension from width and height.
                var dimen = if (width < height) width else height
                dimen = dimen * 3 / 4

                // setting this dimensions inside our qr code
                // encoder to generate our qr code.
                qrgEncoder =
                    QRGEncoder(dataEdt!!.getText().toString(), null, QRGContents.Type.TEXT, dimen)
                try {
                    // getting our qrcode in the form of bitmap.
                    bitmap = qrgEncoder!!.encodeAsBitmap()
                    // the bitmap is set inside our image
                    // view using .setimagebitmap method.
                    qrCodeIV!!.setImageBitmap(bitmap)
                } catch (e: WriterException) {
                    // this method is called for
                    // exception handling.
                    Log.e("Tag", e.toString())
                }
            }
        })
    }
}
