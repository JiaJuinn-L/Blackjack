package BlackJackie.model

import scalafx.scene.image.Image

/***************************************************************************************
 *    Title: blackjack.java
 *    Author:  ImKennyYip
 *    Date: 2023
 *    Code version: Java
 *    Availability: GitHub
 *
 ***************************************************************************************/

case class Card(private var cardValue: String, private var cardSuit: String) {
  // Converts to a string in the format "value-type"
  override def toString: String = s"$cardValue-$cardSuit"

  // Getter for cardValue
  def getCardValue: String = cardValue

  // Returns the correct image path relative to the classpath
  def getImagePath: String = s"/CardImages/$toString.png"

  // Loads the image using the resource path
  def getImage: Image = {
    new Image(getClass.getResourceAsStream(getImagePath))
  }

  // Determines the card's value (Ace is 11, face cards are 10)
  def getValue: Int = {
    cardValue match {
      case "A" => 11
      case "J" | "Q" | "K" => 10
      case _ => cardValue.toInt
    }
  }
}