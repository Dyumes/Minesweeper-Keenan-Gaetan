import hevs.graphics.FunGraphics

import java.awt.event.{KeyEvent, KeyListener, MouseAdapter, MouseEvent}
import scala.util.Random
import java.awt.{Color, Font}

import menu.drawMenu
import menu.drawLevelMenu





object main extends App {
  //def Game(): Unit = {
    var sizeCell = 40
    var rows = 16
    var cols = 16

    val width = 1400
    val height = 900

    var offsetX = width / 2 - (cols * sizeCell / 2)
    var offsetY = height / 2 - (rows * sizeCell / 2)

    val graphics = new FunGraphics(width, height, "MineSweeper")

    var isMenuOpen = false

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


    var grid: Array[Array[Cell]] = Array.ofDim[Cell](rows, cols) // Array of cells

    for (row <- 0 until rows; col <- 0 until cols) { //initialization
      grid(row)(col) = new Cell()
    }

    var nbrMines = 50

    def spawnMines(): Unit = {
      println("POSITIONS MINES----------------")
      for (mine <- 0 until nbrMines) {
        var posX = Random.nextInt(cols)
        var posY = Random.nextInt(rows)
        var cellOccupied = true

        while (cellOccupied == true) {
          if (grid(posX)(posY).isMine == true) {
            cellOccupied = true
            posX = Random.nextInt(cols)
            posY = Random.nextInt(rows)
          } else if (grid(posX)(posY).isMine == false) {
            cellOccupied = false

          }
        }

        grid(posX)(posY).isMine = true


        println(s"$posX, $posY, ${grid(posX)(posY).isMine}")

      }
    }


    def countMines(): Unit = {

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
        val x = col * sizeCell + offsetX
        val y = row * sizeCell + offsetY


        val displayValue = grid(row)(col).getDisplayValue()

        // draw every cell's borders
        graphics.setColor(Color.BLACK)
        graphics.drawRect(x, y, sizeCell, sizeCell)

        // draw the value of cell
        graphics.setColor(Color.BLACK)
        graphics.drawString(x + sizeCell / 2, y + sizeCell / 2, displayValue, font, Color.BLACK, 0, 0)
      }
    }


    def lost(): Unit = {
      graphics.clear()
      val font = new Font("Arial", Font.PLAIN, 56)
      graphics.drawString(width / 2, height / 2, "BIG CHEH", font, Color.BLACK, 0, 0)
    }

    def win(): Unit = {
      graphics.clear()
      val font = new Font("Arial", Font.PLAIN, 56)
      graphics.drawString(width / 2, height / 2, "BRAVO", font, Color.BLACK, 0, 0)
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
        val mouseX = e.getX - offsetX
        val mouseY = e.getY - offsetY

        // coordinates to grid index.
        val row = mouseY / sizeCell
        val col = mouseX / sizeCell

        // clicks IN GAME
        if (isMenuOpen == false) {
          if (e.getButton == MouseEvent.BUTTON1) {

            if (row >= 0 && row < rows && col >= 0 && col < cols) {
              if (isFirstClick == true) {
                grid(row)(col).isMine = false
                isFirstClick = false
                spawnMines()
                grid(row)(col).nbrMine = 0 // A REVOIR
                countMines()
                for (row <- 0 until rows) {
                  for (col <- 0 until cols) {
                    val cell = grid(row)(col)
                    print(s"${if (cell.isMine) "X" else cell.nbrMine} ")
                  }
                  println()
                }
              }
              if (grid(row)(col).isRevealed == false) {
                if (grid(row)(col).isMine) {
                  grid(row)(col).reveal()
                  println("BOOM! Mine cliquée! -> Lost")
                  lost()
                  Thread.sleep(3000)
                  isMenuOpen = true
                  menu.drawMenu()
                  return

                } else {
                  revealAdjacent(row, col)
                }
              }
            }


          } else if (e.getButton == MouseEvent.BUTTON3) {
            grid(row)(col).toggleFlag()

          }

          drawGrid() // new render

          var counterWin: Int = 0
          for (i <- 0 to rows - 1; j <- 0 to cols - 1) {
            if (grid(i)(j).isRevealed && grid(i)(j).nbrMine >= 0) {
              counterWin += 1
            }
            if (counterWin >= rows * cols - nbrMines) {
              Thread.sleep(1000)
              win()
              Thread.sleep(3000)
              isMenuOpen = true
              menu.drawMenu()
              return
            }
          }
        }
      }
    }
    )



    graphics.setKeyManager(new KeyListener {
      override def keyPressed(e: KeyEvent): Unit = {
        e.getKeyCode match {
          case KeyEvent.VK_M =>
            isMenuOpen = true
            menu.drawMenu() // Press 'M' key to return to menu

          case KeyEvent.VK_R =>
          case _ => println(s"Key pressed: ${e.getKeyChar}")
        }
      }

      override def keyReleased(e: KeyEvent): Unit = {

      }

      override def keyTyped(e: KeyEvent): Unit = {

      }
    }
    )

      drawGrid()
      //spawnMines()
      //countMines()
      drawGrid()

      println()



/*
    for (row <- 0 until rows) {
      for (col <- 0 until cols) {
        val cell = grid(row)(col)
        print(s"${if (cell.isMine) "X" else cell.nbrMine} ")
      }
      println()
    }
*/
  def startNewGame(newRows: Int, newCols: Int, newSizeCell: Int, newNbrMines: Int): Unit = {
    grid = Array.ofDim[Cell](newRows, newCols) // Array of cells

    for (row <- 0 until rows; col <- 0 until cols) { //initialization
      grid(row)(col) = new Cell()
    }

    sizeCell = newSizeCell
    nbrMines = newNbrMines

    isFirstClick = true

    offsetX = width / 2 - (cols * sizeCell / 2)
    offsetY = height / 2 - (rows * sizeCell / 2)

    spawnMines()
    countMines()


    drawGrid()

    for (row <- 0 until rows) {
      for (col <- 0 until cols) {
        val cell = grid(row)(col)
        print(s"${if (cell.isMine) "X" else cell.nbrMine} ")
      }
      println()
    }
  }


  //}
}




