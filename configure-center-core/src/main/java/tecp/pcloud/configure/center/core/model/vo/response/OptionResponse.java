/**
 *
 */
package tecp.pcloud.configure.center.core.model.vo.response;

import java.sql.Timestamp;

/**
 * @author pandong
 * @ClassName ConfiguresResponse
 * @Date 2018年9月19日 下午10:55:13
 */
public class OptionResponse {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
