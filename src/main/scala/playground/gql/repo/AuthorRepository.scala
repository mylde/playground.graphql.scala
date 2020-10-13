package playground.gql.repo

import playground.gql.Db
import playground.gql.entity.{Author, ID}

/**
 *
 */
class AuthorRepository extends GenId {
  override protected def table: Seq[ID] = all
  def all(): Seq[Author] = Db.authors.toSeq
  def findById(id: Int): Option[Author] = Db.authors.find(_.id.get == id)
  def findByIds(ids: Seq[Int]): Seq[Author] = Db.authors.filter(author => ids.contains(author.id.get)).toSeq
  def insert(author: Author): Author = {
    val newAuthor = author.withId(genId)
    Db.authors += newAuthor
    Db.authors.sortBy(_.id)
    newAuthor
  }
  def update(author: Author): Author = {
    val idx = Db.authors.indexWhere(_.id == author.id)
    if (idx > -1) {
      Db.authors(idx) = author
      author
    } else {
      insert(author)
    }
  }
}
