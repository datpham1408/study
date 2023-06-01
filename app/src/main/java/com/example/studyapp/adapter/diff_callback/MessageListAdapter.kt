package com.example.studyapp.adapter.diff_callback


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.studyapp.Model.MessageModel
import com.example.studyapp.R
import com.example.studyapp.databinding.ItemMessageBinding


class MessageListAdapter(val callback: (Int) -> Unit) :
    ListAdapter<MessageModel, MessageListAdapter.MessageViewHolder>(
        DiffCallbackMessage()
    ) {
    inner class MessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateBackground() {
            if (adapterPosition % 2 == 0) {
                binding.root.setBackgroundResource(R.color.white)
            } else {
                binding.root.setBackgroundResource(R.color.grey)

            }
        }

        fun bindData(messageModel: MessageModel) {
            binding.ivAvatar.setImageResource(messageModel.avatar)
            binding.tvTitle.text = messageModel.userName
            binding.tvContent.text = messageModel.nickName
            binding.tvHours.text = messageModel.hour

            val url =
                "https://media.istockphoto.com/id/517188688/photo/mountain-landscape.jpg?s=612x612&w=0&k=20&c=A63koPKaCyIwQWOTFBRWXj_PwCrR4cEoOw2S9Q7yVl8="
            val imageView: ImageView = binding.ivAvatar

            val radius = 360
            val options = RequestOptions().transform(RoundedCorners(radius))
            Glide.with(binding.root.context)
                .load(url)
                .apply(options)
                .into(imageView)



            binding.root.setOnClickListener {
                callback(adapterPosition)
            }
            updateBackground()


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        var binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bindData(message)

    }
}



