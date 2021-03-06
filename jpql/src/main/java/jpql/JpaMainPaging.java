package jpql;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainPaging {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {

			for (int i = 0; i < 100; i++) {
				Member member = new Member();
				member.setUsername("member"+i);
				member.setAge(i);
				em.persist(member);
			}

			em.flush();
			em.clear();

			List<Member> resultList = em.createQuery("select m from Member m order by m.age desc",
					Member.class)
				.setFirstResult(0)
				.setMaxResults(10)
				.getResultList();

			System.out.println("resultList.size() = " + resultList.size());
			for (Member member : resultList) {
				System.out.println("member = " + member);
			}

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();


	}
}
