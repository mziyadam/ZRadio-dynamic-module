package com.ziyad.favorite.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ziyad.core.domain.model.Radio
import com.ziyad.core.ui.ListRadioAdapter
import com.ziyad.favorite.databinding.ActivityFavoriteBinding
import com.ziyad.favorite.di.favoriteModule
import com.ziyad.zradio.play.PlayActivity
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val model: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(favoriteModule)
        binding.rvListFavoriteRadio.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            val listRadioAdapter =
                ListRadioAdapter { radio, isFavorited ->
                    if (isFavorited) {
                        model.setFavoriteRadio(radio, false)
                    } else {
                        model.setFavoriteRadio(radio, true)
                    }
                }
            runBlocking {
                model.getFavoriteRadio().observe(this@FavoriteActivity) {
                    if(it.isEmpty()){
                        binding.tvFavoriteListEmpty.visibility=View.VISIBLE
                    }else{
                        binding.tvFavoriteListEmpty.visibility=View.GONE
                    }
                    listRadioAdapter.setData(it)
                }
            }
            adapter = listRadioAdapter
            listRadioAdapter.setOnItemClickCallback(object :
                ListRadioAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Radio) {
                    val moveWithObjectIntent =
                        Intent(this@FavoriteActivity, PlayActivity::class.java)
                    moveWithObjectIntent.putExtra(PlayActivity.SELECTED_RADIO, data)
                    startActivity(moveWithObjectIntent)
                }
            })
        }
        model.currentRadio.observe(this) { radioPlayer ->
            if (radioPlayer != null) {
                binding.apply {
                    cvPlayer.visibility = View.VISIBLE
                    tvName.text = radioPlayer.getName()
                    playerControl.player = radioPlayer.player
                    cvPlayer.setOnClickListener {
                        val moveWithObjectIntent =
                            Intent(this@FavoriteActivity, PlayActivity::class.java)
                        moveWithObjectIntent.putExtra(
                            PlayActivity.SELECTED_RADIO,
                            radioPlayer.getRadio
                        )
                        startActivity(moveWithObjectIntent)
                    }
                }
            } else {
                binding.cvPlayer.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        model.getCurrentRadio()
    }
}