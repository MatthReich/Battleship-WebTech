package controllers

import Battleship._
import Battleship.controller.ControllerBaseImpl.{ CellChanged, GameState, PlayerChanged, PlayerState }
import Battleship.controller.InterfaceController
import akka.actor.{ Actor, ActorRef, ActorSystem, Props }
import akka.stream.Materializer
import com.mohiva.play.silhouette.api.actions.SecuredRequest
import com.mohiva.play.silhouette.impl.providers.GoogleTotpInfo
import javax.inject.{ Inject, Singleton }
import play.api.libs.json.{ JsValue, Json }
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import utils.GridtoJson

import scala.concurrent.ExecutionContext
import scala.swing.Reactor

@Singleton
class BattleshipController @Inject() (
  scc: SilhouetteControllerComponents,
  about: views.html.about,
  setShipView: views.html.setShip,
  idlepageView: views.html.idlepage,
  winningpage: views.html.winningpage,
  landingpage: views.html.landingpage)(implicit ex: ExecutionContext, system: ActorSystem, mat: Materializer) extends SilhouetteController(scc) {
  var gameController: InterfaceController = Game.controller
  var isFirst = false
  var isLast = true

  def playAgain() = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    authInfoRepository.find[GoogleTotpInfo](request.identity.loginInfo).map { totpInfoOpt =>
      Ok(landingpage(request.identity, totpInfoOpt))
    }
  }

  def aboutpage() = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    authInfoRepository.find[GoogleTotpInfo](request.identity.loginInfo).map { totpInfoOpt =>
      Ok(about(request.identity, totpInfoOpt))
    }
  }

  def setPlayer() = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    authInfoRepository.find[GoogleTotpInfo](request.identity.loginInfo).map { totpInfoOpt =>
      val selection = request.body.asFormUrlEncoded
      selection.map { args =>
        gameController.setPlayers(args("namePlayer1").head)
        gameController.setPlayers(args("namePlayer2").head)
        Ok(setShipView(request.identity, totpInfoOpt, gameController))
      }.getOrElse(InternalServerError("Ooopa - Internal Server Error"))
    }
  }

  def getJson = Action(parse.json) {
    Ok(toJson())
  }
  /*
  def save: Action[AnyContent] = Action { implicit request =>
    if (gameController.getGameState == GameState.PLAYERSETTING) {
      gameController.save()
      Ok(views.html.setPlayer(gameController)).withHeaders("Acces-Control-Allow-Origin" -> "http://localhost:8080")
    } else if (gameController.getGameState == GameState.SHIPSETTING) {
      gameController.save()
      Ok(views.html.setShip(gameController)).withHeaders("Acces-Control-Allow-Origin" -> "http://localhost:8080")
    } else if (gameController.getGameState == GameState.IDLE) {
      gameController.save()
      Ok(views.html.idlepage(gameController)).withHeaders("Acces-Control-Allow-Origin" -> "http://localhost:8080")
    } else if (gameController.getGameState == GameState.SOLVED) {
      gameController.save()
      Ok(views.html.winningpage(gameController)).withHeaders("Acces-Control-Allow-Origin" -> "http://localhost:8080")
    } else {
      gameController.save()
      Ok(views.html.landingpage()(request)).withHeaders("Acces-Control-Allow-Origin" -> "http://localhost:8080")
    }
  }

  def load: Action[AnyContent] = Action { implicit request =>
    gameController.load()
    if (gameController.getGameState == GameState.PLAYERSETTING) {
      Ok(views.html.setPlayer(null)).withHeaders("Acces-Control-Allow-Origin" -> "http://localhost:8080")
    } else if (gameController.getGameState == GameState.SHIPSETTING) {
      Ok(views.html.setShip(gameController)).withHeaders("Acces-Control-Allow-Origin" -> "http://localhost:8080")
    } else if (gameController.getGameState == GameState.IDLE) {
      Ok(views.html.idlepage(gameController)).withHeaders("Acces-Control-Allow-Origin" -> "http://localhost:8080")
    } else if (gameController.getGameState == GameState.SOLVED) {
      Ok(views.html.winningpage(gameController)).withHeaders("Acces-Control-Allow-Origin" -> "http://localhost:8080")
    } else {
      Ok(views.html.landingpage()(request)).withHeaders("Acces-Control-Allow-Origin" -> "http://localhost:8080")
    }
  }
*/

  def jsonInput = Action(parse.json) {
    request: Request[JsValue] =>
      {
        val data = readCommand(request.body)
        if (gameController.getGameState == GameState.IDLE) {
          idle(data._1 + " " + data._2)
        } else if (gameController.getGameState == GameState.SHIPSETTING) {
          setShip(data._1 + " " + data._2 + " " + data._3 + " " + data._4)
        }

      }
      Ok(toJson())
  }

  def toIdle() = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    authInfoRepository.find[GoogleTotpInfo](request.identity.loginInfo).map { totpInfoOpt =>
      Ok(idlepageView(request.identity, totpInfoOpt, gameController))
    }
  }

  def idle(coordinates: String): Unit = {
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
        } else {
          gameController.checkGuess(coordinates, gameController.getGridPlayer1)
          gameController.setLastGuess(coordinates)
        }
      }
    }
  }

  def setShip(coordinates: String): Unit = {
    if (gameController.getGameState == GameState.SHIPSETTING) {
      gameController.getPlayerState match {
        case PlayerState.PLAYER_ONE => {
          gameController.setShipSet(false)
          gameController.setShip(coordinates)
          if ((gameController.getNrPlayer1()(0) + gameController.getNrPlayer1()(1) + gameController.getNrPlayer1()(2) +
            gameController.getNrPlayer1()(3)) == 0) {
            gameController.setPlayerState(PlayerState.PLAYER_TWO)
          }
        }
        case PlayerState.PLAYER_TWO => {
          gameController.setShipSet(false)
          gameController.setShip(coordinates)
          if (gameController.getNrPlayer2()(0) + gameController.getNrPlayer2()(1) + gameController.getNrPlayer2()(2) +
            gameController.getNrPlayer2()(3) == 0) {
            gameController.setPlayerState(PlayerState.PLAYER_ONE)
            gameController.setGameState(GameState.IDLE)
          }
        }
      }
    }
  }

  def toJson(): String = {
    val gridtoJson = new GridtoJson()
    gridtoJson.save(gameController.getGridPlayer1, gameController.getGridPlayer2, gameController.getNrPlayer1(), gameController.getNrPlayer2(), gameController.getGameState, gameController.getPlayerState, gameController.getPlayer1, gameController.getPlayer2)
  }

  def readCommand(value: JsValue): (String, String, String, String) = {
    ((value \ "row").get.toString(), (value \ "col").get.toString(), (value \ "row2").get.toString(), (value \ "col2").get.toString())
  }

  def winningpage() = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    authInfoRepository.find[GoogleTotpInfo](request.identity.loginInfo).map { totpInfoOpt =>
      Ok(winningpage(request.identity, totpInfoOpt, gameController))
    }
  }

  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef {
      out => Props(new MyWebSocketActor(out))
    }
  }

  class MyWebSocketActor(out: ActorRef) extends Actor with Reactor {
    listenTo(gameController)
    reactions += {
      case event: CellChanged =>
        println("cell-changed")
        out ! Json.obj("event" -> "cell-changed", "object" -> toJson()).toString()
      case event: PlayerChanged =>
        println("player-changed")
        if (isFirst == true) {
          out ! Json.obj("event" -> "start-game").toString()
          if (isLast == false)
            isFirst = false
          else
            isLast = false
        } else {
          out ! Json.obj("event" -> "player-changed", "object" -> toJson()).toString()
        }
      case other => println("Unmanaged event: " + other.getClass.getName)
    }

    override def receive: Receive = {
      case "Trying to connect to Server" =>
        println("is connected")
        out ! Json.obj("event" -> "cell-changed", "object" -> toJson()).toString()
      case x: String if x.nonEmpty =>
        println("eingabe: " + x)
        if (gameController.getGameState == GameState.IDLE) {
          val eingabe = x.split(" ");
          idle(eingabe(0) + " " + eingabe(1))
        } else if (gameController.getGameState == GameState.SHIPSETTING) {
          val eingabe = x.split(" ");
          setShip(eingabe(0) + " " + eingabe(1) + " " + eingabe(2) + " " + eingabe(3))
        } else if (gameController.getGameState == GameState.PLAYERSETTING) {
          if (gameController.getPlayerState == PlayerState.PLAYER_ONE) {
            out ! Json.obj("event" -> "send-id", "object" -> "player1").toString()
          } else {
            out ! Json.obj("event" -> "send-id", "object" -> "player2").toString()
            isFirst = true
          }
          gameController.setPlayers(x)

        }
    }
  }

}
