<%@ include file="header.jsp"%>
<%@ include file="navigation.jsp"%>

	<div class="container">
	<h1>Your Todos ${name}</h1>
	<div>
		<table class="table table-striped">
			<caption><spring:message code="toto.caption"/></caption>
			<thead>
				<tr>
					<td>Description</td>
					<td>Date</td>
					<td>Completed</td>
					<td>Action</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${todos}" var="todo">
					<tr>
						<td>${todo.desc}</td>
						<td>${todo.targetDate}</td>
						<td>${todo.done}</td>
						<td>
							<a type="button" class="btn btn-warning" href="/delete-todo?id=${todo.id}">Delete</a>
							<a type="button" class="btn btn-primary" href="/update-todo?id=${todo.id}">Edit</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	 <a class="btn btn-success" href="/add-todo">Add</a>
	 
	</div>

<%@ include file="footer.jsp"%>