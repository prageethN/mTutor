package com.mtutor.publication;

public class ItemDetails {

	public String getTextHeader() {
		return textHeader;
	}

	public void setTextHeader(String txtHeader) {
		this.textHeader = txtHeader;
	}

	public String getTextNormal() {
		return textNormal;
	}

	public void setTextNormal(String txtNormal) {
		this.textNormal = txtNormal;
	}

	public String getTextFooter() {
		return textFooter;
	}

	public void setTextFooter(String txtExtra) {
		this.textFooter = txtExtra;
	}

	public String getTextExtra() {
		return txtExtra;
	}

	public void setTextExtra(String txtExtra) {
		this.txtExtra = txtExtra;
	}

	public String getItemId() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	private String itemID;
	private String textHeader;
	private String textNormal;
	private String textFooter;
	private String txtExtra;

}
