/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblProduct;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author BlankSpace
 */
public class TblProductDTO implements Serializable{
    private String itemId;
    private String name;
    private String image;
    private String description;
    private double price;
    private Date createDate;
    private Date updateDate;
    private int quantity;
    private boolean status;
    private int caId;

    public TblProductDTO(String itemId, String name, String image, 
	    String description, double price, Date createDate, 
	    Date updateDate, int quantity, boolean status, int caId) {
	this.itemId = itemId;
	this.name = name;
	this.image = image;
	this.description = description;
	this.price = price;
	this.createDate = createDate;
	this.updateDate = updateDate;
	this.quantity = quantity;
	this.status = status;
	this.caId = caId;
    }

    /**
     * @return the itemId
     */
    public String getItemId() {
	return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(String itemId) {
	this.itemId = itemId;
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the image
     */
    public String getImage() {
	return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
	this.image = image;
    }

    /**
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @return the price
     */
    public double getPrice() {
	return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
	this.price = price;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
	return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
	this.createDate = createDate;
    }

    /**
     * @return the updateDate
     */
    public Date getUpdateDate() {
	return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Date updateDate) {
	this.updateDate = updateDate;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
	return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
	return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
	this.status = status;
    }

    /**
     * @return the caId
     */
    public int getCaId() {
	return caId;
    }

    /**
     * @param caId the caId to set
     */
    public void setCaId(int caId) {
	this.caId = caId;
    }
    
    
}
