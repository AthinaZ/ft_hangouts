package com.example.ft_hangouts.utils

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

const val SEND_SMS_PERMISSION = Manifest.permission.SEND_SMS
const val RECEIVE_SMS_PERMISSION = Manifest.permission.RECEIVE_SMS
const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
const val CALL_PHONE_PERMISSION = Manifest.permission.CALL_PHONE
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
const val READ_MEDIA_IMAGES_PERMISSION = Manifest.permission.READ_MEDIA_IMAGES
const val CAMERA_PERMISSION = Manifest.permission.CAMERA

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun permissions(): List<String> = listOf(
    SEND_SMS_PERMISSION,
    RECEIVE_SMS_PERMISSION,
    READ_CONTACTS,
    CALL_PHONE_PERMISSION,
    READ_MEDIA_IMAGES_PERMISSION,
    CAMERA_PERMISSION,
)

