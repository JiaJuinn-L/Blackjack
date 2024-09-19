package BlackJackie.view

import BlackJackie.MainApp
import scalafx.application.Platform
import scalafx.scene.control.TextField
import scalafxml.core.macros.sfxml

@sfxml
class HomeController(private val playerName: TextField){
  private val audio = new AudioController()

  def startGame(): Unit = {
    audio.buttonPressSound()
    MainApp.showGamePage()
    MainApp.inputName = playerName.text.value.trim
  }

  def viewLeaderboard(): Unit = {
    audio.buttonPressSound()
    MainApp.showLeaderboard()
  }

  def showTutorial(): Unit = {
    MainApp.showTutorial()
  }

  def exitApp(): Unit = {
    audio.buttonPressSound()
    Platform.exit()
  }

}