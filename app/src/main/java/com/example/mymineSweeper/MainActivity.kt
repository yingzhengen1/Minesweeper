package com.example.mymineSweeper

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.SystemClock
import android.view.Window
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


interface OnCellClickListener {
    fun cellClick(cell: Cell?)
}

class MainActivity : AppCompatActivity(), OnCellClickListener {
    private var mineGridRecyclerAdapter: MineGridRecyclerAdapter? = null
    private var grid: RecyclerView? = null
    private var face: TextView? = null
    private var flag: TextView? = null
    private var flagsLeft: TextView? = null
    private var mineSweeperGame: MineSweeperGame? = null
    private var secondsElapsed = 0
    private var meter: Chronometer? = null
    private var clocking = false
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide();
        setContentView(R.layout.activity_main)

        // Create the grid
        grid = findViewById(R.id.grid_id)
        grid?.layoutManager = GridLayoutManager(this, 10)

        // Create the flag counter and chronometer
        flagsLeft = findViewById(R.id.flagsleft_id)
        meter = findViewById<Chronometer>(R.id.chronometer_id)

        mineSweeperGame = MineSweeperGame(GRID_SIZE, BOMB_COUNT)
        mineGridRecyclerAdapter = MineGridRecyclerAdapter(mineSweeperGame!!.mineGrid.getCells(), this)
        grid?.adapter = mineGridRecyclerAdapter

        // Create the emoji face
        face = findViewById(R.id.face_id)
        face?.setOnClickListener {
            face?.text = "\uD83D\uDE00"
            mineSweeperGame = MineSweeperGame(GRID_SIZE, BOMB_COUNT)
            mineGridRecyclerAdapter!!.setCells(mineSweeperGame!!.mineGrid.getCells())


            meter?.base = SystemClock.elapsedRealtime()
            meter?.start()



            secondsElapsed = 0
        }

        meter?.start()
        clocking = true
        flag = findViewById(R.id.flag_id)
        flag?.setOnClickListener {
            mineSweeperGame!!.toggleMode()
            if (mineSweeperGame!!.isFlagMode) {
                val border = GradientDrawable()
                border.setColor(-0x1)
                border.setStroke(1, -0x1000000)
                flag!!.background = border
            }
            else
            {
                val border = GradientDrawable()
                border.setColor(-0x1)
                flag!!.background = border
            }
        }
    }

    override fun cellClick(cell: Cell?)
    {
        if (cell != null) {
            if(!clocking && !mineSweeperGame!!.isGameOver)
            {
                meter?.start()
                clocking = true
            }
            mineSweeperGame!!.handleCellClick(cell)
        }

        flagsLeft!!.text = String.format("%03d", mineSweeperGame!!.numberBombs - mineSweeperGame!!.flagCount)

        if (mineSweeperGame!!.isGameOver)
        {
            if(clocking)
            {
                meter?.stop()
                clocking = false
            }
            face!!.text = "\uD83D\uDE35"
            Toast.makeText(applicationContext, "Game Over", Toast.LENGTH_SHORT).show()
            mineSweeperGame!!.mineGrid.revealAllBombs()
        }

        if (mineSweeperGame!!.isGameWon)
        {

            Toast.makeText(applicationContext, "Game Won", Toast.LENGTH_SHORT).show()
            mineSweeperGame!!.mineGrid.revealAllBombs()
        }
        mineGridRecyclerAdapter!!.setCells(mineSweeperGame!!.mineGrid.getCells())
    }

    companion object
    {
        const val BOMB_COUNT = 10
        const val GRID_SIZE = 10
    }
}