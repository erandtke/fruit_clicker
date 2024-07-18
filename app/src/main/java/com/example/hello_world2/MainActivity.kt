package com.example.hello_world2

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hello_world2.ui.theme.FruitClickerTheme
import android.widget.ImageView
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
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
    private var fruitList = listOf(
        Fruit("Apple", 0, R.drawable.apple, Fruit.Ripeness.RIPE),
        Fruit("Grape", 0, R.drawable.grapes1, Fruit.Ripeness.RIPE),
        Fruit("Orange", 0, R.drawable.orange, Fruit.Ripeness.RIPE),
        Fruit("Banana", 0, R.drawable.banana1, Fruit.Ripeness.RIPE));

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d("dump","dump");

            Surface(color = Color.Blue, modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f))
            {
                Image(
                    painter = painterResource(R.drawable.background),
                    contentDescription = "Background image"
                )
            };
            FruitCountScreen(fruitList);
            RenderRandomFruit();
        };
    }


    @Composable
    private fun RenderRandomFruit()
    {
        Log.d("dump","dump");
        Log.d("dump","dump");
        Log.d("dump","dump");

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
            modifier = Modifier.size(100.dp, 100.dp).offset( x = randomXOffset.dp, y = randomYOffset.dp)
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
    FruitClickerTheme {
        Greeting("Android")
    }
}

//
// ############################
//

@Composable
fun FruitCountScreen(fruitList: List<Fruit>) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Other UI elements for your game (optional)
        fruitList.forEach { fruit ->
            FruitCounter(fruit = fruit) {
                // Increment count and recompose on click
                fruit.count++
            }
        }
    }
}

@Composable
fun FruitCounter(fruit: Fruit, onFruitClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onFruitClick() } // Add click listener to the whole row
    ) {
        Image(
            modifier = Modifier.size(100.dp,100.dp),
            painter = painterResource(fruit.resourceId),
            contentDescription = "An image of a random fruit"
        );
        Text(fruit.name + ": ${fruit.count}");

    }
}