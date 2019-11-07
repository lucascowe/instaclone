package toocowe.`fun`.instaclone

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import keepitsimple.store.qwirklescorer.DatabaseNames.*

class RecAdapter internal constructor(
    private var list: ArrayList<String>? = ArrayList(),
    private val recListener: RecListener
) : RecyclerView.Adapter<RecAdapter.ViewHolder>() {

    inner class ViewHolder internal constructor(itemView: View, internal val mRecListener: RecListener)
        : RecyclerView.ViewHolder(itemView) {//, View.OnClickListener, View.OnLongClickListener {

//        itemView.setOnClickListener(this)
//        itemView.setOnLongClickListener(this)
        itemView

        fun onClick(view: View) {
            val position = adapterPosition
            recListener.onRecClick(position)
        }

        fun onLongClick(view: View): Boolean {
            val position = adapterPosition
            recListener.onRecLongClick(position)
            return true
        }
    }

    interface RecListener {
        fun onRecClick(position: Int): Boolean
        fun onRecLongClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view, recListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // check table row exists
        if (list!!.count() > position) {
            return
        }
        tvHeader.text = list?.get(position)
        holder.header.text = text
        t(list!!.getColumnIndex(PlayersTable.COLUMN_SCORE))
        holder.data4.text = text
        if (1 == list!!.getInt(list!!.getColumnIndex(PlayersTable.COLUMN_SELECTED))) {
            holder.linearLayout.setBackgroundColor(-0x55de690d)
        } else {
            holder.linearLayout.setBackgroundColor(0x66111111)
        }
    }

      override fun getItemCount(): Int {
        try {
            return list!!.count
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }

    internal fun updateCursor(newCursor: Cursor?) {
        if (list != null) {
            list!!.close()
        }
        list = newCursor
        if (newCursor != null) {
            notifyDataSetChanged()
        }
    }

    internal fun getPlayerNumber(p: Int): Int {
        if (list!!.moveToPosition(p))
        Log.i("Error", "Position " + p + " out of range of " + list!!.count)
        return 0
    }

    internal fun getPlayerScore(p: Int): Int {
        if (list!!.moveToPosition(p)) {
            return list!!.getInt(list!!.getColumnIndex(PlayersTable.COLUMN_SCORE))
        }
        Log.i("Error", "Position " + p + " out of range of " + list!!.count)
        return 0
    }

    internal fun getPlayerName(p: Int): String {
        if (list!!.moveToPosition(p)) {
            return list!!.getString(list!!.getColumnIndex(PlayersTable.COLUMN_NAME))
        }
        Log.i("Error", "Position " + p + " out of range of " + list!!.count)
        return ""
    }

    internal fun getPlayer(p: Int): Player? {
        return if (list!!.moveToPosition(p)) {
            Player(
                list!!.getInt(list!!.getColumnIndex(PlayersTable.COLUMN_NUMBER)),
                list!!.getString(list!!.getColumnIndex(PlayersTable.COLUMN_NAME)),
                list!!.getString(list!!.getColumnIndex(PlayersTable.COLUMN_TURN)),
                list!!.getInt(list!!.getColumnIndex(PlayersTable.COLUMN_SCORE)),
                list!!.getInt(list!!.getColumnIndex(PlayersTable.COLUMN_TURNS)),
                list!!.getInt(list!!.getColumnIndex(PlayersTable.COLUMN_LOCATION)),
                list!!.getInt(list!!.getColumnIndex(PlayersTable.COLUMN_SELECTED)) == 1
            )
        } else null
    }
}
