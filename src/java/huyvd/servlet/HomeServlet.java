/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.util.PageCalculate;
import huyvd.food.FoodItem;
import huyvd.tblAccount.TblAccountDTO;
import huyvd.tblCategory.TblCategoryDAO;
import huyvd.tblCategory.TblCategoryDTO;
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
@WebServlet(name = "HomeServlet", urlPatterns = {"/HomeServlet"})
public class HomeServlet extends HttpServlet {

    private final String HOME_PAGE = "homePage.jsp";

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
	String url = HOME_PAGE;
	String searchValue = request.getParameter("txtSearchValue");

	try {
	    if (searchValue == null) {
		HttpSession session = request.getSession(false);
		boolean isAdmin = false;
		
		if (session != null) {
		    TblAccountDTO currentUser = (TblAccountDTO) session.getAttribute("ACCOUNT");
		    if (currentUser != null) {
			isAdmin = currentUser.isIsAdmin();
		    }//end if curUser is exist
		}//end if session exist
		
		loadAllFood(request, isAdmin);
		request.setAttribute("LOAD_ALL_DEFAULTE_PAGE", 1);
	    }//end if search is empty

	    //load categpry list
	    TblCategoryDAO dao = new TblCategoryDAO();
	    dao.loadCategory();
	    List<TblCategoryDTO> listCatogory = dao.getCategoryList();
	    request.setAttribute("CATEGORY_LIST", listCatogory);
	} catch (SQLException e) {
	    log("HomeServlet _ SQLExceptiion: " + e.getMessage());
	} catch (NamingException e) {
	    log("NamingException _ SQLExceptiion: " + e.getMessage());
	} finally {
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
    }

    private void loadAllFood(HttpServletRequest request, boolean role)
	    throws SQLException, NamingException {
	TblproductDAO dao = new TblproductDAO();
	dao.getAllFood(role);
	List<FoodItem> listItems = dao.getListItems();
	request.setAttribute("LIST_ITEMS", listItems);

	int totalItems = dao.getTotalItems(role);
	int totalPages = PageCalculate.calculateNumOfPage(totalItems, 20);
	request.setAttribute("TOTAL_PAGES", totalPages);
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
