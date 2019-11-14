package toocowe.`fun`.instaclone

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.pic_row.view.*

class PictureFeedAdapter (private val pics: ArrayList<Bitmap>, private val mPicListener: PicListener):
    RecyclerView.Adapter<PictureFeedAdapter.PicCustomViewHolder>() {

    override fun onBindViewHolder(holder: PicCustomViewHolder, position: Int) {
        Log.i("PicAdapter","adding pic $position of ${pics.size}")
//        holder.view.feedImageView.setImageBitmap(pics[position])
        holder.view.feedImageView.setImageResource(R.drawable.instaclone)
        holder.view.tvUser.text = "User"
        holder.view.tvComment.text = "Comments"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicCustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val imageRow = layoutInflater.inflate(R.layout.pic_row,parent,false)
        Log.i("PicAdapter","Creating")
        return PicCustomViewHolder(imageRow,mPicListener)
    }

    override fun getItemCount(): Int {
        return 3
//        return pics.size
    }


    inner class PicCustomViewHolder constructor(val view: View, private val picListener: PicListener):
        RecyclerView.ViewHolder(view) {
            init {
                view.setOnClickListener {
                    picListener.onPicClick(adapterPosition)
                    Log.i("clicks","short $adapterPosition")
                }
                view.setOnLongClickListener {
                    picListener.onPicLongClick(adapterPosition)
                    Log.i("clicks","short $adapterPosition")
                    true
                }
            }
    }

    interface PicListener {
        fun onPicClick(position: Int) : Boolean
        fun onPicLongClick(position: Int)
    }

}