package controllers

import Battleship._
import Battleship.controller.ControllerBaseImpl
import javax.inject._
import play.api.mvc._;

@Singleton
class BattleshipController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val gameController = Game.controller;

  def battleship = Action {
    Ok(battleshipAsText)
  }

  def about = Action {
    Ok(views.html.index())
  }

  def battleshipAsText = gameController.getGridPlayer1 + ControllerBaseImpl.GameState.message(gameController.getGameState)
}
