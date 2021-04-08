/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.food;

import huyvd.tblProduct.TblProductDTO;
import java.io.Serializable;

/**
 *
 * @author BlankSpace
 */
public class FoodItem implements Serializable{
    private TblProductDTO food;
    private String categoryName;

    public FoodItem(TblProductDTO food, String categoryName) {
	this.food = food;
	this.categoryName = categoryName;
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
     * @return the categoryName
     */
    public String getCategoryName() {
	return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
    }  
}
