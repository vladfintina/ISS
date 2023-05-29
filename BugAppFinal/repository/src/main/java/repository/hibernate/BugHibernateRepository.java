package repository.hibernate;

import domain.Bug;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import repository.IBugRepository;

import java.util.List;

public class BugHibernateRepository implements IBugRepository {
    public BugHibernateRepository() {
        super();
    }

    @Override
    public Bug findOne(Integer Id) {
        Bug bug = null;
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                bug = session.find(Bug.class, Id);
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
        return bug;
    }

    @Override
    public Iterable<Bug> findAll() {
        List<Bug> allBugs = null;
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                allBugs = session.createQuery("from Bug", Bug.class).list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error at findAll Bugs: " + ex);
                if(tx != null)
                    tx.rollback();
            }
        }
        finally {
            close();
        }
        return allBugs;
    }


    @Override
    public void add(Bug bug) {
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(bug);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error at adding bug: " + ex);
                if(tx != null)
                    tx.rollback();
            }
        }
        finally {
            close();
        }

    }

    @Override
    public Bug delete(Integer integer) {
        return null;
    }

    @Override
    public Bug update(Bug bug) {
        initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.update(bug);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error at update: "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        finally {
            close();
        }
        return bug;
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

    @Override
    public Bug findOneByTitle(String title) {
        Bug bug = null;
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                bug = session.createQuery("from Bug where title=:title",Bug.class).
                        setParameter("title", title).uniqueResult();
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
        return bug;
    }

    @Override
    public Iterable<Bug> findAllByStatus(String status) {
        List<Bug> allBugs = null;
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "from Bug b WHERE b.status = :status";
                Query<Bug> query = session.createQuery(hql);
                query.setParameter("status", status);
                allBugs = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error at findAll Bugs: " + ex);
                if(tx != null)
                    tx.rollback();
            }
        }
        finally {
            close();
        }
        return allBugs;
    }

    @Override
    public Iterable<Bug> findAllByRiskLevel(String riskLevel) {
        List<Bug> allBugs = null;
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "from Bug b WHERE b.riskLevel = :riskLevel";
                Query<Bug> query = session.createQuery(hql);
                query.setParameter("riskLevel", riskLevel);
                allBugs = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error at findAll Bugs: " + ex);
                if(tx != null)
                    tx.rollback();
            }
        }
        finally {
            close();
        }
        return allBugs;
    }
}
