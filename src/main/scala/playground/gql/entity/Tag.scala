package playground.gql.entity

/**
 *
 */
case class Tag(id: Option[Int], name: String) extends ID {
  override def withId(newId: Option[Int]): Tag = Tag(newId, name)
}

