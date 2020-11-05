package controllers

import Battleship.Game.tui.{decreaseShipNumbersToPlace, shipProcessLong}
import Battleship._
import Battleship.controller.ControllerBaseImpl
import Battleship.controller.ControllerBaseImpl.{GameState, PlayerState}
import javax.inject._
import play.api.mvc._;

@Singleton
class BattleshipController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val gameController = Game.controller

  def battleship(input: String) = Action {
    if (gameController.getGameState == GameState.IDLE) {
      print(input)
      Ok(views.html.battleship(gameController))
    } else {
      Ok(views.html.setPlayer("Player 1, please add your name :-)"))
    }
  }

  def setPlayer(name: String) = Action {
    if (gameController.getGameState == GameState.PLAYERSETTING) {
      gameController.setPlayers(name)
    }
    if (gameController.getGameState == GameState.SHIPSETTING) {
      Ok(views.html.setShip(gameController))
    } else {
      Ok(views.html.setPlayer("Player 2, please add your name :-)"))
    }
  }

  def setShip(ship: String) = Action {
    if (gameController.getGameState == GameState.SHIPSETTING) {
      gameController.getPlayerState match {
        case PlayerState.PLAYER_ONE => {
          gameController.setShipSet(false)
          shipProcessLong(ship)
          decreaseShipNumbersToPlace(gameController.getShip, gameController.getShipSet, gameController.getShipDelete)
          if ((gameController.getNrPlayer1()(0) + gameController.getNrPlayer1()(1) + gameController.getNrPlayer1()(2) +
            gameController.getNrPlayer1()(3)) == 0) {
            gameController.setPlayerState(PlayerState.PLAYER_TWO)
          }
        }
        case PlayerState.PLAYER_TWO => {
          gameController.setShipSet(false)
          shipProcessLong(ship)
          decreaseShipNumbersToPlace(gameController.getShip, gameController.getShipSet, gameController.getShipDelete)
          if (gameController.getNrPlayer2()(0) + gameController.getNrPlayer2()(1) + gameController.getNrPlayer2()(2) +
            gameController.getNrPlayer2()(3) == 0) {
            gameController.setPlayerState(PlayerState.PLAYER_ONE)
            gameController.setGameState(GameState.IDLE)

          }
        }
      }
    }
    if (gameController.getGameState == GameState.SHIPSETTING) {
      Ok(views.html.setShip(gameController))
    } else {
      Ok(views.html.battleship(gameController))
    }
  }

  def about = Action {
    Ok(views.html.index())
  }

  def landingpage = Action {
    Ok(views.html.landingpage())
  }

  def battleshipAsText = gameController.getGridPlayer1 + ControllerBaseImpl.GameState.message(gameController.getGameState)
}
