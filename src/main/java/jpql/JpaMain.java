package jpql;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            /* TypedQuery<> */
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            // many result
            List<Member> resultList = query1.getResultList();
            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }
            // one result
            Member result = query1.getSingleResult();
            System.out.println("result = " + result);

            // parameter
            Member singleResult = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();
            System.out.println("singleResult = " + singleResult.getUsername());

            /* Query */
            Query query3 = em.createQuery("select m.username, m.age from Member m");


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("==================================================");
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void logic(Member m1, Member m2) {
        System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass()));
        System.out.println("m1 instanceof Member: " + (m1 instanceof Member));
        System.out.println("m2 instanceof Member: " + (m2 instanceof Member));
    }

}
