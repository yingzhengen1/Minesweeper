package com.example.mymineSweeper

class Cell(val value: Int)
{
    var isRevealed = false
    var isFlagged = false

    companion object
    {
        const val BOMB = -1
        const val BLANK = 0
    }
}