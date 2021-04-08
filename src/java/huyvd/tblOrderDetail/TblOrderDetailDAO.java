/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblOrderDetail;

import huyvd.cart.CartItem;
import huyvd.util.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
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
public class TblOrderDetailDAO implements Serializable {

    public boolean createOrder(List<CartItem> itemList, String orderID) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = MyConnection.makeConnection();
	    if (con != null) {
		String sql = "INSERT INTO tblOrderDetail(orderId, itemId, quantity, total) "
			+ "VALUES(?,?,?,?)";

		pst = con.prepareStatement(sql);

		// Set auto-commit to false
		con.setAutoCommit(false);

		int totalRow = 0;
		for (CartItem cartItem : itemList) {
		    pst.setString(1, orderID);
		    pst.setString(2, cartItem.getFood().getItemId());
		    pst.setInt(3, cartItem.getQuantity());
		    pst.setDouble(4, cartItem.getTotal());
		    pst.addBatch();
		    totalRow++;
		}

		int[] result = pst.executeBatch();
		con.commit();

		if (result.length == totalRow) {
		    return true;
		}
	    }
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return false;
    }

    public List<ItemDTO> getOrderDetail(String orderId)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<ItemDTO> listOrderDetail = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT p.name, p.price, od.quantity, od.total "
			+ "FROM tblOrderDetail od join tblProduct p on od.itemId = p.itemId "
			+ "WHERE orderId = ? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, orderId);
		rs = pst.executeQuery();
		while (rs.next()) {
		    String name = rs.getString("name");
		    double price = rs.getDouble("price");
		    int quantity = rs.getInt("quantity");
		    double total = rs.getDouble("total");
		    ItemDTO item = new ItemDTO(name, price, quantity, total);

		    if (listOrderDetail == null) {
			listOrderDetail = new ArrayList<>();
		    }

		    listOrderDetail.add(item);
		}
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
	return listOrderDetail;
    }
}
