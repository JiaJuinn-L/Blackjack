package BlackJackie.model

class Dealer(card1: Card, card2: Card) extends Participant(card1, card2) {
  override def isDealer: Boolean = true
}