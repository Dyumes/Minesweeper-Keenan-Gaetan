import main.{buttonSound, cols, drawGrid, gameHellmode, gameSong, graphics, grid, height, isFirstClick, isMenuOpen, lost, nbrMines, offsetX, offsetY, revealAdjacent, rows, settingsMenu, sizeCell, startNewGame, width, win}

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

    graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, images.activePath(10))
    graphics.drawTransformedPicture(width/ 2, height - height /10, 0.0, 0.3, images.activePath(15))
    graphics.drawTransformedPicture(width / 2, height / 8, 0.0, 1, images.activePath(11))
    graphics.drawTransformedPicture(width / 2, height / 3, 0.0, 1, images.activePath(17))
    graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, images.activePath(18))
    graphics.drawTransformedPicture(width / 2, height / 6 + height / 2, 0.0, 1, images.activePath(19))
    graphics.drawString(width / 8, height / 3, "Menu", fontSection, Color.BLACK, 0, 0)
    graphics.drawTransformedPicture(width / 8, height / 3, 0.0, 1, images.activePath(16))

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
              buttonSound.play()
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
                buttonSound.play()
                if (images.hellsweeperMode){
                  graphics.frontBuffer.synchronized{
                    graphics.clear()
                    General.generalVoiceline()
                  }
                }
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
                buttonSound.play()
                if (images.hellsweeperMode){
                  graphics.frontBuffer.synchronized{
                    graphics.clear()
                    General.generalVoiceline()
                  }
                }
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
                buttonSound.play()
                if (images.hellsweeperMode){
                  graphics.frontBuffer.synchronized{
                    graphics.clear()
                    General.generalVoiceline()
                  }
                }
                startNewGame(rows, cols, sizeCell, nbrMines)
              }
            }
          }
        }
      }
    })
  }
  settingsMenu = true

  private def drawSettingsMenu(): Unit = {
    val fontSection = new Font("Arial", Font.PLAIN, 32)
    // Clear the graphics buffer
    graphics.clear()

    // Draw background and menu elements

    graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, images.activePath(10))
    graphics.drawTransformedPicture(width/ 2, height - height /10, 0.0, 0.3, images.activePath(15))
    graphics.drawTransformedPicture(width / 2, height / 8, 0.0, 1, images.activePath(11))
    if (images.hellsweeperMode){
      val font = new Font("Monospaced", Font.BOLD, 25)
      graphics.drawString(width / 2, height / 2, "It is heresy to turn your back on democracy.", font, Color.WHITE, 0, 0)
      graphics.drawString(width / 2, height / 2 + height/ 10, "Press M key.", font, Color.WHITE, 0, 0)
    }else{
      graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, images.activePath(20))
    }

    // Add mouse listener for menu interactions
    graphics.addMouseListener(new MouseAdapter() {
      override def mouseClicked(e: MouseEvent): Unit = {

        // Get mouse click coordinates
        val mouseX = e.getX
        val mouseY = e.getY

        // Handle left-click events in the menu
        if (e.getButton == MouseEvent.BUTTON1) {
          if (settingsMenu && !isMenuOpen) {
            if (mouseX >= 105 && mouseX <= 245 && mouseY >= 275 && mouseY <= 335) {
              println("Return to menu")
              isMenuOpen = true
              settingsMenu= false
              buttonSound.play()
              graphics.frontBuffer.synchronized {
                drawMenu()
              }
            }

              // Helldiver mode
            if (mouseX >= 560 && mouseX <= 840 && mouseY >= 425 && mouseY <= 485) {
              images.hellsweeperMode = true
              isMenuOpen = true
              images.getImagesPath()
              println("before drawMenu()")
              settingsMenu = false
              gameSong.audioClip.stop
              buttonSound.play()
              graphics.frontBuffer.synchronized{
                drawMenu()
              }

              gameHellmode.play()
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
      println("catch ismenuopen = true)")
      // Clear the graphics buffer
      graphics.clear()

      // Draw background and menu elements
      graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, images.activePath(10))
      graphics.drawTransformedPicture(width/ 2, height - height /10, 0.0, 0.3, images.activePath(15))
      graphics.drawTransformedPicture(width / 2, height / 8, 0.0, 1, images.activePath(11))
      graphics.drawTransformedPicture(width / 2, height / 3, 0.0, 1, images.activePath(12))
      graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, images.activePath(13))
      graphics.drawTransformedPicture(width / 2, height / 6 + height / 2, 0.0, 1, images.activePath(14))

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
            if (!playMenu && !settingsMenu) {
              if (mouseX >= 560 && mouseX <= 840) {
                // Play Button
                if (mouseY >= 275 && mouseY <= 335) {
                  println("Play")
                  playMenu = true
                  buttonSound.play()
                  graphics.frontBuffer.synchronized {
                    drawLevelMenu()
                  }
                }

                // Settings Button
                if (mouseY >= 425 && mouseY <= 485) {
                  println("Settings")
                  settingsMenu = true
                  buttonSound.play()
                  graphics.frontBuffer.synchronized{
                    drawSettingsMenu()
                  }
                  isMenuOpen = false
                }

                // Exit Button
                if (mouseY >= 575 && mouseY <= 635) {
                  println("Exit")
                  buttonSound.play()
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
