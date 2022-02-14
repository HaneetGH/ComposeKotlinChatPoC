package org.jetbrains.compose.splitpane.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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