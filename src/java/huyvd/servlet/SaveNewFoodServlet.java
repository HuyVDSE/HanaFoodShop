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
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "SaveNewFoodServlet", urlPatterns = {"/SaveNewFoodServlet"})
public class SaveNewFoodServlet extends HttpServlet {

    private final String LOAD_MANAGE_CONTROLLER = "LoadManagePageServlet";

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
	String url = LOAD_MANAGE_CONTROLLER;

	String name = request.getParameter("txtName");
	String description = request.getParameter("txtDescription");
	String quantity = request.getParameter("txtQuantity");
	String price = request.getParameter("txtPrice");
	String categoryId = request.getParameter("cmbCategory");
	String imageURL = request.getParameter("txtImage");

	try {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		TblAccountDTO curUser = (TblAccountDTO) session.getAttribute("ACCOUNT");
		if (curUser != null) {
		    boolean isAdmin = curUser.isIsAdmin();
		    if (isAdmin) {
			//call dao
			TblproductDAO dao = new TblproductDAO();
			String createDate = DateHandle.createCurrentDate();
			dao.createFood(name, imageURL, description, price, createDate, quantity, categoryId);
		    }//end if is admin role
		}//end if current user is exist
	    }//end if session exist
	} catch (SQLException e) {
	    log("SaveNewFoodServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("SaveNewFoodServlet _ NamingException: " + e.getMessage());
	} finally {
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
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
