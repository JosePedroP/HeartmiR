package databaseAccess;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

@ManagedBean(eager=true)
@ApplicationScoped
public class StartUpBean {
	protected SessionFactory sessionFactory = null;
	
	public StartUpBean()
	{
		super();
		try {
			
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch(Exception e)
		{e.printStackTrace();}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
