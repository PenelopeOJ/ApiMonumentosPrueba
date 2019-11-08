package com.turismo.apimonumentos.resources;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.turismo.apimonumentos.models.Monumento;
import com.turismo.apimonumentos.util.DatabaseConnector;

@Path("monumentos")
public class MonumentoResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMonumentos() {
		
		Session session=null;
		
		List<Monumento>listaMonumentos=null;
		Map<String, Object>response=null;
		Status status=Status.INTERNAL_SERVER_ERROR;
		String mensaje=null;
		
		try {
			session=DatabaseConnector.getSession();
			Query query= session.createQuery("FROM Monumento m", Monumento.class);
			listaMonumentos=query.getResultList();
			if(listaMonumentos!=null) {
				status=Status.OK;
				mensaje="Se muestran todos los monumentos";
			}else {
				status=Status.NOT_FOUND;
				mensaje="No se encontro ningun monumento";
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			mensaje="Error en la sesion";
		} catch (Exception e) {
			e.printStackTrace();
			mensaje="Error";
		}
		
		response=new HashMap<>();
		response.put("data", listaMonumentos);
		response.put("mensaje", mensaje);
		
		return Response.status(status).entity(response).build();
	}
	
}
