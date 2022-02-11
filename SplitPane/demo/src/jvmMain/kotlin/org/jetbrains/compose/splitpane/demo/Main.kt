package org.jetbrains.compose.splitpane.demo

import androidx.compose.desktop.DesktopTheme
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import java.awt.Cursor
import java.awt.Image


@OptIn(ExperimentalComposeUiApi::class)
private fun Modifier.cursorForHorizontalResize(): Modifier =
    pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))
val users: List<String> = listOf("Haneet", "Milan", "Vikas", "Deepak")
val samepleChat: List<String> = listOf("How Are You", "Good", "And", "You?")
@OptIn(ExperimentalSplitPaneApi::class)
fun main() = singleWindowApplication(
    title = "SplitPane demo"
) {
    MaterialTheme {
        val splitterState = rememberSplitPaneState()
        val hSplitterState = rememberSplitPaneState()
        HorizontalSplitPane(
            splitPaneState = splitterState
        ) {
            first(200.dp) {
                printUserList()
            }
            second(50.dp) {
                VerticalSplitPane(splitPaneState = hSplitterState) {
                    first(50.dp) {
                        Box(Modifier.background(Color.Blue).fillMaxSize())
                    }
                    second(20.dp) {
                        printUserChat()
                    }
                }
            }
            splitter {
                visiblePart {
                    Box(
                        Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                            .background(MaterialTheme.colors.background)
                    )
                }
                handle {
                    Box(
                        Modifier
                            .markAsHandle()
                            .cursorForHorizontalResize()
                            .background(SolidColor(Color.Gray), alpha = 0.50f)
                            .width(9.dp)
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}
@Composable
fun printUserList(){
   // val context = LocalContext.current
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(users) { user ->
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable(onClick = {  })
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
    }}
@Composable
fun printUserChat(){
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
                    .clickable(onClick = {  })
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
    }}