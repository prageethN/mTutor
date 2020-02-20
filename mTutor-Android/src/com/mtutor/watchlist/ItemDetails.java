package com.mtutor.watchlist;

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

	public void setTextFooter(String textFooter) {
		this.textFooter = textFooter;
	}
	public String getTextExtra() {
		return txtExtra;
	}

	public void setTextExtra(String txtExtra) {
		this.txtExtra = txtExtra;
	}
	public String getTextOptional() {
		return txtOptinoal;
	}

	public void setTextOptional(String txtOptinoal) {
		this.txtOptinoal = txtOptinoal;
	}
	public String getTextAdditional() {
		return txtAdditional;
	}

	public void setTextAdditional(String txtAdditional) {
		this.txtAdditional = txtAdditional;
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
	private String txtExtra;
	private String txtOptinoal;
	private String txtAdditional;
	private Boolean isNotifiyEnabled;

	private int imageNumber;

}
