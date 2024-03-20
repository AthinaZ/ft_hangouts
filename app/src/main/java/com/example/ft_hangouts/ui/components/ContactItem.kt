package com.example.ft_hangouts.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.R
import com.example.ft_hangouts.data.entity.ContactEntity
import java.io.InputStream

// Function to load image from URI
fun loadImageFromUri(context: Context, uri: Uri): Bitmap? {
    var image: Bitmap? = null
    try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        if (inputStream != null) {
            image = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return image
}


@Composable
fun ContactItem(
    context: Context,
    contact: ContactEntity,
    onItemClick: () -> Unit,
    onMessageClick: () -> Unit,
    onCallClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = onItemClick)
    ) {
        // Display contact image or placeholder
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        ) {
            if (contact.imageUri != null) {
                // Load image if available
                val bitmap = loadImageFromUri(context, Uri.parse(contact.imageUri))
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Contact Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Show placeholder if image loading fails
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_person_24),
                        contentDescription = "Contact Placeholder",
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            } else {
                // Show placeholder if image is not available
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_person_24),
                    contentDescription = "Contact Placeholder",
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))

        // Display contact name
        Text(
            text = "${contact.firstname} ${contact.lastname}",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.weight(1f)
        )

        // Call button
        IconButton(onClick = { onCallClick() }) {
            Icon(Icons.Default.Call, contentDescription = "Call")
        }

        // Message button
        IconButton(onClick = { onMessageClick() }) {
            Icon(Icons.Default.Message, contentDescription = "Message")
        }
    }
}




