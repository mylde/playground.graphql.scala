package playground.gql.entity

import playground.gql.repo.{AuthorRepository, Container}
import sangria.execution.deferred.{Fetcher, HasId}
import sangria.schema.{Argument, Field, IntType, ObjectType, StringType, fields}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 *
 */
case class Author(id: Int, name: String)

/**
 *
 */
object Author {
  // implicit
  val hasId: HasId[Author, Int] = HasId[Author, Int](_.id)

  val objectType: ObjectType[AuthorRepository, Author] = ObjectType(
    "Author",
    "An Author",
    fields[AuthorRepository, Author](
      Field("id", IntType, Some("Author id"), resolve = _.value.id),
      Field("name", StringType, Some("Author name"), resolve = _.value.name)
    )
  )

  val fetcher: Fetcher[Container, Author, Author, Int] =
    Fetcher((ctx: Container, ids: Seq[Int]) => Future(ctx.authors.findByIds(ids)))(hasId)

  val arg: Argument[Int] = Argument("id", IntType, description = "id arg")
}
