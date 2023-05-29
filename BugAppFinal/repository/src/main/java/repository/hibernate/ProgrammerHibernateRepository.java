package repository.hibernate;

import domain.Programmer;
import domain.Tester;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.IProgrammerRepository;

public class ProgrammerHibernateRepository implements IProgrammerRepository {
    @Override
    public Programmer findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Programmer> findAll() {
        return null;
    }

    @Override
    public void add(Programmer entity) {

    }
    @Override
    public Programmer delete(Integer integer) {
        return null;
    }

    @Override
    public Programmer update(Programmer entity) {
        return null;
    }

    public Programmer getAccount(String username, String password){
        Programmer programmer = null;
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                programmer = session.createQuery("from Programmer where username=:username and password=:password", Programmer.class).
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
        return programmer;
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
