/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.util.PageCalculate;
import huyvd.food.FoodItem;
import huyvd.tblAccount.TblAccountDTO;
import huyvd.tblProduct.TblproductDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
@WebServlet(name = "SearchFoodServlet", urlPatterns = {"/SearchFoodServlet"})
public class SearchFoodServlet extends HttpServlet {

    private final String LOAD_HOME_CONTROLLER = "HomeServlet";
    private final String LOAD_MANAGE_PAGE_CONTROLLER = "LoadManagePageServlet";
//    private final String LOAD_MANAGE_PAGE_CONTROLLER = "manageFood.jsp";

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
	String url = LOAD_HOME_CONTROLLER;
	String searchValue = request.getParameter("txtSearchValue");
	String priceRange = request.getParameter("cmbPrice");
	String category = request.getParameter("cmbCategory");
	int page = 1;//default page
	String pageString = request.getParameter("page");
	boolean admin = false;

	try {
	    if (pageString != null) {
		page = Integer.parseInt(pageString);
	    }

	    HttpSession session = request.getSession(false);
	    if (session != null) {
		TblAccountDTO curUser = (TblAccountDTO) session.getAttribute("ACCOUNT");
		if (curUser != null) {
		    admin = curUser.isIsAdmin();
		}//end if curUser is exist
	    }//end if session exist

	    //create dao
	    TblproductDAO dao = new TblproductDAO();
	    //do search
	    dao.doSearch(searchValue, priceRange, category, admin, page);
	    List<FoodItem> listItems = dao.getListItems();
	    request.setAttribute("LIST_ITEMS", listItems);
	    //calculate total page for paging
	    int totalItems = dao.getTotalResult(searchValue, priceRange, category, admin);
	    int totalPages = PageCalculate.calculateNumOfPage(totalItems, 20);
	    request.setAttribute("TOTAL_PAGES", totalPages);
	} catch (SQLException e) {
	    log("SearchFoodServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("SearchFoodServlet _ NamingException: " + e.getMessage());
	} finally {
	    //check destination because search function is using by 2 different pages
	    String destination = request.getParameter("destination");
	    if (destination != null) {
		//block normal user to access admin page
		if (destination.equals("manage-food") && !admin) {
		    url = LOAD_HOME_CONTROLLER;
		} else if (destination.equals("manage-food") && admin) {
		    url = LOAD_MANAGE_PAGE_CONTROLLER;
		}
	    }//end if destination param exist

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
