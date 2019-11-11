package toocowe.`fun`.instaclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_friends_list.*

class FriendsListActivity : AppCompatActivity(), RecAdapter.RecListener {
    override fun onRecClick(position: Int): Boolean {
        Log.i("main clicks","short $position")
        Toast.makeText(this,"Click position $position",Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onRecLongClick(position: Int) {
        Log.i("main clicks","long $position")
        Toast.makeText(this,"Long Click position $position",Toast.LENGTH_SHORT).show()
    }

    private var recAdapter: RecAdapter? = null
    private var friendsList: ArrayList<String> = ArrayList()

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
    }
}
