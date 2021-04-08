/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.tblAccount.TblAccountDTO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author BlankSpace
 */
public class DispatchServlet extends HttpServlet {

    private final String LOGIN_PAGE = "login.html";
    private final String HOME_CONTROLLER = "HomeServlet";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String SEARCH_CONTROLLER = "SearchFoodServlet";
    private final String SHOPPING_CONTROLLER = "AddToCartServlet";
    private final String MANAGE_CONTROLLER = "LoadManagePageServlet";
    private final String DELETE_FOOD_CONTROLLER = "DeleteFoodServlet";
    private final String UPDATE_FOOD_CONTROLLER = "UpdateFoodServlet";
    private final String LOAD_CREATE_PAGE_CONTROLLER = "LoadCreateFoodServlet";
    private final String SAVE_NEW_FOOD_CONTROLLER = "SaveNewFoodServlet";
    private final String VIEW_CART_CONTROLLER = "ViewCartServlet";
    private final String UPDATE_ITEM_QUANTITY_CONTROLLER = "UpdateQuantityItemSerlvet";
    private final String DELETE_ITEM_CONTROLLER = "DeleteItemServlet";
    private final String CHECKOUT_CART_CONTROLLER = "CheckoutCartServlet";
    private final String TRACK_ORDER_PAGE = "trackOrder.jsp";
    private final String TRACK_ORDER_CONTROLLER = "TrackOrderServlet";

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

	String url = LOGIN_PAGE;
	String button = request.getParameter("btAction");

	try {
	    if (null == button) {
		url = HOME_CONTROLLER;
	    } else if (button.equals("Login")) {
		url = LOGIN_CONTROLLER;
	    } else if (button.equals("Logout")) {
		url = LOGOUT_CONTROLLER;
	    } else if (button.equals("Home")) {
		url = HOME_CONTROLLER;
	    } else if (button.equals("Search")) {
		url = SEARCH_CONTROLLER;
	    } else if (button.equals("ManageFood")) {
		boolean isAdmin = checkAdminRole(request);
		if (isAdmin) {
		    url = MANAGE_CONTROLLER;
		} else {
		    url = LOGIN_PAGE;
		}
	    } else if (button.equals("Delete Food")) {
		boolean isAdmin = checkAdminRole(request);
		if (isAdmin) {
		    url = DELETE_FOOD_CONTROLLER;
		} else {
		    url = LOGIN_PAGE;
		}
	    } else if (button.equals("Update Food")) {
		boolean isAdmin = checkAdminRole(request);
		if (isAdmin) {
		    url = UPDATE_FOOD_CONTROLLER;
		} else {
		    url = LOGIN_PAGE;
		}
	    } else if (button.equals("CreateFoodPage")) {
		boolean isAdmin = checkAdminRole(request);
		if (isAdmin) {
		    url = LOAD_CREATE_PAGE_CONTROLLER;
		} else {
		    url = LOGIN_PAGE;
		}
	    } else if (button.equals("Save")) {
		boolean isAdmin = checkAdminRole(request);
		if (isAdmin) {
		    url = SAVE_NEW_FOOD_CONTROLLER;
		} else {
		    url = LOGIN_PAGE;
		}
	    } else if (button.equals("Add to cart")) {
		boolean isLogin = checkIsLogin(request);
		boolean isAdmin = checkAdminRole(request);
		if (isLogin) {
		    url = SHOPPING_CONTROLLER;
		} else if (isAdmin) {
		    url = HOME_CONTROLLER;
		} else if (!isLogin) {
		    url = LOGIN_PAGE;
		}
	    } else if ("ViewCart".equals(button)) {
		boolean isLogin = checkIsLogin(request);
		boolean isAdmin = checkAdminRole(request);
		if (isLogin) {
		    url = VIEW_CART_CONTROLLER;
		} else if (isAdmin) {
		    url = HOME_CONTROLLER;
		} else if (!isLogin) {
		    url = LOGIN_PAGE;
		}
	    } else if ("Update".equals(button)) {
		url = UPDATE_ITEM_QUANTITY_CONTROLLER;
	    } else if ("DeleteItem".equals(button)) {
		url = DELETE_ITEM_CONTROLLER;
	    } else if ("Checkout".equals(button)) {
		boolean isLogin = checkIsLogin(request);
		if (isLogin) {
		    url = CHECKOUT_CART_CONTROLLER;
		} else {
		    url = LOGIN_PAGE;
		}
	    } else if ("TrackOrder".equals(button)) {
		url = TRACK_ORDER_PAGE;
	    } else if ("Search Order".equals(button)) {
		url = TRACK_ORDER_CONTROLLER;
	    }
	} finally {
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
    }

    private boolean checkAdminRole(HttpServletRequest request) {
	HttpSession session = request.getSession(false);
	if (session != null) {
	    TblAccountDTO curUser = (TblAccountDTO) session.getAttribute("ACCOUNT");
	    if (curUser != null) {
		return curUser.isIsAdmin();
	    }
	}
	return false;
    }

    private boolean checkIsLogin(HttpServletRequest request) {
	HttpSession session = request.getSession(false);
	if (session != null) {
	    TblAccountDTO curUser = (TblAccountDTO) session.getAttribute("ACCOUNT");
	    if (curUser != null) {
		return true;
	    }
	}
	return false;
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
	request.setCharacterEncoding("UTF-8");
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
	request.setCharacterEncoding("UTF-8");
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
