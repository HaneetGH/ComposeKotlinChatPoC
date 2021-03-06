package org.jetbrains.compose.splitpane.demo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


lateinit var notesList: SnapshotStateList<MessageModel>

@Composable
fun cardShapeFor(message: MessageModel): Shape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return when {
        message.isMine -> roundedCorners.copy(bottomEnd = CornerSize(0))
        else -> roundedCorners.copy(bottomStart = CornerSize(0))
    }
}

@Composable
fun printUserChat(user: UserQuickDetails, modifier: Modifier) {
    // val context = LocalContext.current
    notesList = remember {
        mutableStateListOf()
    }
    notesList.clear()
    notesList.addAll(user.listOfChats)

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(notesList) { chat ->
            MessageCard(chat, "", user.user)
        }
    }
}

@Composable
fun MessageCard(messageItem: MessageModel, myName: String, frndName: String) { // 1
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = when { // 2
            messageItem.isMine -> Alignment.End
            else -> Alignment.Start
        },
    ) {
        Card(
            modifier = Modifier.widthIn(max = 340.dp),
            shape = cardShapeFor(messageItem), // 3
            backgroundColor = when {
                messageItem.isMine -> Color(0XFF128c7e)
                else -> Color(0XFF2f3e45)
            },
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = messageItem.msg,
                color = when {
                    messageItem.isMine -> Color.White
                    else -> Color.White
                },
            )
        }
        var name = if (messageItem.isMine) myName else frndName
        Text(
            // 4

            text = name,
            fontSize = 12.sp,
        )
    }
}
