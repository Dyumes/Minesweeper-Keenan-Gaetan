import javax.swing.ImageIcon
import java.awt.Image


object images {
  var hellsweeperMode: Boolean = false
  var defaultPath : Array[String] = Array(
      "/res/grid/flag.png", //index(0)
      "/res/grid/1.png",    //..1
      "/res/grid/2.png",    //..2
      "/res/grid/3.png",    //..3
      "/res/grid/4.png",    //..4
      "/res/grid/5.png",    //..5
      "/res/grid/6.png",    //..6
      "/res/grid/7.png",    //..7
      "/res/grid/8.png",    //..8
      "/res/grid/controls.png",  //..9
      "/res/grid/background.png", //..10
      "/res/menus/title.png", //..11
      "/res/menus/play.png",  //..12
      "/res/menus/settings.png",  //..13
      "/res/menus/exit.png",  //..14
      "/res/menus/logo.png",  //..15
      "/res/menus/menu.png",  //..16
      "/res/menus/easy.png",  //..17
      "/res/menus/medium.png",  //..18
      "/res/menus/hard.png", //..19
      "/res/hellsweeper/menus/helldive.png", //20 -> mode selection
      "/res/grid/mine.png",  //..21

    )

    var hellsweeperPath : Array[String] = Array(
      "/res/hellsweeper/grid/seflag.png",    //index(0)
      "/res/hellsweeper/grid/one.png",       //...1
      "/res/hellsweeper/grid/two.png",       //...2
      "/res/hellsweeper/grid/three.png",     //...3
      "/res/hellsweeper/grid/four.png",      //...4
      "/res/hellsweeper/grid/five.png",      //...5
      "/res/hellsweeper/grid/six.png",       //...6
      "/res/hellsweeper/grid/seven.png",     //...7
      "/res/hellsweeper/grid/eight.png",     //...8
      "/res/grid/controls.png",              //..9
      "/res/hellsweeper/grid/background.png",//..10
      "/res/hellsweeper/menus/title.png",    //...11
      "/res/hellsweeper/menus/play.png",     //...12
      "/res/hellsweeper/menus/settings.png", //...13
      "/res/hellsweeper/menus/exit.png",     //...14
      "/res/menus/logo.png",                 //..15
      "/res/hellsweeper/menus/home.png",     //...16
      "/res/hellsweeper/menus/trivial.png",  //...17
      "/res/hellsweeper/menus/medium.png",   //...18
      "/res/hellsweeper/menus/helldive.png", //...19
      "/res/hellsweeper/menus/normal.png",   //20 -> return to normal mode
      "/res/hellsweeper/grid/mines.png"      //..21
    )

  var activePath : Array[String] = defaultPath

  def getImagesPath(): Unit = {
    if (hellsweeperMode){
      activePath = hellsweeperPath
    }
    if (!hellsweeperMode){
      activePath = defaultPath
    }
  }

  var lostImage = "/res/hellsweeper/grid/lost.png"
  var winImage = "/res/hellsweeper/grid/win.png"


}
