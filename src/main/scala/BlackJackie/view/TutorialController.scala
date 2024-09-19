package BlackJackie.view

import BlackJackie.MainApp
import javafx.scene.media.AudioClip
import scalafxml.core.macros.sfxml

@sfxml
class TutorialController{

  private val audio = new AudioController
  def backToHome(): Unit = {
    audio.buttonPressSound()
    MainApp.showHomePage()
  }
}