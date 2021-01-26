package controllers

import Battleship._
import Battleship.controller.ControllerBaseImpl.{CellChanged, GameState, PlayerChanged, PlayerState}
import Battleship.controller.InterfaceController
import akka.actor.{ActorSystem, _}
import akka.stream.ActorMaterializer

import javax.inject._
import play.api.libs.json.{JsValue, Json}
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import utils.GridtoJson

import scala.swing.Reactor

@Singleton
class BattleshipController @Inject()(cc: ControllerComponents)(implicit system: ActorSystem) extends AbstractController(cc) {
  var gameController: InterfaceController = Game.controller
  var isFirst = false
  var isLast = true

  def playAgain(): Action[AnyContent] = Action {
    Ok("").withHeaders("Acces-Control-Allow-Origin"->"http://localhost:8080")
  }

  def setPlayer(): Action[AnyContent] = Action { implicit request =>
    val selection = request.body.asFormUrlEncoded
    selection.map { args =>
      gameController.setPlayers(args("namePlayer1").head)
      gameController.setPlayers(args("namePlayer2").head)
      Ok("").withHeaders("Acces-Control-Allow-Origin"->"http://localhost:8080")
    }.getOrElse(InternalServerError("Ooopa - Internal Server Error"))
  }

  def getJson: Action[AnyContent] = Action(parse.json) {
    Ok(toJson).withHeaders("Acces-Control-Allow-Origin"->"http://localhost:8080")
  }

  def save: Action[AnyContent] = Action {
    gameController.save()
    Ok("").withHeaders("Acces-Control-Allow-Origin"->"http://localhost:8080")
  }

  def load: Action[AnyContent] = Action {
    gameController.load()
    Ok("").withHeaders("Acces-Control-Allow-Origin"->"http://localhost:8080")
  }

  def jsonInput: Action[JsValue] = Action(parse.json) {
    request: Request[JsValue] => {
      val data = readCommand(request.body)
      if (gameController.getGameState == GameState.IDLE) {
        idle(data._1 + " " + data._2)
      } else if (gameController.getGameState == GameState.SHIPSETTING) {
        setShip(data._1 + " " + data._2 + " " + data._3 + " " + data._4)
      }

    }
      Ok(toJson).withHeaders("Acces-Control-Allow-Origin"->"http://localhost:8080")
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
        }
        else {
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

  def toJson: String = {
    val gridtoJson = new GridtoJson()
    gridtoJson.save(gameController.getGridPlayer1, gameController.getGridPlayer2, gameController.getNrPlayer1(), gameController.getNrPlayer2(), gameController.getGameState, gameController.getPlayerState, gameController.getPlayer1, gameController.getPlayer2)
  }

  def readCommand(value: JsValue): (String, String, String, String) = {
    ((value \ "row").get.toString(), (value \ "col").get.toString(), (value \ "row2").get.toString(), (value \ "col2").get.toString())
  }

  def socket: WebSocket = WebSocket.accept[String, String] { request =>
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    ActorFlow.actorRef {
      out => Props(new MyWebSocketActor(out))
    }
  }

  class MyWebSocketActor(out: ActorRef) extends Actor with Reactor {
    listenTo(gameController)
    reactions += {
      case event: CellChanged =>
        println("cell-changed:" + event)
        out ! Json.obj("event" -> "cell-changed").toString()
      case event: PlayerChanged =>
        println("player-changed:" + event)
        if (isFirst){
          out ! Json.obj("event" -> "start-game").toString()
          if (!isLast)
            isFirst = false
          else
            isLast = false
        } else {
          out ! Json.obj("event" -> "player-changed").toString()
        }
      case other => println("Unmanaged event: " + other.getClass.getName)
    }

    override def receive: Receive = {
      case "Connect to Server" =>
        println("New client is connected")
      case x: String if x.nonEmpty =>
        println("eingabe: " + x)
        if (gameController.getGameState == GameState.IDLE) {
          val eingabe = x.split(" ");
          idle(eingabe(0) + " " + eingabe(1))
        } else if (gameController.getGameState == GameState.SHIPSETTING) {
          val eingabe = x.split(" ");
          setShip(eingabe(0) + " " + eingabe(1) + " " + eingabe(2) + " " + eingabe(3))
        } else if (gameController.getGameState == GameState.PLAYERSETTING){
          if (gameController.getPlayerState == PlayerState.PLAYER_ONE){
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
