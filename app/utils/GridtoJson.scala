package utils

import play.api.libs.json.{JsNumber, JsValue, Json, Writes}

class GridtoJson {

  def save(grid: InterfaceGrid, grid2: InterfaceGrid, shipSetting: Array[Int], shipSetting2: Array[Int], gameState: GameState, playerState: PlayerState, player: InterfacePerson, player2: InterfacePerson): String = {
    (Json.prettyPrint(getAllObj(grid, grid2, shipSetting, shipSetting2, gameState, playerState, player, player2)))
  }

  def getAllObj(grid: InterfaceGrid, grid2: InterfaceGrid, shipSetting: Array[Int], shipSetting2: Array[Int], gameStateX: GameState, playerStateX: PlayerState, player: InterfacePerson, player2x: InterfacePerson): JsValue = {
    val array: Array[Array[Int]] = Array(shipSetting, shipSetting2)
    val players: Array[InterfacePerson] = Array(player, player2x)
    Json.toJson(List(grid1ToJson(grid), grid2ToJson(grid2), arrayToJson.writes(array), gameState.writes(gameStateX), playerState.writes(playerStateX), playerToJson.writes(players)))
  }

  implicit val arrayToJson = new Writes[Array[Array[Int]]] {
    override def writes(array: Array[Array[Int]]): JsValue = Json.obj(
      "arraysInt" -> Json.obj(
        "shipSetting" -> Json.toJson(array(0)),
        "shipSetting2" -> Json.toJson(array(1))
      )
    )
  }

  implicit val gameState = new Writes[GameState] {
    def writes(gameState: GameState): JsValue = Json.obj(
      "gameState" -> gameState
    )
  }

  implicit val playerState = new Writes[PlayerState] {
    def writes(playerState: PlayerState): JsValue = Json.obj(
      "playerState" -> playerState
    )
  }

  implicit val grid2 = new Writes[InterfaceGrid] {
    def writes(grid2: InterfaceGrid): JsValue = grid2ToJson(grid2)
  }


  def grid1ToJson(grid: InterfaceGrid) = {
    val gridSize = grid.getSize
    Json.obj(
      "grid1" -> Json.obj(
        "size" -> JsNumber(gridSize),
        "cells" -> Json.toJson(
          for {
            row <- 0 until gridSize
            col <- 0 until gridSize
          } yield {
            Json.obj(
              "row" -> row,
              "col" -> col,
              "valueY" -> grid.getValue(row, col)
            )
          }
        )
      )
    )
  }

  def grid2ToJson(grid: InterfaceGrid) = {
    val gridSize = 10
    Json.obj(
      "grid2" -> Json.obj(
        "size" -> JsNumber(gridSize),
        "cells" -> Json.toJson(
          for {
            row <- 0 until gridSize
            col <- 0 until gridSize
          } yield {
            Json.obj(
              "row" -> row,
              "col" -> col,
              "valueY" -> grid.getValue(row, col)
            )
          }
        )
      )
    )
  }

  implicit val playerToJson = new Writes[Array[InterfacePerson]] {
    override def writes(player: Array[InterfacePerson]): JsValue = Json.obj(
      "players" -> Json.obj(
        "player1" -> Json.toJson(player(0).toString),
        "player2" -> Json.toJson(player(1).toString)
      )
    )
  }

}
