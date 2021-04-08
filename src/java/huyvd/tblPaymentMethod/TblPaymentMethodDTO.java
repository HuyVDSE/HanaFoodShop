/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblPaymentMethod;

import java.io.Serializable;

/**
 *
 * @author BlankSpace
 */
public class TblPaymentMethodDTO implements Serializable{
    private String methodId;
    private String methodName;

    public TblPaymentMethodDTO(String methodId, String methodName) {
	this.methodId = methodId;
	this.methodName = methodName;
    }

    /**
     * @return the methodId
     */
    public String getMethodId() {
	return methodId;
    }

    /**
     * @param methodId the methodId to set
     */
    public void setMethodId(String methodId) {
	this.methodId = methodId;
    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
	return methodName;
    }

    /**
     * @param methodName the methodName to set
     */
    public void setMethodName(String methodName) {
	this.methodName = methodName;
    }
    
    
}
