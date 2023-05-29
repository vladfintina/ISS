package repository.hibernate;

import domain.Tester;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.ITesterRepository;

public class TesterHibernateRepository implements ITesterRepository {
    @Override
    public Tester findOne(Integer integer) {
        return null;
    }
    @Override
    public Iterable<Tester> findAll() {
        return null;
    }

    @Override
    public void add(Tester tester) {
        initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(tester);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error at add tester: "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        finally {
            close();
        }
    }

    @Override
    public Tester delete(Integer integer) {
        return null;
    }

    @Override
    public Tester update(Tester entity) {
        return null;
    }

    public Tester getAccount(String username, String password){
        Tester tester = null;
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                tester = session.createQuery("from Tester where username=:username and password=:password", Tester.class).
                        setParameter("username", username).setParameter("password", password).setMaxResults(1).uniqueResult();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error at findOne Bugs: " + ex);
                if(tx != null)
                    tx.rollback();
            }
        }
        finally {
            close();
        }
        return tester;
    }

    private static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exceptie "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }
    static void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }
}
