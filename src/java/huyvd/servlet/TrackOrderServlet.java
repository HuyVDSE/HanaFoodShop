/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.tblAccount.TblAccountDTO;
import huyvd.tblOrder.TblOrderDAO;
import huyvd.tblOrder.TblOrderDTO;
import huyvd.tblOrderDetail.ItemDTO;
import huyvd.tblOrderDetail.TblOrderDetailDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "TrackOrderServlet", urlPatterns = {"/TrackOrderServlet"})
public class TrackOrderServlet extends HttpServlet {

    private final String TRACK_ORDER_PAGE = "trackOrder.jsp";

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
	String url = TRACK_ORDER_PAGE;

	String SearchOrderDate = request.getParameter("txtSearchOrder");
	String userId = "";
	boolean isAdmin = false;

	try {
	    if (SearchOrderDate != null) {
		HttpSession session = request.getSession(false);
		if (session != null) {
		    TblAccountDTO curUser = (TblAccountDTO) session.getAttribute("ACCOUNT");
		    if (curUser != null) {
			userId = curUser.getUserId();
			isAdmin = curUser.isIsAdmin();
		    }//end if current user exist
		}//end if session exist

		TblOrderDAO orderDAO = new TblOrderDAO();
		orderDAO.getOrderByDate(SearchOrderDate, userId, isAdmin);
		List<TblOrderDTO> orderList = orderDAO.getListOrder();

		//use map to store order detail list with orderId is key
		Map<String, List<ItemDTO>> orderDetailMap = null;

		if (!orderList.isEmpty()) {
		    orderDetailMap = new HashMap<>();
		    TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();

		    for (TblOrderDTO order : orderList) {
			List<ItemDTO> detailList = orderDetailDAO.getOrderDetail(order.getOrderId());
			orderDetailMap.put(order.getOrderId(), detailList);
		    }
		}
		
		request.setAttribute("ORDER_LIST", orderList);

		request.setAttribute("ORDER_DETAIL_MAP", orderDetailMap);
	    }

	} catch (SQLException e) {
	    log("TrackOrderServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("TrackOrderServlet _ NamingException: " + e.getMessage());
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
