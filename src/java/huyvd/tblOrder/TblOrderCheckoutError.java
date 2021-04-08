/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblOrder;

/**
 *
 * @author BlankSpace
 */
public class TblOrderCheckoutError {
    private String buyQuantityError;
    private String availableError;

    /**
     * @return the buyQuantityError
     */
    public String getBuyQuantityError() {
	return buyQuantityError;
    }

    /**
     * @param buyQuantityError the buyQuantityError to set
     */
    public void setBuyQuantityError(String buyQuantityError) {
	this.buyQuantityError = buyQuantityError;
    }

    /**
     * @return the availableError
     */
    public String getAvailableError() {
	return availableError;
    }

    /**
     * @param availableError the availableError to set
     */
    public void setAvailableError(String availableError) {
	this.availableError = availableError;
    }
    
}
