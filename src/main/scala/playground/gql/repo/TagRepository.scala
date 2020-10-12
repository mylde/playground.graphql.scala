package playground.gql.repo

import playground.gql.Db
import playground.gql.entity.Tag

/**
 *
 */
class TagRepository {
  def findByIds(ids: Seq[Int]): Seq[Tag] = Db.tags.filter(tag => ids.contains(tag.id)).toSeq
}
