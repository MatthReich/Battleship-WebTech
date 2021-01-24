package controllers

import Battleship._
import controller.ControllerBaseImpl.{ CellChanged, GameState, PlayerChanged, PlayerState }
import controller.InterfaceController
import akka.actor.{ ActorSystem, _ }
import akka.stream.Materializer
import com.mohiva.play.silhouette.api.Silhouette

import javax.inject.{ Inject, Singleton }
import play.api.libs.json.{ JsValue, Json }
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import utils.GridtoJson
import com.mohiva.play.silhouette.api.actions.SecuredRequest
import com.mohiva.play.silhouette.impl.providers.GoogleTotpInfo
import net.codingwell.scalaguice.ScalaModule
import utils.auth.DefaultEnv

import scala.concurrent.ExecutionContext
import scala.swing.Reactor

@Singleton
class BattleshipController @Inject() (
  scc: SilhouetteControllerComponents,
  about: views.html.about,
  silhouette: Silhouette[DefaultEnv]
)(implicit ex: ExecutionContext, system: ActorSystem, mat: Materializer) extends SilhouetteController(scc) {
  var gameController = Game.controller

  def aboutpage = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    authInfoRepository.find[GoogleTotpInfo](request.identity.loginInfo).map { totpInfoOpt =>
      Ok(about(request.identity, totpInfoOpt))
    }
  }

}
