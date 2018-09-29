/**
 * 
 */
package tecp.pcloud.configure.center.core.model.vo.request;

import tecp.pcloud.configure.center.core.model.PageInfo;
import tecp.pcloud.configure.center.core.model.SearchParameters;

/**
 * @ClassName SearchPeametersRequest
 * @author pandong
 * @Date 2018年9月21日 上午9:54:35
 */
public class SearchPeametersRequest<T extends SearchParameters> {
	private T parameters;
	private PageInfo page;

	public T getParameters() {
		return parameters;
	}

	public void setParameters(T parameters) {
		this.parameters = parameters;
	}

	public PageInfo getPage() {
		return page;
	}

	public void setPage(PageInfo page) {
		this.page = page;
	}
}
