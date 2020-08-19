package net.csolorzano.tiktactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    var currentPlayer = 1
    var cellsPlayer1 = mutableListOf<String>()
    var cellsPlayer2 = mutableListOf<String>()
    var dirtyButtons = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun btnClicked(view: View) {
        val btnSelected = view as Button
        if(btnSelected.id == View.NO_ID)
            return

        val cell = view.resources.getResourceName(btnSelected.id).takeLast(2)
        MarkButton(btnSelected, cell, view)
    }

    fun MarkButton(button: Button, cell: String, view: View){
        var mark = if (currentPlayer==1) "X" else "O"

        if(button.text.isEmpty()){
            button.text = mark

            if(currentPlayer==1)
            {
                currentPlayer = 2
                cellsPlayer1.add(cell)
            }else{
                currentPlayer = 1
                cellsPlayer2.add(cell)
            }

            dirtyButtons.add(button)

            if(checkWin(cellsPlayer1)){
                val builder = view.let { AlertDialog.Builder(this) }
                builder.setMessage("Player 1 Won the Game")
                builder.create().show()
                clearCells(view)
            }

            if(checkWin(cellsPlayer2)){
                Toast.makeText(this,"Player 2 Won the Game", Toast.LENGTH_LONG).show()
                Thread.sleep(2_000)
                clearCells(view)
            }


        }



    }

    private fun clearCells(view: View) {
        cellsPlayer1.clear()
        cellsPlayer2.clear()
        for (button in dirtyButtons) button.text = ""
        dirtyButtons.clear()
    }

    private fun checkWin(cells: MutableList<String>) : Boolean{
        return checkDiagonals(cells) || checkColumns(cells) || checkRows(cells)
    }

    private fun checkDiagonals(cells: MutableList<String>): Boolean {
        return cells.containsAll(listOf("11","22","33"))||
                cells.containsAll(listOf("31","22","13"))
    }

    private fun checkColumns(cells: MutableList<String>): Boolean {
        return cells.containsAll(listOf("11","21","31"))||
                cells.containsAll(listOf("12","22","32"))||
                cells.containsAll(listOf("13","23","33"))
    }

    private fun checkRows(cells: MutableList<String>): Boolean {
        return cells.containsAll(listOf("11","12","13"))||
                cells.containsAll(listOf("21","22","23"))||
                cells.containsAll(listOf("31","32","33"))
    }


}