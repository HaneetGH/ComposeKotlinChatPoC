package org.jetbrains.compose.splitpane.demo

import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter


import androidx.compose.ui.unit.Density

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.singleWindowApplication

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import org.jetbrains.skia.impl.Log
import java.awt.Cursor
import java.io.IOException
import java.net.URL


interface Listener {
    fun doThis()
    fun doThat(user: UserQuickDetails)
}

@OptIn(ExperimentalComposeUiApi::class)
private fun Modifier.cursorForHorizontalResize(): Modifier =
    pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

var IMAGE = "https://github.com/JetBrains/compose-jb/raw/master/artwork/idea-logo.svg"
val users: List<String> = listOf("Haneet", "Milan", "Vikas", "Deepak")
val listOfLastMessages: List<String> = listOf("I'm in india", "I'm in Japan", "I'm in india", "I'm in Delhi")
val listOfImagees: List<String> = listOf(IMAGE, IMAGE, IMAGE, IMAGE)
val listOfTimes: List<String> = listOf("Today, 10:30 AM", "Today, 10:40 AM", "Today, 11:30 AM", "Today, 10:20 AM")
val samepleChat: List<String> = listOf("How Are You", "Good", "And", "You?")
var listOfQuickDetails: MutableList<UserQuickDetails> = mutableListOf()

lateinit var clk: ClickUser

@OptIn(ExperimentalSplitPaneApi::class)
fun main() = singleWindowApplication(
    title = "SplitPane demo"
) {

    for (i in 0..3) {
        var quickMessages = UserQuickDetails(users[i], listOfLastMessages[i], listOfImagees[i], listOfTimes[i])
        listOfQuickDetails.add(quickMessages)
    }

    MaterialTheme {
        val splitterState = rememberSplitPaneState()
        val hSplitterState = rememberSplitPaneState()
        HorizontalSplitPane(
            splitPaneState = splitterState
        ) {
            first(330.dp) {
                printUserList()
            }
            second(50.dp) {

                clk = remember { ClickUser(listOfQuickDetails[0]) }
                secondView(clk.user)

            }
        }
    }
}

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun secondView(user: UserQuickDetails) {
    val splitterState = rememberSplitPaneState()
    val hSplitterState = rememberSplitPaneState()
    VerticalSplitPane(splitPaneState = hSplitterState) {
        first(50.dp) {
            Box(Modifier.background(Color.Black).fillMaxSize()) {
                printUserName(user)
            }
        }
        second(20.dp) {
            printUserChat(user)
        }
    }

}

@Composable
fun printUserName(user: UserQuickDetails) {
    Text(
        color = Color.White,
        text = user.user,
        style = MaterialTheme.typography.subtitle2
    )
}

@Composable
fun printUserList() {
    // val context = LocalContext.current
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(listOfQuickDetails) { user ->
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .requiredSize(300.dp, 100.dp)
                    .clickable(onClick = {
                        clk.user = user

                    })
                    .clip(RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                cardForUser(user)

            }
        }
    }
}

@Composable
fun cardForUser(user: UserQuickDetails) {
    val density = LocalDensity.current
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.requiredSize(300.dp, 100.dp)
            .padding(all = 8.dp)
            .shadow(375.dp)
    ) {
        Row {

            AsyncImage(

                load = {
                    loadSvgPainter(
                        user.imageUrl,
                        density = density
                    )
                },
                painterFor = { it },
                contentDescription = "Idea logo",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(50.dp)
            )
            Column(
                modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),

                ) {
                Text(
                    text = user.user,
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = user.userLastMessage,
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Text(
                text = user.lastMstTime,
                modifier = Modifier.padding(5.dp),
                color = Color.Black,
                fontSize = 30.sp
            )
        }
    }
}

@Composable
fun <T> AsyncImage(
    load: suspend () -> T,
    painterFor: @Composable (T) -> Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val image: T? by produceState<T?>(null) {
        value = withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {
                // instead of printing to console, you can also write this to log,
                // or show some error placeholder
                e.printStackTrace()
                null
            }
        }
    }

    if (image != null) {
        Image(
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            painter = painterFor(image!!),
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}


fun loadSvgPainter(url: String, density: Density): Painter =
    URL(url).openStream().buffered().use { loadSvgPainter(it, density) }


@Composable
fun printUserChat(user: UserQuickDetails) {
    // val context = LocalContext.current
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(samepleChat) { user ->
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable(onClick = { })
                    .clip(RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = user,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

class ClickUser(user: UserQuickDetails) {
    var user by mutableStateOf(user)
}