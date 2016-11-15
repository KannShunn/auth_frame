package com.cesgroup.imp.wotalk.entity;

public class OldOrg {

	private Integer organizeId;
	
	private Integer parentId;
	
	private String organizeName;
	
	private String organizeTypeName;

	private Integer showOrder;

	public Integer getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(Integer organizeId) {
		this.organizeId = organizeId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getOrganizeName() {
		return organizeName;
	}

	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}

	public String getOrganizeTypeName() {
		return organizeTypeName;
	}

	public void setOrganizeTypeName(String organizeTypeName) {
		this.organizeTypeName = organizeTypeName;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}
	
	
}
