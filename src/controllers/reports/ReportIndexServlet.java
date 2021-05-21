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
import models.Report;
import utils.DBUtil;
/**
 * Servlet implementation class ReportIndexServlet
 */
@WebServlet("/reports/index")
public class ReportIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();


		int page;
		try {
			page = Integer.parseInt(request.getParameter("page"));

		} catch(Exception e) {
		page = 1;
		}
	   List<Report> reports;

	 //従業員の全日報を取得しその結果をリストで受け取る
	   reports = em.createNamedQuery("getAllReports", Report.class)
				  .setFirstResult(15 * (page - 1))
				  .setMaxResults(15)
				  .getResultList();

	   long reports_count;
		 reports_count=(long)em.createNamedQuery("getReportsCount", Long.class)
			 		.getSingleResult();


		//検索キーワードresultsをパラメータから取得する
		String results = request.getParameter("results");

		if(results != null) {


			//employeeから従業員の情報を取得しその結果をリストで受け取る
			List<Employee>employees;
			 employees = em.createNamedQuery("getAllEmployeesName", Employee.class)
								 .setParameter("name", '%' + results + '%')
								// .setFirstResult(15 * (page -1))
								 //setMaxResults(15)
								 .getResultList();


		 if(employees.size() > 0) {
       //employeeから受け取った情報を使ってそれに一致する日報を取得しその結果をリストで受け取る
			 reports = em.createNamedQuery("getAllResults", Report.class)
					 .setParameter("getAllEmployeesName", employees)
					 .setFirstResult(15 * (page - 1))
					 .setMaxResults(15)
					 .getResultList();



			reports_count=(long)em.createNamedQuery("getSearchReportsCount", Long.class)
				 	    .setParameter("getAllEmployeesName", employees)
				 		.getSingleResult();


		 }else{
			 reports = null;
			 reports_count = 0;

			}

		}
		em.close();



			request.setAttribute("reports", reports);
			request.setAttribute("reports_count", reports_count);
			request.setAttribute("page", page);
			request.setAttribute("results", results);
			if(request.getSession().getAttribute("flush") != null) {
			   request.setAttribute("flush", request.getSession().getAttribute("flush"));
			   request.getSession().removeAttribute("flush");

			}

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
			rd.forward(request, response);
		}

	}

