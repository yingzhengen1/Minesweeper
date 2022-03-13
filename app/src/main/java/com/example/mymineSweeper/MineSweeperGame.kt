package com.example.mymineSweeper

class MineSweeperGame(size: Int, val numberBombs: Int) {
    val mineGrid: MineGrid = MineGrid(size)
    var isGameOver = false
        private set
    var isFlagMode = false
        private set
    var isClearMode = true
        private set
    var flagCount = 0
        private set

    fun handleCellClick(cell: Cell) {
        if (!isGameOver && !isGameWon  && !cell.isRevealed) {
            if (isClearMode) {
                clear(cell)
            }
            else if (isFlagMode) {
                flag(cell)
            }
        }
    }

    fun clear(cell: Cell) {
        val index = mineGrid.getCells().indexOf(cell)
        mineGrid.getCells()[index].isRevealed = true
        if (cell.value == Cell.BOMB) {
            isGameOver = true
        }
        else if (cell.value == Cell.BLANK) {
            val toClear: MutableList<Cell> = ArrayList()
            val toCheckAdjacent: MutableList<Cell> = ArrayList()
            toCheckAdjacent.add(cell)
            while (toCheckAdjacent.size > 0) {
                val c = toCheckAdjacent[0]
                val cellIndex = mineGrid.getCells().indexOf(c)
                val cellPos = mineGrid.toXY(cellIndex)
                for (adjacent in mineGrid.adjacentCells(cellPos[0], cellPos[1])) {
                    if (adjacent.value == Cell.BLANK) {
                        if (!toClear.contains(adjacent)) {
                            if (!toCheckAdjacent.contains(adjacent)) {
                                toCheckAdjacent.add(adjacent)
                            }
                        }
                    }
                    else {
                        if (!toClear.contains(adjacent)) {
                            toClear.add(adjacent)
                        }
                    }
                }
                toCheckAdjacent.remove(c)
                toClear.add(c)
            }
            for (c in toClear) {
                c.isRevealed = true
            }
        }
    }

    fun flag(cell: Cell) {
        cell.isFlagged = !cell.isFlagged
        var count = 0
        for (c in mineGrid.getCells()) {
            if (c.isFlagged) {
                count++
            }
        }
        flagCount = count
    }

    val isGameWon: Boolean
        get() {
            var numbersUnrevealed = 0
            for (c in mineGrid.getCells()) {
                if (c.value != Cell.BOMB && c.value != Cell.BLANK && !c.isRevealed) {
                    numbersUnrevealed++
                }
            }
            return numbersUnrevealed == 0
        }

    fun toggleMode() {
        isClearMode = !isClearMode
        isFlagMode = !isFlagMode
    }

    init {
        mineGrid.generateGrid(numberBombs)
    }
}