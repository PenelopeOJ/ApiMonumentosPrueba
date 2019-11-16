package com.turismo.apimonumentos.util;

import java.util.List;

import javax.ws.rs.NotFoundException;

import org.hibernate.HibernateException;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;

import com.turismo.apimonumentos.models.Usuario;

public class UsuarioDAO implements DAO<Usuario> {

	private Session session = null;
	private Transaction tx = null;

	/**
	 * 
	 * @return
	 * @throws HibernateException
	 */
	public Session getSession() throws HibernateException {

		session = DatabaseConnector.getSession();
		return session;
	}

	/**
	 * 
	 * @throws HibernateException
	 */
	public void closeSession() throws HibernateException {

		DatabaseConnector.closeSession(session);
	}

	/**
	 * 
	 * @return
	 * @throws HibernateException
	 * @throws TransactionException
	 */
	public Session getSessionAndTransaction() throws HibernateException, TransactionException {

		session = DatabaseConnector.getSession();
		tx = session.beginTransaction();
		return session;
	}

	/**
	 * 
	 * @throws HibernateException
	 * @throws TransactionException
	 */
	public void closeSessionAndTransaction() throws HibernateException, TransactionException {

		tx.commit();
		DatabaseConnector.closeSession(session);
	}

	/**
	 * 
	 */
	public void rollback() {

		if (tx != null) {
			tx.rollback();
		}
	}

	/**
	 * Recibe un objeto tipo Usuario para el cual busca en base de datos si hay
	 * algún registro de email que coincida con el del registro introducido. En caso
	 * de existir ya un registro con el mismo email regresa booleano para indicar
	 * que fue imposible crear un usuario nuevo.
	 * 
	 * @param u
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean persist(Usuario u) {

		u.setVisitado(true);
		Usuario usuarioDB = null;
		String query = "FROM usuarios u WHERE u.email LIKE :email";
		List<Usuario> lista = session.createQuery(query).setParameter("email", '%' + u.getEmail() + '%')
				.setMaxResults(1).getResultList();

		if (lista.isEmpty()) {
			session.save(u);
		} else if (!lista.isEmpty() && !lista.get(0).isActivo()) {
			usuarioDB = lista.get(0);
			u.setId(usuarioDB.getId());
			usuarioDB.actualizar(u);
			session.update(usuarioDB);
		} else if (!lista.isEmpty() && lista.get(0).isActivo()) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getAll() throws QueryException {
		List<Usuario> listaUsuarios = null;
		listaUsuarios = session.createQuery("FROM usuarios u WHERE u.activo=true").list();
		return listaUsuarios;
	}

	/**
	 * 
	 * @param n
	 * @return listaUsuarios
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuariosByActividad(int n) {
		List<Usuario> listaUsuarios = null;
		String query = "FROM usuarios u WHERE u.activo=true ORDER BY u.cantidad_monumentos DESC";
		listaUsuarios = session.createQuery(query).setMaxResults(n).list();

		return listaUsuarios;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getByNombre(String nombre) {
		List<Usuario> listaUsuarios = null;
		String query = "FROM usuarios u WHERE u.nombre LIKE :nombre";
		listaUsuarios = session.createQuery(query).setParameter("nombre", '%' + nombre + '%').list();
		return listaUsuarios;
	}

	@Override
	public void update(Usuario u) {
		Usuario usuarioDB = null;
		usuarioDB = session.find(Usuario.class, u.getId());
		if (usuarioDB != null) {
			usuarioDB.actualizar(u);
			session.update(usuarioDB);
		}

	}

	/**
	 * @param id
	 */
	@Override
	public void delete(int id) {
		Usuario usuarioDB = null;
		usuarioDB = session.find(Usuario.class, id);
		if (usuarioDB != null) {
			usuarioDB.setActivo(false);
			session.update(usuarioDB);
		} else {
			throw new NotFoundException();
		}
	}
}
