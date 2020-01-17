package giit.java.maven;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class LangRepository {
    private List<Lang> languages;
    Optional<Lang> findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Lang result = session.get(Lang.class, id);
        transaction.commit();
        session.close();
        return Optional.ofNullable(result);
    }
}
