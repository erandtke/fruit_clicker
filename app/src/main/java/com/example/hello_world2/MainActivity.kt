package com.example.hello_world2

import android.content.Context
import android.icu.text.ListFormatter.Width
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hello_world2.ui.theme.Hello_world2Theme
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private lateinit var fruitImageView: ImageView
    private val fruitImages = intArrayOf(
        R.drawable.apple,
        R.drawable.orange,
        R.drawable.banana1,
        R.drawable.grapes1,
        R.drawable.grapes2,
        R.drawable.banana2
    )

    private var screenWidth = 0
    private var screenHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)

        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

        fruitImageView = ImageView(this)
        fruitImageView.setImageResource(R.drawable.apple)
        fruitImageView.setOnClickListener {
            renderRandomFruit()
        }

        setContentView(fruitImageView)
        renderRandomFruit()

        val lp = fruitImageView.layoutParams
        lp.width = 200
        lp.height = 200
        fruitImageView.layoutParams = lp


    }

    override fun onPostCreate(savedInstanceState: Bundle?)
    {
        super.onPostCreate(savedInstanceState)
        val backgroundDrawable = resources.getDrawable(R.drawable.background_image)
        window.setBackgroundDrawable(backgroundDrawable)
    }


    private fun renderRandomFruit()
    {

        val randomIndex = (0 until fruitImages.size).random()
        val randomXOffset = (0..screenWidth-200).random()
        val randomYOffset = (0 .. screenHeight-200).random()
        fruitImageView.translationX = randomXOffset.toFloat()
        fruitImageView.translationY = randomYOffset.toFloat()

        fruitImageView.setImageResource(fruitImages[randomIndex])
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Hello_world2Theme {
        Greeting("Android")
    }
}