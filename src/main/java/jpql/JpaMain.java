package jpql;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.OrderColumn;
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

            Member member1 = new Member();
            member1.setUsername("관리자1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            em.persist(member2);

            em.flush();
            em.clear();

//            String query = "select concat('a','b') from Member m";                // ab
//            String query = "select substring(m.username, 2, 3) from Member m";    // 리자
//            String query = "select locate('de', 'abcdefg') from Member m";        // 4 - Integer
//            String query = "select size(t.members) from Team t";                  // 0 - Integer

//            String query = "select function('group_concat', m.username) from Member m";
            String query = "select group_concat(m.username) from Member m";
            List<String> resultList = em.createQuery(query, String.class)
                .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }

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
