package com.rodrigo.contactapp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import com.rodrigo.contactapp.model.Contact;
import static com.rodrigo.contactapp.model.Contact.*;

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
        System.out.println(contact);

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
