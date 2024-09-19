package BlackJackie.model
import BlackJackie.MainApp
import scala.util.{Failure, Success}

class Game {
  private val deck = new Deck()
  deck.buildDeck()
  deck.shuffleDeck()
  private val player = new Player(deck.drawCard(), deck.drawCard())
  private val dealer = new Dealer(deck.drawCard(), deck.drawCard())
  private var totalRound: Int = 1
  private var winner = ""


  def getTotalRound: Int = totalRound
  def getWinner: String = winner
  def getPlayer: Player = player
  def getDealer: Dealer = dealer

  def playerAddCard(): Unit = {
    if (player.getHand.size < 5) {
      player.addCard(deck.drawCard())
      println("\nPlayer's hand:")
      player.showHand()
      println(s"Player's hand value: ${player.handValue}")
    } else {
      println("\nPlayer cannot draw more cards.")
    }
  }

  def dealerAddCard(): Unit = {
    if (dealer.getHand.size < 5) {
      dealer.addCard(deck.drawCard())
      println("\nDealer's hand:")
      dealer.showHand()
      println(s"Dealer's hand value: ${dealer.handValue}")
    } else {
      println("\nDealer cannot draw more cards.")
    }
  }

  def playerStand(): Unit = {
    botPlay()
    winner = determineWinner(player, dealer)
    if(winner == "Player Win!"){
      player.incrementWonRound()
    }
    totalRound+=1
  }


    def resetGame(): Unit = {
      deck.buildDeck()
      deck.shuffleDeck()
      player.resetHand()
      player.addCard(deck.drawCard())
      player.addCard(deck.drawCard())
      dealer.resetHand()
      dealer.addCard(deck.drawCard())
      dealer.addCard(deck.drawCard())
    }


    def botPlay(): Unit = {
      while (dealer.handValue < 17) {
        dealerAddCard()
      }
    }

  def determineWinner(player: Participant, dealer: Participant): String = {
    val playerValue = player.handValue
    val dealerValue = dealer.handValue

    // Check if both player and dealer have Blackjack
    val playerHasBlackjack = player.cardCount() == 2 && playerValue == 21
    val dealerHasBlackjack = dealer.cardCount() == 2 && dealerValue == 21

    if (playerHasBlackjack && dealerHasBlackjack) return "Tie!"
    if (playerHasBlackjack) return "Player Win!"
    if (dealerHasBlackjack) return "Dealer Win!"

    // Check if both player and dealer have busted (over 21)
    if (playerValue > 21 && dealerValue > 21) return "Tie!" // Both bust
    if (playerValue > 21) return "Dealer Win!" // Player busts
    if (dealerValue > 21) return "Player Win!" // Dealer busts

    // Check if player has Five-Card Charlie
    val playerFiveCardCharlie = player.cardCount() == 5 && playerValue <= 21
    if (playerFiveCardCharlie) return "Player Win!"

    // Regular win conditions
    if (playerValue > dealerValue) return "Player Win!"
    if (dealerValue > playerValue) return "Dealer Win!"

    // If neither player nor dealer has won, it's a tie
    "Tie!"
  }



  def saveScore(): Unit = {
    // Existing save score logic
    val scoreCounter = player.getWonRound
    val scoreInstance = Score(scoreCounter, MainApp.inputName)
    scoreInstance.save() match {
      case Success(_) => println(s"Score of $scoreCounter saved successfully., ${MainApp.inputName}")
      case Failure(exception) => println(s"Failed to save score: ${exception.getMessage}")
    }
  }
}
