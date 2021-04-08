/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblPaymentMethod;

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
public class TblPaymentMethodDAO implements Serializable{
    private List<TblPaymentMethodDTO> listPaymentMethod;

    public List<TblPaymentMethodDTO> getListPaymentMethod() {
	return listPaymentMethod;
    }
    
    public void loadPaymentMethod() throws SQLException, NamingException{
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT methodId, methodName "
			+ "FROM tblPaymentMethod ";
		pst = cn.prepareStatement(sql);
		rs = pst.executeQuery();
		while (rs.next()) {		    
		    String methodId = rs.getString("methodId");
		    String methodName = rs.getString("methodName");
		    TblPaymentMethodDTO dto = new TblPaymentMethodDTO(methodId, methodName);
		    
		    if (this.listPaymentMethod == null) {
			this.listPaymentMethod = new ArrayList<>();
		    }
		    
		    this.listPaymentMethod.add(dto);
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
