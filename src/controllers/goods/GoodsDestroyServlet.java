package controllers.goods;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Goods;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class GoodsDestroyServlet
 */
@WebServlet("/goods/destroy")
public class GoodsDestroyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GoodsDestroyServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String _token = request.getParameter("_token");
		if (_token != null && _token.equals(request.getSession().getId())) {
			EntityManager em = DBUtil.createEntityManager();




			Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

			Report r = em.find(Report.class, Integer.parseInt(request.getParameter("report")));


			Goods g = em.createNamedQuery("getGoodsInformation", Goods.class)
					.setParameter("id", r)
					.setParameter("login_employee", login_employee)
					.getSingleResult();





			em.getTransaction().begin();
			em.remove(g);
			em.getTransaction().commit();
			em.close();

			request.getSession().removeAttribute("goods_id");

			response.sendRedirect(request.getContextPath() + "/reports/index");

		}
	}

}
