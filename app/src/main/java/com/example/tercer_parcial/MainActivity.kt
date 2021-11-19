package com.example.tercer_parcial

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var apellido_p: EditText
    private lateinit var apellido_m: EditText
    private lateinit var direccion: EditText
    private lateinit var tipo_sangre: EditText
    private lateinit var imagenid: ImageView
    private lateinit var ubiBtn: Button

    private var latitud: String? = null
    private var longitud: String? = null
    private var foto: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nombre = findViewById(R.id.nombre)
        apellido_p = findViewById(R.id.apellido_p)
        apellido_m = findViewById(R.id.apellido_m)
        direccion = findViewById(R.id.direccion)
        tipo_sangre = findViewById(R.id.tipo_sangre)
        imagenid = findViewById(R.id.imagenid)
        ubiBtn = findViewById(R.id.ubiBtn)

        ubiBtn.setOnClickListener {
            val intent = Intent(this,  Ubicacion::class.java)

            intent.putExtra("Coo",  (latitud + "/" +longitud)  )
            startActivity(intent)
        }

        val url = "https://gist.githubusercontent.com/claudialegrec/7425c64656213dd8470fca5c947e48b4/raw/77fe1ff2f7a507efe82aadc3f18406d422f4f6e4/ContactInfo.json"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            com.android.volley.Request.Method.GET, url, Response.Listener { response ->
            Log.d("response","La respuesta es ${response}")

            val contact = Gson().fromJson(response, ContactInfo::class.java)

                nombre.setText(contact.nombre)
                apellido_p.setText(contact.apellido_p)
                apellido_m.setText(contact.apellido_m)
                direccion.setText(contact.direccion)
                tipo_sangre.setText(contact.tipo_sangre)
                latitud = contact.latitud
                longitud = contact.longitud
                foto = contact.imagenid

                val profilePicture = foto
                val base64Image: String = profilePicture!!.split(",").get(1)
                val decodedString = Base64.decode(base64Image, Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                imagenid.setImageBitmap(decodedByte)
        },
            Response.ErrorListener {
                Log.d("error", "algo salio mal")
            })
        queue.add(stringRequest)
    }
}