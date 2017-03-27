package pl.pyda;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.pyda.entity.SimpleEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author jpyda on 25/03/2017.
 */
@Slf4j
class HibernateDirtyCheckingExample {
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private EntityTransaction tx = null;

	void testDirtyChecking() {
		try {
			emf = Persistence.createEntityManagerFactory("com.pyda.hibernate");
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			SimpleEntity simpleEntity = new SimpleEntity("name", 1L);
			em.persist(simpleEntity);

			em.flush();
			tx.commit();
			em.close();
			em = null;
			log.info("-----Put in DB-----");

			em = emf.createEntityManager();
			SimpleEntity result = em.find(SimpleEntity.class, simpleEntity.getId());
			log.info("-----Read " + result.getName() + "-----");
			em.close();
			emf.close();


			log.info("\n\n---Testing dirty checking---");
			SessionFactory factory = new Configuration().configure().buildSessionFactory();
			Session session = factory.withOptions().interceptor(new UpdateInterceptor()).openSession();
			session.beginTransaction();
			//result = (Employee) session.get(Employee.class, employee.getId());
			//result = (Employee) session.merge(result);
			result.setName("NEW NAME");
			session.saveOrUpdate(result);
			session.getTransaction().commit();
			session.close();

			emf = Persistence.createEntityManagerFactory("com.pyda.hibernate");
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			SimpleEntity result2 = em.find(SimpleEntity.class, simpleEntity.getId());
			log.info("-----Read " + result2.getName() + "-----");
			assert result.getName().equals(result2.getName());
			em.close();
			em = null;
			emf.close();
			emf = null;

		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) tx.rollback();
			System.exit(1);
		} finally {
			if (em != null)
				em.close();
			if (emf != null)
				emf.close();
		}
		log.info("Finished execution");
	}

	public static class UpdateInterceptor extends EmptyInterceptor {
		@Override
		public Boolean isTransient(Object entity) {
			log.info(entity + ": isTransient called");
			return Boolean.FALSE;
		}
	}
}
