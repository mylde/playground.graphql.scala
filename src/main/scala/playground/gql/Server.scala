package playground.gql

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.MediaTypes.`text/html`
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives.{_enhanceRouteWithConcatenation, as, complete, entity, get, getFromResource, path, post, redirect, _}
import akka.http.scaladsl.server._
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport._
import io.circe.Json
import io.circe.optics.JsonPath.root
import playground.gql.model.GraphQLRequestUnmarshaller._
import playground.gql.repo.Container
import playground.gql.schema.Article
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.marshalling.circe._
import sangria.parser.QueryParser

import scala.util.{Failure, Success}

/**
 *
 */
object Server extends App {
  implicit val system: ActorSystem = ActorSystem("sangria-playground-server")
  import system.dispatcher

  val route: Route =
    path("graphql") {
      get {
        explicitlyAccepts(`text/html`) {
          getFromResource("assets/playground.html")
        }
      } ~
      post {
        entity(as[Json]) { body => {
          val json = root.query.string.getOption(body)
          val operationName = root.operationName.string.getOption(body)
          val variables: Json = root.variables.json.getOption(body).getOrElse(Json.obj())

          json.map(QueryParser.parse(_)) match {
            case Some(Success(doc)) =>
              complete(m = Executor.execute(Article.schema, doc, new Container,
                variables = variables,
                operationName = operationName,
                deferredResolver = Article.deferredResolver
              )
                .map(OK -> _)
                .recover {
                  case error: QueryAnalysisError => BadRequest -> error.resolveError
                  case error: ErrorWithResolver => InternalServerError -> error.resolveError
                })
            case Some(Failure(_)) => complete("failure")
            case None => complete("none")
          }
        }}
      }
    } ~
    path("shutdown") {
      Http().shutdownAllConnectionPools() andThen { case _ => system.terminate() }
      complete("Shutting down app")
    } ~
    path("") {
      redirect("/graphql", PermanentRedirect)
    }

  Http().newServerAt("0.0.0.0", 8080).bind(route)
}