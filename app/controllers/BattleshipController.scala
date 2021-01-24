package controllers

import Battleship._
import Battleship.controller.InterfaceController
import akka.actor.ActorSystem
import akka.stream.Materializer
import com.mohiva.play.silhouette.api.Silhouette
import com.mohiva.play.silhouette.api.actions.SecuredRequest
import com.mohiva.play.silhouette.impl.providers.GoogleTotpInfo
import javax.inject.{Inject, Singleton}
import play.api.mvc._
import utils.auth.DefaultEnv

import scala.concurrent.ExecutionContext

@Singleton
class BattleshipController @Inject() (
  scc: SilhouetteControllerComponents,
  about: views.html.about,
  silhouette: Silhouette[DefaultEnv]
)(implicit ex: ExecutionContext, system: ActorSystem, mat: Materializer) extends SilhouetteController(scc) {
  var gameController: InterfaceController = Game.controller

  def aboutpage = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    authInfoRepository.find[GoogleTotpInfo](request.identity.loginInfo).map { totpInfoOpt =>
      Ok(about(request.identity, totpInfoOpt))
    }
  }

}
