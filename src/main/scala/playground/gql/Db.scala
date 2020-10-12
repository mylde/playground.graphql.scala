package playground.gql

import playground.gql.entity.{Article, Author, Tag}

import scala.collection.mutable

/**
 *
 */
object Db {
  val articles: mutable.Seq[Article] = mutable.Seq(
    Article(1, "Scala", Some(1), scala.collection.immutable.Seq(1)),
    Article(2, "GraphQL", Some(2), scala.collection.immutable.Seq(2)),
    Article(3, "Sangria", Some(1), scala.collection.immutable.Seq(1,3))
  )

  val authors: mutable.Seq[Author] = mutable.Seq(
    Author(1, "John"),
    Author(2, "Michele")
  )

  val tags: mutable.Seq[Tag] = mutable.Seq(
    Tag(1, "Programming"),
    Tag(2, "Spec"),
    Tag(3, "Implementation")
  )
}
