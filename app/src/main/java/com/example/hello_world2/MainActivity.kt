package com.example.hello_world2

import android.content.Context
import android.icu.text.ListFormatter.Width
import android.media.AudioManager
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
import androidx.annotation.Dimension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
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
        setContent {
            Surface(color = Color.Blue, modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f))
            {
                Image(
                    painter = painterResource(R.drawable.background),
                    contentDescription = "Background image"
                )
            };
            renderRandomFruit();
        };
    }


    @Composable
    private fun renderRandomFruit()
    {
        // Remember the current index to avoid recursion
        var currentIndex = remember { 0 }

        // State variable to hold the new random fruit index
        var newFruitIndex by remember { mutableIntStateOf(0) }
        var randomXOffset by remember {mutableIntStateOf(0)}
        var randomYOffset by remember {mutableIntStateOf(0)}

        val handleClick: (Context) -> Unit = { context ->
            val clickSound = AudioManager.FLAG_PLAY_SOUND
            // Generate a new random index (different logic from currentIndex)
            newFruitIndex = (0 until fruitImages.size).random()

            val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val displayMetrics = DisplayMetrics()
            display.getMetrics(displayMetrics)

            screenWidth = displayMetrics.widthPixels
            screenHeight = displayMetrics.heightPixels
            randomXOffset = (0..screenWidth/3-200).random()
            randomYOffset = (0 .. screenHeight/3-200).random()

            // Play the default click sound using system service

            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD)
        }
        val context = LocalContext.current;
        Image(
            modifier = Modifier.offset( x = randomXOffset.dp, y = randomYOffset.dp)
                .clickable(onClick = { handleClick(context) }),
            painter = painterResource(fruitImages[newFruitIndex]),
            contentDescription = "An image of a random fruit"
        )

        // Update currentIndex on state change (new fruit)
        LaunchedEffect(newFruitIndex) {
            currentIndex = newFruitIndex
        }
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