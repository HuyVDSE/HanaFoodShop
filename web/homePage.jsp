<%-- 
    Document   : searchFood
    Created on : Jan 5, 2021, 10:49:20 PM
    Author     : BlankSpace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Search Food Page</title>
	<link rel="stylesheet" href="assets/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/css/customize.css">
    </head>

    <body>
	<c:set var="account" value="${sessionScope.ACCOUNT}" />
	<c:set var="cart" value="${sessionScope.CUS_CART}" />
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
			<a class="nav-link active" href="DispatchServlet?btAction=Home">Home</a>
		    </li>
		    <c:if test="${account.isAdmin}">
			<li class="nav-item">
			    <a class="nav-link" href="DispatchServlet?btAction=ManageFood">Manage Food</a>
			</li>
			<li class="nav-item">
			    <a class="nav-link" href="DispatchServlet?btAction=TrackOrder">Order History</a>
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
			<li class="nav-item">
			    <a class="nav-link" href="DispatchServlet?btAction=TrackOrder">Order History</a>
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
	    <!-- search form -->
	    <c:set var="searchValue" value="${param.txtSearchValue}" />
	    <c:set var="searchPrice" value="${param.cmbPrice}" />
	    <c:set var="searchCategory" value="${param.cmbCategory}" />
	    <c:set var="categoryList" value="${requestScope.CATEGORY_LIST}" />
	    <div class="my-4">
		<div class="my-4">
		    <form action="DispatchServlet" class="form-group" method="POST">
			<label>Name</label>
			<input type="text" name="txtSearchValue" value="${searchValue}" class="form-control" placeholder="Search Food..." />
			<label>Price Range (VND)</label>
			<select name="cmbPrice" class="form-control">
			    <option></option>
			    <option>10000 - 50000</option>
			    <option>51000 - 100000</option>
			    <option>101000 - 150000</option>
			    <option>151000 - 200000</option>
			    <option>201000 - 250000</option>
			    <option>251000 - 300000</option>
			    <option>>300000</option>
			</select>
			<label>Category</label>
			<select name="cmbCategory" class="form-control">
			    <option></option>
			    <c:forEach var="item" items="${categoryList}">
				<option value="${item.categoryId}"
					<c:if test="${item.categoryId eq searchCategory}">
					    selected="true"
					</c:if>>
				    ${item.categoryName}
				</option>
			    </c:forEach>
			</select>
			<div class="text-right">
			    <input type="submit" value="Search" name="btAction" class="btn btn-success my-3" />
			    <input type="hidden" name="destination" value="home" />
			</div>
		    </form>
		</div>
		<!--Display result-->
		<c:set var="result" value="${requestScope.LIST_ITEMS}" />
		<c:set var="totalPages" value="${requestScope.TOTAL_PAGES}" />

		<c:if test="${not empty result}">
		    <h3 class="text-center">Food</h3>
		    <!--Display Food-->
		    <div class="row justify-content-center">
			<c:forEach var="item" items="${result}">
			    <div class="col-12 col-md-6 col-lg-3 my-4">
				<div class="card h-100">
				    <h6 class="card-header">
					${item.food.name}
				    </h6>
				    <img src="${item.food.image}" class="card-img-top" />
				    <div class="card-body">
					<div>
					    <span style="font-weight: bold">Description: </span> ${item.food.description}
					</div>
					<div>
					    <span style="font-weight: bold">Category: </span> ${item.categoryName}
					</div>
					<div>
					    <span style="font-weight: bold">Created Date: </span> ${item.food.createDate}
					</div>
					<div>
					    <span style="font-weight: bold">Price: </span>
					    <span style="font-weight: bold; color: red">${item.food.price} VND</span>
					</div>
				    </div>
				    <c:if test="${!account.isAdmin or empty account}">
					<div class="card-footer">
					    <div class="text-center">
						<form action="DispatchServlet">
						    <input type="hidden" name="itemId" value="${item.food.itemId}" />
						    <input type="hidden" name="searchValue" value="${searchValue}" />
						    <input type="hidden" name="cmbCategory" value="${searchCategory}" />
						    <input type="hidden" name="cmbPrice" value="${searchPrice}" />
						    <input type="hidden" name="destination" value="home" />
						    <input type="hidden" name="page" value="${param.page}" />
						    <!-- hidden text for redirect Home page -->
						    <input type="hidden" name="destination" value="home" />
						    <input type="submit" value="Add to cart" name="btAction" class="btn btn-success" />
						</form>
					    </div>
					</div>
				    </c:if>
				</div>
			    </div>
			</c:forEach>
		    </div>

		    <!-- get current page -->
		    <c:set var="curPage" value="${requestScope.LOAD_ALL_DEFAULTE_PAGE}" />
		    <c:if test="${empty curPage}">
			<c:set var="curPage" value="${param.page}" />
		    </c:if>
		    <!-- Page indicator -->
		    <nav>
			<ul class="pagination justify-content-center mt-4">
			    <c:forEach var="page" begin="1" end="${totalPages}" step="1">
				<c:set var="urlPaging" value="DispatchServlet?txtSearchValue=${searchValue}&cmbPrice=${searchPrice}&cmbCategory=${searchCategory}&page=${page}&destination=home&btAction=Search" />
				<c:if test="${curPage eq page}">
				    <li class="page-item active">
					<a class="page-link" href="${urlPaging}">${page}</a>
				    </li>
				</c:if>
				<c:if test="${curPage ne page}">
				    <li class="page-item">
					<a class="page-link" href="${urlPaging}">${page}</a>
				    </li>
				</c:if>
			    </c:forEach>
			</ul>
		    </nav>
		</c:if>

		<c:if test="${empty result}">
		    <h2 class="text-center">No food found!!!</h2>
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
    </body>

</html>