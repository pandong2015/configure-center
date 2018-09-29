package tecp.pcloud.configure.center.core.model.po;

import tecp.pcloud.configure.center.core.model.NameProperty;
import tecp.pcloud.configure.center.core.model.PKProperty;
import tecp.pcloud.configure.center.core.model.StatusProperty;
import tecp.pcloud.configure.center.core.model.TimeProperty;

public interface DefaultProperties<T> extends PKProperty<T>,NameProperty, TimeProperty, StatusProperty {
}
