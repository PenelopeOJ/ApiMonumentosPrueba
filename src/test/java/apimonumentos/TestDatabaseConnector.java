package apimonumentos;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

public class TestDatabaseConnector {
	
	public static String URL_FORMAT="jdbc:mysql://%s:%s/%s"+
			"?useUnicode=true" + 
			"&useJDBCCompliantTimezoneShift=true" + 
			"&useLegacyDatetimeCode=false" + 
			"&serverTimezone=UTC" ;
	
	@Test
	public void testConnection() {
		String serverHost="localhost";
		String serverPort="3306";
		String databaseName="turismo";
		String databaseUser="root";
		String databasePass="penelope96";
		
		Connection conn=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url=String.format(URL_FORMAT, serverHost,serverPort,databaseName);
			conn= DriverManager.getConnection(url,databaseUser,databasePass);
			assertNotNull("La conexion no se establecio", conn);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			fail("Error al cargar el driver de MySQL");
		}finally {
			if(conn!=null) {
				try {
					conn.close();
					assertTrue("La conexion no se cerro", conn.isClosed());
				} catch (SQLException e) {
					e.printStackTrace();
					fail("Fallo al cerrar la conexion");
				}
			}
		}
	}
	
}
