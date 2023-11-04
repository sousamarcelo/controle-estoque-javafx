package modelo.dao;

import db.DB;

public class DaoFactory {
	
	public static ProductDaoJDBC createProductDao() {
		return new ProductDaoJDBC(DB.getConnection());
	}
	
}
