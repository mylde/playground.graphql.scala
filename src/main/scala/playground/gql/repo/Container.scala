package playground.gql.repo

/**
 *
 */
class Container {
  val articles = new ArticleRepository
  val authors = new AuthorRepository
  val tags = new TagRepository
}
