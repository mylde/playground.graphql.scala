package playground.gql.schema.typedef

import playground.gql.entity.Author
import playground.gql.repo.{AuthorRepository, Container}
import sangria.execution.deferred.{Fetcher, HasId}
import sangria.marshalling.FromInput
import sangria.schema.{Argument, Field, IntType, ListInputType, ObjectType, OptionType, StringType, fields}
import sangria.util.tag.@@

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 *
 */
object AuthorType {
  // implicit
  val hasId: HasId[Author, Int] = HasId[Author, Int](_.id.get)
  val objectType: ObjectType[AuthorRepository, Author] = ObjectType(
    "Author",
    "An Author",
    fields[AuthorRepository, Author](
      Field("id", OptionType(IntType), Some("Author id"), resolve = _.value.id),
      Field("name", StringType, Some("Author name"), resolve = _.value.name)
    )
  )
  val fetcher: Fetcher[Container, Author, Author, Int] =
    Fetcher((ctx: Container, ids: Seq[Int]) => Future(ctx.authors.findByIds(ids)))(hasId)
  val queryArgId: Argument[Int] = Argument("id", IntType, description = "id arg")
  val queryArgIdList: Argument[Seq[Int @@ FromInput.CoercedScalaResult]] = Argument("ids", ListInputType(IntType))
}
