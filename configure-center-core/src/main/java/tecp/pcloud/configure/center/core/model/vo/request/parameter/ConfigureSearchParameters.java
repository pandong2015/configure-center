/**
 * 
 */
package tecp.pcloud.configure.center.core.model.vo.request.parameter;

import tecp.pcloud.configure.center.core.model.SearchParameters;

import java.util.List;

/**
 * @ClassName DataTableSearch
 * @author pandong
 * @Date 2018年9月19日 下午12:35:34
 */
public class ConfigureSearchParameters implements SearchParameters {
	private List<Long> serviceIds;
	private List<Long> profileIds;
	private String name;
	private String status;
	private String type;

	public List<Long> getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(List<Long> serviceIds) {
		this.serviceIds = serviceIds;
	}

	public List<Long> getProfileIds() {
		return profileIds;
	}

	public void setProfileIds(List<Long> profileIds) {
		this.profileIds = profileIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
