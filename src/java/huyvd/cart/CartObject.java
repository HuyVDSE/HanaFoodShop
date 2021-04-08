/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.cart;

import huyvd.tblProduct.TblProductDTO;
import huyvd.tblProduct.TblproductDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.naming.NamingException;

/**
 *
 * @author BlankSpace
 */
public class CartObject implements Serializable{
    private List<CartItem> itemList;
    private int totalItems;

    public List<CartItem> getItemList() {
	return itemList;
    }

    public int getTotalItems() {
	totalItems = itemList.size();
	return totalItems;
    }
 
    public void addFoodToCart(String itemId) throws SQLException, NamingException{
	//1. check itemList
	if (this.itemList == null) {
	    this.itemList = new ArrayList<>();
	}//end if item list is exist
	
	//2.check exist item in list
	int quantity = 1;//set default quantity of new Item in cart
	
	CartItem item = searchItemInCart(itemId);
	
	if (item == null) {
	    TblproductDAO dao = new TblproductDAO();
	    TblProductDTO dto = dao.getFood(itemId);
	    item = new CartItem(dto, quantity);
	    itemList.add(item);
	} else {
	    quantity = item.getQuantity() + 1;
	    item.setQuantity(quantity);
	    item.updateTotal();
	}
    }
    
    private CartItem searchItemInCart(String itemId) {
	for (CartItem cartItem : itemList) {
	    if (cartItem.getFood().getItemId().equals(itemId)) {
		return cartItem;
	    }
	}
	return null;
    }
    
    public double getTotalPriceOfCart() {
	double totalPrice = 0;
	for (CartItem cartItem : itemList) {
	    totalPrice += cartItem.getTotal();
	}
	return totalPrice;
    }
    
    public void updateQuantityOfItem(String itemId, int quantity) {
	CartItem updateItem = searchItemInCart(itemId);
	updateItem.setQuantity(quantity);
	updateItem.updateTotal();
    }
    
    public void removeItem(String itemId) {
	if (itemList == null) {
	    return;
	}
	
	CartItem removedItem = searchItemInCart(itemId);
	if (removedItem != null) {
	    itemList.remove(removedItem);
	    
	    if (itemList.isEmpty()) {
		itemList = null;
	    }//remove cart if item list is empty
	}
    }
    
    //create cart id with format ddmmyy-hhmmss-xxxxxx(x is random digit)
    public String generateCartId() {
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy-hhmmss");
	Date currentDate = new Date();

	String datePart = sdf.format(currentDate);
	String randomPart = "";

	for (int i = 0; i < 6; i++) {
	    int randomNum = new Random().nextInt(10);
	    randomPart += randomNum;
	}

	return datePart + "-" + randomPart;
    }
}
