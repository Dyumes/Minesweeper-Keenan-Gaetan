import hevs.graphics.FunGraphics

import java.awt.event.{MouseAdapter, MouseEvent}
import scala.util.Random
import java.awt.{Color, Font}



object Minesweeper extends App {
  val sizeCell = 50
  val rows = 10
  val cols = 10
  val width = cols * sizeCell
  val height = rows * sizeCell

  val graphics = new FunGraphics(width, height, "MineSweeper")

  class Cell(var isMine: Boolean = false, var isRevealed: Boolean = false, var nbrMine: Int = 0, var isFlagged: Boolean = false) {
    def reveal(): Unit = {
      isRevealed = true
    }


    def toggleFlag(): Unit = {
      if (!isRevealed) {
        isFlagged = !isFlagged
      }
    }

    def getDisplayValue(): String = {
      if (isFlagged) {
        "F" // Flag
      } else if (isRevealed) {
        if (isMine) "X" else nbrMine.toString
      } else {
        " "
      }
    }


  }


  val grid: Array[Array[Cell]] = Array.ofDim[Cell](rows, cols) // Array of cells

  for (row <- 0 until rows; col <- 0 until cols) { //initialization
    grid(row)(col) = new Cell()
  }

  def spawnMines(): Unit = {
    var nbrMines = 20
    println("POSITIONS MINES----------------")
    for (mine <- 0 until nbrMines){
      var posX = Random.nextInt(cols)
      var posY = Random.nextInt(rows)

      grid(posX)(posY).isMine = true


      println(s"$posX, $posY, ${grid(posX)(posY).isMine}")

    }
  }



  def countMines(): Unit ={

    for (row <- 0 until rows; col <- 0 until cols) {
      var counter = 0
      if (grid(row)(col).isMine == false) {
        for (i <- -1 to 1; j <- -1 to 1) {
          val newRow = row + i
          val newCol = col + j
          if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
            if (grid(newRow)(newCol).isMine == true) {
              counter += 1
            }
          }
        }
        grid(row)(col).nbrMine = counter
      }
    }

  }


  // Draw the grid
  def drawGrid(): Unit = {
    graphics.clear()
    val font = new Font("Arial", Font.PLAIN, 24)
    for (row <- 0 until rows; col <- 0 until cols) {
      val x = col * sizeCell
      val y = row * sizeCell


      val displayValue = grid(row)(col).getDisplayValue()

      // draw every cell's borders
      graphics.setColor(Color.BLACK)
      graphics.drawRect(x, y, sizeCell, sizeCell)

      // draw the value of cell
      graphics.setColor(Color.BLACK)
      graphics.drawString(x + sizeCell / 2, y + sizeCell / 2, displayValue, font, Color.BLACK, 0, 0)
    }
  }

  def revealAdjacent(row: Int, col: Int): Unit = {
    // verify if cell is in grid limits
    if (row < 0 || row >= rows || col < 0 || col >= cols || grid(row)(col).isRevealed) return

    // reveal cell
    grid(row)(col).reveal()

    // if no mines, explore further
    if (grid(row)(col).nbrMine == 0) {
      for (i <- -1 to 1; j <- -1 to 1) {
        if (i != 0 || j != 0) { // don't over reveal OG cell
          revealAdjacent(row + i, col + j)
        }
      }
    }
  }

  var isFirstClick: Boolean = true

  graphics.addMouseListener(new MouseAdapter() {
    override def mouseClicked(e: MouseEvent): Unit = {
      // coordinates of mouse
      val mouseX = e.getX
      val mouseY = e.getY

      // coordinates to grid index.
      val row = mouseY / sizeCell
      val col = mouseX / sizeCell

      // is click in grid limits ?
      if (e.getButton == MouseEvent.BUTTON1) {

        if (row >= 0 && row < rows && col >= 0 && col < cols) {
          if (isFirstClick == true) {
            grid(row)(col).nbrMine = 0
            isFirstClick = false
          }
          if (grid(row)(col).isRevealed == false) {
            if (grid(row)(col).isMine) {
              grid(row)(col).reveal()
              println("BOOM! Mine cliquée!")
            } else {
              revealAdjacent(row, col)
            }
          }
        }

        // Wining
        /*
        var counterWin: Int = 0
        for (i <- 0 to rows - 1; j <- 0 to cols - 1) {
          if (grid(i)(j).isRevealed && grid(i)(j).nbrMine > 0) {
            counterWin += 1
          }
          if (counterWin >= rows * cols - 20 - 1) {
            println("gagné")
          }
        }
        */



      }else if (e.getButton == MouseEvent.BUTTON3) {
        grid(row)(col).toggleFlag()

      }

      drawGrid() // new render

    }
  }


  )

  spawnMines()

  countMines()

  drawGrid()

  println()

  for (row <- 0 until rows) {
    for (col <- 0 until cols) {
      val cell = grid(row)(col)
      print(s"${if (cell.isMine) "X" else cell.nbrMine} ")
    }
    println()
  }
}




