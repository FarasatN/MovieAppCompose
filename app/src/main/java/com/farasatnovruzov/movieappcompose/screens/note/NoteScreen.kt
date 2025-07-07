package com.farasatnovruzov.movieappcompose.screens.note

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.farasatnovruzov.movieappcompose.R
import com.farasatnovruzov.movieappcompose.components.NoteButton
import com.farasatnovruzov.movieappcompose.components.NoteIntputText
import com.farasatnovruzov.movieappcompose.data.NotesDataSource
import com.farasatnovruzov.movieappcompose.model.Note
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit = {},
    onRemoveNote: (Note) -> Unit = {}
){
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(4.dp)
    ) {
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.notes))
            },
            actions = {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "Icon"
                )
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFA7D7FF),
                titleContentColor = Color.Black.copy(alpha = 0.87f),
                navigationIconContentColor = Color.Black.copy(alpha = 0.87f),
                actionIconContentColor = Color.Black.copy(alpha = 0.87f)
            )
        )

        //Content
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteIntputText(
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                text = title,
                label = "Title",
                onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) title = it
                }
            )
            NoteIntputText(
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                text = description,
                label = "Add a note",
                onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) description = it
                }
            )
            // Enable button only when both fields have some text
            val enabled = title.isNotBlank() && description.isNotBlank()

            NoteButton(
                text = "Save",
                enabled = enabled,
                onClick = {
                    onAddNote(Note(title = title, description = description))
                    Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                }
            )
        }
        HorizontalDivider(modifier = Modifier.padding(2.dp))
        LazyColumn{
            items(notes) {note->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NoteRow(note = note, onNoteClicked = {
                        onRemoveNote(note)
                        Toast.makeText(context, "Note Removed", Toast.LENGTH_SHORT).show()
                    })
                }
            }
        }


    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit) {
    Surface(modifier
        .padding(2.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(topEnd = 12.dp, bottomStart = 12.dp)),
        color = Color(0xFFA7D7FF),
        shadowElevation = 4.dp,
        ){
        Column(
            modifier
                .clickable { onNoteClicked(note) }
                .padding(horizontal = 4.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.Start) {
            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
            Text(text = note.description, style = MaterialTheme.typography.titleSmall)
//            Text(text = note.entryDate.toString(), style = MaterialTheme.typography.bodySmall)
            Text(text = note.entryDate.format(DateTimeFormatter.ofPattern("EEE, d MMM")), style = MaterialTheme.typography.bodySmall)

        }

    }

}


@Preview(showBackground = true)
@Composable
fun NoteScreenPreview(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NoteScreen(notes = NotesDataSource().loadNotes(),onAddNote = {}, onRemoveNote = {})
    }
}