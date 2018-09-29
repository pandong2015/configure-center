/**
 * 
 */
package tecp.pcloud.configure.center.core.model;

/**
 * @ClassName PageInfo
 * @author pandong
 * @Date 2018年9月21日 上午9:52:36
 */
public class PageInfo {
	private int pageNo;
	private int pageSize;
	
	public int getStart() {
		int start = (pageNo - 1) * pageSize;
		if (start < 0) {
			start = 0;
		}
		return start;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
