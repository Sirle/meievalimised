package com.uvalimised.data;

public class Area implements java.io.Serializable{

	private static final long serialVersionUID = 3236806654582706267L;
	private Long areaId;
	private String areaName;
	
	public Area (String areaName){
		this.areaName = areaName;
		
	}
	public Area(){}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	
	
}