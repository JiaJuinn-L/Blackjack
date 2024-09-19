package BlackJackie.model

import BlackJackie.util.Database
import scalafx.beans.property.{IntegerProperty, StringProperty}
import scalikejdbc.{AutoSession, DB, DBSession, scalikejdbcSQLInterpolationImplicitDef}

import scala.util.Try

/***************************************************************************************
 *    Title: AddressApp (Database)
 *    Author: Dr. Chin Teck Min (Lecturer)
 *    Date: 21/08/2024
 *    Code version: SCALA 2.12.19/ ScalaFX8
 *    Availability: Tutorial from Uni work
 *
 ***************************************************************************************/

class Score(private val wonRound: Int, private val playerName: String) extends Database {
  var won = IntegerProperty(wonRound) // Initializes the IntegerProperty
  var name = StringProperty(playerName)// Initializes the StringProperty

  def save(): Try[Int] = {
    if (!isExist) {
      Try(DB autoCommit { implicit session =>
        sql"""
          insert into score (score, player_name)
          values (${won.value}, ${name.value})
        """.update.apply()
      })
    } else {
      Try(DB autoCommit { implicit session =>
        sql"""
          update score
          set score = ${won.value}, player_name = ${name.value}
          where score = ${won.value} and player_name = ${name.value}
        """.update.apply()
      })
    }
  }

  def delete(): Try[Int] = {
    if (isExist) {
      Try(DB autoCommit { implicit session =>
        sql"""
          delete from score where score = ${won.value} and player_name = ${name.value}
        """.update.apply()
      })
    } else {
      throw new Exception("Score does not exist in the database")
    }
  }

  def isExist: Boolean = {
    DB readOnly { implicit session =>
      sql"""
        select * from score where score = ${won.value} and player_name = ${name.value}
      """.map(rs => rs.int("score")).single.apply()
    } match {
      case Some(_) => true
      case None => false
    }
  }
}

object Score extends Database {
  def apply(scorePoint: Int, playerName: String): Score = {
    new Score(scorePoint, playerName)
  }

  def initializeTable()(implicit session: DBSession = AutoSession): Unit = {
    DB autoCommit { implicit session =>
      sql"""
        create table score (
          id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
          score int not null,
          player_name varchar(255) not null
        )
      """.execute.apply()
    }
  }

  def getAllScores(): List[Score] = {
    DB readOnly { implicit session =>
      sql"""
      select * from score order by score desc
    """.map(rs => Score(rs.int("score"), rs.string("player_name"))).list.apply()
    }
  }
}