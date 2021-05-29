<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
	<c:param name="content">
		<c:choose>
			<c:when test="${report != null}">
				<h2>日報 詳細ページ</h2>

				<table>
					<tbody>
						<tr>
							<th>氏名</th>
							<td><c:out value="${report.employee.name}" /></td>
						</tr>
						<tr>
							<th>日付</th>
							<td><fmt:formatDate value="${report.report_date}"
									pattern="yyyy-MM-dd" /></td>
						</tr>
						<tr>
							<th>内容</th>
							<td><pre>
									<c:out value="${report.content}" />
								</pre></td>
						</tr>
						<tr>
							<th>登録日時</th>
							<td><fmt:formatDate value="${report.created_at}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
						<tr>
							<th>更新日時</th>
							<td>
							<fmt:formatDate value="${report.updated_at}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
					</tbody>
				</table>

				<c:choose>
				<c:when test ="${goods.size() != 1}" >
				<form method="POST" action="<c:url value='/goods/create' />">
					<input type="hidden" name="report" value="${report.id}" />
					<input type="hidden" name="_token" value="${_token}" />
					<button class="Likes-Icon" type="submit">いいね！</button>
				</form>
				</c:when>
				  <c:otherwise>
					<form method="POST" action="<c:url value='/goods/destroy' />">
					<input type="hidden" name="report" value="${report.id}" />
					<input type="hidden" name="_token" value="${_token}" />
					<button class="Likes-Icon" type="submit">いいね解除</button>
					</form>
				 </c:otherwise>
				 </c:choose>

				<c:if test="${sessionScope.login_employee.id == report.employee.id}">
					<p>
						<a href="<c:url value="/reports/edit?id=${report.id}" />">この日報を編集する</a>
					</p>
				</c:if>
			</c:when>
			<c:otherwise>
				<h2>お探しのデータは見つかりませんでした。</h2>
			</c:otherwise>
		</c:choose>

	<c:forEach var="comment" items="${comments}" varStatus="status">
		<c:out value="${comment.report.employee.name}" /><br />
		<c:out value="${comment.content}" /><br />
		<fmt:formatDate value="${comment.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
		 <p><a href="#" onclick="confirmDestroy();">削除</a></p>
                <form method="POST" action="<c:url value='/comment/destroy' />">
                    <input type="hidden" name="_token" value="${_token}" />
                    <input type="hidden" name="comment" value="${comment.id}" />
                </form><br /><br />
                <script>
                    function confirmDestroy() {
                        if(confirm("本当に削除してよろしいですか？")) {
                            document.forms[1].submit();
                        }
                    }
                </script>
	</c:forEach>

		<form method="POST" action="<c:url value='/comment/create' />">
		<label for="name">氏名</label><br />
		<c:out value="${sessionScope.login_employee.name}" />
		<br /><br />

		<label for="content">コメント</label><br />
		<textarea name="content" rows="10" cols="15"></textarea>
		<input type="hidden" name="_token" value="${_token}" />
		<input type="hidden" name="report" value="${report.id}" />
		<button type="submit">投稿</button>
		</form>

		<p>
			<a href="<c:url value="/reports/index" />">一覧に戻る</a>
		</p>

	</c:param>
</c:import>