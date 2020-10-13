package playground.gql.schema

import playground.gql.repo.Container
import playground.gql.schema.typedef.{ArticleType, AuthorType, TagType}
import sangria.execution.deferred.DeferredResolver
import sangria.schema.{Field, ListType, ObjectType, OptionType, Schema, fields}

/**
 *
 */
object Article {
  val deferredResolver: DeferredResolver[Container] = DeferredResolver.fetchers(AuthorType.fetcher, TagType.fetcher)
  val queryType: ObjectType[Container, Unit] = ObjectType(
    "Query",
    fields[Container, Unit](
      Field("article", OptionType(ArticleType.objectType), arguments = ArticleType.queryArgId :: Nil,
        resolve = c => c.ctx.articles.findById(c.arg(ArticleType.queryArgId))),
      Field("articles", ListType(ArticleType.objectType), arguments = ArticleType.queryArgIdList :: Nil,
        resolve = c => {
          val ids: Seq[Int] = c.arg[Seq[Int]]("ids")
          if (ids.isEmpty) {
            c.ctx.articles.all()
          } else {
            c.ctx.articles.findByIds(ids)
          }
        }
      ),
      Field("author", OptionType(AuthorType.objectType), arguments = AuthorType.queryArgId :: Nil,
        resolve = c => c.ctx.authors.findById(c.arg(AuthorType.queryArgId))),
      Field("authors", ListType(AuthorType.objectType), arguments = AuthorType.queryArgIdList :: Nil,
        resolve = c => {
          val ids: Seq[Int] = c.arg[Seq[Int]]("ids")
          if (ids.isEmpty) {
            c.ctx.authors.all()
          } else {
            c.ctx.authors.findByIds(ids)
          }
        }
      ),
      Field("tag", OptionType(TagType.objectType), arguments = TagType.queryArgId :: Nil,
        resolve = c => c.ctx.tags.findById(c.arg(TagType.queryArgId))),
      Field("tags", ListType(TagType.objectType), arguments = TagType.queryArgIdList :: Nil,
        resolve = c => {
          val ids: Seq[Int] = c.arg[Seq[Int]]("ids")
          if (ids.isEmpty) {
            c.ctx.tags.all()
          } else {
            c.ctx.tags.findByIds(ids)
          }
        }
      )
    ))
  val mutationType: ObjectType[Container, Unit] = ObjectType(
    "Mutation",
    fields[Container, Unit](
      Field("new_article", ArticleType.objectType, arguments = ArticleType.mutationArgNew :: Nil,
        resolve = c => c.ctx.articles.insert(c.arg(ArticleType.mutationArgNew))
      ),
      Field("update_article", ArticleType.objectType, arguments = ArticleType.mutationArgNew :: Nil,
        resolve = c => c.ctx.articles.update(c.arg(ArticleType.mutationArgNew))
      )
  ))
  val schema: Schema[Container, Unit] = Schema(queryType, Some(mutationType))
}
