package com.turismo.apimonumentos.resources;

import java.sql.Connection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.turismo.apimonumentos.models.Monumento;
import com.turismo.apimonumentos.util.DatabaseConnector;
import com.turismo.apimonumentos.util.DatabaseManager;

@Path("monumentos")
public class MonumentoResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMonumentos() {
		//TODO: Verificar porque me regresa tiempoConstruccion
		Connection conn=DatabaseConnector.getConnection();
		DatabaseManager dbManager= new DatabaseManager(conn);
		List<Monumento> listaMonumentos= dbManager.getMonumentos();
		String jsonMonumentos=new Gson().toJson(listaMonumentos);
		return jsonMonumentos;
	}
	
}
