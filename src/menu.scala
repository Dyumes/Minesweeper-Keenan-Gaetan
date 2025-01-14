import main.{cols, drawGrid, graphics, grid, height, isFirstClick, isMenuOpen, lost, nbrMines, offsetX, offsetY, revealAdjacent, rows, sizeCell, startNewGame, width, win}

import java.awt.event.{MouseAdapter, MouseEvent}
import java.awt.{Color, Font, Rectangle}
import java.nio.file.{Files, Paths}

object menu extends App {

  def drawLevelMenu() ={

    val fontSection = new Font("Arial", Font.PLAIN, 32)

    graphics.clear()

    graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, "/res/grid/background.png")
    graphics.drawTransformedPicture(width/2, height / 8, 0.0, 1, "/res/menus/Title.png")
    graphics.drawTransformedPicture(width / 2, height / 3, 0.0, 1, "/res/menus/easy.png")
    graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, "/res/menus/medium.png")
    graphics.drawTransformedPicture(width / 2, height / 6 + height / 2, 0.0, 1, "/res/menus/hard.png")
    graphics.drawString(width / 8, height / 3, "Menu", fontSection, Color.BLACK, 0, 0)

    var playMenu = true
    graphics.addMouseListener(new MouseAdapter() {
      override def mouseClicked(e: MouseEvent): Unit = {

        // coordinates of mouse
        val mouseX = e.getX
        val mouseY = e.getY

        // coordinates to grid index.

        // clicks IN MENU
        if (e.getButton == MouseEvent.BUTTON1) {
          if (playMenu == true) {
            if (mouseX >= 105 && mouseX <= 245 && mouseY >= 275 && mouseY <= 335){
              println("return to menu")
              isMenuOpen = true
              playMenu = false
              graphics.frontBuffer.synchronized{
                drawMenu()
              }
            }
            if (mouseX >= 560 && mouseX <= 840) {
              if (mouseY >= 275 && mouseY <= 335) {
                println("easy")
                isMenuOpen = false
                playMenu = false
                rows = 9
                cols = 9
                sizeCell = 50
                nbrMines = 13
                startNewGame(rows, cols, sizeCell, nbrMines)
              }
              if (mouseY >= 425 && mouseY <= 485) {
                println("medium")
                isMenuOpen = false
                playMenu = false
                rows = 12
                cols = 12
                sizeCell = 40
                nbrMines = 23
                startNewGame(rows, cols, sizeCell, nbrMines)
              }
              if (mouseY >= 575 && mouseY <= 635) {
                println("hard")
                isMenuOpen = false
                playMenu = false
                rows = 16
                cols = 16
                sizeCell = 40
                nbrMines = 40
                startNewGame(rows, cols, sizeCell, nbrMines)
              }
            }
          }
        }
      }
    }
    )

  }
  def drawMenu(): Unit = {
    if (isMenuOpen == true) {
      graphics.clear()

      graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, "/res/grid/background.png")
      graphics.drawTransformedPicture(width/2, height / 8, 0.0, 1, "/res/menus/Title.png")
      graphics.drawTransformedPicture(width / 2, height / 3, 0.0, 1, "/res/menus/play.png")
      graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, "/res/menus/settings.png")
      graphics.drawTransformedPicture(width / 2, height / 6 + height / 2, 0.0, 1, "/res/menus/exit.png")

      var playMenu = false

      graphics.addMouseListener(new MouseAdapter() {
        override def mouseClicked(e: MouseEvent): Unit = {

          // coordinates of mouse
          val mouseX = e.getX
          val mouseY = e.getY

          // coordinates to grid index.


          // clicks IN MENU
          if (e.getButton == MouseEvent.BUTTON1) {
            println(s"$mouseX:$mouseY")
            if (playMenu == false) {
              if (mouseX >= 560 && mouseX <= 840) {
                if (mouseY >= 275 && mouseY <= 335) {
                  println("play")
                  playMenu = true
                  graphics.frontBuffer.synchronized{
                    drawLevelMenu()
                  }
                }

                if (mouseY >= 425 && mouseY <= 485) {
                  println("settings")
                }
                if (mouseY >= 575 && mouseY <= 635) {
                  println("exit")
                  System.exit(0)
                }

              }
            }

          } else if (e.getButton == MouseEvent.BUTTON3) {

          }
        }
      }
    )

  }

  }
}
