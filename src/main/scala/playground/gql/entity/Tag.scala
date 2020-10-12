package playground.gql.entity

import playground.gql.repo.{Container, TagRepository}
import sangria.execution.deferred.{Fetcher, HasId}
import sangria.marshalling.FromInput
import sangria.schema.{Argument, Field, IntType, ListInputType, ObjectType, StringType, fields}
import sangria.util.tag.@@

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 *
 */
case class Tag(id: Int, name: String)

/**
 *
 */
object Tag {
  // implicit
  val hasId: HasId[Tag, Int] = HasId[Tag, Int](_.id)

  val objectType: ObjectType[TagRepository, Tag] = ObjectType(
    "Tag",
    "Tag",
    fields[TagRepository, Tag](
      Field("id", IntType, Some("Tag id"), resolve = _.value.id),
      Field("name", StringType, Some("Tag name"), resolve = _.value.name)
    )
  )

  val fetcher: Fetcher[Container, Tag, Tag, Int] =
    Fetcher((ctx: Container, ids: Seq[Int]) => Future(ctx.tags.findByIds(ids)))(hasId)

  val arg: Argument[Seq[Int @@ FromInput.CoercedScalaResult]] = Argument("ids", ListInputType(IntType))
}