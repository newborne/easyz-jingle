package cn.easyz.common.model.repository;

import cn.easyz.common.model.pojo.neo4j.relationship.MaterialRecommendRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Material recommend relationship repository.
 */
@Repository
public interface MaterialRecommendRelationshipRepository extends Neo4jRepository<MaterialRecommendRelationship, Long> {
}
