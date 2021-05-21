<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
	<c:param name="content">
<c:if test="${flush != null}">
		<div id="flush_sucess">
			<c:out value="${flush}"></c:out>
		</div>
		</c:if>
		<h2>いいね！ 一覧</h2>
		<table id="goods_list">
			<tbody>
				<tr>
					<th class="goods_name">氏名</th>
					<th class="goods_date">日付</th>
					<th class="goods_title">タイトル</th>
					<th class="goods_action">操作</th>
				</tr>
				<c:forEach var="goods" items="${goods}" varStatus="status">
				<tr class="row${status.count % 2}">
				 <td class="goods_name"><c:out value="${goods.report.employee.name}"/></td>
				 <td class="goods_date"><fmt:formatDate value='${goods.report.report_date}' pattern='yyyy-MM-dd' /></td>
				 <td class = "goods_title">${goods.report.title}</td>
				 <td class= "goods_action"><a href="<c:url value='/reports/show?id=${goods.report.id}' />">詳細を見る</a></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>

		<div id="pagination">
			(全 ${goods_count} 件)<br />
			<c:forEach var="i" begin="1" end="${((goods_count - 1) / 15) + 1}" step="1">
			<c:choose>
				<c:when test="${i == page}">
				<c:out value="${i}" />&nbsp;
			    </c:when>
			    <c:otherwise>
			    	<a href="<c:url value='/goods/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
			    </c:otherwise>
			 </c:choose>
			</c:forEach>

		</div>
		</c:param>
</c:import>