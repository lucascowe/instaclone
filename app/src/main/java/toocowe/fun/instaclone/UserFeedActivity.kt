package toocowe.`fun`.instaclone

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.createBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.parse.*
import kotlinx.android.synthetic.main.activity_friends_list.*
import kotlinx.android.synthetic.main.activity_user_feed.*

class UserFeedActivity : AppCompatActivity(), PictureFeedAdapter.PicListener {

    private val pictures: ArrayList<Bitmap> = ArrayList()
    private var recAdapter: PictureFeedAdapter? = null

    private fun initRecycler() {
        feedRecyclerView.layoutManager = LinearLayoutManager(this)
        recAdapter = PictureFeedAdapter(pictures, this)
        feedRecyclerView.adapter = recAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_feed)

        var user = intent.getStringExtra("username")

        if (user == null) {
            user = "Main feed"
        }
        title = user

        downloadPictures()
        initRecycler()



        recAdapter?.notifyDataSetChanged()


//
//        val imageView = ImageView(this)
//        imageView.layoutParams = ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_launcher_foreground))
//        linearLayout.addView(imageView)
    }

    override fun onPicClick(position: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPicLongClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun downloadPictures() {
        val query = ParseQuery<ParseObject>("Image")
        if (null != intent.getStringExtra("username")) {
            query.whereEqualTo("username", title)
        }
        query.orderByDescending("createdAt")
        query.findInBackground(FindCallback { objects, e ->
            if (null == e && objects.size > 0) {
                var cnt = 0
                for (obj in objects) {
                    cnt++
                    Log.i("LOAD IMAGES","Image $cnt")
                    val file: ParseFile = obj.get("image") as ParseFile
                    file.getDataInBackground(GetDataCallback() {
                            data: ByteArray?, e: ParseException? ->
                        if (null == e && null != data) {
                            pictures.add(BitmapFactory.decodeByteArray(data, 0, data.size))
                            Log.i("Adding picture","${pictures.size}")
                        } else {
                            Log.i("Adding picture","failed $data")
                        }
                    })
                }
                Log.i("Pictures", "size is ${pictures.size}")
            } else {
                Log.i("Feed Pics","failed in background")
                Log.i("Pictures", "size is ${pictures.size}")
            }
            recAdapter?.notifyDataSetChanged()
        })
    }
}
