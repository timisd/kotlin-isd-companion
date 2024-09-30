package org.hshl.isd.companion.features.professors

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import org.hshl.isd.companion.features.shared.getImageFromBytes
import org.hshl.isd.companion.features.shared.openEmail
import org.hshl.isd.companion.features.shared.openPhone

class ProfessorsScreen : Screen {
    @Composable
    override fun Content() {
        var searchQuery by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        val profs = getProfessors()

        Column(
            modifier = Modifier.fillMaxSize().clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusManager.clearFocus() }
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
                placeholder = { Text("Suchen...") },
                singleLine = true
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 56.dp)
            ) {
                items(profs.filter { it.matchesSearchQuery(searchQuery) }) { professor ->
                    ProfessorCard(professor = professor)
                }
            }
        }
    }
}

@Composable
fun ProfessorCard(professor: ProfessorModel) {
    val nameTextSize: TextUnit = 18.sp
    val profilePictureSize: Dp = 80.dp

    Card(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.inversePrimary
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f).padding(16.dp)) {
                Row(modifier = Modifier.padding(bottom = 16.dp)) {
                    if (professor.title.isNotEmpty()) {
                        Text(
                            text = professor.title + "  ",
                            fontWeight = FontWeight.Bold,
                            fontSize = nameTextSize
                        )
                    }
                    Text(
                        text = professor.firstName + "  ",
                        fontSize = nameTextSize
                    )
                    Text(
                        text = professor.lastName,
                        fontWeight = FontWeight.Bold,
                        fontSize = nameTextSize
                    )
                }
                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                    val mailString = buildAnnotatedString {
                        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                            append(professor.email)
                        }
                    }
                    Text(
                        text = "E-Mail:  ",
                        fontWeight = FontWeight.Bold
                    )
                    ClickableText(
                        text = mailString,
                        onClick = {
                            openEmail(professor.email, this)
                        }
                    )
                }
                Row {
                    val phoneString = buildAnnotatedString {
                        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                            append(professor.phoneNumber)
                        }
                    }
                    Text(
                        text = "Tel.:  ",
                        fontWeight = FontWeight.Bold
                    )
                    ClickableText(
                        text = phoneString,
                        onClick = {
                            openPhone(professor.phoneNumber, this)
                        }
                    )
                }
            }
            Box(
                modifier = Modifier.size(profilePictureSize),
                contentAlignment = Alignment.Center
            ) {
                ProfilePicture(professor, Modifier.size(profilePictureSize))
            }
        }
    }
}

@Composable
fun ProfilePicture(prof: ProfessorModel, modifier: Modifier, iconSize: Dp = 25.dp) {
    val photo = getImageFromBytes(prof.imageBytes)
    val photoModifier = modifier.clip(RoundedCornerShape(35))
    if (photo != null) {
        Image(
            bitmap = photo,
            contentDescription = "${prof.firstName} ${prof.lastName}",
            modifier = photoModifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = photoModifier.background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Person,
                contentDescription = "Person Icon",
                modifier = Modifier.size(iconSize),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}