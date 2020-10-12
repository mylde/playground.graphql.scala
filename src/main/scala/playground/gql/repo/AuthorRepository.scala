package playground.gql.repo

import playground.gql.Db
import playground.gql.entity.Author

/**
 *
 */
class AuthorRepository {
  def findById(id: Int): Option[Author] = Db.authors.find(_.id == id)
  def findByIds(ids: Seq[Int]) : Seq[Author] = Db.authors.filter(author => ids.contains(author.id)).toSeq
}
