package uk.co.vurt.hakken.domain.task;

public class PageSelector {

	String pageName;
	String condition;
	
	public PageSelector(){}
	
	public PageSelector(String pageName){
		this();
		this.pageName = pageName;
	}
	
	public PageSelector(String pageName, String condition) {
		this(pageName);
		this.condition = condition;
	}
	
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "PageSelector [pageName=" + pageName + ", condition="
				+ condition + "]";
	}
	
}
