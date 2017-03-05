package com.rodrigo.contactapp;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import com.rodrigo.contactapp.model.Contact;
import static com.rodrigo.contactapp.model.Contact.*;

import java.util.List;

public class Application {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args) {

        Contact contact = new ContactBuilder("Rodrigo", "Castro")
            .withEmail("romacas@hotmail.co.uk")
            .withPhone(12345678L)
            .build();
        Contact contact2 = new ContactBuilder("Kate", "Lee")
            .withEmail("katelee@hotmail.co.uk")
            .withPhone(12345678L)
            .build();

        int id1 = save(contact);
        int id2 = save(contact2);

        //System.out.println(findById(2));
        System.out.println("Contacts before the update");
        listContacts().forEach(System.out::println);

        Contact con = findById(id2);
        con.setEmail("wwwww150@naver.com");

        System.out.println("Beginning update...");
        update(con);
        System.out.println("Update finished.");

        System.out.println("Contacts before the update");
        listContacts().forEach(System.out::println);

        System.out.println("Beginning delete...");
        delete(contact);
        System.out.println("Deleting finished.");

        System.out.println("Contacts after deleting.");
        listContacts().forEach(System.out::println);

    }

    private static Contact findById(int id) {
        Session session = sessionFactory.openSession();
        Contact contact = session.get(Contact.class, id);
        session.close();
        return contact;
    }

    @SuppressWarnings("unchecked")
    private static List<Contact> listContacts() {
        Session session = sessionFactory.openSession();

        //Create criteria
        Criteria criteria = session.createCriteria(Contact.class);

        List<Contact> contacts = criteria.list();

        session.close();

        return contacts;
    }

    private static void update(Contact contact) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.update(contact);
        session.getTransaction().commit();

        session.close();
    }

    private static int save(Contact contact) {
        //Open session
        Session session = sessionFactory.openSession();

        //Begin transaction
        session.beginTransaction();

        //Save the contact
        int id = (int) session.save(contact);

        //Commit transaction
        session.getTransaction().commit();

        //Close session
        session.close();
        return id;
    }

    private static void delete(Contact contact) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.delete(contact);
        session.getTransaction().commit();

        session.close();
    }
}
