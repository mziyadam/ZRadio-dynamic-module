package com.ziyad.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ziyad.core.R
import com.ziyad.core.databinding.ItemRadioBinding
import com.ziyad.core.domain.model.Radio
import com.ziyad.core.utils.Utils.loadImage

class ListRadioAdapter(
    private val onFavoriteClicked: (Radio, Boolean) -> Unit
) :
    RecyclerView.Adapter<ListRadioAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var listData = ArrayList<Radio>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<Radio>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemRadioBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRadioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val radio = listData[position]
        var isFavorited = false
        holder.apply {
            binding.apply {
                if (radio.favicon != "")
                    ivAvatar.loadImage(radio.favicon)
                tvName.text = radio.name
                tvState.text = radio.state
                if (radio.isFavorite) {
                    isFavorited = true
                    this.ivFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            this.ivFavorite.context,
                            R.drawable.ic_baseline_favorited_24
                        )
                    )
                } else {
                    isFavorited = false
                    this.ivFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            this.ivFavorite.context,
                            R.drawable.ic_baseline_favorite_24
                        )
                    )
                }
                ivFavorite.setOnClickListener {
                    onFavoriteClicked(radio, isFavorited)
                }
            }
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(radio)
            }
        }

    }

    override fun getItemCount(): Int = listData.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Radio)
    }

}