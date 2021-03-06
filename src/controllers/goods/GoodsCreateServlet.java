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
 * Servlet implementation class GoodsCreate
 */
@WebServlet("/goods/create")
public class GoodsCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String _token = request.getParameter("_token");
		 if(_token != null && _token.equals(request.getSession().getId())) {
			EntityManager em = DBUtil.createEntityManager();

			   Goods g = new Goods();

	     	     g.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

	     	     Report r =  em.find(Report.class, (Integer.parseInt(request.getParameter("report"))));
	     	     g.setReport(r);


			em.getTransaction().begin();
			em.persist(g);
			em.getTransaction(). commit();
			em.close();

			response.sendRedirect(request.getContextPath() + "/reports/index");

		}
	}

}