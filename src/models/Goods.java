package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "goods")

@NamedQueries({
	@NamedQuery(
			name = "getAllGoods",
			query = "SELECT g FROM Goods AS g WHERE  g.employee = :login_employee ORDER BY g.id DESC"
		  ),
	@NamedQuery(
			name = "getGoodsCount",
			query = "SELECT COUNT(g) FROM Goods AS g WHERE g.employee = :login_employee"
			),
	@NamedQuery(
			name = "getGoodsInformation",
			query = "SELECT g FROM Goods AS g WHERE g.report = :id AND g.employee = :login_employee ORDER BY g.id DESC"
			),
	@NamedQuery(
			name = "getGoodsInformationCount",
			query = "SELECT COUNT(g) FROM Goods AS g WHERE g.report = :id AND g.employee = :login_employee"
			)

	   })

@Entity
public class Goods {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "report_id", nullable = false)
	private Report report;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}


	}