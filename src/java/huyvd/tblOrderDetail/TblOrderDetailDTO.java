/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblOrderDetail;

import java.io.Serializable;

/**
 *
 * @author BlankSpace
 */
public class TblOrderDetailDTO implements Serializable{
    private String orderId;
    private int itemId;
    private int quantity;
    private double total;

    public TblOrderDetailDTO(String orderId, int itemId, int quantity, double total) {
	this.orderId = orderId;
	this.itemId = itemId;
	this.quantity = quantity;
	this.total = total;
    }

    /**
     * @return the orderId
     */
    public String getOrderId() {
	return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
	this.orderId = orderId;
    }

    /**
     * @return the itemId
     */
    public int getItemId() {
	return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(int itemId) {
	this.itemId = itemId;
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
     * @return the total
     */
    public double getTotal() {
	return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
	this.total = total;
    }
    
    
}
