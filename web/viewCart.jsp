<%-- 
    Document   : viewCart
    Created on : Jan 14, 2021, 10:30:56 PM
    Author     : BlankSpace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Shopping Cart</title>
	<link rel="stylesheet" href="assets/css/customize.css">
	<link rel="stylesheet" href="assets/css/bootstrap.min.css">
    </head>

    <body>
	<c:set var="account" value="${sessionScope.ACCOUNT}" />
	<c:set var="cart" value="${sessionScope.CUS_CART}" />
	<div class="jumbotron text-center" style="margin-bottom: 0">
	    <h1>Hana Shop</h1>
	    <p>FastFood on your way</p>
	</div>
	<nav class="navbar navbar-expand-sm navbar-dark bg-dark">


	    <c:if test="${not empty account}">
		<ul class="navbar-nav mr-auto py-0">
		    <li class="nav-item">
			<a class="nav-link" href="DispatchServlet?btAction=Home">Home</a>
		    </li>
		    <c:if test="${!account.isAdmin}">
			<li class="nav-item">
			    <a class="nav-link active" href="DispatchServlet?btAction=ViewCart">View Cart</a>
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
			<a class="nav-link" href="DispatchServlet?btAction=TrackOrder">Order History</a>
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

	<!--display cart detail-->
	<div class="container">
	    <div class="mt-3">
		<c:set var="cart" value="${requestScope.CUS_CART}"/>
		<c:if test="${not empty cart}">
		    <h4 class="text-center my-5">
			Your cart
		    </h4>
		    <table class="table text-center table-bordered">
			<thead class="thead-light">
			    <tr>
				<th scope="col">No.</th>
				<th scope="col">Food Name</th>
				<th scope="col">Price</th>
				<th scope="col">Quantity</th>
				<th scope="col">Total</th>
				<th scope="col">Update</th>
				<th scope="col">Delete</th>
			    </tr>
			</thead>
			<tbody>
			    <c:forEach var="item" items="${cart.itemList}" varStatus="counter">
			    <form action="DispatchServlet" method="POST">
				<tr>
				    <td>
					${counter.count}
				    </td>
				    <td>
					${item.food.name}
				    </td>
				    <td>
					${item.food.price}
				    </td>
				    <td>
					<input type="number" class="text-center" name="txtQuantity" value="${item.quantity}" min="1" step="1"/>
				    </td>
				    <td>
					${item.total} VND
				    </td>
				    <td>
					<input type="hidden" name="txtitemId" value="${item.food.itemId}" />
					<input type="submit" value="Update" name="btAction" class="btn btn-outline-success"/>
				    </td>
				    <td>
					<c:set var="deleteItemInCart" value="DispatchServlet?btAction=DeleteItem&itemId=${item.food.itemId}"/>
					<a href="${deleteItemInCart}" class="btn btn btn-outline-danger" onclick="return confirm('Do you really want to delete?');">Delete</a>
				    </td>
				</tr>
			    </form>
			</c:forEach>
			<c:set var="total" value="${requestScope.TOTAL_PRICE}"/>
			<tr class="alert-primary text-right" style="font-weight: bold">
			    <td scope="row" colspan="6">Cart Total:</td>
			    <td class="text-center" scope="row">${total} VND</td>
			</tr>
			<form action="DispatchServlet" method="POST">
			    <tr class="alert-warning text-right" style="font-weight: bold">
				<td scope="row" colspan="6">
				    Payment Method: 
				</td>
				<td>
				    <c:set var="paymentMethod" value="${requestScope.LIST_PAYMENT_METHOD}"/>
				    <select name="cmbPaymentMethod" class="form-control">
					<c:forEach var="item" items="${paymentMethod}">
					    <option value="${item.methodId}">
						${item.methodName}
					    </option>
					</c:forEach>
				    </select>
				</td>
			    </tr>
			    <tr class="alert-info text-right">
				<td scope="row" colspan="7">
				    <input type="submit" value="Checkout" name="btAction" class="btn btn-primary"/>
				</td>
			    </tr>
			</form>
			</tbody>
		    </table>
		    <c:set var="checkoutError" value="${requestScope.CHECKOUT_ERROR}"/>
		    <c:if test="${not empty checkoutError.availableError}">
			<div class="alert alert-danger">
			    ${checkoutError.availableError}
			</div>
		    </c:if>
		    <c:if test="${not empty checkoutError.buyQuantityError}">
			<div class="alert alert-danger">
			    ${checkoutError.buyQuantityError}
			</div>
		    </c:if>
		</c:if>
		<c:if test="${empty cart}">
		    <h4 class="text-center my-5">
			Your cart is empty!!
		    </h4>
		</c:if>
	    </div>
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
	<script src="assets/js/script.js"></script>
    </body>

</html>