package com.turismo.apimonumentos.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import com.turismo.apimonumentos.models.Usuario;
import com.turismo.apimonumentos.util.UsuarioDAO;

@Path("usuario")
public class UsuarioService {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarios() {

		List<Usuario> listaUsuarios = null;
		Map<String, Object> response = null;
		Status status = Status.INTERNAL_SERVER_ERROR;
		String mensaje = null;
		try {
			usuarioDAO.getSession();
			listaUsuarios = usuarioDAO.getAll();
			usuarioDAO.closeSession();
			if (listaUsuarios != null) {
				status = Status.OK;
				mensaje = "Se muestran todos los usuarios";
			} else {
				status = Status.NOT_FOUND;
				mensaje = "No se encontró ningún usuario";
			}
		} catch (QueryException e) {
			e.printStackTrace();
			mensaje = "Error en la query";
		} catch (HibernateException e) {
			e.printStackTrace();
			mensaje = "Error en la sesión";
		} catch (Exception e) {
			e.printStackTrace();
			mensaje = "Error";
		}

		response = new HashMap<>();
		response.put("data", listaUsuarios);
		response.put("mensaje", mensaje);

		return Response.status(status).entity(response).build();
	}

	@GET
	@Path("{numero}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumeroDeUsuarios(@PathParam("numero") int n) {

		Map<String, Object> response = null;
		String mensaje = null;
		Status status = Status.INTERNAL_SERVER_ERROR;
		List<Usuario> listaUsuarios = null;

		try {
			usuarioDAO.getSession();
			listaUsuarios = usuarioDAO.getUsuariosByActividad(n);
			usuarioDAO.closeSession();
			if (listaUsuarios != null) {
				status = Status.OK;
				mensaje = "Estos son los usuarios encontrados";
			} else {
				status = Status.NOT_FOUND;
				mensaje = "No existen usuarios para mostrar";
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			mensaje = "Error en la conexión con la base de datos";
		} catch (Exception e) {
			e.printStackTrace();
			mensaje = "Error";
		}

		response = new HashMap<>();
		response.put("data", listaUsuarios);
		response.put("mensaje", mensaje);

		return Response.status(status).entity(response).build();
	}

	@GET
	@Path("buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioByNombre(@QueryParam("nombre") String nombre) {

		List<Usuario> listaUsuarios = null;
		Map<String, Object> response = null;
		Status status = Status.INTERNAL_SERVER_ERROR;
		String mensaje = null;

		try {
			usuarioDAO.getSessionAndTransaction();
			listaUsuarios = usuarioDAO.getByNombre(nombre);
			usuarioDAO.closeSessionAndTransaction();
			status = Status.OK;
			mensaje = "Estos son los usuarios que coinciden con " + nombre;
			if (listaUsuarios == null) {
				status = Status.NOT_FOUND;
				mensaje = "No se encontró ningún usuario con el nombre " + nombre;
			}

		} catch (QueryException e) {
			e.printStackTrace();
			mensaje = "Error en la query";
		} catch (TransactionException e) {
			e.printStackTrace();
			usuarioDAO.rollback();
			mensaje = "Error en la transaccion";
		} catch (HibernateException e) {
			e.printStackTrace();
			mensaje = "Error en la conexion con la base de datos";
		} catch (Exception e) {
			e.printStackTrace();
			mensaje = "Error";
		}

		response = new HashMap<>();
		response.put("data", listaUsuarios);
		response.put("mensaje", mensaje);

		return Response.status(status).entity(response).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response guardarUsuario(Usuario usuario) {

		Status status = Status.INTERNAL_SERVER_ERROR;
		String mensaje = null;

		Map<String, Object> response = null;

		try {
			usuarioDAO.getSessionAndTransaction();
			boolean isSuccessful = usuarioDAO.persist(usuario);
			usuarioDAO.closeSessionAndTransaction();
			if (isSuccessful) {
				status = Status.CREATED;
				mensaje = "El usuario se guardó correctamente";
			} else {
				status = Status.CONFLICT;
				mensaje = "Este usuario ya existe, ingresa uno nuevo";
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			usuarioDAO.rollback();
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
		response.put("data", usuario);
		response.put("mensaje", mensaje);

		return Response.status(status).entity(response).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUsuario(Usuario u) {

		Map<String, Object> response = null;
		Status status = Status.INTERNAL_SERVER_ERROR;
		String mensaje = null;

		try {
			usuarioDAO.getSessionAndTransaction();
			usuarioDAO.update(u);
			usuarioDAO.closeSessionAndTransaction();
			status = Status.OK;
			mensaje = "El usuario se actualizo correctamente";
		} catch (NotFoundException e) {
			e.printStackTrace();
			mensaje = "No se encontro el usuario a actualizar";
		} catch (TransactionException e) {
			e.printStackTrace();
			usuarioDAO.rollback();
			mensaje = "Error en la transaccion";
		} catch (HibernateException e) {
			e.printStackTrace();
			mensaje = "Error en la conexion con la base de datos";
		} catch (Exception e) {
			e.printStackTrace();
			mensaje = "Error";
		}

		response = new HashMap<>();
		response.put("data", u);
		response.put("mensaje", mensaje);

		return Response.status(status).entity(response).build();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUsuario(Usuario u) {

		int id = u.getId();

		Map<String, Object> response = null;
		Status status = Status.INTERNAL_SERVER_ERROR;
		String mensaje = null;

		try {
			usuarioDAO.getSessionAndTransaction();
			usuarioDAO.delete(id);
			usuarioDAO.closeSessionAndTransaction();
			status = Status.OK;
			mensaje = "El usuario " + u.getNombre() + " se elimino correctamente";
		} catch (NotFoundException e) {
			e.printStackTrace();
			usuarioDAO.rollback();
			mensaje = "No se encontro el usuario a eliminar";
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
}
