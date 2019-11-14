package toocowe.`fun`.instaclone

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.view.*

class RecAdapter(private val friends: ArrayList<String>, private val mRecListener: RecListener):
    RecyclerView.Adapter<RecAdapter.CustomViewHolder>() {

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.tvHeader.text = friends[position]

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.row, parent, false)
        return CustomViewHolder(cellForRow, mRecListener)
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    inner class CustomViewHolder constructor(var view: View, private val recListener: RecListener) :
        RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                recListener.onRecClick(adapterPosition)
                Log.i("clicks","short $adapterPosition")
            }
            view.setOnLongClickListener {
                recListener.onRecLongClick(adapterPosition)
                Log.i("clicks","short $adapterPosition")
                true
            }
        }
    }

    interface RecListener {
        fun onRecClick(position: Int) : Boolean
        fun onRecLongClick(position: Int)
    }
}


