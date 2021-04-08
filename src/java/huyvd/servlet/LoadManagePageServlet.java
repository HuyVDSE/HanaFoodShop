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
@WebServlet(name = "LoadManagePageServlet", urlPatterns = {"/LoadManagePageServlet"})
public class LoadManagePageServlet extends HttpServlet {

    private final String MANAGE_PAGE = "manageFood.jsp";

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
	String url = MANAGE_PAGE;
	boolean admin = false;

	String searchValue = request.getParameter("txtSearchValue");

	try {
	    if (searchValue == null) {
		HttpSession session = request.getSession(false);

		if (session != null) {
		    TblAccountDTO curUser = (TblAccountDTO) session.getAttribute("ACCOUNT");
		    if (curUser != null) {
			admin = curUser.isIsAdmin();
		    }//end if curUser is exist
		}//end if session is exist

		getAllFood(request, admin);
		request.setAttribute("LOAD_ALL_DEFAULTE_PAGE", 1);
		request.setAttribute(url, url);
	    }//end if searchValue is empty

	    TblCategoryDAO dao = new TblCategoryDAO();
	    dao.loadCategory();
	    List<TblCategoryDTO> listCategory = dao.getCategoryList();
	    request.setAttribute("CATEGORY_LIST", listCategory);
	} catch (SQLException e) {
	    log("LoadManagePageServlet _ SQLException" + e.getMessage());
	} catch (NamingException e) {
	    log("LoadManagePageServlet _ NamingException" + e.getMessage());
	} finally {
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
    }

    private void getAllFood(HttpServletRequest request, boolean admin)
	    throws SQLException, NamingException {
	TblproductDAO dao = new TblproductDAO();
	dao.getAllFood(admin);
	List<FoodItem> listItems = dao.getListItems();
	request.setAttribute("LIST_ITEMS", listItems);
	

	int totalItems = dao.getTotalItems(admin);
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
