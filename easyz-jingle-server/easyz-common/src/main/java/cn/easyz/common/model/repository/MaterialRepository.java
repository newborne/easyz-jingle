package cn.easyz.common.model.repository;

import cn.easyz.common.model.pojo.neo4j.node.MaterialNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The interface Material repository.
 */
@Repository
public interface MaterialRepository extends Neo4jRepository<MaterialNode, Long> {
    /**
     * Find by material rid material node.
     *
     * @param materialRid the material rid
     * @return the material node
     */
    MaterialNode findByMaterialRid(@Param("material_rid") Long materialRid);
}
