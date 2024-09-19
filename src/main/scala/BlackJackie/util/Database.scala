package BlackJackie.util

import BlackJackie.model.Score
import scalikejdbc._

/***************************************************************************************
 *    Title: AddressApp (Database)
 *    Author: Dr. Chin Teck Min (Lecturer)
 *    Date: 21/08/2024
 *    Code version: SCALA 2.12.19/ ScalaFX8
 *    Availability: Tutorial from Uni work
 *
 ***************************************************************************************/

trait Database {
  private val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

  private val dbURL = "jdbc:derby:BlackjackDB;create=true;";
  // initialize JDBC driver & connection pool
  Class.forName(derbyDriverClassname)

  ConnectionPool.singleton(dbURL, "user", "password")

  // ad-hoc session provider on the REPL
  implicit val session = AutoSession
}


object Database extends Database{
  def setupDB() = {
    if (!hasDBInitialize){
      Score.initializeTable()
    }

  }

  def hasDBInitialize : Boolean = {
    DB getTable "Score" match {
      case Some(x) => true
      case None => false
    }

  }
}