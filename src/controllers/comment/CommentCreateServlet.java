package controllers.comment;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import models.Employee;
import models.Report;
import models.validators.CommentValidator;
import utils.DBUtil;

/**
 * Servlet implementation class CommentCreateServlet
 */
@WebServlet("/comment/create")
public class CommentCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentCreateServlet() {
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

			Comment c = new Comment();

			c.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

			Report r = em.find(Report.class, (Integer.parseInt(request.getParameter("report"))));
			c.setReport(r);

			c.setContent(request.getParameter("content"));


			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			c.setCreated_at(currentTime);


			List<String> errors = CommentValidator.validate(c);
			if(errors.size() > 0) {
				em.close();

				request.setAttribute("_token", request.getSession().getId());
				request.setAttribute("comment", c);
				request.setAttribute("errors", errors);

				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
				rd.forward(request, response);
			} else {
				em.getTransaction().begin();
				em.persist(c);
				em.getTransaction().commit();
				em.close();

				 request.getSession().setAttribute("flush", "??????????????????????????????");

	                response.sendRedirect(request.getContextPath() + "/reports/index");
	            }
	        }

			}




}
