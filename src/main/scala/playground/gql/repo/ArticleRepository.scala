package playground.gql.repo

import playground.gql.Db
import playground.gql.entity.Article

/**
 *
 */
class ArticleRepository {
  def findById(id: Int): Option[Article] = Db.articles.find(_.id == id)
  def all(): Seq[Article] = Db.articles.toSeq
}
