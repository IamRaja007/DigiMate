package com.example.studentscompanion.adapters

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.example.studentscompanion.R
import com.example.studentscompanion.response.QueriesItems
import com.example.studentscompanion.response.ReplyItems
import com.example.studentscompanion.util.ProgressDialogBox
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_query.view.*

class QueryAdapter(
    val context: Context,
    private val data: List<QueriesItems>,
    val click: ItemSeeRepliesClick
) : RecyclerView.Adapter<QueryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryAdapter.ViewHolder {
        val itemview = LayoutInflater.from(context).inflate(
            R.layout.item_row_query,
            parent,
            false
        )

        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: QueryAdapter.ViewHolder, position: Int) {
        val myQueries = data[position]

        holder.date.text = myQueries.date
        holder.name.text = myQueries.name
        val textTime = "at ${myQueries.time}"
        holder.time.text = textTime

        println("user = ${myQueries.user}")
        val linked =
            "https://appbackend-100.herokuapp.com/api/users/view-profilepic/${myQueries.user}"
        Picasso.get()
            .load(linked)
            .placeholder(R.drawable.default_avatar)
            .error(R.drawable.default_avatar)
            .into(holder.profileImage)

        if (myQueries.pdfdocument == null && myQueries.photo != null) {
            println("id =${myQueries.id}")
            val link =
                "https://appbackend-100.herokuapp.com/api/queries/view-query-photo/${myQueries.id}"
            Picasso.get()
                .load(link)
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .into(holder.image)
        } else {
            holder.image.visibility = View.GONE
            holder.LLpdf.visibility = View.VISIBLE
            setMargins(holder.btnSeeReplies, 0, 120, 0, 0)
        }

        if (myQueries.text != null && myQueries.photo == null && myQueries.pdfdocument == null) {
            holder.image.visibility = View.GONE
            holder.LLpdf.visibility = View.GONE
            setMargins(holder.btnSeeReplies, 0, 10, 0, 0)
        } else if (myQueries.text != null && myQueries.photo == null && myQueries.pdfdocument != null) {
            holder.image.visibility = View.GONE
            holder.LLpdf.visibility = View.VISIBLE
            setMargins(holder.btnSeeReplies, 0, 120, 0, 0)
            holder.LLpdf.setOnClickListener {
                val link =
                    "https://appbackend-100.herokuapp.com/api/queries/view-query-pdf/${myQueries.id}"
                download(link, ".pdf")
            }
        } else if (myQueries.text == null && myQueries.photo == null && myQueries.pdfdocument != null) {
            holder.image.visibility = View.GONE
            holder.LLpdf.visibility = View.VISIBLE
            setMargins(holder.btnSeeReplies, 0, 120, 0, 20)
            setMargins(holder.LLpdf,0,30,0,0)
            holder.LLpdf.setOnClickListener {
                val link =
                    "https://appbackend-100.herokuapp.com/api/queries/view-query-pdf/${myQueries.id}"
                download(link, ".pdf")
            }
        } else {
            holder.image.visibility = View.VISIBLE
            holder.image.setOnClickListener {
                ProgressDialogBox.dialogWithPosNegBtn(
                    context,
                    "Do you want to download this image?",
                    true
                )
                    .setPositiveButton("Yes") { _, _ ->
                        val link =
                            "https://appbackend-100.herokuapp.com/api/queries/view-query-photo/${myQueries.id}"
                        download(link, ".jpeg")
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }

                    .create()
                    .show()
            }

            holder.LLpdf.visibility = View.GONE
        }
        if (myQueries.text == null) {
            holder.textQuery.visibility = View.GONE
        } else {
            holder.textQuery.visibility = View.VISIBLE
            holder.textQuery.text = myQueries.text
        }

    }


    override fun getItemCount(): Int {

        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name = itemView.tvQueryProfileName
        var date = itemView.tvQueryDate
        var time = itemView.tvQueryTime
        var image = itemView.ivQuery
        var textQuery = itemView.tvQueryText
        var profileImage = itemView.civQueryProfileImage
        var LLpdf = itemView.LLPdfDoc
        var btnSeeReplies = itemView.btnSeeReplies

        init {
            btnSeeReplies.setOnClickListener {
//              click.onSeeRepliesClick(data[adapterPosition].reply as List<ReplyItems>?)
                click.onSeeRepliesClick(
                    data[adapterPosition].reply as List<ReplyItems>?,
                    data[adapterPosition].id!!
                )
            }
        }


    }

    interface ItemSeeRepliesClick {
        fun onSeeRepliesClick(repliesList: List<ReplyItems>?, pathId: String)
    }

    private fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is MarginLayoutParams) {
            val p = view.layoutParams as MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }

    fun download(url: String, extension: String) {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("Download")
        request.setDescription("Downloading Your File")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            System.currentTimeMillis().toString() + extension
        )
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }

}
