package playground.gql.model

import io.circe.Json
import sangria.parser.SyntaxError

import scala.util.control.NonFatal

/**
 *
 */
object ErrorFormatter {
  def format(error: Throwable): Json = error match {
    case syntaxError: SyntaxError =>
      Json.obj("errors" -> Json.arr(
        Json.obj(
          "message" -> Json.fromString(syntaxError.getMessage),
          "locations" -> Json.arr(Json.obj(
            "line" -> Json.fromBigInt(syntaxError.originalError.position.line),
            "column" -> Json.fromBigInt(syntaxError.originalError.position.column))))))
    case NonFatal(e) =>
      format(e.getMessage)
    case e =>
      throw e
  }
  def format(message: String): Json =
    Json.obj("errors" -> Json.arr(Json.obj("message" -> Json.fromString(message))))
}
