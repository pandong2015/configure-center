/**
 * 
 */
package tecp.pcloud.configure.center.core.model.vo.response;

import tecp.pcloud.configure.center.core.model.PageInfo;
import tecp.pcloud.configure.center.core.model.SearchParameters;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;

import java.util.List;

/**
 * @ClassName PageSearchResponse
 * @author pandong
 * @Date 2018年9月21日 上午9:58:09
 */
public class PageSearchResponse<T extends SearchParameters, E> {
	private PageInfo page;
	private T parameters;
	private List<E> list;
	private int totalSize;

	public int getPageSize() {
		if (getPage() == null) {
			return 0;
		}
		return getPage().getPageSize();
	}

	public int getCurrentPage() {
		if (getPage() == null) {
			return 0;
		}
		return getPage().getPageNo();
	}

	public void setRequest(SearchPeametersRequest<T> request) {
		this.page = request.getPage();
		this.parameters = request.getParameters();
	}

	public PageInfo getPage() {
		return page;
	}

	public void setPage(PageInfo page) {
		this.page = page;
	}

	public T getParameters() {
		return parameters;
	}

	public void setParameters(T parameters) {
		this.parameters = parameters;
	}

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
}
