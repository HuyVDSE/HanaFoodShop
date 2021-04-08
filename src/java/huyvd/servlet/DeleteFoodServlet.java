/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.tblAccount.TblAccountDTO;
import huyvd.tblProduct.TblproductDAO;
import huyvd.util.DateHandle;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author BlankSpace
 */
@WebServlet(name = "DeleteFoodServlet", urlPatterns = {"/DeleteFoodServlet"})
public class DeleteFoodServlet extends HttpServlet {

    private final String LOAD_MANAGE_PAGE = "LoadManagePageServlet";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");
	//get field for call search function
	String searchValue = request.getParameter("txtSearchValue");
	String cmbPrice = request.getParameter("cmbPrice");
	String cmbCategory = request.getParameter("cmbCategory");
	
//	String url = LOAD_MANAGE_PAGE;
	String itemId = request.getParameter("itemId");

	try {
	    //get current user
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		TblAccountDTO curUser = (TblAccountDTO) session.getAttribute("ACCOUNT");
		if (curUser != null) {
		    String userId = curUser.getUserId();
		    TblproductDAO dao = new TblproductDAO();
		    //write log
		    String updateDate = DateHandle.createCurrentDate();
		    dao.writeLog(itemId, updateDate, userId);

		    //delete item
		    dao.deleteFood(itemId);
		}//end if curUser exist
	    }//end if session exist
	} catch (SQLException e) {
	    log("DeleteFoodServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("DeleteFoodServlet _ NamingException: " + e.getMessage());
	} finally {
	    String urlRewriting = "DispatchServlet?btAction=Search&txtSearchValue="+searchValue
		    +"&cmbPrice="+cmbPrice+"&cmbCategory="+cmbCategory+"&destination=manage-food";
//	    RequestDispatcher rd = request.getRequestDispatcher(url);
//	    rd.forward(request, response);
	    response.sendRedirect(urlRewriting);
	}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>

}
