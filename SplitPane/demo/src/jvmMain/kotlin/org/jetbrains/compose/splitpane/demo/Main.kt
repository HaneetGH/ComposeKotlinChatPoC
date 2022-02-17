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
import androidx.compose.material.TopAppBar
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign


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
val samepleChat = mutableListOf(
    MessageModel("How Are ", false),
    MessageModel("How  You", true),
    MessageModel("How Are ", false),
    MessageModel("How Are You", true)
)
val samepleChat2 = mutableListOf(
    MessageModel("Hey", true),
    MessageModel(" Are You", false),
    MessageModel("How  You", true),
    MessageModel("How Are ", false)
)
val samepleChat3 = mutableListOf(
    MessageModel("Hi", false),
    MessageModel("How Are ", true),
    MessageModel("How You", false),
    MessageModel(" Are You", true)
)
val samepleChat4 = mutableListOf(
    MessageModel("Compose", true),
    MessageModel("How Are You", false),
    MessageModel("How  You", true),
    MessageModel("How Are ", false)
)
val listOfChats = mutableListOf(samepleChat, samepleChat2, samepleChat3, samepleChat4)
var listOfQuickDetails: MutableList<UserQuickDetails> = mutableListOf()

lateinit var clk: ClickUser

@OptIn(ExperimentalSplitPaneApi::class)
fun main() = singleWindowApplication(
    title = "SplitPane demo"
) {

    for (i in 0..3) {
        var quickMessages = UserQuickDetails(
            users[i], listOfChats[i][0].msg, listOfImagees[i], listOfTimes[i], listOfChats[i]
        )
        listOfQuickDetails.add(quickMessages)
    }

    MaterialTheme {
        val splitterState = rememberSplitPaneState()
        val hSplitterState = rememberSplitPaneState()
        HorizontalSplitPane(
            splitPaneState = splitterState
        ) {
            first(330.dp) {
                Column {
                    TopBar()
                    printUserList(listOfQuickDetails)
                }
            }
            second(50.dp) {

                clk = remember { ClickUser(listOfQuickDetails[0]) }
                secondViewAlpha(clk.user)

            }

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopBar() {
    val imageModifier = Modifier.height(240.dp).fillMaxWidth().clip(RoundedCornerShape(12.dp))
    TopAppBar(title = {
        Text(
            text = "Admin", style = TextStyle(
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
    }, actions = {
        Image(
            painter = painterResource("profile.png"),
            contentDescription = "..profile.png",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(30.dp)
        )
        Image(
            painter = painterResource("setting.png"),
            contentDescription = "image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(30.dp).padding(all = 8.dp).fillMaxWidth(),
        )
    }, backgroundColor = Color.White
    )
}

@Composable
fun cardForUser(user: UserQuickDetails) {
    val density = LocalDensity.current
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.requiredSize(300.dp, 100.dp).padding(all = 8.dp).shadow(375.dp)
    ) {
        Row {

            AsyncImage(

                load = {
                    loadSvgPainter(
                        user.imageUrl, density = density
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
                    text = user.user, style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = user.userLastMessage, style = MaterialTheme.typography.subtitle2
                )
            }
            Text(
                text = user.lastMstTime, modifier = Modifier.padding(5.dp), color = Color.Black, fontSize = 30.sp
            )
        }
    }
}


fun loadSvgPainter(url: String, density: Density): Painter =
    URL(url).openStream().buffered().use { loadSvgPainter(it, density) }

@Composable
fun printUserList(listOfUsers: MutableList<UserQuickDetails>) {
    // val context = LocalContext.current
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(listOfUsers) { user ->
            Row(
                modifier = Modifier.padding(8.dp).requiredSize(300.dp, 100.dp).clickable(onClick = {
                    clk.user = user

                }).clip(RoundedCornerShape(8.dp)), verticalAlignment = Alignment.CenterVertically
            ) {
                cardForUser(user)

            }
        }
    }


}

class ClickUser(user: UserQuickDetails) {
    var user by mutableStateOf(user)
}