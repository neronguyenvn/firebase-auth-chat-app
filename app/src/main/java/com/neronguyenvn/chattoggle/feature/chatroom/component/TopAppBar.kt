package com.neronguyenvn.chattoggle.feature.chatroom.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.neronguyenvn.chattoggle.R
import com.neronguyenvn.chattoggle.core.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChattieTopAppBar(
    currentUser: User,
    signOut: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    androidx.compose.material3.TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            Box {
                AsyncImage(
                    model = currentUser.photoUrl,
                    contentDescription = "avatar",
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .size(40.dp)
                        .clickable {
                            expanded = true
                        }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(currentUser.run { "Name: $name\nEmail: $email" }) },
                        onClick = { }
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text(text = "Sign Out") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "sign out"
                            )
                        },
                        onClick = signOut
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
    )
}
