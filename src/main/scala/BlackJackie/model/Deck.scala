package BlackJackie.model

class Deck {
  private var deck: Array[Card] = Array()

  /***************************************************************************************
   *    Title: blackjack.java
   *    Author:  ImKennyYip
   *    Date: 2023
   *    Code version: Java
   *    Availability: GitHub
   *
   ***************************************************************************************/

  def buildDeck(): Unit = {
    val cardValues = Array("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    val cardSuits = Array("Spade", "Heart", "Diamond", "Club")

    deck = for {
      cardValue <- cardValues
      cardSuit <- cardSuits
    } yield Card(cardValue, cardSuit)
  }

  def shuffleDeck(): Unit = {
    val rnd = new scala.util.Random
    deck = rnd.shuffle(deck.toList).toArray
  }

  def drawCard(): Card = {
    if (deck.isEmpty) {
      throw new NoSuchElementException("The deck is empty!")
    } else {
      val card = deck.head
      deck = deck.tail
      card
    }
  }
}