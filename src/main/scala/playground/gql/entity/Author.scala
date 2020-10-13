package playground.gql.entity

/**
 *
 */
case class Author(id: Option[Int], name: String) extends ID {
  override def withId(newId: Option[Int]): Author = Author(newId, name)
}
