package com.turismo.apimonumentos.util;

import java.util.Date;
import java.util.List;

import javax.ws.rs.NotFoundException;

import org.hibernate.HibernateException;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;

import com.turismo.apimonumentos.models.Monumento;

public class MonumentoDAO implements DAO<Monumento> {

	private Session session = null;
	private Transaction tx = null;

	public Session getSession() throws HibernateException {
		session = DatabaseConnector.getSession();
		return session;
	}

	public void closeSession() throws HibernateException {
		DatabaseConnector.closeSession(session);
	}

	public Session getSessionAndTransaction() throws HibernateException, TransactionException {
		session = DatabaseConnector.getSession();
		tx = session.beginTransaction();
		return session;
	}

	public void closeSessionAndTransaction() throws TransactionException, HibernateException {
		tx.commit();
		DatabaseConnector.closeSession(session);
	}

	public void rollback() {
		if (tx != null) {
			tx.rollback();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean persist(Monumento m) {
		m.setFechaRegistro(new Date());
		m.setMostrable(true);
		Monumento monumentoDB = null;
		String query = "FROM Monumento m WHERE m.nombre LIKE :nombre";
		List<Monumento> lista = session.createQuery(query).setParameter("nombre", '%' + m.getNombre() + '%')
				.setMaxResults(1).getResultList();

		if (lista.isEmpty()) {
			session.save(m);
		} else if (!lista.isEmpty() && !lista.get(0).isMostrable()) {
			monumentoDB = lista.get(0);
			m.setId(monumentoDB.getId());
			monumentoDB.actualizar(m);
			session.update(monumentoDB);
		} else if (!lista.isEmpty() && lista.get(0).isMostrable()) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Monumento> getAll() throws QueryException {
		List<Monumento> listaMonumentos = null;
		listaMonumentos = session.createQuery("FROM Monumento m WHERE m.mostrable=true").list();
		return listaMonumentos;
	}

	@SuppressWarnings("unchecked")
	public List<Monumento> getMonumentosPopulares(int n) {
		List<Monumento> listaMonumentos = null;
		String query = "FROM Monumento m WHERE m.mostrable=true ORDER BY m.fechaConstruccion DESC";
		listaMonumentos = session.createQuery(query).setMaxResults(n).list();

		return listaMonumentos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Monumento> getByNombre(String nombre) throws QueryException {
		List<Monumento> listaMonumentos = null;
		String query = "FROM Monumento m WHERE m.nombre LIKE :nombre";
		listaMonumentos = session.createQuery(query).setParameter("nombre", '%' + nombre + '%').list();
		return listaMonumentos;
	}

	@Override
	public void update(Monumento m) {
		Monumento monumentoDB = null;
		monumentoDB = session.find(Monumento.class, m.getId());
		if (monumentoDB != null) {
			monumentoDB.actualizar(m);
			session.update(monumentoDB);
		} else {
			throw new NotFoundException();
		}
	}

	@Override
	public void delete(int id) {
		Monumento monumentoDB = null;
		monumentoDB = session.find(Monumento.class, id);
		if (monumentoDB != null) {
			monumentoDB.setMostrable(false);
			session.update(monumentoDB);
		} else {
			throw new NotFoundException();
		}
	}

}
