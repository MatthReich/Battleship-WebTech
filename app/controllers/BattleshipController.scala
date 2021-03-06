package controllers

import Battleship.Game.tui.{decreaseShipNumbersToPlace, shipProcessLong}
import Battleship._
import Battleship.controller.ControllerBaseImpl.{GameState, PlayerState}
import Battleship.controller.InterfaceController
import javax.inject._
import play.api.mvc._;
import com.google.inject.Guice

@Singleton
class BattleshipController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  var gameController: InterfaceController = Game.controller

  def playAgain(): Action[AnyContent] = Action { implicit request =>
    val injector = Guice.createInjector(new GameModule)
    gameController = injector.getInstance(classOf[InterfaceController])
    Ok(views.html.landingpage()(request))
  }

  def idle(coordinates: String): Action[AnyContent] = Action {
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

  def setPlayer(): Action[AnyContent] = Action { implicit request =>
    val selection = request.body.asFormUrlEncoded
    selection.map { args =>
      gameController.setPlayers(args("namePlayer1").head)
      gameController.setPlayers(args("namePlayer2").head)
      Ok(views.html.setShip(gameController))
    }.getOrElse(InternalServerError("Ooopa - Internal Server Error"))
  }

  def setShip(ship: String): Action[AnyContent] = Action {
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

  def about: Action[AnyContent] = Action {
    Ok(views.html.aboutpage())
  }

  def save: Action[AnyContent] = Action { implicit request =>
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
      Ok(views.html.landingpage()(request))
    }
  }

  def load: Action[AnyContent] = Action { implicit request =>
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
      Ok(views.html.landingpage()(request))
    }
  }

  def landingpage: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.landingpage()(request))
  }

  def setShipView: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.setShip(gameController))
  }

  def idleView: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.idlepage(gameController))
  }

}
