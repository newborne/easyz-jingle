package cn.easyz.common.model.repository;

import cn.easyz.common.model.pojo.neo4j.node.ArticleTypeNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Article type repository.
 */
@Repository
public interface ArticleTypeRepository extends Neo4jRepository<ArticleTypeNode, Long> {
}
