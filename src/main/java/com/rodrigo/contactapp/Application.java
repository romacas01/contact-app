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

        save(contact);
        save(contact2);

        listContacts().forEach(System.out::println);

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

    private static void save(Contact contact) {
        //Open session
        Session session = sessionFactory.openSession();

        //Begin transaction
        session.beginTransaction();

        //Save the contact
        session.save(contact);

        //Commit transaction
        session.getTransaction().commit();

        //Close session
        session.close();
    }
}
