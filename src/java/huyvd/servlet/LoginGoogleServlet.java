/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.google.GoogleLoginUtils;
import huyvd.google.GooglePojo;
import huyvd.tblAccount.TblAccountDAO;
import huyvd.tblAccount.TblAccountDTO;
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
@WebServlet(name = "LoginGoogleServlet", urlPatterns = {"/LoginGoogleServlet"})
public class LoginGoogleServlet extends HttpServlet {

    private final String HOME_COTROLLER = "HomeServlet";
    private final String INVALID_PAGE = "invalid.html";

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
	String url = INVALID_PAGE;

	//code return by google after user choose account
	String code = request.getParameter("code");
	try {
	    if (code != null && !code.isEmpty()) {
		String accessToken = GoogleLoginUtils.getToken(code);
		GooglePojo googlePojo = GoogleLoginUtils.getUserInfo(accessToken);

		String email = googlePojo.getEmail();
		TblAccountDAO dao = new TblAccountDAO();
		TblAccountDTO result = dao.checkGoogleAccountExist(email);
		if (result == null) {
		    String name = convertToFullname(googlePojo.getEmail());
		    dao.insertGoogleAccount(email, name);
		    result = dao.checkGoogleAccountExist(email);
		    HttpSession session = request.getSession();
		    session.setAttribute("ACCOUNT", result);
		} else {
		    HttpSession session = request.getSession();
		    session.setAttribute("ACCOUNT", result);
		}
		url = HOME_COTROLLER;
	    }
	} catch (SQLException e) {
	    log("LoginGoogleServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("LoginGoogleServlet _ NamingException: " + e.getMessage());
	} finally {
	    response.sendRedirect(url);
	}
    }

    private String convertToFullname(String email) {	
	return email.replace("@gmail.com", "");
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
