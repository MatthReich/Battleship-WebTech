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

  def idle(coordinates: String) = Action {
    if (gameController.getGameState == GameState.IDLE) {
      if (coordinates == "undo guess") {
        if (gameController.getPlayerState == PlayerState.PLAYER_ONE) {
          gameController.undoGuess(coordinates, gameController.getGridPlayer2)
        } else {
          gameController.undoGuess(coordinates, gameController.getGridPlayer2)
        }
      } else {
        if (gameController.getPlayerState == PlayerState.PLAYER_ONE) {
          gameController.checkGuess(coordinates, gameController.getGridPlayer2)
          gameController.setLastGuess(coordinates)
        }
        else {
          gameController.checkGuess(coordinates, gameController.getGridPlayer1)
          gameController.setLastGuess(coordinates)
        }
      }
      Ok(views.html.idlepage(gameController))
    } else if (gameController.getGameState == GameState.SOLVED) {
      Ok(views.html.winningpage(gameController))
    } else {
      Ok(views.html.setPlayer(gameController))
    }

  }

  def setPlayer(name: String) = Action {
    var tmp = name.substring(name.indexOf("=") + 1)
    System.out.println(tmp)
    gameController.setPlayers(tmp.substring(tmp.indexOf("&")))
    tmp = tmp.substring(tmp.indexOf("=") + 1)
    System.out.println(tmp)
    gameController.setPlayers(tmp.substring(tmp.indexOf("&")))
    Ok(views.html.setShip(gameController))
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
      Ok(views.html.idlepage(gameController))
    }
  }

  def about = Action {
    Ok(views.html.aboutpage())
  }

  def save = Action {
    if (gameController.getGameState == GameState.PLAYERSETTING) {
      gameController.save()
      Ok(views.html.setPlayer(gameController))
    } else if (gameController.getGameState == GameState.SHIPSETTING) {
      gameController.save()
      Ok(views.html.setShip(gameController))
    } else if (gameController.getGameState == GameState.IDLE) {
      gameController.save()
      Ok(views.html.idlepage(gameController))
    } else if (gameController.getGameState == GameState.SOLVED) {
      gameController.save()
      Ok(views.html.winningpage(gameController))
    } else {
      gameController.save()
      Ok(views.html.landingpage())
    }
  }

  def load = Action {
    gameController.load()
    if (gameController.getGameState == GameState.PLAYERSETTING) {
      Ok(views.html.setPlayer(null))
    } else if (gameController.getGameState == GameState.SHIPSETTING) {
      Ok(views.html.setShip(gameController))
    } else if (gameController.getGameState == GameState.IDLE) {
      Ok(views.html.idlepage(gameController))
    } else if (gameController.getGameState == GameState.SOLVED) {
      Ok(views.html.winningpage(gameController))
    } else {
      Ok(views.html.landingpage())
    }
  }

  def landingpage = Action {
    Ok(views.html.landingpage())
  }

  def battleshipAsText = gameController.getGridPlayer1 + ControllerBaseImpl.GameState.message(gameController.getGameState)

}
