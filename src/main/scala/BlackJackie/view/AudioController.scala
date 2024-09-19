package BlackJackie.view

import javafx.scene.media.AudioClip

class AudioController{

  // Method to play a sound file
  private def playSound(filePath: String): Unit = {
    val clip = new AudioClip(getClass.getResource(filePath).toString)
    clip.play()
  }

  def cardShuffle(): Unit = {
    playSound("/SoundEffects/cardShuffle.mp3")
  }

  def cardDrawnSound(): Unit = {
    playSound("/SoundEffects/cardDraw.mp3")
  }

  def youWinSound(): Unit = {
    playSound("/SoundEffects/youWin.mp3")
  }

  def youLoseSound(): Unit = {
    playSound("/SoundEffects/youLose.mp3")
  }

  def tieSound(): Unit = {
    playSound("/SoundEffects/Tie.mp3")
  }

  def actionDenied(): Unit = {
    playSound("/SoundEffects/actionDenied.mp3")
  }

  def buttonPressSound(): Unit = {
    playSound("/SoundEffects/buttonPress.wav")
  }
}