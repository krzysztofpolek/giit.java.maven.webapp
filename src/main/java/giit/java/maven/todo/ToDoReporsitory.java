package giit.java.maven.todo;

import giit.java.maven.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

class ToDoReporsitory {
    List<ToDo> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<ToDo> result = session.createQuery("from ToDo", ToDo.class).list();

        transaction.commit();
        session.close();
        return  result;
    }

    ToDo toggleTodo(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        ToDo result  = session.get(ToDo.class, id);
        result.setDone(!result.isDone());

        transaction.commit();
        session.close();

        return result;
    }

    ToDo addTodo(ToDo newToDo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(newToDo);
        transaction.commit();
        session.close();

        return newToDo;
    }
}
