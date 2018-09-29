/**
 *
 */
package tecp.pcloud.configure.center.core.model.vo.request.parameter;

import tecp.pcloud.configure.center.core.model.SearchParameters;

/**
 * @author pandong
 * @ClassName DataTableSearch
 * @Date 2018年9月19日 下午12:35:34
 */
public class StatusSearchParameters implements SearchParameters {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
