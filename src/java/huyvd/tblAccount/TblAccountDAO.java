/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblAccount;

import huyvd.util.HashUtils;
import huyvd.util.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author BlankSpace
 */
public class TblAccountDAO implements Serializable {

    public TblAccountDTO checkLogin(String userId, String password)
	    throws SQLException, NamingException {
	TblAccountDTO dto = null;
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT userId, password, fullname, role "
			+ "FROM tblAccount "
			+ "WHERE userId=? AND password=? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, userId);
		pst.setString(2, password);

		rs = pst.executeQuery();

		if (rs.next()) {
		    String curUserId = rs.getString("userId");
		    String curPassword = rs.getString("password");
		    String fullname = rs.getString("fullname");
		    boolean isAdmin = rs.getBoolean("role");
		    if (curUserId.equals(userId) && curPassword.equals(password)) {
			dto = new TblAccountDTO(curUserId, curPassword, fullname, isAdmin);
		    }
		}//end if rs
	    }//end if connection
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
	return dto;
    }

    public TblAccountDTO checkGoogleAccountExist(String email) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT userId, password, fullname, role "
			+ "FROM tblAccount "
			+ "WHERE userId=? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, email);

		rs = pst.executeQuery();

		if (rs.next()) {
		    String curUserId = rs.getString("userId");
		    String password = rs.getString("password");
		    String fullname = rs.getString("fullname");
		    boolean isAdmin = rs.getBoolean("role");
		    if (curUserId.equals(email)) {
			return new TblAccountDTO(curUserId, password, fullname, isAdmin);
		    }
		}//end if rs
	    }//end if connection
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
	return null;
    }

    public void insertGoogleAccount(String email, String fullname)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "INSERT tblAccount(userId, password, fullname, role) "
			+ "VALUES(?,?,?,?)";
		pst = cn.prepareStatement(sql);
		pst.setString(1, email);
		String password = HashUtils.createHashString(email);
		pst.setString(2, password);
		pst.setString(3, fullname);
		pst.setBoolean(4, false);
		pst.executeUpdate();
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
    }
}
