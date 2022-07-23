package com.ziyad.zradio.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ziyad.zradio.R
import com.ziyad.core.domain.model.Radio
import com.ziyad.core.ui.ListRadioAdapter
import com.ziyad.zradio.databinding.ActivityMainBinding
import com.ziyad.zradio.play.PlayActivity
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvListRadio.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            val listRadioAdapter =
                ListRadioAdapter { radio, isFavorited ->
                    if (isFavorited) {
                        model.setFavoriteRadio(radio, false)
                    } else {
                        model.setFavoriteRadio(radio, true)
                    }
                }
            runBlocking {
                model.getAllIndonesianRadio().observe(this@MainActivity) {
                    listRadioAdapter.setData(it)
                }
            }
            adapter = listRadioAdapter
            listRadioAdapter.setOnItemClickCallback(object :
                ListRadioAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Radio) {
                    val moveWithObjectIntent =
                        Intent(this@MainActivity, PlayActivity::class.java)
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
                            Intent(this@MainActivity, PlayActivity::class.java)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                /*startActivity(Intent(this, com.ziyad.favorite.favorite.FavoriteActivity::class.java))*/
                val uri = Uri.parse("zradio://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            else -> true
        }
    }
}