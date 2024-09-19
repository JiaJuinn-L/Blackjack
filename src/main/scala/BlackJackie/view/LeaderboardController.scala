package BlackJackie.view

import BlackJackie.MainApp
import BlackJackie.model.Score
import scalafx.beans.value.ObservableValue
import scalafx.scene.control.{TableColumn, TableView}
import scalafxml.core.macros.sfxml

@sfxml
class LeaderboardController(private val leaderboard:TableView[Score],
                            private val playerNameColumn:TableColumn[Score,String],
                            private val wonRoundColumn:TableColumn[Score,Number]){

  private val audio = new AudioController

  // Initialize the table with existing data
  refreshTable()

  playerNameColumn.cellValueFactory = { cellData: TableColumn.CellDataFeatures[Score, String] =>
    cellData.value.name
  }

  // Set the cell value factory using ScalaFX properties
  wonRoundColumn.cellValueFactory = { cellData: TableColumn.CellDataFeatures[Score, Number] =>
    cellData.value.won.asInstanceOf[ObservableValue[Number, Number]]
  }

  // Method to refresh the table data
  def refreshTable(): Unit = {
    // Clear the existing data
    MainApp.learderBoardScore.clear()

    // Fetch the updated data from the database
    MainApp.learderBoardScore ++= Score.getAllScores()

    // Reassign the updated buffer to the TableView
    leaderboard.items = MainApp.learderBoardScore

    // Optionally, force a refresh
    leaderboard.refresh()
  }

  def backToHome(): Unit = {
    audio.buttonPressSound()
    MainApp.showHomePage()
  }
}