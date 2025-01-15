
import hevs.graphics.FunGraphics


import java.awt.event.{KeyEvent, KeyListener, MouseAdapter, MouseEvent}
import scala.util.Random
import java.awt.{Color, Font, Rectangle}





object main extends App {
    var sizeCell = 40
    var rows = 16
    var cols = 16

    val width = 1400
    val height = 900

    var offsetX = width / 2 - (cols * sizeCell / 2)
    var offsetY = height / 2 - (rows * sizeCell / 2)

    private var nbrFlag = 0

    val graphics = new FunGraphics(width, height, "MineSweeper")

    //graphics.syncGameLogic(60)
    graphics.displayFPS(true)

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

      /*def getDisplayValue(): String = {
        if (isFlagged) {
          "F" // Flag
        } else if (isRevealed) {
          if (isMine) "X" else nbrMine.toString
        } else {
          " "
        }
      }
      */



    }


    var grid: Array[Array[Cell]] = Array.ofDim[Cell](rows, cols) // Array of cells

    for (row <- 0 until rows; col <- 0 until cols) { //initialization
      grid(row)(col) = new Cell()
    }

    var nbrMines = 0

    private def spawnMines(): Unit = {
      println("POSITIONS MINES----------------")
      for (mine <- 0 until nbrMines) {
        var posX = Random.nextInt(cols)
        var posY = Random.nextInt(rows)
        var cellOccupied = true

        while (cellOccupied) {
          if (grid(posX)(posY).isMine && grid(posX)(posY).nbrMine == 0 && !grid(posX)(posY).isRevealed) {
            cellOccupied = true
            posX = Random.nextInt(rows)
            posY = Random.nextInt(cols)
          } else if (!grid(posX)(posY).isMine && grid(posX)(posY).nbrMine == 0 && !grid(posX)(posY).isRevealed) {
            cellOccupied = false

          }
        }
        grid(posX)(posY).isMine = true


        println(s"$posX, $posY, ${grid(posX)(posY).isMine}")

      }
    }


    private def countMines(): Unit = {

      for (row <- 0 until rows; col <- 0 until cols) {
        var counter = 0
        if (!grid(row)(col).isMine) {
          for (i <- -1 to 1; j <- -1 to 1) {
            val newRow = row + i
            val newCol = col + j
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
              if (grid(newRow)(newCol).isMine) {
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

      graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, "/res/grid/background.png")
      graphics.drawTransformedPicture(width / 2 + width / 3 , height / 2, 0.0, 1, "/res/grid/controls.png")

      for (row <- 0 until rows; col <- 0 until cols) {
        val x = col * sizeCell + offsetX
        val y = row * sizeCell + offsetY


        //val displayValue = grid(row)(col).getDisplayValue()

        // draw every cell's borders


        // draw border of the cell and background when unrevealed

        if (!grid(row)(col).isRevealed) {
          if ((row + col) % 2 > 0) {
            graphics.setColor(new Color(93,163, 113))
            graphics.drawFillRect(x, y, sizeCell, sizeCell)
          } else {
            graphics.setColor(new Color(48,83, 58))
            graphics.drawFillRect(x, y, sizeCell, sizeCell)
          }
        }
        if (grid(row)(col).isRevealed) {
          if ((row + col) % 2 > 0) {
            graphics.setColor(new Color(220,220,220))
            graphics.drawFillRect(x, y, sizeCell, sizeCell)
          } else {
            graphics.setColor(new Color(200,200,200))
            graphics.drawFillRect(x, y, sizeCell, sizeCell)
          }
        }

        var xImage = x + sizeCell / 2
        var yImage = y + sizeCell / 2
        if (grid(row)(col).isRevealed && !grid(row)(col).isMine) {
          grid(row)(col).nbrMine match {
            case 1 => graphics.drawTransformedPicture(xImage, yImage, 0.0, sizeCell/50, images.GridImages(1))
            case 2 => graphics.drawTransformedPicture(xImage, yImage, 0.0, sizeCell/50, images.GridImages(2))
            case 3 => graphics.drawTransformedPicture(xImage, yImage, 0.0, sizeCell/50, images.GridImages(3))
            case 4 => graphics.drawTransformedPicture(xImage, yImage, 0.0, sizeCell/50, images.GridImages(4))
            case 5 => graphics.drawTransformedPicture(xImage, yImage, 0.0, sizeCell/50, images.GridImages(5))
            case 6 => graphics.drawTransformedPicture(xImage, yImage, 0.0, sizeCell/50, images.GridImages(6))
            case 7 => graphics.drawTransformedPicture(xImage, yImage, 0.0, sizeCell/50, images.GridImages(7))
            case 8 => graphics.drawTransformedPicture(xImage, yImage, 0.0, sizeCell/50, images.GridImages(8))
            case _ =>
          }
        } else if (grid(row)(col).isFlagged){
          graphics.drawTransformedPicture(xImage, yImage, 0.0, 1, images.GridImages(0))
        }


        val fontSection = new Font("Arial", Font.PLAIN, 32)

        graphics.drawTransformedPicture(width / 2 - width / 10, height / 2 - rows * sizeCell / 2 - sizeCell, 0.0, 1, images.GridImages(0))

        if (nbrFlag >= 0) {
          graphics.drawString(width / 2, height / 2 - rows * sizeCell / 2 - sizeCell,
            s"${nbrFlag.toString}", fontSection, Color.BLACK, 0, 0)
        } else if (nbrFlag < 0){
          graphics.drawString(width / 2, height / 2 - rows * sizeCell / 2 - sizeCell,
            s"${nbrFlag.toString}", fontSection, Color.RED, 0, 0)
        }
        //val rNbrFlag = new Rectangle(width / 2 - 100, height / 2 - rows * sizeCell / 2 - sizeCell - 30, 200, 60)
        //graphics.drawRect(rNbrFlag)
      }
    }




    def lost(): Unit = {
      val font = new Font("Arial", Font.PLAIN, 56)
      var counter = 0
      while (counter != 9){
        counter += 1
        graphics.drawString(width / 2, height / 2, "LOST", font, Color.RED, 0, 0)
        Thread.sleep(150)
        graphics.frontBuffer.synchronized{
          drawGrid()
        }
        Thread.sleep(150)
      }

    }

    def win(): Unit = {
      val font = new Font("Arial", Font.PLAIN, 56)
      var counter = 0
      while (counter != 9){
        counter += 1
        graphics.drawString(width / 2, height / 2, "WIN", font, Color.green, 0, 0)
        Thread.sleep(150)
        graphics.frontBuffer.synchronized{
          drawGrid()
        }
        Thread.sleep(150)
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

    private def clearFirstClickArea(row: Int, col: Int): Unit = {
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
        if (!isMenuOpen) {
          if (e.getButton == MouseEvent.BUTTON1) {
            if (row >= 0 && row < rows && col >= 0 && col < cols) {
              if (isFirstClick && !grid(row)(col).isFlagged) {
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
              if (!grid(row)(col).isRevealed && !grid(row)(col).isFlagged) {
                if (grid(row)(col).isMine) {
                  grid(row)(col).reveal()
                  println("BOOM! Mine cliquée! -> Lost")
                  lost()
                  isMenuOpen = true
                  graphics.frontBuffer.synchronized{
                   menu.drawMenu()
                  }
                  return

                } else {
                  revealAdjacent(row, col)
                }
                //drawGrid()
                graphics.frontBuffer.synchronized()

              }

            }


          } else if (e.getButton == MouseEvent.BUTTON3) {
            if (!isFirstClick && !grid(row)(col).isRevealed) {
              grid(row)(col).toggleFlag()
              if (grid(row)(col).isFlagged) {
                nbrFlag -= 1
              } else if (!grid(row)(col).isFlagged) {
                nbrFlag += 1
              }
            }
          }
          graphics.frontBuffer.synchronized{
            drawGrid() // new render
          }


          var counterWin: Int = 0
          for (i <- 0 until rows; j <- 0 until cols) {
            if (grid(i)(j).isRevealed && grid(i)(j).nbrMine >= 0) {
              counterWin += 1


            if (counterWin >= rows * cols - nbrMines) {
              win()
              isMenuOpen = true
              graphics.frontBuffer.synchronized {
                menu.drawMenu()
              }
              return
            }
          }
        }
      }}
    }
    )



    graphics.setKeyManager(new KeyListener {
      override def keyPressed(e: KeyEvent): Unit = {
        e.getKeyCode match {
          case KeyEvent.VK_M => // 'M' key to return to menu
            isMenuOpen = true
            graphics.clear()
            graphics.frontBuffer.synchronized{
              menu.drawMenu()
            }
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
      graphics.frontBuffer.synchronized{
        menu.drawMenu()
      }

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

    graphics.frontBuffer.synchronized{
      drawGrid()
    }


    for (row <- 0 until rows) {
      for (col <- 0 until cols) {
        val cell = grid(row)(col)
        print(s"${if (cell.isMine) "X" else cell.nbrMine} ")
      }
      println()
    }
  }

  while (true) {
    if (!isMenuOpen) {
      graphics.frontBuffer.synchronized{
        drawGrid()
      }
    }
  }
}




