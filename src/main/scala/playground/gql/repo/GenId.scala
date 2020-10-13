package playground.gql.repo

import playground.gql.entity.ID

/**
 *
 */
trait GenId {
  protected def table: Seq[ID]
  def genId: Option[Int] = {
    if (table.isEmpty) {
      Some(1)
    } else {
      Some(table.last.id.get + 1)
    }
  }
}
