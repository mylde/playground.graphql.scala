package playground.gql.repo

import playground.gql.Db
import playground.gql.entity.{ID, Tag}

/**
 *
 */
class TagRepository extends GenId {
  override protected def table: Seq[ID] = all
  def all(): Seq[Tag] = Db.tags.toSeq
  def findById(id: Int): Option[Tag] = Db.tags.find(_.id.get == id)
  def findByIds(ids: Seq[Int]): Seq[Tag] = Db.tags.filter(tag => ids.contains(tag.id.get)).toSeq
  def insert(tag: Tag): Tag = {
    val newTag = tag.withId(genId)
    Db.tags += newTag
    Db.tags.sortBy(_.id)
    newTag
  }
  def update(tag: Tag): Tag = {
    val idx = Db.authors.indexWhere(_.id == tag.id)
    if (idx > -1) {
      Db.tags(idx) = tag
      tag
    } else {
      insert(tag)
    }
  }
}
