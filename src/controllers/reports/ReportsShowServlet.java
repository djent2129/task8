package controllers.reports;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();



		Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

		Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));


	    List<Goods> goods = em.createNamedQuery("getGoodsInformation", Goods.class)
	    		.setParameter("id", r)
	    		.setParameter("login_employee", login_employee)
				.getResultList();

	    long goods_count = (long)em.createNamedQuery("getGoodsInformationCount", Long.class)
	    		.setParameter("id", r)
	    		.setParameter("login_employee", login_employee)
	    		.getSingleResult();



	    em.close();
	    request.setAttribute("goods", goods);
	    request.setAttribute("goods_count", goods_count);

		request.setAttribute("report", r);
		request.setAttribute("_token",  request.getSession().getId());


		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
		rd.forward(request, response);
	}
}
