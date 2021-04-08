<%-- 
    Document   : createFood
    Created on : Jan 13, 2021, 6:44:43 PM
    Author     : BlankSpace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Food</title>
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
			    <a class="nav-link active" href="DispatchServlet?btAction=ManageFood">Manage Food</a>
			</li>
		    </c:if>
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
	    <c:set var="categoryList" value="${requestScope.CATEGORY_LIST}" />
	    <h3 class="text-center">Create Food</h3>
	    <div class="my-4">
		<form action="DispatchServlet" class="form-group" method="POST" onsubmit="return validateForm('New');">
		    <label>Name</label>
		    <input type="text" id="nameNew" name="txtName" class="form-control" placeholder="Enter name..." required/>
		    <label>Description</label>
		    <input type="text" id="descriptionNew" name="txtDescription" class="form-control" placeholder="Enter description..." required/>
		    <label>Quantity</label>
		    <input type="text" id="quantityNew" name="txtQuantity" class="form-control" placeholder="Enter quantity..." required/>	
		    <label>Price (VND)</label>
		    <input type="text" id="priceNew" name="txtPrice" class="form-control" placeholder="Enter price..." required/>
		    <label>Category</label>
		    <select name="cmbCategory" class="form-control">
			<c:forEach var="item" items="${categoryList}">
			    <option value="${item.categoryId}">
				${item.categoryName}
			    </option>
			</c:forEach>
		    </select>
		    <label>URL image</label>
		    <input type="text" name="txtImage" class="form-control" placeholder="Enter image link..." required/>	
		    <div class="text-right">
			<input type="submit" value="Save" name="btAction" class="btn btn-success my-3" />
		    </div>
		</form>
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
