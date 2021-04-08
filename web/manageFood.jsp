<%-- 
    Document   : manageFood
    Created on : Jan 9, 2021, 3:02:45 PM
    Author     : BlankSpace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Manage Page</title>
	<link rel="stylesheet" href="assets/css/customize.css">
	<link rel="stylesheet" href="assets/css/bootstrap.min.css">
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

	<div class="container">
	    <!-- search form -->
	    <c:set var="searchValue" value="${param.txtSearchValue}" />
	    <c:set var="searchPrice" value="${param.cmbPrice}" />
	    <c:set var="searchCategory" value="${param.cmbCategory}" />
	    <c:set var="categoryList" value="${requestScope.CATEGORY_LIST}" />
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
			<a href="DispatchServlet?btAction=CreateFoodPage" class="btn btn-primary">Create Food</a>
			<input type="submit" value="Search" name="btAction" class="btn btn-success my-3" />
			<input type="hidden" name="destination" value="manage-food" />
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
			<div class="col-md-6 my-3">
			    <div class="card h-100">

				<h6 class="card-header">
				    ${item.food.name}
				</h6>
				<img src="${item.food.image}" class="card-img-top manage-img-size" />
				<form action="DispatchServlet" method="POST" class="manage-form" onsubmit="return validateForm('${item.food.itemId}');">
				    <div class="card-body">
					<div class="form-group">
					    <label>Name:</label>
					    <input type="text" id="name${item.food.itemId}" name="txtName" value="${item.food.name}" placeholder="(eg 1 - 50 chars)" class="form-control" required/>
					</div>
					<div class="form-group">
					    <label>Description:</label>
					    <input type="text" id="description${item.food.itemId}" name="txtDescription" value="${item.food.description}" placeholder="(eg 1 - 200 chars)" class="form-control" required/>
					</div>
					<div class="form-group">
					    <label>Category:</label>
					    <select name="cmbCategory" class="form-control">
						<c:forEach var="category" items="${categoryList}">
						    <option value="${category.categoryId}"
							    <c:if test="${item.food.caId eq category.categoryId}">
								selected="true"
							    </c:if>>
							${category.categoryName}
						    </option>
						</c:forEach>
					    </select>
					</div>
					<div class="form-group">
					    <label>Quantity:</label>
					    <input type="text" id="quantity${item.food.itemId}" name="txtQuantity" value="${item.food.quantity}" placeholder="(eg 1...15 chars)" class="form-control" required/>
					</div>
					<div class="form-group">
					    <label>Price:</label>
					    <input type="text" id="price${item.food.itemId}" name="txtPrice" value="${item.food.price}" placeholder="(enter price of item...)" class="form-control" required/>
					</div>
					<div class="form-group">
					    <label>URL Image:</label>
					    <input type="text" name="txtImage" value="${item.food.image}" placeholder="(input a image link...)" class="form-control" required/>
					</div>
					<div>
					    <span style="font-weight: bold">Created Date: </span> ${item.food.createDate}
					</div>
					<div>
					    <span style="font-weight: bold">Status: </span>
					    <c:if test="${item.food.status}">
						<span style="font-weight: bold; color: green">Active</span>
					    </c:if>
					    <c:if test="${!item.food.status}">
						<span style="font-weight: bold; color: red">Inactive</span>
					    </c:if>
					</div>
				    </div>
				    <div class="card-footer">
					<div class="text-center">
					    <input type="hidden" name="itemId" value="${item.food.itemId}" />
					    <input type="hidden" name="txtSearchValue" value="${searchValue}" />
					    <input type="hidden" name="cmbPrice" value="${searchPrice}" />
					    <input type="hidden" name="cmbCategory" value="${searchCategory}" />
					    <input type="submit" value="Update Food" name="btAction" class="btn btn-success" />

					    <c:set var="urlRewriting" value="DeleteFoodServlet?itemId=${item.food.itemId}&txtSearchValue=${searchValue}&cmbPrice=${searchPrice}&cmbCategory=${searchCategory}&destination=manage-food" />
					    <a class="btn btn-danger" href="${urlRewriting}" onclick="return confirm('Do you really want to delete?');">Delete Food</a>
					</div>
				    </div>
				</form>
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
			<input type="hidden" name="destination" value="manage-food" />
			<c:forEach var="page" begin="1" end="${totalPages}" step="1">
			    <c:set var="urlPaging" value="DispatchServlet?txtSearchValue=${searchValue}&cmbPrice=${searchPrice}&cmbCategory=${searchCategory}&page=${page}&destination=manage-food&btAction=Search" />
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
		<h2 class="text-center">No Food Found!!</h2>
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
	<script src="assets/js/script.js"></script>
    </body>

</html>