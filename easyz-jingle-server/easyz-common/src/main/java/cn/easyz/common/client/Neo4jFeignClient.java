package cn.easyz.common.client;

import cn.easyz.common.model.pojo.neo4j.node.ArticleNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The interface Neo 4 j feign client.
 */
@FeignClient(value = "easyz-neo4j")
@Repository
public interface Neo4jFeignClient {
    /**
     * Query recommend article list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
    @GetMapping("api/v1/neo4j/queryRecommendArticleList/{userId}")
    List<ArticleNode> queryRecommendArticleList(@PathVariable("userId") Integer userId,
                                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size);
}
