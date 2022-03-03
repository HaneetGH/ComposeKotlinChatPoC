package org.jetbrains.compose.splitpane.demo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import org.jetbrains.skiko.hostId

var userLocal: UserQuickDetails? = null;

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun secondViewAlpha(user: UserQuickDetails) {
    userLocal = user;

    secondView(user)
}
@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun secondView(user: UserQuickDetails) {

    val splitterState = rememberSplitPaneState()
    val hSplitterState = rememberSplitPaneState()
    VerticalSplitPane(splitPaneState = hSplitterState) {
        first(50.dp) {
            Box {
                messagesListTopBar(user)
            }
        }
        second(20.dp) {
            Column(Modifier.background(Color(0XFFece5dd)).fillMaxSize()) {
                printUserChat(user, modifier = Modifier.weight(1f))
                MessageInput()
            }
        }
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun messagesListTopBar(user: UserQuickDetails) {
    val imageModifier = Modifier.height(250.dp).fillMaxWidth().clip(RoundedCornerShape(12.dp))
    TopAppBar(backgroundColor = Color(0XFF455A64), title = {
        Row(
            ) {
            Image(
                painter = painterResource("profile.png"),
                contentDescription = "..profile.png",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(30.dp)
            )
        Column(
            modifier = Modifier.padding(start = 8.dp)

            ) {
            Text(
                text = user.user, style = MaterialTheme.typography.subtitle2, color = Color.White, fontSize = 16.sp
            )
            Text(
                text = user.lastMstTime, style = MaterialTheme.typography.subtitle2, color = Color(0xFFebe8e8), fontSize = 12.sp
            )
        }}
    }, actions = {

        Image(
            painter = painterResource("phone-call.png"),
            contentDescription = "image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(30.dp).padding(end = 8.dp).fillMaxWidth(),
        )
        Image (painter = painterResource("zoom.png"),
        contentDescription = "image",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.width(50.dp).padding(start = 8.dp, end = 8.dp).fillMaxWidth(),
        )
    }
    )
}


@Composable
fun floatingButton(fabClick: () -> Unit) {
    FloatingActionButton(onClick = {
        fabClick()
    }) {
        Image(
            painter = painterResource("plus.png"),
            contentDescription = "..profile.png",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(30.dp)
        )
    }
}

@Composable
fun SenderIcon() {
    Box(

        modifier = Modifier.clip(CircleShape).size(30.dp)
    ) {
        Image(
            painter = painterResource("profile.png"),
            contentDescription = "..profile.png",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(30.dp)
        )
    }
}

@Composable
fun Sender(sender: String, modifier: Modifier = Modifier) {
    Text(
        text = sender, style = TextStyle(
            color = Color.Black, fontSize = 18.sp
        ), modifier = modifier, overflow = TextOverflow.Ellipsis, maxLines = 1
    )
}

@Composable
fun MessageInput(
) {
    var inputValue by remember { mutableStateOf("") } // 2

    fun sendMessage() {
        notesList.add(MessageModel(inputValue.toString(), true))
        inputValue = ""
    }

    Row {
        TextField(
            // 4
            modifier = Modifier.weight(1f),
            value = inputValue,
            onValueChange = { inputValue = it.toString() },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions { sendMessage() },
        )
        Button(
            // 5
            modifier = Modifier.height(56.dp),
            onClick = { sendMessage() },
            enabled = inputValue.isNotBlank(),
        ) {
            Icon( // 6
                imageVector = Icons.Default.Send, contentDescription = "Send"
            )
        }
    }
}

class SearchChat(user: String) {
    var searchChat by mutableStateOf(user)
}

