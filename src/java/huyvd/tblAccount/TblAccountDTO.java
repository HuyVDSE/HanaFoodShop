/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblAccount;

import java.io.Serializable;

/**
 *
 * @author BlankSpace
 */
public class TblAccountDTO implements Serializable {

    private String userId;
    private String password;
    private String fullname;
    private boolean isAdmin;

    public TblAccountDTO(String userId, String password, String fullname, boolean isAdmin) {
	this.userId = userId;
	this.password = password;
	this.fullname = fullname;
	this.isAdmin = isAdmin;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
	return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
	this.userId = userId;
    }

    /**
     * @return the password
     */
    public String getPassword() {
	return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @return the isAdmin
     */
    public boolean isIsAdmin() {
	return isAdmin;
    }

    /**
     * @param isAdmin the isAdmin to set
     */
    public void setIsAdmin(boolean isAdmin) {
	this.isAdmin = isAdmin;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
	return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
	this.fullname = fullname;
    }

}
