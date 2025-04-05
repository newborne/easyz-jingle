package cn.easyz.common.model.repository;

import cn.easyz.common.model.pojo.neo4j.relationship.ArticleRecommendRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Article recommend relationship repository.
 */
@Repository
public interface ArticleRecommendRelationshipRepository extends Neo4jRepository<ArticleRecommendRelationship, Long> {
}
