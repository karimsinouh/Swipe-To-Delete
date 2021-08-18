package com.karimsinouh.swipetodelete

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.karimsinouh.swipetodelete.ui.theme.SwipeToDeleteTheme

class MainActivity : ComponentActivity() {



    val items= mutableStateListOf<String>(
        "Item Number 1",
        "Item Number 2",
        "Item Number 3",
        "Item Number 4",
        "Item Number 5",
        "Item Number 6",
        "Item Number 7",
        "Item Number 8",
    )


    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipeToDeleteTheme {
                window.statusBarColor=MaterialTheme.colors.primaryVariant.toArgb()
                Scaffold(topBar = {MainTopBar()}) {


                    LazyColumn{

                        itemsIndexed(
                            items=items,
                            key={index,item->
                                item.hashCode()
                            }
                        ){index,item->

                            val state= rememberDismissState(
                                confirmStateChange = {
                                    if (it==DismissValue.DismissedToStart){
                                        items.remove(item)
                                    }
                                    true
                                }
                            )


                            SwipeToDismiss(
                                state = state,
                                background = {
                                    val color=when(state.dismissDirection){
                                        DismissDirection.StartToEnd -> Color.Transparent
                                        DismissDirection.EndToStart -> Color.Red
                                        null -> Color.Transparent
                                    }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color)
                                            .padding(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = null,
                                            tint=Color.White,
                                            modifier = Modifier.align(Alignment.CenterEnd)
                                        )
                                    }

                                },
                                dismissContent = {
                                    MyCustomItem(text = item)
                                },
                                directions=setOf(DismissDirection.EndToStart)
                            )
                            Divider()

                        }

                    }


                }
            }
        }
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MyCustomItem(text:String) {
        ListItem (
            text={ Text(text = text)},
            overlineText = { Text(text = "OverLine") },
            icon = { Icon(imageVector = Icons.Outlined.Share, contentDescription = null) },
            trailing = { Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = null
            ) },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
        )
    }


    @Composable
    private fun MainTopBar(){
        TopAppBar(title={ Text(text = "Swipe To Delete")})
    }

}
