package playground.gql.entity

/**
 *
 */
trait ID {
  val id: Option[Int]
  def withId(id: Option[Int]): ID
}
