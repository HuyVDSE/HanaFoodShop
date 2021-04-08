/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.cart.CartItem;
import huyvd.cart.CartObject;
import huyvd.tblAccount.TblAccountDTO;
import huyvd.tblOrder.TblOrderCheckoutError;
import huyvd.tblOrder.TblOrderDAO;
import huyvd.tblOrderDetail.TblOrderDetailDAO;
import huyvd.tblProduct.TblProductDTO;
import huyvd.tblProduct.TblproductDAO;
import huyvd.util.DateHandle;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
@WebServlet(name = "CheckoutCartServlet", urlPatterns = {"/CheckoutCartServlet"})
public class CheckoutCartServlet extends HttpServlet {

    private final String CHECKOUT_SUCCESS_PAGE = "checkoutSuccess.html";
    private final String ERROR_PAGE = "ViewCartServlet";

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
	String url = ERROR_PAGE;
	String paymentMethod = request.getParameter("cmbPaymentMethod");
	String usesId = "";

	try {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		//get current user
		TblAccountDTO curUser = (TblAccountDTO) session.getAttribute("ACCOUNT");
		if (curUser != null) {
		    usesId = curUser.getUserId();
		}

		CartObject cart = (CartObject) session.getAttribute("CUS_CART");
		if (cart != null) {
		    if (!cart.getItemList().isEmpty()) {
			//check error
			TblOrderCheckoutError errors = new TblOrderCheckoutError();
			boolean foundErr = false;

			String unavailableError = checkFoodAvailable(cart);
			if (!unavailableError.isEmpty()) {
			    errors.setAvailableError(unavailableError + ". Please remove it from your cart to checkout!");
			    foundErr = true;
			}
			
			String buyQuantityError = checkFoodQuantity(cart);
			if (!buyQuantityError.isEmpty()) {
			    errors.setBuyQuantityError(buyQuantityError + " Please change quantity to checkout!");
			    foundErr = true;
			}

			if (foundErr) {
			    request.setAttribute("CHECKOUT_ERROR", errors);
			} else {
			    String cartId = cart.generateCartId();
			    String orderDate = DateHandle.createOrderDate();
			    double totalPayment = cart.getTotalPriceOfCart();

			    TblOrderDAO orderDAO = new TblOrderDAO();
			    boolean createdOrderSuccessful = orderDAO.createOrder(cartId, orderDate, totalPayment, usesId, paymentMethod);
			    if (createdOrderSuccessful) {
				TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
				boolean success = orderDetailDAO.createOrder(cart.getItemList(), cartId);

				if (success) {
				    updateQuantityInStore(cart);

				    //remove cart
				    session.setAttribute("CUS_CART", null);

				    url = CHECKOUT_SUCCESS_PAGE;
				}//end if create order success
			    }
			}//end if not found error
		    }//end if cart have item
		}//end if cart exist
	    }//end if session exist
	} catch (SQLException e) {
	    log("CheckoutCartServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("CheckoutCartServlet _ NamingException: " + e.getMessage());
	} finally {
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
    }

    public String checkFoodAvailable(CartObject cart) throws SQLException, NamingException {
	String unavailableError = "";
	boolean foundErr = false;
	TblproductDAO dao = new TblproductDAO();
	List<String> notAvailableFood = null;
	
	for (CartItem cartItem : cart.getItemList()) {
	    TblProductDTO food = cartItem.getFood();
	    String itemId = food.getItemId();
	    
	    boolean isAvailable = dao.checkAvailableFood(itemId);
	    if (!isAvailable) {
		if (notAvailableFood == null) {
		    notAvailableFood = new ArrayList<>();
		}
		
		notAvailableFood.add(food.getName());
		foundErr = true;
	    }
	}
	
	if (foundErr) {
	    unavailableError = "Food not available or out of stock: ";
	    for (String foodName : notAvailableFood) {
		unavailableError += foodName + ", ";
	    }
	}
	return unavailableError;
    }
    
    public String checkFoodQuantity(CartObject cart) throws SQLException, NamingException {
	String errorString = "";
	for (CartItem cartItem : cart.getItemList()) {
	    TblProductDTO food = cartItem.getFood();
	    int buyQuantity = cartItem.getQuantity();
	    String foodId = food.getItemId();

	    int newQuantity = calculateNewFoodQuantity(foodId, buyQuantity);
	    if (newQuantity < 0) {
		//set error msg
		int quantityInStore = newQuantity + buyQuantity;
		errorString += food.getName() + " just have " + quantityInStore + " left!\n";
	    }
	}
	return errorString;
    }

    public int calculateNewFoodQuantity(String itemId, int buyQuantity)
	    throws SQLException, NamingException {
	TblproductDAO dao = new TblproductDAO();
	TblProductDTO food = dao.getFood(itemId);
	int newQuantity = food.getQuantity();

	//get food
	boolean isAvailable = dao.checkAvailableFood(itemId);
	if (isAvailable) {
	    newQuantity -= buyQuantity;
	}

	return newQuantity;
    }

    public void updateQuantityInStore(CartObject cart)
	    throws SQLException, NamingException {
	TblproductDAO dao = new TblproductDAO();
	for (CartItem cartItem : cart.getItemList()) {
	    String itemId = cartItem.getFood().getItemId();
	    int buyQuantity = cartItem.getQuantity();

	    int newQuantity = calculateNewFoodQuantity(itemId, buyQuantity);
	    dao.updateQuantity(itemId, newQuantity);
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
