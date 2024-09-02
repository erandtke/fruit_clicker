package com.example.hello_world2

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.util.DisplayMetrics
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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

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

            Surface(color = Color.Blue, modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f))
            {
                Image(
                    painter = painterResource(R.drawable.background),
                    contentDescription = "Background image"
                )
            };
            MainContent(fruitList);
        };
    }

    @Composable fun MainContent(fruitList: List<Fruit>)
    {
        println("enter main content")
        val fruitListState = remember {mutableStateListOf(*fruitList.toTypedArray())}
        RenderRandomFruit(fruitListState);
        FruitCountScreen(fruitListState);
    }

}

@Composable
private fun RenderRandomFruit(fruitList: SnapshotStateList<Fruit>)
{
    // Remember the current index to avoid recursion
    var currentIndex by remember { mutableIntStateOf(0) }

    // State variable to hold the new random fruit index
    //var newFruitIndex by remember { mutableIntStateOf(0) }
    var randomXOffset by remember {mutableIntStateOf(300)}
    var randomYOffset by remember {mutableIntStateOf(300)}

    val windowManager = LocalContext.current.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val audioManager = LocalContext.current.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    Button(onClick =
    {
        println("Fruit count before incremented, ${fruitList[currentIndex].name} : ${fruitList[currentIndex].count}")

        //fruitList[currentIndex].count++;
        fruitList[currentIndex] = fruitList[currentIndex].copy(count = fruitList[currentIndex].count + 1)

        println("Fruit count incremented, ${fruitList[currentIndex].name} : ${fruitList[currentIndex].count}")

        val clickSound = AudioManager.FLAG_PLAY_SOUND
        // Generate a new random index (different logic from currentIndex)
        currentIndex = (0 until fruitList.size).random()

        //val windowManager = LocalContext.current.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)

        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        randomXOffset = (200..screenWidth/3-200).random()
        randomYOffset = (200 .. screenHeight/3-200).random()

        // Play the default click sound using system service

        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD)
        // Update currentIndex on state change (new fruit)

    },
        modifier = Modifier.size(150.dp, 150.dp).offset(x = randomXOffset.dp, y = randomYOffset.dp).background(Color.Transparent).padding(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    )
    {
        println("recomposing image, fruit res id: ${fruitList[currentIndex].resourceId}")
        Image(painter = painterResource(fruitList[currentIndex].resourceId), contentDescription = "A picture of a random fruit", modifier = Modifier.size(150.dp, 150.dp), contentScale = ContentScale.Fit)
    }



}

//
// ############################
//

@Composable
fun FruitCountScreen(fruitList: SnapshotStateList<Fruit>) {
    Column(modifier = Modifier.fillMaxSize()) {
        println("FruitCountScreen recomposed")
        // Other UI elements for your game (optional)
        fruitList.forEach { fruit ->
            FruitCounter(fruit = fruit) {
            }
        }
    }
}

@Composable
fun FruitCounter(fruit: Fruit, onFruitClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onFruitClick() } // Add click listener to the whole row
    ) {
        println("FruitCounter recomposed: ${fruit.name}, count: ${fruit.count}")
        Image(
            modifier = Modifier.size(100.dp,100.dp),
            painter = painterResource(fruit.resourceId),
            contentDescription = "An image of a random fruit"
        );
        Text(fruit.name + ": ${fruit.count}",
            color = Color.White,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ));

    }
}