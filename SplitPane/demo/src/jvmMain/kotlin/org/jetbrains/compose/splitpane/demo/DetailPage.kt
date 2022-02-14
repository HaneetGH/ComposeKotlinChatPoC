package org.jetbrains.compose.splitpane.demo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun secondView(user: UserQuickDetails) {
    val splitterState = rememberSplitPaneState()
    val hSplitterState = rememberSplitPaneState()
    VerticalSplitPane(splitPaneState = hSplitterState) {
        first(50.dp) {
            Box(Modifier.background(Color.Black).fillMaxSize()) {
                messagesListTopBar(user)
            }
        }
        second(20.dp) {
            printUserChat(user)
        }
    }

}





@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun messagesListTopBar(user: UserQuickDetails) {
    val imageModifier = Modifier
        .height(240.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
    TopAppBar(
        title = {
            Text(
                text = user.user,
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
        },
        actions = {
            Image(
                painter = painterResource("profile.png"),
                contentDescription = "..profile.png",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(30.dp)
            )
            Image(
                painter = painterResource("menu.png"),
                contentDescription = "image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(30.dp).padding(all = 8.dp).fillMaxWidth(),
            )
        },
        backgroundColor = Color.White
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

        modifier = Modifier.clip(CircleShape)
            .size(30.dp)
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
        text = sender,
        style = TextStyle(
            color = Color.Black,
            fontSize = 18.sp
        ),
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}
