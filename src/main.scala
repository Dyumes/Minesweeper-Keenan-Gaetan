import hevs.graphics.utils.GraphicsBitmap
import hevs.graphics.{FunGraphics, ImageGraphics}
import menu.drawMenu

import java.awt.event.{KeyEvent, KeyListener, MouseAdapter, MouseEvent}
import scala.util.Random
import java.awt.{Color, Font, Rectangle}
import javax.swing.ImageIcon
import java.awt.Image



object main extends App {
    var sizeCell = 40
    var rows = 16
    var cols = 16

    val width = 1400
    val height = 900

    var offsetX = width / 2 - (cols * sizeCell / 2)
    var offsetY = height / 2 - (rows * sizeCell / 2)

    var nbrFlag = 0

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

    var nbrMines = 0

    def spawnMines(): Unit = {
      println("POSITIONS MINES----------------")
      for (mine <- 0 until nbrMines) {
        var posX = Random.nextInt(cols)
        var posY = Random.nextInt(rows)
        var cellOccupied = true

        while (cellOccupied == true) {
          if (grid(posX)(posY).isMine == true && grid(posX)(posY).nbrMine == 0 && grid(posX)(posY).isRevealed == false) {
            cellOccupied = true
            posX = Random.nextInt(rows)
            posY = Random.nextInt(cols)
          } else if (grid(posX)(posY).isMine == false && grid(posX)(posY).nbrMine == 0 && grid(posX)(posY).isRevealed == false) {
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


        //val displayValue = grid(row)(col).getDisplayValue()

        // draw every cell's borders
        graphics.setColor(Color.BLACK)
        graphics.drawRect(x, y, sizeCell, sizeCell)


        //graphics.drawTransformedPicture(x + sizeCell/2, y + sizeCell/2, 0.0, 1, "/res/redcrosstest.png") // for easy mode


        // draw the value of cell
        //graphics.setColor(Color.BLACK)
        //graphics.drawString(x + sizeCell / 2, y + sizeCell / 2, displayValue, font, Color.BLACK, 0, 0)

        if (grid(row)(col).isRevealed == true && grid(row)(col).isMine == false) {
          grid(row)(col).nbrMine match {
            case 0 => graphics.drawTransformedPicture(x + sizeCell / 2, y + sizeCell / 2, 0.0, 1, "/res/grid/0test.png") // for easy mode
            case 1 => graphics.drawTransformedPicture(x + sizeCell / 2, y + sizeCell / 2, 0.0, 1, "/res/grid/1test.png") // for easy mode
            case 2 => graphics.drawTransformedPicture(x + sizeCell / 2, y + sizeCell / 2, 0.0, 1, "/res/grid/2test.png") // for easy mode
            case 3 => graphics.drawTransformedPicture(x + sizeCell / 2, y + sizeCell / 2, 0.0, 1, "/res/grid/3test.png") // for easy mode
            case 4 => graphics.drawTransformedPicture(x + sizeCell / 2, y + sizeCell / 2, 0.0, 1, "/res/grid/4test.png") // for easy mode
            case 5 => graphics.drawTransformedPicture(x + sizeCell / 2, y + sizeCell / 2, 0.0, 1, "/res/grid/5test.png") // for easy mode
            case 6 => graphics.drawTransformedPicture(x + sizeCell / 2, y + sizeCell / 2, 0.0, 1, "/res/grid/6test.png") // for easy mode
            case _ => println("out of images")
          }
        } else if (grid(row)(col).isFlagged == true){
          graphics.drawTransformedPicture(x + sizeCell / 2, y + sizeCell / 2, 0.0, 1, "/res/grid/testflag.png")
        }


        val fontSection = new Font("Arial", Font.PLAIN, 32)

        graphics.drawString(width / 8, height / 3, "Menu", fontSection, Color.BLACK, 0, 0)
        var rHome = new Rectangle(105, 275, 140, 60)
        graphics.drawRect(rHome)

        if (nbrFlag >= 0) {
          graphics.drawString(width / 2, height / 2 - rows * sizeCell / 2 - sizeCell,
            s"Flags: ${nbrFlag.toString}", fontSection, Color.BLACK, 0, 0)
        } else if (nbrFlag < 0){
          graphics.drawString(width / 2, height / 2 - rows * sizeCell / 2 - sizeCell,
            s"Flags: ${nbrFlag.toString}", fontSection, Color.RED, 0, 0)
        }
        var rNbrFlag = new Rectangle(width / 2 - 100, height / 2 - rows * sizeCell / 2 - sizeCell - 30, 200, 60)
        graphics.drawRect(rNbrFlag)
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

    def clearFirstClickArea(row: Int, col: Int): Unit = {
      var compteur = 0
      for (i <- -1 to 1; j <- -1 to 1) { // Check neighboring cells
        val newRow = row + i
        val newCol = col + j
        if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid(newRow)(newCol).isMine) {
          grid(newRow)(newCol).isMine = false // Delete near mines
          println(s"mine supprimée: $row;$col")
          compteur += 1
        }
      }
      println(compteur)
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
              if (isFirstClick == true && grid(row)(col).isFlagged == false) {
                isFirstClick = false
                spawnMines()
                clearFirstClickArea(row, col)
                countMines()
                for (row <- 0 until rows) {
                  for (col <- 0 until cols) {
                    val cell = grid(row)(col)

                    print(s"${if (cell.isMine) "X" else cell.nbrMine} ")
                  }
                  println()
                }
              }
              if (grid(row)(col).isRevealed == false && grid(row)(col).isFlagged == false) {
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
            if (isFirstClick == false && grid(row)(col).isRevealed == false) {
              grid(row)(col).toggleFlag()
              if (grid(row)(col).isFlagged == true) {
                nbrFlag -= 1
              } else if (grid(row)(col).isFlagged == false) {
                nbrFlag += 1
              }
            }
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
          case KeyEvent.VK_M => // 'M' key to return to menu
            isMenuOpen = true
            menu.drawMenu()
          case KeyEvent.VK_R => // 'R' key to restart a game in the same difficulty as selected
            startNewGame(rows, cols, sizeCell, nbrMines)
          case KeyEvent.VK_ESCAPE => // exit the game
            System.exit(0)
          case _ => println(s"Key pressed: ${e.getKeyChar}")
        }
      }

      override def keyReleased(e: KeyEvent): Unit = {

      }

      override def keyTyped(e: KeyEvent): Unit = {

      }
    }
    )

      isMenuOpen = true
      menu.drawMenu()

      println()



  def startNewGame(newRows: Int, newCols: Int, newSizeCell: Int, newNbrMines: Int): Unit = {
    grid = Array.ofDim[Cell](newRows, newCols) // Array of cells

    for (row <- 0 until rows; col <- 0 until cols) { //initialization
      grid(row)(col) = new Cell()
    }


    sizeCell = newSizeCell
    nbrMines = newNbrMines

    nbrFlag = nbrMines

    println(nbrMines)

    isFirstClick = true

    offsetX = width / 2 - (cols * sizeCell / 2)
    offsetY = height / 2 - (rows * sizeCell / 2)

    //spawnMines()
    //countMines()

    drawGrid()


    for (row <- 0 until rows) {
      for (col <- 0 until cols) {
        val cell = grid(row)(col)
        print(s"${if (cell.isMine) "X" else cell.nbrMine} ")
      }
      println()
    }
  }
}




