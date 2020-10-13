package playground.gql.entity

/**
 *
 */
case class Article(id: Option[Int], title: String, authorId: Option[Int], tagIds: Seq[Int]) extends ID {
  override def withId(newId: Option[Int]): Article = Article(newId, title, authorId, tagIds)
}
