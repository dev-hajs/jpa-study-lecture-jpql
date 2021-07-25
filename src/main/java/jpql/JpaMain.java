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

            em.flush();
            em.clear();

//            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
//            Member findMember = result.get(0);
//            findMember.setAge(20);

//            List resultList = em.createQuery("select m.username, m.age from Member m").getResultList();
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;
//            for (Object o1 : result) {
//                System.out.println("o1 = " + o1);
//            }

//            List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m").getResultList();
//            Object[] result = resultList.get(0);
//            for (Object o : result) {
//                System.out.println("o = " + o);
//            }

            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                .getResultList();
            for (MemberDTO memberDTO : resultList) {
                System.out.println("memberDTO.usename = " + memberDTO.getUsername());
                System.out.println("memberDTO.age = " + memberDTO.getAge());
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
