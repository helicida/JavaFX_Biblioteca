package sample;

import org.hibernate.*;
import sample.Objetos.Llibre;
import sample.Objetos.Prestec;
import sample.Objetos.Soci;

import java.util.ArrayList;

/**
 * Created by 46465442z on 24/01/16.
 */
public class DAO {

    private Session session;
    private Transaction transaction;

    // Metodos para anyadir información a la BBDD

    public void afegirLlibre(Llibre llibre) throws HibernateException {

        try  {
            // Con estas dos lineas hacemos la conexión a nuestra BBDD
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Guardamos el libro
            session.save(llibre);
            transaction.commit();
        }

        catch (HibernateException one) {
            transaction.rollback();
            throw new HibernateException("Error afegint el llibre", one);
        }
        finally {
            session.close();
        }
    }

    public void afegirSoci(Soci soci) throws HibernateException {

        try {
            // Con estas dos lineas hacemos la conexión a nuestra BBDD
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Guardamos los socios
            session.save(soci);
            transaction.commit();
        }
        catch (HibernateException one) {
            transaction.rollback();
            throw new HibernateException("Error afegint el soci", one);
        }

        finally {
            session.close();
        }
    }

    public void afegirPrestec(Prestec prestec) throws HibernateException {

        try {
            // Con estas dos lineas hacemos la conexión a nuestra BBDD
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Guaradamos los prestamos
            session.save(prestec);
            transaction.commit();
        }
        catch (HibernateException one) {
            transaction.rollback();
            throw new HibernateException("Error afegint el prestec", one);
        }
        finally {
            session.close();
        }
    }

    // Metodos para modificar informacion de la BBDD

    public void modificarLlibre(Llibre llibre) throws HibernateException {

        try  {
            // Con estas dos lineas hacemos la conexión a nuestra BBDD
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Guardamos el libro
            session.update(llibre);
            transaction.commit();
        }

        catch (HibernateException one) {
            transaction.rollback();
            throw new HibernateException("Error actualitzant el llibre", one);
        }
        finally {
            session.close();
        }
    }

    public void modificarSoci(Soci soci) throws HibernateException {

        try  {
            // Con estas dos lineas hacemos la conexión a nuestra BBDD
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Guardamos el libro
            session.update(soci);
            transaction.commit();
        }

        catch (HibernateException one) {
            transaction.rollback();
            throw new HibernateException("Error actualitzant el llibre", one);
        }
        finally {
            session.close();
        }
    }

    // Métodos para obtener información de la BBDD

    public ArrayList<Llibre> obtenirLlibres() {

        // Con estas dos lineas hacemos la conexión a nuestra BBDD
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        // Obtenemos todos los libros
        Query query = session.createQuery("FROM Llibre ");
        return (ArrayList<Llibre>) query.list();
    }

    public ArrayList<Soci> obtenirSocis() {

        // Con estas dos lineas hacemos la conexión a nuestra BBDD
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        // Obtenemos todos los socios
        Query query = session.createQuery("FROM Soci ");
        return (ArrayList<Soci>) query.list();
    }

    public ArrayList<Prestec> obtenirPrestecs() {

        // Con estas dos lineas hacemos la conexión a nuestra BBDD
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        // Obtenemos todos los prestamos
        Query query = session.createQuery("FROM Prestec ");
        return (ArrayList<Prestec>) query.list();
    }

    // Métodos para eliminar información de la BBDD

    public boolean eliminarTotsElsLlibres() {

        try {
            // Con estas dos lineas hacemos la conexión a nuestra BBDD
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Eliminamos todos los libros
            session.createQuery("DELETE FROM Llibre").executeUpdate();
            transaction.commit();
            session.close();
            return true;
        }
        catch (Exception one){
            return false;
        }
    }

    public boolean eliminarTotsElsSocis()  {

        try {
            // Con estas dos lineas hacemos la conexión a nuestra BBDD
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Eliminamos todos los socios
            session.createQuery("DELETE FROM Soci").executeUpdate();
            transaction.commit();
            session.close();
            return true;
        }
        catch (Exception one){
            return false;
        }
    }

    public boolean eliminarTotsElsPrestecs() {

        try  {
            // Con estas dos lineas hacemos la conexión a nuestra BBDD
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Eliminamos todos los prestamos
            session.createQuery("DELETE FROM Prestec").executeUpdate();
            transaction.commit();
            session.close();
            return true;
        }
        catch (Exception one){
            return false;
        }
    }

    public boolean eliminarLlibre(Llibre llibre) {

        try {
            // Con estas dos lineas hacemos la conexión a nuestra BBDD
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Eliminamos el libro
            session.delete(llibre);
            transaction.commit();
            session.close();
            return true;
        }
        catch (Exception one){return false;}
    }

    public boolean eliminarSoci(Soci soci) {

        try {
            // Con estas dos lineas hacemos la conexión a nuestra BBDD
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Eliminamos el libro
            session.delete(soci);
            transaction.commit();
            session.close();
            return true;
        }
        catch (Exception one){return false;}
    }

    public boolean eliminarPrestec(Prestec prestec) {

        try {
            // Con estas dos lineas hacemos la conexión a nuestra BBDD
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Eliminamos el libro
            session.delete(prestec);
            transaction.commit();
            session.close();
            return true;
        }
        catch (Exception one){return false;}
    }
}