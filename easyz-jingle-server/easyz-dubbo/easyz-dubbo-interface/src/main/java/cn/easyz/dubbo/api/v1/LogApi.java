package cn.easyz.dubbo.api.v1;

/**
 * The interface Log api.
 */
public interface LogApi {
    /**
     * Save.
     *
     * @param id         the id
     * @param requestURI the request uri
     * @param toString   the to string
     */
    void save(Long id, String requestURI, String toString);
}
