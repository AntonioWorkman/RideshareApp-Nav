//package com.uwi.rideshareapp.ui.profile
//
//import android.app.Activity
//import android.content.Intent
//import android.graphics.Bitmap
//import android.os.Bundle
//import android.provider.MediaStore
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ImageView
//import androidx.fragment.app.Fragment
//import com.google.firebase.database.DatabaseReference
//import com.uwi.rideshareapp.R
//import com.uwi.rideshareapp.databinding.ActivityMainBinding
//import com.uwi.rideshareapp.glide.GlideApp
//import com.uwi.rideshareapp.util.FirestoreUtil
//import com.uwi.rideshareapp.util.StorageUtil
//import kotlinx.android.synthetic.main.fragment_profile.*
//import kotlinx.android.synthetic.main.fragment_update_profile.*
//import java.io.ByteArrayOutputStream
//
//class ProfileFragment : Fragment() {
//
//    private val RC_SELECT_IMAGE = 2
//    private lateinit var selectedImageBytes: ByteArray
//    private var pictureJustChanged = false
//
//    private lateinit var binding : ActivityMainBinding
//    private lateinit var database : DatabaseReference
//
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//
//        val v = inflater.inflate(R.layout.fragment_profile, container, false)
//
//        view.apply {
//            val profilePic = v.findViewById<View>(R.id.profile_image) as ImageView
//            profilePic.setOnClickListener {
//                val intent = Intent().apply {
//                    type = "image/*"
//                    action = Intent.ACTION_GET_CONTENT
//                    putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
//                }
//                startActivityForResult(
//                    Intent.createChooser(intent, "Select Image"),
//                    RC_SELECT_IMAGE
//                )
//            }
//
//
//
//            val saveBtn = v.findViewById<View>(R.id.btn_save) as Button
//            saveBtn.setOnClickListener {
//                if (::selectedImageBytes.isInitialized)
//                    StorageUtil.uploadProfilePhoto(selectedImageBytes) { imagePath ->
//                        FirestoreUtil.updateCurrentUser(
//                            editText_name.text.toString(),
//                            editText_bio.text.toString(),
//                            editText_email.text.toString(),
//                            editText_address.text.toString(),
//                            editText_phoneNumber.text.toString(),
//                            imagePath
//                        )
//                    }
//                else
//                    FirestoreUtil.updateCurrentUser(
//                        editText_name.text.toString(),
//                        editText_bio.text.toString(),
//                        editText_email.text.toString(),
//                        editText_address.text.toString(),
//                        editText_phoneNumber.text.toString(),
//                        null
//                    )
//            }
//        }
//        return v
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
//            data != null && data.data != null
//        ) {
//            val selectedImagePath = data.data
//            val selectedImageBmp = MediaStore.Images.Media
//                .getBitmap(activity?.contentResolver, selectedImagePath)
//
//            val outputStream = ByteArrayOutputStream()
//            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
//            selectedImageBytes = outputStream.toByteArray()
//
//            GlideApp.with(this)
//                .load(selectedImageBytes)
//                .into(profile_image)
//
//            pictureJustChanged = true
//        }
//    }
//
////    override fun onStart() {
////        super.onStart()
////        FirestoreUtil.getCurrentUser { user ->
////            if (this@ProfileFragment.isVisible) {
////                editText_name.setText(user.name)
////                editText_bio.setText(user.bio)
////                editText_address.setText(user.address)
////                editText_phoneNumber.setText(user.phoneNo)
////                if (!pictureJustChanged && user.profilePicturePath != null)
////                    GlideApp.with(this)
////                        .load(StorageUtil.pathToReference(user.profilePicturePath))
////                        .placeholder(R.drawable.user)
////                        .into(profile_image)
////            }
////        }
////    }
//
//}
//
//
