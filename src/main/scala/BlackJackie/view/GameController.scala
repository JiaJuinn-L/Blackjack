package BlackJackie.view
import BlackJackie.MainApp
import BlackJackie.model.Game
import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml
import scalafx.animation.PauseTransition
import scalafx.scene.control.Button
import scalafx.util.Duration
import scalafx.scene.text.Text

@sfxml
class GameController(
                      private val playerCard1: ImageView,
                      private val playerCard2: ImageView,
                      private val playerCard3: ImageView,
                      private val playerCard4: ImageView,
                      private val playerCard5: ImageView,
                      private val dealerCard1: ImageView,
                      private val dealerCard2: ImageView,
                      private val dealerCard3: ImageView,
                      private val dealerCard4: ImageView,
                      private val dealerCard5: ImageView,
                      private val playerHandValue: Text,
                      private val dealerHandValue: Text,
                      private val numRound: Text,
                      private val numWonRound: Text,
                      private val hitButton: Button,
                      private val standButton: Button,
                      private val winner: Text,
                      private val exitBtn: Button
                    ) {

  private val game = new Game()
  private val audio = new AudioController()

  initialize()

  def initialize(): Unit = {
    audio.cardShuffle()
    displayPlayerHand()
    updatePlayerHandValue()
    updateNumRound()
    displayDealerFirstHand()
    resetDealerHandValue()
    resetNumWonRound()
  }


  def gameReset(): Unit = {
    audio.cardShuffle()
    resetCardImages()
    resetWinner()
    updateNumRound()
    updateNumWonRound()
    displayPlayerHand()
    displayDealerFirstHand()
    updatePlayerHandValue()
    resetDealerHandValue()
  }

  def getWinner(): Unit = {
    val value = game.getWinner
    winner.setText(value)
    if (value == "Player Win!" || value == "Player Blackjack!"){
      audio.youWinSound()
    }else if (value == "Dealer Win!" || value == "DealerBlackjack!"){
      audio.youLoseSound()
    }else{
      audio.tieSound()
    }
  }

  def resetWinner(): Unit = {
    winner.setText("")
  }

  def updatePlayerHandValue(): Unit = {
    val value = game.getPlayer.handValue.toString
    playerHandValue.setText(value)
  }

  def updateDealerHandValue(): Unit = {
    val value = game.getDealer.handValue.toString
    dealerHandValue.setText(value)
  }

  def resetDealerHandValue(): Unit = {
    val dealerHand = game.getDealer.getHand
    val value = dealerHand(1).getValue.toString
    dealerHandValue.setText(value)
  }

  def updateNumRound(): Unit = {
    val value = game.getTotalRound.toString
    numRound.setText(value)
  }

  def updateNumWonRound(): Unit = {
    val value = game.getPlayer.getWonRound.toString
    numWonRound.setText(value)
  }

  def resetNumWonRound(): Unit = {
    numWonRound.setText("0")
  }


  def displayDealerFirstHand(): Unit = {
    val hiddenCard = new Image("/CardImages/BACK.png")
    val dealerHand = game.getDealer.getHand
    dealerCard1.setImage(hiddenCard)
    dealerCard2.setImage(dealerHand(1).getImage)
  }

  def displayDealerHand(): Unit = {
    val dealerCards = Seq(dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5)
    game.getDealer.getHand.zip(dealerCards).foreach {
      case (card, imageView) => imageView.setImage(card.getImage)
    }
  }

  def displayPlayerHand(): Unit = {
    val playerCards = Seq(playerCard1, playerCard2, playerCard3, playerCard4, playerCard5)
    game.getPlayer.getHand.zip(playerCards).foreach {
      case (card, imageView) => imageView.setImage(card.getImage)
    }
  }

  def resetCardImages(): Unit = {
    val playerCards = Seq(playerCard1, playerCard2, playerCard3, playerCard4, playerCard5)
    val dealerCards = Seq(dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5)
    playerCards.foreach(_.setImage(null))
    dealerCards.foreach(_.setImage(null))
  }

  def addCard(): Unit = {
    if(game.getPlayer.cardCount() < 5){
      game.playerAddCard()
      displayPlayerHand()
      updatePlayerHandValue()
      audio.cardDrawnSound()
    }
    else{
      audio.actionDenied()
    }
  }

  def restartGame(): Unit = {
    game.resetGame()
    gameReset()
    hitButton.disable = false
    standButton.disable = false
    exitBtn.disable = false
  }

  def stand(): Unit = {
    game.playerStand()
    getWinner()
    displayDealerHand()
    updateDealerHandValue()

    // Create a pause of 3 seconds
    val pause = new PauseTransition(Duration(3000))
    hitButton.disable = true
    standButton.disable = true
    exitBtn.disable = true

    // Set the action to perform after the pause
    pause.setOnFinished(_ => restartGame())
    // Start the pause
    pause.play()
  }

  def backToHome(): Unit = {
    game.saveScore()
    MainApp.showHomePage()
  }
}
