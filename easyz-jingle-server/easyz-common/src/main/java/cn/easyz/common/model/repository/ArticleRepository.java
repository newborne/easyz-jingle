package cn.easyz.common.model.repository;

import cn.easyz.common.model.pojo.neo4j.node.ArticleNode;
import feign.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Article repository.
 */
@Repository
public interface ArticleRepository extends Neo4jRepository<ArticleNode, Long> {
    /**
     * Find by article rid article node.
     *
     * @param articleRid the article rid
     * @return the article node
     */
    ArticleNode findByArticleRid(@Param("article_rid") Long articleRid);
}
