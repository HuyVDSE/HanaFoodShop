/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblProduct;

import huyvd.util.MyConnection;
import huyvd.food.FoodItem;
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
public class TblproductDAO implements Serializable {

    private final String ACTIVE_STATUS = "1";
    private List<FoodItem> listItems;

    public List<FoodItem> getListItems() {
	return listItems;
    }

    public void getAllFood(boolean isAdmin)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT itemId, name, image, description, price, createDate, updateDate, quantity, status, p.caId, c.CategoryName "
			+ "FROM tblProduct p, tblCategory c "
			+ "WHERE p.CaId = c.CaId "
			+ "AND quantity > ? ";

		if (!isAdmin) {
		    sql += "AND status = '" + ACTIVE_STATUS + "'";
		}

		sql += "GROUP BY itemId, name, image, description, price, createDate, updateDate, quantity, status, p.caId, c.CategoryName "
			+ "ORDER BY createDate DESC "
			+ "OFFSET 0 ROWS "
			+ "FETCH NEXT 20 ROWS ONLY ";

		pst = cn.prepareStatement(sql);

		if (isAdmin) {
		    pst.setInt(1, -1);
		} else {
		    pst.setInt(1, 0);
		}

		//execute query
		rs = pst.executeQuery();

		while (rs.next()) {
		    String itemId = rs.getString("itemId");
		    String name = rs.getString("name");
		    String image = rs.getString("image");
		    String description = rs.getString("description");
		    double price = rs.getDouble("price");
		    Date createDate = rs.getDate("createDate");
		    Date updateDate = rs.getDate("updateDate");
		    int quantity = rs.getInt("quantity");
		    boolean status = rs.getBoolean("status");
		    int categoryId = rs.getInt("CaId");
		    String categoryName = rs.getString("CategoryName");

		    TblProductDTO dto = new TblProductDTO(itemId, name, image,
			    description, price, createDate,
			    updateDate, quantity, status, categoryId);

		    FoodItem item = new FoodItem(dto, categoryName);

		    if (this.listItems == null) {
			this.listItems = new ArrayList<>();
		    }

		    this.listItems.add(item);
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

    public int getTotalItems(boolean isAdmin) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT COUNT(itemId) as total "
			+ "FROM tblProduct "
			+ "WHERE quantity > ? ";

		if (!isAdmin) {
		    sql += "AND status = '" + ACTIVE_STATUS + "'";
		}

		pst = cn.prepareStatement(sql);
		if (isAdmin) {
		    pst.setInt(1, -1);
		} else {
		    pst.setInt(1, 0);
		}
		rs = pst.executeQuery();
		if (rs.next()) {
		    return rs.getInt("total");
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
	return 0;
    }

    public void doSearch(String searchValue, String priceRange, String categoryId, boolean admin, int page)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT itemId, name, image, description, price, createDate, updateDate, quantity, status, p.caId, c.categoryName "
			+ "FROM tblProduct p, tblCategory c "
			+ "WHERE p.CaId = c.CaId "
			+ "AND quantity > ? ";

		//checking searchValue to create sql string
		if (!searchValue.trim().isEmpty()) {
		    sql += "AND name like '%" + searchValue + "%'";
		}

		//checking priceRange is empty or not to create sql String
		if (!priceRange.trim().isEmpty()) {
		    if (priceRange.contains("-")) {
			String[] arr = priceRange.trim().split("-");
			double min = Double.parseDouble(arr[0]);
			double max = Double.parseDouble(arr[1]);
			sql += "AND (price BETWEEN " + min + " AND " + max + ")";
		    } else {
			priceRange = priceRange.replace(">", "");
			sql += "AND price > " + priceRange;
		    }
		}

		//checking category is empty or not
		if (!categoryId.trim().isEmpty()) {
		    sql += "AND c.CaId = " + categoryId;
		}

		//checking if role is admin or not
		if (!admin) {
		    sql += "AND status = " + ACTIVE_STATUS;
		}

		sql += "GROUP BY itemId, name, image, description, price, createDate, updateDate, quantity, quantity, status, p.caId, c.categoryName "
			+ "ORDER BY createDate DESC "
			+ "OFFSET ? ROWS "
			+ "FETCH NEXT 20 ROWS ONLY ";

		pst = cn.prepareStatement(sql);
		if (admin) {
		    pst.setInt(1, -1);
		} else {
		    pst.setInt(1, 0);
		}
		pst.setInt(2, (page - 1) * 20);

		//execute query
		rs = pst.executeQuery();
		while (rs.next()) {
		    //itemId, name, image, description, price, createDate, updateDate, quantity, status, p.caId
		    String itemId = rs.getString("itemId");
		    String name = rs.getString("name");
		    String image = rs.getString("image");
		    String description = rs.getString("description");
		    double price = rs.getDouble("price");
		    Date createDate = rs.getDate("createDate");
		    Date updateDate = rs.getDate("updateDate");
		    int quantity = rs.getInt("quantity");
		    boolean status = rs.getBoolean("status");
		    int caId = rs.getInt("caId");
		    String categoryName = rs.getString("categoryName");

		    TblProductDTO dto = new TblProductDTO(itemId, name, image, description,
			    price, createDate, updateDate, quantity, status, caId);
		    FoodItem item = new FoodItem(dto, categoryName);

		    if (this.listItems == null) {
			this.listItems = new ArrayList<>();
		    }
		    this.listItems.add(item);
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

    public int getTotalResult(String searchValue, String priceRange, String category, boolean admin)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT COUNT(itemId) as TotalProduct "
			+ "FROM tblProduct p, tblCategory c "
			+ "WHERE p.CaId = c.CaId "
			+ "AND quantity > ? ";

		//checking searchValue to create sql string
		if (!searchValue.trim().isEmpty()) {
		    sql += "AND name like '%" + searchValue + "%'";
		}

		//checking priceRange is empty or not to create sql String
		if (!priceRange.trim().isEmpty()) {
		    if (priceRange.contains("-")) {
			String[] arr = priceRange.trim().split("-");
			double min = Double.parseDouble(arr[0]);
			double max = Double.parseDouble(arr[1]);
			sql += "AND (price BETWEEN " + min + " AND " + max + ")";
		    } else {
			priceRange = priceRange.replace(">", "");
			sql += "AND price > " + priceRange;
		    }
		}

		//checking category is empty or not
		if (!category.trim().isEmpty()) {
		    sql += "AND c.CaId = " + category;
		}

		//checking if role is admin or not
		if (!admin) {
		    sql += "AND status = " + ACTIVE_STATUS;
		}

		pst = cn.prepareStatement(sql);
		if (admin) {
		    pst.setInt(1, -1);
		} else {
		    pst.setInt(1, 0);
		}
		//execute query
		rs = pst.executeQuery();
		if (rs.next()) {
		    return rs.getInt("TotalProduct");
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
	return 0;
    }

    public void writeLog(String itemId, String updateDate, String userId)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "UPDATE tblProduct "
			+ "SET updateDate = ? , userIdUpdate = ? "
			+ "WHERE itemId = ? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, updateDate);
		pst.setString(2, userId);
		pst.setString(3, itemId);
		pst.executeUpdate();
	    }//end if connection
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
    }

    public void deleteFood(String itemId)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "UPDATE tblProduct "
			+ "SET status = 0 "
			+ "WHERE itemId = ? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, itemId);
		pst.executeUpdate();
	    }//end if connection
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
    }

    public int updateFood(String itemId, String name, String description,
	    String categoryId, String quantity, String price, String image)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "UPDATE tblProduct "
			+ "SET name = ?, description = ?, image = ?, price = ?, "
			+ "quantity = ?, caId = ? "
			+ "WHERE itemId = ? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, name);
		pst.setString(2, description);
		pst.setString(3, image);
		pst.setString(4, price);
		pst.setString(5, quantity);
		pst.setString(6, categoryId);
		pst.setString(7, itemId);
		int row = pst.executeUpdate();
		if (row > 0) {
		    return row;
		}
	    }//end if connection
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
	return 0;
    }

    public int createFood(String name, String imageURL, String description,
	    String price, String createDate, String quantity, String categoryId)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		boolean status = true;
		String sql = "INSERT tblProduct(name, image, description, price, createDate, quantity, caId, status) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		pst = cn.prepareStatement(sql);
		pst.setString(1, name);
		pst.setString(2, imageURL);
		pst.setString(3, description);
		pst.setString(4, price);
		pst.setString(5, createDate);
		pst.setString(6, quantity);
		pst.setString(7, categoryId);
		pst.setBoolean(8, status);
		int row = pst.executeUpdate();
		if (row > 0) {
		    return row;
		}
	    }//end if connection
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
	return 0;
    }

    public TblProductDTO getFood(String itemId) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT name, image, description, price, quantity, createDate, updateDate, status, CaId "
			+ "FROM tblProduct "
			+ "WHERE itemId = ? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, itemId);

		rs = pst.executeQuery();
		if (rs.next()) {
		    String name = rs.getString("name");
		    String image = rs.getString("image");
		    String description = rs.getString("description");
		    double price = rs.getDouble("price");
		    int quantity = rs.getInt("quantity");
		    Date createDate = rs.getDate("createDate");
		    Date updateDate = rs.getDate("updateDate");
		    boolean status = rs.getBoolean("status");
		    int categoryId = rs.getInt("caId");
		    TblProductDTO dto = new TblProductDTO(itemId, name, image, description, price, createDate, updateDate, quantity, status, categoryId);
		    return dto;
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
	return null;
    }

    public boolean checkAvailableFood(String itemId)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT name "
			+ "FROM tblProduct "
			+ "WHERE itemId = ? "
			+ "AND quantity > ? "
			+ "AND status = ? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, itemId);
		pst.setInt(2, 0);
		pst.setString(3, ACTIVE_STATUS);
		rs = pst.executeQuery();
		if (rs.next()) {
		    return true;
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
	return false;
    }
    
    public void updateQuantity(String itemId, int newQuantity) throws SQLException, NamingException{
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = MyConnection.makeConnection();
	    if (con != null) {
		String sql = "UPDATE tblProduct "
			+ "SET quantity = ? "
			+ "WHERE itemId = ? ";
		
		pst = con.prepareStatement(sql);
		pst.setInt(1, newQuantity);
		pst.setString(2, itemId);
		
		pst.executeUpdate();
	    }
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
    }
}
