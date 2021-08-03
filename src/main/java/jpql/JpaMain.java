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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
//            member.setUsername(null);
            member.setUsername("관리자");
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // 기본 CASE 식, 나중에 QueryDSL 사용하면 자바코드로 편하게 짤 수 있음
//            String query =
//                "SELECT " +
//                    "CASE WHEN m.age <= 10 THEN '학생요금' " +
//                    "     WHEN m.age >= 60 THEN '경로요금' " +
//                    "     ELSE '일반요금' " +
//                    "END " +
//                "FROM Member m";

            String query =
//                "SELECT COALESCE(m.username, '이름 없는 회원') FROM Member m";
                "SELECT NULLIF(m.username, '관리자') FROM Member m";


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
