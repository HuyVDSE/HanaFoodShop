<%-- 
    Document   : trackOrder
    Created on : Jan 17, 2021, 5:47:18 PM
    Author     : BlankSpace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Tracking</title>
	<link rel="stylesheet" href="assets/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/css/customize.css">
    </head>
    <body>
	<c:set var="account" value="${sessionScope.ACCOUNT}" />
	<div class="jumbotron text-center" style="margin-bottom: 0">
	    <h1>Hana Shop</h1>
	    <p>FastFood on your way</p>
	</div>
	<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
	    <c:if test="${empty account}">
		<ul class="navbar-nav mr-auto py-0">
		    <li class="nav-item">
			<a class="nav-link active" href="DispatchServlet?btAction=Home">Home</a>
		    </li>
		</ul>
		<ul class="navbar-nav ml-auto text-center">
		    <li class="nav-item">
			<a class="btn btn-light btn-sm my-2 my-sm-0 mx-3" href="login.html">Login</a>
		    </li>
		</ul>
	    </c:if>

	    <c:if test="${not empty account}">
		<ul class="navbar-nav mr-auto py-0">
		    <li class="nav-item">
			<a class="nav-link" href="DispatchServlet?btAction=Home">Home</a>
		    </li>
		    <c:if test="${account.isAdmin}">
			<li class="nav-item">
			    <a class="nav-link" href="DispatchServlet?btAction=ManageFood">Manage Food</a>
			</li>
		    </c:if>
		    <c:if test="${!account.isAdmin}">
			<li class="nav-item">
			    <a class="nav-link" href="DispatchServlet?btAction=ViewCart">View Cart</a>
			</li>
			<c:if test="${not empty cart.itemList}">
			    <li class="nav-item">
				<span class="badge badge-danger">${cart.totalItems}</span>
			    </li>
			</c:if>
			<c:if test="${empty cart.itemList}">
			    <li class="nav-item">
				<span class="badge badge-danger">0</span>
			    </li>
			</c:if>  
		    </c:if>
		    <li class="nav-item">
			<a class="nav-link active" href="DispatchServlet?btAction=TrackOrder">Order History</a>
		    </li>  
		</ul>
		<ul class="navbar-nav ml-auto text-center">
		    <li class="nav-item">
			<div class="text-primary">Welcome, ${account.fullname}</div>
		    </li>
		    <li class="nav-item">
			<form action="DispatchServlet">
			    <input class="btn btn-light btn-sm my-2 my-sm-0 mx-3" type="submit" value="Logout" name="btAction" />
			</form>
		    </li>
		</ul>
	    </c:if>
	</nav>

	<div class="container">
	    <c:set var="searchValue" value="${param.txtSearchOrder}"/>
	    <div class= "my-4">
		<form action="DispatchServlet" class="form-inline ml-auto"/> 
		<input type="date" name="txtSearchOrder" value="${searchValue}" class="form-control col-2" placeholder="Enter OrderDate"/>
		<input type="submit" value="Search Order" name="btAction" class="btn btn-success col-2" style="margin-left: 1rem" /> 
		</form> 
	    </div>

	    <c:set var="listOrder" value="${requestScope.ORDER_LIST}"/>
	    <c:set var="orderDetailMap" value="${requestScope.ORDER_DETAIL_MAP}"/>
	    <c:if test="${not empty listOrder}">
		<c:forEach var="order" items="${listOrder}">
		    <label>OrderID: ${order.orderId}</label></br>
		    <label>OrderDate: ${order.orderDate}</label></br>
		    <label>UserId: ${order.userId}</label></br>
		    <table class="table text-center table-bordered">
			<thead class="thead-light">
			    <tr>
				<th scope="col">No.</th>
				<th scope="col">Food Name</th>
				<th scope="col">Price</th>
				<th scope="col">Quantity</th>
				<th scope="col">Total</th>
			    </tr>
			</thead>
			<tbody>
			    <c:forEach var="item" items="${orderDetailMap[order.orderId]}" varStatus="counter">
				<tr>
				    <td>
					${counter.count}
				    </td>
				    <td>
					${item.name}
				    </td>
				    <td>
					${item.price}
				    </td>
				    <td>
					${item.quantity}
				    </td>
				    <td>
					${item.total} VND
				    </td>
				</tr>
			    </c:forEach> 
			    <tr class="alert-primary text-right" style="font-weight: bold">
				<td scope="row" colspan="4">Cart Total:</td>
				<td class="text-center" scope="row">${order.total} VND</td>
			    </tr>
			    <tr class="alert-warning text-right" style="font-weight: bold">
				<td scope="row" colspan="4">
				    Payment Method: 
				</td>
				<td class="text-center">
				    ${order.methodId}
				</td>
			    </tr>
			</tbody>
		    </table>
		</c:forEach>
	    </c:if>
	    <c:if test="${empty listOrder}">
		<h4 class="text-center my-5">
		    No Orders Found!!
		</h4>
	    </c:if>
	</div>

	<!---Footer area-->
	<footer>
	    <div class="mini-footer">
		<div class="container">
		    <div class="row">
			<div class="col-md-12">
			    <p>
				Copyrights © 2021. All rights reserved by BlankSpace
			    </p>
			</div>
		    </div>
		</div>
	    </div>
	</footer>
	<script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
