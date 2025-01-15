import main.{cols, drawGrid, graphics, grid, height, isFirstClick, isMenuOpen, lost, nbrMines, offsetX, offsetY, revealAdjacent, rows, sizeCell, startNewGame, width, win}

import java.awt.event.{MouseAdapter, MouseEvent}
import java.awt.{Color, Font}

/**
 * The `menu` object manages the display and interaction of the game menu.
 */
object menu extends App {

  /**
   * Draws the level selection menu with buttons for Easy, Medium, and Hard levels.
   */
  private def drawLevelMenu(): Unit = {
    val fontSection = new Font("Arial", Font.PLAIN, 32)

    // Clear the graphics buffer
    graphics.clear()

    // Draw background and menu elements

    graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, "/res/grid/background.png")
    graphics.drawTransformedPicture(width/ 2, height - height /10, 0.0, 0.3, "/res/menus/logo.png")
    graphics.drawTransformedPicture(width / 2, height / 8, 0.0, 1, "/res/menus/Title.png")
    graphics.drawTransformedPicture(width / 2, height / 3, 0.0, 1, "/res/menus/easy.png")
    graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, "/res/menus/medium.png")
    graphics.drawTransformedPicture(width / 2, height / 6 + height / 2, 0.0, 1, "/res/menus/hard.png")
    graphics.drawString(width / 8, height / 3, "Menu", fontSection, Color.BLACK, 0, 0)
    graphics.drawTransformedPicture(width / 8, height / 3, 0.0, 1, "/res/menus/menu.png")

    // Flag to track if the menu is active
    var playMenu = true

    // Add mouse listener for menu interactions
    graphics.addMouseListener(new MouseAdapter() {
      override def mouseClicked(e: MouseEvent): Unit = {

        // Get mouse click coordinates
        val mouseX = e.getX
        val mouseY = e.getY

        // Handle left-click events in the menu
        if (e.getButton == MouseEvent.BUTTON1) {
          if (playMenu) {
            if (mouseX >= 105 && mouseX <= 245 && mouseY >= 275 && mouseY <= 335) {
              println("Return to menu")
              isMenuOpen = true
              playMenu = false
              graphics.frontBuffer.synchronized {
                drawMenu()
              }
            }

            if (mouseX >= 560 && mouseX <= 840) {
              // Easy Level
              if (mouseY >= 275 && mouseY <= 335) {
                println("Easy")
                isMenuOpen = false
                playMenu = false
                rows = 9
                cols = 9
                sizeCell = 50
                nbrMines = 13
                startNewGame(rows, cols, sizeCell, nbrMines)
              }
              // Medium Level
              if (mouseY >= 425 && mouseY <= 485) {
                println("Medium")
                isMenuOpen = false
                playMenu = false
                rows = 12
                cols = 12
                sizeCell = 40
                nbrMines = 23
                startNewGame(rows, cols, sizeCell, nbrMines)
              }
              // Hard Level
              if (mouseY >= 575 && mouseY <= 635) {
                println("Hard")
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
    })
  }

  /**
   * Draws the main menu with buttons for Play, Settings, and Exit.
   */
  def drawMenu(): Unit = {
    if (isMenuOpen) {
      // Clear the graphics buffer
      graphics.clear()

      // Draw background and menu elements
      graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, "/res/grid/background.png")
      graphics.drawTransformedPicture(width/ 2, height - height /10, 0.0, 0.3, "/res/menus/logo.png")
      graphics.drawTransformedPicture(width / 2, height / 8, 0.0, 1, "/res/menus/Title.png")
      graphics.drawTransformedPicture(width / 2, height / 3, 0.0, 1, "/res/menus/play.png")
      graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, "/res/menus/settings.png")
      graphics.drawTransformedPicture(width / 2, height / 6 + height / 2, 0.0, 1, "/res/menus/exit.png")

      // Flag to track if the play menu is active
      var playMenu = false

      // Add mouse listener for main menu interactions
      graphics.addMouseListener(new MouseAdapter() {
        override def mouseClicked(e: MouseEvent): Unit = {

          // Get mouse click coordinates
          val mouseX = e.getX
          val mouseY = e.getY

          // Handle left-click events in the menu
          if (e.getButton == MouseEvent.BUTTON1) {
            println(s"Mouse clicked at: $mouseX:$mouseY")
            if (!playMenu) {
              if (mouseX >= 560 && mouseX <= 840) {
                // Play Button
                if (mouseY >= 275 && mouseY <= 335) {
                  println("Play")
                  playMenu = true
                  graphics.frontBuffer.synchronized {
                    drawLevelMenu()
                  }
                }

                // Settings Button
                if (mouseY >= 425 && mouseY <= 485) {
                  println("Settings")
                }

                // Exit Button
                if (mouseY >= 575 && mouseY <= 635) {
                  println("Exit")
                  System.exit(0)
                }
              }
            }
          }
        }
      })
    }
  }
}
