import main.{cols, drawGrid, graphics, grid, height, isFirstClick, isMenuOpen, lost, nbrMines, offsetX, offsetY, revealAdjacent, rows, sizeCell, startNewGame, width, win}

import java.awt.event.{MouseAdapter, MouseEvent}
import java.awt.{Color, Font, Rectangle}

object menu extends App {
  def drawLevelMenu() ={
    val fontTitle = new Font("Arial", Font.PLAIN, 48)
    val fontSection = new Font("Arial", Font.PLAIN, 32)
    graphics.clear()
    graphics.setColor(Color.BLACK)
    graphics.drawString(width / 2, height / 8, "Minesweeper", fontTitle, Color.BLACK, 0, 0)
    graphics.drawString(width / 2, height / 3, "EASY", fontSection, Color.BLACK, 0, 0)
    graphics.drawString(width / 2, height / 2, "MEDIUM", fontSection, Color.BLACK, 0, 0)
    graphics.drawString(width / 2, height / 6 + height / 2, "HARD", fontSection, Color.BLACK, 0, 0)
    graphics.drawString(width / 8, height / 3, "Menu", fontSection, Color.BLACK, 0, 0)
    var rEasy = new Rectangle(560, 275, 280, 60)
    var rMedium = new Rectangle(560, 425, 280, 60)
    var rHard = new Rectangle(560, 575, 280, 60)
    graphics.drawRect(rEasy)
    graphics.drawRect(rMedium)
    graphics.drawRect(rHard)

    var playMenu = true
    graphics.addMouseListener(new MouseAdapter() {
      override def mouseClicked(e: MouseEvent): Unit = {

        // coordinates of mouse
        val mouseX = e.getX
        val mouseY = e.getY

        // coordinates to grid index.

        // clicks IN MENU
        if (e.getButton == MouseEvent.BUTTON1) {
          println(s"$mouseX:$mouseY")
          if (playMenu == true) {
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
      val fontTitle = new Font("Arial", Font.PLAIN, 48)
      val fontSection = new Font("Arial", Font.PLAIN, 32)
      graphics.setColor(Color.BLACK)
      graphics.drawString(width / 2, height / 8, "Minesweeper", fontTitle, Color.BLACK, 0, 0)
      graphics.drawString(width / 2, height / 3, "PLAY", fontSection, Color.BLACK, 0, 0)
      graphics.drawString(width / 2, height / 2, "SETTINGS", fontSection, Color.BLACK, 0, 0)
      graphics.drawString(width / 2, height / 6 + height / 2, "EXIT", fontSection, Color.BLACK, 0, 0)
      var rPlay = new Rectangle(560, 275, 280, 60)
      var rSettings = new Rectangle(560, 425, 280, 60)
      var rExit = new Rectangle(560, 575, 280, 60)
      graphics.drawRect(rPlay)
      graphics.drawRect(rSettings)
      graphics.drawRect(rExit)

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
                  drawLevelMenu()
                }

                if (mouseY >= 425 && mouseY <= 485) {
                  println("settings")
                }
                if (mouseY >= 575 && mouseY <= 635) {
                  println("exit")
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
