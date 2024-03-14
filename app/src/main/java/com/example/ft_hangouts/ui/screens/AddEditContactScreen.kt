package com.example.ft_hangouts.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ft_hangouts.viewmodel.ContactViewModel
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.example.ft_hangouts.data.entity.ContactEntity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditContactScreen(navController: NavController, viewModel: ContactViewModel, contactId: Int? = null) {
    Log.d("AddEditContactScreen", "Composable recomposed")

    // Use MutableState to hold contact data
    val contactState = viewModel.getContactById(contactId ?: -1).observeAsState()

    val context = LocalContext.current

    // Extract contact details from the contactState
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    // MutableState to hold the URI of the selected photo
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Function to handle the result of image selection
    val onImageSelected: (Uri) -> Unit = { uri ->
        imageUri = uri
    }

    // Initialize the state variables with the values from the contactState
    if (contactState.value != null) {
        firstname = contactState.value!!.firstname
        lastname = contactState.value!!.lastname
        phoneNumber = contactState.value!!.phone
        email = contactState.value!!.email ?: ""
        address = contactState.value!!.address ?: ""
        notes = contactState.value!!.notes ?: ""
        imageUri = contactState.value!!.imageUri?.toUri()
    }

    // Create activity result launcher for picking image from gallery
    val activity = LocalContext.current as Activity
    val activityResultLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            selectedImageUri?.let { onImageSelected(it) }
        }
    }

    // Function to open the image picker
    fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        activityResultLauncher.launch(intent)
    }

    // Function to create a temporary image file for the camera
    @Throws(IOException::class)
    fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    // Function to open the camera
    fun openCamera() {
        Log.d("Camera", "Opening camera") // Log the camera opening
        try {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoFile = createImageFile()
            val photoUri = FileProvider.getUriForFile(
                context,
                "${context.applicationContext.packageName}.provider",
                photoFile
            )
            Log.d("Camera", "Photo URI: $photoUri") // Log the photo URI
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            imageUri = photoUri // Update the Uri value
            activityResultLauncher.launch(cameraIntent)
        } catch (e: Exception) {
            Log.e("Camera", "Error opening camera", e)
            // Handle error
        }
    }

    // Function to handle the camera permission request result
    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, proceed with opening the camera
            openCamera()
        } else {
            // Permission denied
            Toast.makeText(
                context,
                "Camera permission is required to take photos.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Function to check and request camera permission
    fun checkAndRequestCameraPermission() {
        val cameraPermission = Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(
                context,
                cameraPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            requestCameraPermissionLauncher.launch(cameraPermission)
        } else {
            // Permission is already granted, proceed with opening the camera
            openCamera()
        }
    }

    // Function to show the dialog for choosing between camera and gallery
    fun showImageSourceDialog() {
        val items = arrayOf("Camera", "Gallery")
        AlertDialog.Builder(context)
            .setTitle("Select Image Source")
            .setItems(items) { dialog, which ->
                when (which) {
                    0 -> {
                        // Check and request camera permission before opening the camera
                        checkAndRequestCameraPermission()
                    }
                    1 -> openImagePicker()
                }
                dialog.dismiss()
            }
            .show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (contactId != null) "Edit Contact" else "Add Contact") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(36.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable { showImageSourceDialog() } // Make the box clickable to open the image picker
                ) {
                    // Display the selected image if available, else display the placeholder
                    val bitmap = imageUri?.let { uri ->
                        try {
                            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                                BitmapFactory.decodeStream(inputStream)
                            }
                        } catch (e: IOException) {
                            null
                        }
                    }
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Contact Profile Picture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = "Add Photo",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(44.dp),
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(26.dp))
                OutlinedTextField(
                    value = firstname,
                    onValueChange = { firstname = it },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = lastname,
                    onValueChange = { lastname = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        // Handle button click based on contactId
                        if (contactId != null) {
                            // Update existing contact
                            val updatedContact = ContactEntity(
                                id = contactId,
                                firstname = firstname,
                                lastname = lastname,
                                phone = phoneNumber,
                                email = email,
                                address = address,
                                notes = notes,
                                imageUri = imageUri.toString()
                            )
                            viewModel.updateContact(updatedContact)
                        } else {
                            // Save new contact
                            val newContact = ContactEntity(
                                firstname = firstname,
                                lastname = lastname,
                                phone = phoneNumber,
                                email = email,
                                address = address,
                                notes = notes,
                                imageUri = imageUri.toString()
                            )
                            viewModel.insertContact(newContact)
                        }
                        navController.popBackStack()
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = if (contactId != null) "Save" else "Add")
                }
                if (contactId != null) {
                    // If editing an existing contact, show delete button
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            // Delete existing contact
                            val deleteContact = ContactEntity(
                                id = contactId,
                                firstname = firstname,
                                lastname = lastname,
                                phone = phoneNumber,
                                email = email,
                                address = address,
                                notes = notes,
                                imageUri = imageUri.toString()
                            )
                            viewModel.deleteContact(deleteContact)
                            navController.popBackStack()
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    )
}




