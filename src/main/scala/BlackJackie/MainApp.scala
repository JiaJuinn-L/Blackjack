package BlackJackie

import BlackJackie.model.Score
import BlackJackie.util.Database
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.collections.ObservableBuffer

object MainApp extends JFXApp {

  //Set empty name as anonymous
  var inputName = "Anonymous"
  Database.setupDB()

  val learderBoardScore = new ObservableBuffer[Score]()
  learderBoardScore ++= Score.getAllScores()


  // transform path of homagePage.fxml to URI for resource location.
  private val rootResource = getClass.getResource("../View/RootLayout.fxml")

  private val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load();

  // retrieve the root component BorderPane from the FXML
  private val roots = loader.getRoot[jfxs.layout.BorderPane]
  // : javafx.scene.layout.BorderPane

  // initialize stage
  stage = new PrimaryStage {
    title = "BlackJackie"
    scene = new Scene {
      root = roots
    }
  }

  def showHomePage(): Unit = {
    val resource = getClass.getResource("../View/Home.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  // actions for display game window
  def showGamePage(): Unit = {
    val resource = getClass.getResource("../View/Game.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
    if (inputName.isEmpty) {
      inputName = "Anonymous"
    }
  }

  def showLeaderboard(): Unit = {
    val resource = getClass.getResource("../View/Leaderboard.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showTutorial(): Unit = {
    val resource = getClass.getResource("../View/Tutorial.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }
  showHomePage()
}