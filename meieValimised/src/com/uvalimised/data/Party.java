package com.uvalimised.data;

public class Party implements java.io.Serializable{

	private static final long serialVersionUID = 3236806654582706267L;
	private Long partyId;
	private String partyName;
	
	public Party (String partyName){
		this.partyName = partyName;
		
	}
	public Party(){}
	
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	
}