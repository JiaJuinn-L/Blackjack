package BlackJackie.model

abstract class Participant(private val card1: Card, private val card2: Card) {
  protected var hand: List[Card] = List(card1,card2)

  def addCard(newCard: Card): Unit = {
    hand = hand :+ newCard
  }

  def handValue: Int = {
    var aceCount = 0
    val initialValue = hand.foldLeft(0) { (acc, card) =>
      card.getValue.toString match {
        case "A" => aceCount += 1; acc + 11
        case "K" | "Q" | "J" => acc + 10
        case _ => acc + card.getValue.toInt
      }
    }

    // Adjust for Aces if the total value is greater than 21
    var finalValue = initialValue
    while (finalValue > 21 && aceCount > 0) {
      finalValue -= 10
      aceCount -= 1
    }
    finalValue
  }

  def showHand(): Unit = {
    hand.foreach(println)
  }

  // Method to get the hand of cards
  def getHand: List[Card] = hand

  // Reset the hand to an empty list
  def resetHand(): Unit = {
    hand = List()
  }

  // Method to count the number of cards in hand
  def cardCount(): Int = {
    hand.size
  }

  def isDealer: Boolean = true
}