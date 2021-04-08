/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.cart.CartObject;
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
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

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
	String url = null;
	String itemId = request.getParameter("itemId");
	
	try {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		CartObject cart = (CartObject) session.getAttribute("CUS_CART");
		
		if (cart == null) {
		    cart = new CartObject();
		}//end if cart object is exist
		
		cart.addFoodToCart(itemId);
		session.setAttribute("CUS_CART", cart);
	    }//end if session is exist
	    
	    //create url to call search function
	    String searchValue = request.getParameter("searchValue");
	    String cmbCategory = request.getParameter("cmbCategory");
	    String cmbPrice = request.getParameter("cmbPrice");
	    String destination = request.getParameter("destination");
	    String page = request.getParameter("page");
	    if (page.trim().isEmpty()) {
		page = "1";
	    }
	    url = "DispatchServlet?btAction=Search"
		    + "&txtSearchValue=" + searchValue
		    + "&cmbPrice=" + cmbPrice
		    + "&cmbCategory=" + cmbCategory
		    + "&destination=" + destination
		    + "&page=" + page;
	} catch (SQLException e) {
	    log("AddToCartServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("AddToCartServlet _ NamingException: " + e.getMessage());
	}  finally {
	    response.sendRedirect(url);
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
