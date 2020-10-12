package playground.gql.entity

import playground.gql.repo.Container
import sangria.schema.{Argument, Field, IntType, ListType, ObjectType, OptionType, StringType, fields}

/**
 *
 */
case class Article(id: Int, title: String, authorId: Option[Int], tagIds: Seq[Int])

/**
 *
 */
object Article {
  val objectType: ObjectType[Container, Article] = ObjectType(
    "Article",
    "Article written by an author",
    fields[Container, Article](
      Field("id", IntType, Some("Article id"), resolve = _.value.id),
      Field("title", StringType, Some("Article title"), resolve = _.value.title),
      Field("author_id", OptionType(IntType), Some("Article author id"), resolve = _.value.authorId),
      Field("tag_ids", ListType(IntType), Some("Article tags"), resolve = _.value.tagIds),
      Field("author", OptionType(Author.objectType), Some("Author"),
        resolve = c => Author.fetcher.deferOpt(c.value.authorId)
      ),
      Field("tags", ListType(Tag.objectType), Some("Tag list"),
        resolve = c => Tag.fetcher.deferSeq(c.value.tagIds)
      )
    )
  )

  val arg: Argument[Int] = Argument("id", IntType, description = "id arg")
}
