package playground.gql.schema

import playground.gql.entity.{Author, Tag, Article => eArticle}
import playground.gql.repo.Container
import sangria.execution.deferred.DeferredResolver
import sangria.schema.{Field, ListType, ObjectType, OptionType, Schema, fields}

/**
 *
 */
object Article {
  val deferredResolver: DeferredResolver[Container] = DeferredResolver.fetchers(Author.fetcher, Tag.fetcher)

  val queryType: ObjectType[Container, Unit] = ObjectType(
    "Query",
    fields[Container, Unit](
      Field("article", OptionType(eArticle.objectType), arguments = eArticle.arg :: Nil,
        resolve = c => c.ctx.articles.findById(c.arg(eArticle.arg))),

      Field("articles", ListType(eArticle.objectType), resolve = c => c.ctx.articles.all()),

      Field("author", OptionType(Author.objectType), arguments = Author.arg :: Nil,
        resolve = c => c.ctx.authors.findById(c.arg(eArticle.arg))),

      Field("tags", ListType(Tag.objectType), arguments = Tag.arg :: Nil,
        resolve = c => c.ctx.tags.findByIds(c.arg[Seq[Int]]("ids"))
      )
    ))

  val schema: Schema[Container, Unit] = Schema(queryType)
}
