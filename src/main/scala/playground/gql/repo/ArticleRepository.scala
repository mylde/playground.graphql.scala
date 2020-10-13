package playground.gql.repo

import playground.gql.Db
import playground.gql.entity.{Article, ID}

/**
 *
 */
class ArticleRepository extends GenId {
  override protected def table: Seq[ID] = all
  def all(): Seq[Article] = Db.articles.toSeq
  def findById(id: Int): Option[Article] = Db.articles.find(_.id.get == id)
  def findByIds(ids: Seq[Int]): Seq[Article] = Db.articles.filter(article => ids.contains(article.id.get)).toSeq
  def insert(article: Article): Article = {
    val newArticle = article.withId(genId)
    Db.articles += newArticle
    Db.articles.sortBy(_.id)
    newArticle
  }
  def update(article: Article): Article = {
    val idx = Db.articles.indexWhere(_.id == article.id)
    if (idx > -1) {
      Db.articles(idx) = article
      article
    } else {
      insert(article)
    }
  }
}
