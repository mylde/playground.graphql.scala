package playground.gql

import playground.gql.entity.{Article, Author, Tag}

import scala.collection.mutable

/**
 *
 */
object Db {
  val articles: mutable.ArrayBuffer[Article] = mutable.ArrayBuffer(
    Article(Some(1), "Scala", Some(1), scala.collection.immutable.Seq(1)),
    Article(Some(2), "GraphQL", Some(2), scala.collection.immutable.Seq(2)),
    Article(Some(3), "Sangria", Some(1), scala.collection.immutable.Seq(1,3))
  )
  val authors: mutable.ArrayBuffer[Author] = mutable.ArrayBuffer(
    Author(Some(1), "John"),
    Author(Some(2), "Michele")
  )
  val tags: mutable.ArrayBuffer[Tag] = mutable.ArrayBuffer(
    Tag(Some(1), "Programming"),
    Tag(Some(2), "Spec"),
    Tag(Some(3), "Implementation")
  )
}
