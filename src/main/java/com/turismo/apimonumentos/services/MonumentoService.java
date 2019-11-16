package com.turismo.apimonumentos.services;

//import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
//import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.HibernateException;
import org.hibernate.QueryException;
import org.hibernate.TransactionException;

import com.turismo.apimonumentos.models.Monumento;
import com.turismo.apimonumentos.util.MonumentoDAO;

@Path("monumentos")
public class MonumentoService {

	private MonumentoDAO monumentoDAO = new MonumentoDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMonumentos() {

		List<Monumento> listaMonumentos = null;
		Map<String, Object> response = null;
		Status status = Status.INTERNAL_SERVER_ERROR;
		String mensaje = null;
		try {
			monumentoDAO.getSession();
			listaMonumentos = monumentoDAO.getAll();
			monumentoDAO.closeSession();
			if (listaMonumentos != null) {
				status = Status.OK;
				mensaje = "Se muestran todos los monumentos";
			} else {
				status = Status.NOT_FOUND;
				mensaje = "No se encontro ningun monumento";
			}
		} catch (QueryException e) {
			e.printStackTrace();
			mensaje = "Error en la query";
		} catch (HibernateException e) {
			e.printStackTrace();
			mensaje = "Error en la sesion";
		} catch (Exception e) {
			e.printStackTrace();
			mensaje = "Error";
		}

		response = new HashMap<>();
		response.put("data", listaMonumentos);
		response.put("mensaje", mensaje);

		return Response.status(status).entity(response).build();
	}

	@GET
	@Path("{numero}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumeroDeMonumentos(@PathParam("numero") int n) {

		Map<String, Object> response = null;
		String mensaje = null;
		Status status = Status.INTERNAL_SERVER_ERROR;
		List<Monumento> listaMonumento = null;

		try {
			monumentoDAO.getSession();
			listaMonumento = monumentoDAO.getMonumentosPopulares(n);
			monumentoDAO.closeSession();
			if (listaMonumento != null) {
				status = Status.OK;
				mensaje = "Estos son los monumentos encontrados";
			} else {
				status = Status.NOT_FOUND;
				mensaje = "No existen monumentos para mostrar";
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			mensaje = "Error en la conexion con la base de datos";
		} catch (Exception e) {
			e.printStackTrace();
			mensaje = "Error";
		}

		response = new HashMap<>();
		response.put("data", listaMonumento);
		response.put("mensaje", mensaje);

		return Response.status(status).entity(response).build();
	}

	@GET
	@Path("buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMonumentoByNombre(@QueryParam("nombre") String nombre) {

		List<Monumento> listaMonumentos = null;
		Map<String, Object> response = null;
		Status status = Status.INTERNAL_SERVER_ERROR;
		String mensaje = null;

		try {
			monumentoDAO.getSessionAndTransaction();
			listaMonumentos = monumentoDAO.getByNombre(nombre);
			monumentoDAO.closeSessionAndTransaction();
			status = Status.OK;
			mensaje = "Estos son todos los monumentos que coinciden con " + nombre;
			if (listaMonumentos == null) {
				status = Status.NOT_FOUND;
				mensaje = "No se encontro ningun monumento con el nombre " + nombre;
			}

		} catch (QueryException e) {
			e.printStackTrace();
			mensaje = "Error en la query";
		} catch (TransactionException e) {
			e.printStackTrace();
			monumentoDAO.rollback();
			mensaje = "Error en la transaccion";
		} catch (HibernateException e) {
			e.printStackTrace();
			mensaje = "Error en la conexion con la base de datos";
		} catch (Exception e) {
			e.printStackTrace();
			mensaje = "Error";
		}

		response = new HashMap<>();
		response.put("data", listaMonumentos);
		response.put("mensaje", mensaje);

		return Response.status(status).entity(response).build();

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response guardarMonumento(Monumento monumento) {

		Status status = Status.INTERNAL_SERVER_ERROR;
		String mensaje = null;

		Map<String, Object> response = null;

		try {
			monumentoDAO.getSessionAndTransaction();
			boolean isSuccesful = monumentoDAO.persist(monumento);
			monumentoDAO.closeSessionAndTransaction();
			if (isSuccesful) {
				status = Status.CREATED;
				mensaje = "El monumento se guardo correctamente";
			} else {
				status = Status.CONFLICT;
				mensaje = "Este monumento ya existe, ingresa uno nuevo";
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			monumentoDAO.rollback();
			mensaje = "Error en la transaccion";
			System.out.println(mensaje);
		} catch (HibernateException e) {
			e.printStackTrace();
			mensaje = "Error en la conexion con la base de datos";
			System.out.println(mensaje);
		} catch (Exception e) {
			e.printStackTrace();
			mensaje = "Error";
		}
		response = new HashMap<>();
		response.put("data", monumento);
		response.put("mensaje", mensaje);

		return Response.status(status).entity(response).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMonumento(Monumento m) {

		Map<String, Object> response = null;
		Status status = Status.INTERNAL_SERVER_ERROR;
		String mensaje = null;

		try {
			monumentoDAO.getSessionAndTransaction();
			monumentoDAO.update(m);
			monumentoDAO.closeSessionAndTransaction();
			status = Status.OK;
			mensaje = "El monumento se actualizo correctamente";
		} catch (NotFoundException e) {
			e.printStackTrace();
			mensaje = "No se encontro el monumento a actualizar";
		} catch (TransactionException e) {
			e.printStackTrace();
			monumentoDAO.rollback();
			mensaje = "Error en la transaccion";
		} catch (HibernateException e) {
			e.printStackTrace();
			mensaje = "Error en la conexion con la base de datos";
		} catch (Exception e) {
			e.printStackTrace();
			mensaje = "Error";
		}

		response = new HashMap<>();
		response.put("data", m);
		response.put("mensaje", mensaje);

		return Response.status(status).entity(response).build();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMonumento(Monumento m) {

		int id = m.getId();

		Map<String, Object> response = null;
		Status status = Status.INTERNAL_SERVER_ERROR;
		String mensaje = null;

		try {
			monumentoDAO.getSessionAndTransaction();
			monumentoDAO.delete(id);
			monumentoDAO.closeSessionAndTransaction();
			status = Status.OK;
			mensaje = "El monumento " + m.getNombre() + " se elimino correctamente";
		} catch (NotFoundException e) {
			e.printStackTrace();
			monumentoDAO.rollback();
			mensaje = "No se encontro el monumento a eliminar";
		} catch (TransactionException e) {
			e.printStackTrace();
			mensaje = "Error en la transaccion";
		} catch (HibernateException e) {
			e.printStackTrace();
			mensaje = "Error en la conexion con la base de datos";
		} catch (Exception e) {
			e.printStackTrace();
			mensaje = "Error";
		}

		response = new HashMap<>();
		response.put("mensaje", mensaje);

		return Response.status(status).entity(response).build();
	}

	// TODO: Metodo que recibe la imagen en base64 y que se reenvia a ApiPython

}
