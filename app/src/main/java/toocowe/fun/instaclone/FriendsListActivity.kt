package toocowe.`fun`.instaclone

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.parse.*
import kotlinx.android.synthetic.main.activity_friends_list.*
import java.io.ByteArrayOutputStream
import java.lang.Exception

class FriendsListActivity : AppCompatActivity(), RecAdapter.RecListener {
    private var recAdapter: RecAdapter? = null
    private var friendsList: ArrayList<String> = ArrayList()

    override fun onRecClick(position: Int): Boolean {
        intent = Intent(applicationContext, UserFeedActivity::class.java)
        intent.putExtra("username", friendsList[position])
        Log.i("USER NAME TO PASS", friendsList[position])
        Log.i("USER NAME TO PASS",intent.getStringExtra("username"))
        startActivity(intent)
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (1 == requestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }

    private fun getPhoto() {
        intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = data?.data

        if (1 == requestCode && Activity.RESULT_OK == resultCode && data != null) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                val file = ParseFile("image.png", byteArray)
                val obj = ParseObject("Image")
                obj.put("image", file)
                obj.put("username", ParseUser.getCurrentUser().username)

                obj.saveInBackground { e ->
                    when (e) {
                        null -> Log.i("Uploaded","Photo uploaded")
                        else -> Log.i("Uploaded","Failed")
                    }

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                } else {
                    getPhoto()
                }
            }
            R.id.logout -> {
                ParseUser.logOut()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.feed -> {
                intent = Intent(applicationContext, UserFeedActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRecLongClick(position: Int) {
        Log.i("main clicks","long $position")
        Toast.makeText(this,"Long Click position $position",Toast.LENGTH_SHORT).show()
    }

    private fun initRecycler() {
        // link Adapter to list
        recyclerView.layoutManager = LinearLayoutManager(this)
        recAdapter = RecAdapter(friendsList, this)
        recyclerView.adapter = recAdapter
    }

    fun updateList() {
        val query = ParseUser.getQuery()
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().username)
        query.addAscendingOrder("username")
        Log.i("updateList()", "query setup")

        query.findInBackground { objects, e ->
            if (e == null) {
                Log.i("updateList()", "no errors")
                if (objects.size > 0) {
                    for (user in objects) {
                        Log.i("updateList()", "passing user ${user.username}")
                        friendsList.add(user.username)
                    }
                    for (friend in friendsList) {
                        Log.i("friendsList", friend)
                    }
                    if (recAdapter != null) {
                        recAdapter?.notifyDataSetChanged()
                    } else println("recAdapter is NULL")
                }
            } else {
                Log.i("updateList()", "error")
                e.printStackTrace()
            }
        }
        Log.i("updateList()", "finishing")
        for (friend in friendsList) {
            Log.i("friendsList", friend)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)
        initRecycler()
        updateList()

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }
}
