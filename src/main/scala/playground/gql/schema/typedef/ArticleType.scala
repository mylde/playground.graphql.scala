package playground.gql.schema.typedef

import io.circe.{Decoder, HCursor}
import playground.gql.entity.Article
import playground.gql.repo.Container
import sangria.marshalling.circe.CirceResultMarshaller
import sangria.marshalling.{FromInput, ResultMarshaller}
import sangria.schema.{Argument, Field, InputField, InputObjectType, IntType, ListInputType, ListType, ObjectType, OptionInputType, OptionType, StringType, fields}
import sangria.util.tag.@@

/**
 *
 */
object ArticleType {
  val objectType: ObjectType[Container, Article] = ObjectType(
    "Article",
    "Article written by an author",
    fields[Container, Article](
      Field("id", OptionType(IntType), Some("Article id"), resolve = _.value.id),
      Field("title", StringType, Some("Article title"), resolve = _.value.title),
      Field("author_id", OptionType(IntType), Some("Article author id"), resolve = _.value.authorId),
      Field("tag_ids", ListType(IntType), Some("Article tags"), resolve = _.value.tagIds),
      Field("author", OptionType(AuthorType.objectType), Some("Author"),
        resolve = c => AuthorType.fetcher.deferOpt(c.value.authorId)
      ),
      Field("tags", ListType(TagType.objectType), Some("Tag list"),
        resolve = c => TagType.fetcher.deferSeq(c.value.tagIds)
      )
    )
  )
  val queryArgId: Argument[Int] = Argument("id", IntType, description = "id arg")
  val queryArgIdList: Argument[Seq[Int @@ FromInput.CoercedScalaResult]] = Argument("ids", ListInputType(IntType))
  //  val decoder: Decoder[Article] = deriveDecoder
  val decoder: Decoder[Article] = (c: HCursor) => {
    for {
      id <- c.downField("id").as[Option[Int]]
      title <- c.downField("title").as[String]
      authorId <- c.downField("author_id").as[Option[Int]]
      tagIds <- c.downField("tag_ids").as[Seq[Int]]
    } yield Article(id, title, authorId, tagIds)
  }
  val articleInputType: InputObjectType[Article] = InputObjectType[Article]("ArticleInput", List(
    InputField("id", OptionInputType(IntType)),
    InputField("title", StringType),
    InputField("author_id", OptionInputType(IntType)),
    InputField("tag_ids", ListInputType(IntType))
  ))
  //  val articleInputType: InputObjectType[Article] = deriveInputObjectType[Article](
  //    InputObjectTypeName("ArticleInput")
  //  )
  implicit val articleInput: FromInput[Article] = new FromInput[Article] {
    override val marshaller: ResultMarshaller = CirceResultMarshaller
    override def fromResult(node: marshaller.Node): Article =
      decoder.decodeJson(node.asInstanceOf[CirceResultMarshaller.Node]) match {
        case Right(article: Article) => article
        case Left(e) => throw e
      }
  }
  val mutationArgNew: Argument[Article] = Argument("article_new", articleInputType)
}
