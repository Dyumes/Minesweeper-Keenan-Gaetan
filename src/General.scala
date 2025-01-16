import main.{buttonSound, cols, drawGrid, gameHellmode, gameSong, graphics, grid, height, isFirstClick, isMenuOpen, lost, nbrFlag, nbrMines, offsetX, offsetY, revealAdjacent, rows, settingsMenu, sizeCell, startNewGame, width, win}

import java.awt.event.{MouseAdapter, MouseEvent}
import java.awt.{Color, Font}
import scala.util.Random

object General {
  var voiceline : Array[String] = Array(
    "How about a nice cup of LIBER-TEA?",
    "No pain, no freedom",
    "Today you’ve curved another foothold in the long climb to liberty.",
    "Democracy has arrived!",
    "Helldivers, remember: liberty isn’t free—it’s paid for in ammunition and courage!",
    "Pain is temporary, but democracy is eternal. Let’s remind them of that!",
    "No retreat, no surrender—only victory! Let’s make it happen!",
    "Helldivers, they think they can resist freedom. Let’s prove them wrong!",
    "Remember: every step you take is a step toward liberty—and a minefield. Tread boldly!"
  )

  def generalVoiceline(): Unit = {
      graphics.clear()
      graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1, images.activePath(10))
      graphics.drawTransformedPicture(width / 2, height / 2, 0.0, 1.5, images.generalImage)
      var line = Random.between(0, 9)
      graphics.drawTransformedPicture(width / 2, height / 8, 0.0, 1, images.generalTextImage)
      Thread.sleep(500)
      val fontSection = new Font("Monospaced", Font.BOLD, 25)
      graphics.drawString(width / 2, height / 5, voiceline(line), fontSection, Color.WHITE, 0, 0)
      Thread.sleep(5000)

  }

}
