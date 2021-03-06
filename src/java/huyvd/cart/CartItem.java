/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.cart;

import huyvd.tblProduct.TblProductDTO;
import java.io.Serializable;

/**
 *
 * @author BlankSpace
 */
public class CartItem implements Serializable{
    private TblProductDTO food;
    private int quantity;
    private double total;

    public CartItem(TblProductDTO food, int quantity) {
	this.food = food;
	this.quantity = quantity;
	this.total = food.getPrice() * quantity;
    }

    /**
     * @return the food
     */
    public TblProductDTO getFood() {
	return food;
    }

    /**
     * @param food the food to set
     */
    public void setFood(TblProductDTO food) {
	this.food = food;
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
    
    public void updateTotal() {
	this.total = quantity * food.getPrice();
    }
}
