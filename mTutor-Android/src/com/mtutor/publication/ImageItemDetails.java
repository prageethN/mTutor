package com.mtutor.publication;

public class ImageItemDetails {

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

	public int getImageNumber() {
		return imageNumber;
	}

	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}

	public String getTextExtra() {
		return textExtra;
	}

	public void setTextOptional(String textOptional) {
		this.textOptional = textOptional;
	}
	public String getTextOptional() {
		return textOptional;
	}

	public void setTextExtra(String txtExtra) {
		this.textExtra = txtExtra;
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
	private String textExtra;
	private String textOptional;
	private int imageNumber;

}
