package com.mtutor.savedsearch;

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

	public void setTextFooter(String txtFooter) {
		this.textFooter = txtFooter;
	}

	public boolean getNotifyEnbale() {
		return isNotifiyEnabled;
	}

	public void setNotifyEnbale(Boolean condition) {
		this.isNotifiyEnabled = condition;
	}

	public int getImageNumber() {
		return imageNumber;
	}

	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
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
	private Boolean isNotifiyEnabled;
	private int imageNumber;

}
