package com.rahmanarif.footballclub.view.activity.detail_player

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.api.ApiRepository
import com.rahmanarif.footballclub.model.Player
import com.rahmanarif.footballclub.presenter.DetailPlayerPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailPlayerActivity : AppCompatActivity(), DetailViewPlayer {
    private var idPlayer: String = ""
    private lateinit var namePlayer: String
    private lateinit var presenter: DetailPlayerPresenter
    private lateinit var player: Player

    override fun getDetailPlayer(data: Player) {
        player = Player(data.playerId, data.strPlayer, data.strPosition, data.strCutout,data.strDescription,
                data.strThumb, data.strHeight, data.strWeight, data.strFanart1)
        Log.d("idPlayer", data.playerId)
        namePlayer = data.strPlayer.toString()
        when(data.playerId){
            idPlayer -> {
                Picasso.get().load(data.strThumb).into(image_player)
                weight_player.text = data.strWeight
                height_player.text = data.strHeight
                position_player.text = data.strPosition
                description_player.text = data.strDescription
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)

        supportActionBar?.title = "Player Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        idPlayer = intent.getStringExtra("idPlayer")
        Log.d("idPlayer", idPlayer)

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPlayerPresenter(this, request, gson)

        presenter.getDetailPlayer(idPlayer)
    }
}
