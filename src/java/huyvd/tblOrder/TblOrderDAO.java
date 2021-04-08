/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblOrder;

import huyvd.util.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author BlankSpace
 */
public class TblOrderDAO implements Serializable {

    private List<TblOrderDTO> ListOrder;

    public List<TblOrderDTO> getListOrder() {
	return ListOrder;
    }

    public boolean createOrder(String cardId, String orderDate, double totalPayment, String userId, String paymentMethod)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "insert tblOrder(orderId, orderDate, total, userId, methodId) "
			+ "values(?, ?, ?, ?, ?)";
		pst = cn.prepareStatement(sql);
		pst.setString(1, cardId);
		pst.setString(2, orderDate);
		pst.setDouble(3, totalPayment);
		pst.setString(4, userId);
		pst.setString(5, paymentMethod);
		int row = pst.executeUpdate();

		if (row > 0) {
		    return true;
		}
	    }
	    //end if connection
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
	return false;
    }

    public void getOrderByDate(String orderDate, String curUserId, boolean role)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		boolean isAdmin = role;

		String sql = "SELECT orderId, orderDate, total, userId, methodName "
			+ "FROM tblOrder o join tblPaymentMethod p on o.methodId = p.methodId "
			+ "WHERE orderDate between ? and ? ";
		
		if (!isAdmin) {
		    sql += "AND userId = '" + curUserId + "' ";
		}//admin can see all order
		
		pst = cn.prepareStatement(sql);
		pst.setString(1, orderDate);
		pst.setString(2, orderDate + " 23:59:59");

		rs = pst.executeQuery();
		while (rs.next()) {
		    String orderId = rs.getString("orderId");
		    Date orderDateData = rs.getDate("orderDate");
		    double total = rs.getDouble("total");
		    String userId = rs.getString("userId");
		    String methodId = rs.getString("methodName");
		    TblOrderDTO order = new TblOrderDTO(orderId, orderDateData, total, userId, methodId);
		    
		    if (this.ListOrder == null) {
			this.ListOrder = new ArrayList<>();
		    }
		    
		    this.ListOrder.add(order);
		}
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
    }
}
