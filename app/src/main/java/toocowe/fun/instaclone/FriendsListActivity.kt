package toocowe.`fun`.instaclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_friends_list.*

class FriendsListActivity : AppCompatActivity(), RecAdapter.RecListener {
    override fun onRecClick(position: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRecLongClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var recAdapter: RecAdapter? = null
    private var friendsList: ArrayList<String> = ArrayList()

    private fun initRecycler() {
        // link Adapter to list
        recAdapter = RecAdapter(friendsList, this)

        // Set up Recycler manager to link to adapter
        val manager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = manager
        recyclerView.adapter = recAdapter
    }

    fun updateList() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)
        updateList()
        initRecycler()
    }
}
