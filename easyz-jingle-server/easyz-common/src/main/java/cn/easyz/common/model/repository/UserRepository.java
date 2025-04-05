package cn.easyz.common.model.repository;

import cn.easyz.common.model.pojo.neo4j.node.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends Neo4jRepository<UserNode, Long> {
    /**
     * Find by user id user node.
     *
     * @param userId the user id
     * @return the user node
     */
    UserNode findByUserId(@Param("user_id") Integer userId);
}
