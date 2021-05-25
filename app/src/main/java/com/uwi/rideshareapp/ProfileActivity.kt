package com.uwi.rideshareapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.uwi.rideshareapp.databinding.ActivityProfileBinding
import com.uwi.rideshareapp.model.User


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var database: DatabaseReference

    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setTitle("Profile")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        val phoneNumber = findViewById<TextView>(R.id.userPhoneNo)
        phoneNumber.text = mAuth?.currentUser?.phoneNumber.toString()

        //Picasso.get().load(User.profileImageUrl).into(R.id.profile_image)

        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(mAuth?.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                val name = it.child("name").value
                val bio = it.child("bio").value
                val address = it.child("address").value
                val email = it.child("email").value


                binding.userName.text = name.toString()
                binding.userBio.text = bio.toString()
                binding.userAddress.text = address.toString()
                binding.userEmail.text = email.toString()
            }
            else {
                Toast.makeText(this, "Please update profile", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }

        binding.updateProfile.setOnClickListener {
            val intent = Intent(this, UpdateProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}