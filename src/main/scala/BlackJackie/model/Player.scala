package BlackJackie.model

class Player(card1: Card, card2: Card) extends Participant(card1, card2) {

  protected var wonRound: Int = 0

  // Method to increment the wonRound counter
  def incrementWonRound(): Unit = {
    wonRound += 1
  }

  // Method to get the current number of won rounds
  def getWonRound: Int = {
    wonRound
  }

  override def isDealer: Boolean = false
}