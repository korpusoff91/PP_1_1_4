package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    @Override
    public void createUsersTable() {

        try (Session session =  getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("create table if not exists users" +
                    "(id int not null auto_increment primary key, " +
                    "name varchar(45) default null, " +
                    "lastname varchar(45) default null, " +
                    "age tinyint default null)")
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table create!");
        } catch (RuntimeException e) {
            System.out.println("Table not created");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session =  getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("drop table if exists users")
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table deleted!");
        } catch (RuntimeException e) {
            System.out.println("Table not deleted");
            e.printStackTrace();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session =  getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User saved!");
        } catch (RuntimeException e) {
            System.out.println("User not saved!");
            e.printStackTrace();
        }
    }


    @Override
    public void removeUserById(long id) {

        try (Session session =  getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
            System.out.println("User " + id + " deleted!");
        } catch (RuntimeException e) {
            System.out.println("User not deleted!");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        try (Session session =  getSessionFactory().openSession()) {
            session.beginTransaction();
            List<User> users = (List<User>) getSessionFactory().openSession()
                    .createQuery("From User").list();
            System.out.println(users.toString());
            session.getTransaction().commit();
            System.out.println("User table:");
            return users;
        } catch (RuntimeException e) {
            System.out.println("User table error");
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void cleanUsersTable() {

        try (Session session =  getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("truncate table users")
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table cleaned!");
        } catch (RuntimeException e) {
            System.out.println("Table not cleaned!");
            e.printStackTrace();
        }
    }


}