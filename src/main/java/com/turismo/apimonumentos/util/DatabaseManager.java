package com.turismo.apimonumentos.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.turismo.apimonumentos.models.Monumento;

public class DatabaseManager {
	
	private  Connection conn;
	
	public DatabaseManager(Connection conn) {
		this.conn = conn;
	}
	
	public List<Monumento> getMonumentos() {
		
		String query="SELECT * FROM monumento WHERE mostrable=true";
		Statement stmnt=null;
		ResultSet result =null;
		
		List<Monumento> listaMonumentos= new ArrayList<Monumento>();
		
		try {
			stmnt=conn.createStatement();
			result=stmnt.executeQuery(query);
			Monumento mon=null;
			
			while(result.next()) {
				mon=new Monumento(result.getInt("id"), result.getString("nombre"),
						result.getInt("fechaConstruccion"), result.getBoolean("mostrable"),
						result.getDate("fechaRegistro"));
				listaMonumentos.add(mon);
				}
			result.close();
			stmnt.close();
			DatabaseConnector.closeConnection(conn);
			
		} catch (SQLException e) {
			System.out.println("Fallo al crear o ejecutar el statement");
			e.printStackTrace();
		}finally {
			if(stmnt!=null) {
				try {
					stmnt.close();
				} catch (SQLException e) {
					System.out.println("Fallo al cerrar el stmnt");
					e.printStackTrace();
				}
			}
		}
		
		return listaMonumentos;
	}

	public int addMonumento(Monumento monumento) {
		
		String query="INSERT INTO monumento(nombre,fechaConstruccion,mostrable) "
				+ "VALUES(?,?,?)";
		PreparedStatement stmnt=null;
		int result=-1;
		try {
			stmnt=conn.prepareStatement(query);
			stmnt.setString(1, monumento.getNombre());
			stmnt.setInt(2, monumento.getFechaConstruccion());
			stmnt.setString(3, monumento.isMostrable()?"1":"0");
			
			result=stmnt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Fallo al preparar o ejecutar el statement");
			e.printStackTrace();
		}finally {
			if(stmnt!=null) {
				try {
					stmnt.close();
				} catch (SQLException e) {
					System.out.println("Fallo al cerrar el statement");
					e.printStackTrace();
				}
			}
		}
		return result;
		
	}
	
}
