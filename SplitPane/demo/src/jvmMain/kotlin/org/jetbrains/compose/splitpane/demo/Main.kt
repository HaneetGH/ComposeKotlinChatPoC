package org.jetbrains.compose.splitpane.demo

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
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
import androidx.compose.ui.text.input.ImeAction
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
import org.w3c.dom.Text
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
    MessageModel("How Are, where are you ", false),
    MessageModel("How  You, some sample text", true),
    MessageModel("How Are, some sample text ", false),
    MessageModel("How Are You, some sample text", true)
)
val samepleChat2 = mutableListOf(
    MessageModel("Hey, some sample text", true),
    MessageModel(" Are You, some sample text", false),
    MessageModel("How  You, some sample text", true),
    MessageModel("How Are , some sample text", false)
)
val samepleChat3 = mutableListOf(
    MessageModel("Hi", false),
    MessageModel("How Are, some sample text ", true),
    MessageModel("How You, some sample text", false),
    MessageModel(" Are You, some sample text", true)
)
val samepleChat4 = mutableListOf(
    MessageModel("Compose, some sample text", true),
    MessageModel("How Are You, some sample text", false),
    MessageModel("How  You, some sample text", true),
    MessageModel("How Are , some sample text", false)
)
val listOfChats = mutableListOf(samepleChat, samepleChat2, samepleChat3, samepleChat4)
var listOfQuickDetails: MutableList<UserQuickDetails> = mutableListOf()

lateinit var clk: ClickUser
lateinit var searchUser: SearchUser

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
    searchUser = remember { SearchUser("") }
    MaterialTheme {
        val splitterState = rememberSplitPaneState()
        val hSplitterState = rememberSplitPaneState()
        HorizontalSplitPane(
            splitPaneState = splitterState
        ) {
            first(300.dp) {
                Column {
                    TopBar()
                    SearchInput()
                    printUserList(listOfQuickDetails.filter { userQuickDetails ->  (userQuickDetails.user.lowercase()).contains(searchUser.searchUser.lowercase())}.toMutableList())
                }
            }
            second(50.dp) {

                clk = remember { ClickUser(listOfQuickDetails[0]) }
                secondViewAlpha(clk.user)

            }

        }
    }
}

@Composable
fun SearchInput(
) {
    Row (verticalAlignment = Alignment.CenterVertically,modifier = Modifier.background(Color(0XFF2f3e45)).padding(5.dp).clip(RoundedCornerShape(20.dp))) {

        BasicTextField(
            // 4


            modifier = Modifier.background(
                Color.Gray,
                MaterialTheme.shapes.small,
            ).cursorForHorizontalResize().padding(start = 15.dp, top = 5.dp)
                .fillMaxWidth().height(30.dp).clip(RoundedCornerShape(10.dp)),
            value = searchUser.searchUser,
            singleLine = true,
            cursorBrush = SolidColor(Color.Gray),
            textStyle = LocalTextStyle.current.copy(
                color = Color.White,
                fontSize = 15.sp,
                textAlign = TextAlign.Start,

            ),
            onValueChange = { searchUser.searchUser = it },


        )

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopBar() {
    val imageModifier = Modifier.height(240.dp).fillMaxWidth().clip(RoundedCornerShape(12.dp))
    TopAppBar(title = {
        Image(
            painter = painterResource("profile.png"),
            contentDescription = "..profile.png",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(30.dp)
        )
    }, actions = {
        Image(
            painter = painterResource("setting.png"),
            contentDescription = "image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(30.dp).padding(all = 5.dp).fillMaxWidth(),
        )
    }, backgroundColor = Color(0XFF455A64)
    )
}

@Composable
fun cardForUser(user: UserQuickDetails) {
    val density = LocalDensity.current

    Box(
        modifier = Modifier.background(Color.Transparent).cursorForHorizontalResize()
    ) {
        Column {
            Row(

                modifier = Modifier.padding(10.dp).fillMaxWidth().cursorForHorizontalResize(), horizontalArrangement = Arrangement.Center
            ) {

                /*AsyncImage(

                 load = {
                     loadSvgPainter(
                         user.imageUrl, density = density
                     )
                 },
                 painterFor = { it },
                 contentDescription = "Idea logo",
                 contentScale = ContentScale.FillWidth,
                 modifier = Modifier.width(50.dp)
             )*/
                Image(
                    painter = painterResource("profile.png"),
                    contentDescription = "..profile.png",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(50.dp)
                )
                Column(
                    modifier = Modifier.padding(all = 8.dp).fillMaxWidth().cursorForHorizontalResize(),

                    ) {
                    Text(
                        text = user.user,
                        style = MaterialTheme.typography.subtitle2,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Box(modifier = Modifier.padding(top = 3.dp).cursorForHorizontalResize()) {
                        Text(

                            text = user.userLastMessage,
                            style = MaterialTheme.typography.subtitle2,
                            color = Color(0xFFebe8e8),
                            fontSize = 12.sp
                        )
                    }

                }

            }
            Divider(color = Color.White, thickness = 1.dp, modifier = Modifier.padding(start = 8.dp, end = 8.dp).cursorForHorizontalResize())
        }


    }
}


fun loadSvgPainter(url: String, density: Density): Painter =
    URL(url).openStream().buffered().use { loadSvgPainter(it, density) }

@Composable
fun printUserList(listOfUsers: MutableList<UserQuickDetails>) {
    // val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0XFF2f3e45)).cursorForHorizontalResize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(listOfUsers) { user ->
            Row(
                modifier = Modifier.requiredSize(300.dp, 80.dp).cursorForHorizontalResize().clickable(onClick = {
                    clk.user = user

                }), verticalAlignment = Alignment.CenterVertically
            ) {
                cardForUser(user)

            }
        }
    }


}

class ClickUser(user: UserQuickDetails) {
    var user by mutableStateOf(user)
}

class SearchUser(user: String) {
    var searchUser by mutableStateOf(user)
}