package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class BattleshipController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  // val.gameController = Battleship.controller
  //def battleshipAsText = gamecontroller.gridToString + GameStatus.message(gameController.gameStatus)

  def about = Action {
    Ok(views.html.index())
  }

  //def battleship = Action {
  //Ok(battleshipAsText)
  //}
}
