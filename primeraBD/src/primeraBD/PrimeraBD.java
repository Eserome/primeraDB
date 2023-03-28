package primeraBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.*;

public class PrimeraBD {

	public static String url = "jdbc:mysql://127.0.0.1:3307/prueba";
	public static String user = "alumno";
	public static String pass = "alumno";
	
	
	static void mostrarMenu() {
		System.out.println("-----MENU-----");
		System.out.println("1) Mostrar todo el contenido de la tabla");
		System.out.println("2) Mostrar los nombres de las personas mayores de edad");
		System.out.println("3) Mostrar la edad de una persona con un id tecleado por el usuario");
		System.out.println("4) Insertar una nueva persona, pidiéndole id, nombre y edad al usuario");
		System.out.println("5) Borrar la persona con un id tecleado por el usuario");
		System.out.println("6) Actualizar la edad de una persona con un id tecleado por el usuario");
	}
	
	public static void main(String[] args) throws SQLException {
		int opcion;
		Scanner in = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(url,user,pass);
			
			do {
				mostrarMenu();
				System.out.println("\nSelecciona una opción:");
				opcion = in.nextInt();
				
				switch(opcion) {
				
				case 1:
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM persona");
					
					while(rs.next()) {
						int idPersona = rs.getInt("id");
						String nombrePersona = rs.getString("nombre");
						int edadPersona = rs.getInt("edad");
						System.out.println("Persona de nombre: " + nombrePersona + ", con id: " + idPersona + ", y con edad: " + edadPersona);
						System.out.println();
					}
					rs.close();
					stmt.close();
					
					break;
				case 2:
					Statement stmt1 = con.createStatement();
					ResultSet rs1 = stmt1.executeQuery("SELECT * FROM persona");
					
					while(rs1.next()) {
						int idPersona = rs1.getInt("id");
						String nombrePersona = rs1.getString("nombre");
						int edadPersona = rs1.getInt("edad");
						if(edadPersona<19) 
							continue;
						System.out.println("Persona de nombre: " + nombrePersona + ", con id: " + idPersona + ", y con edad: " + edadPersona);
						System.out.println();
					}
					rs1.close();
					stmt1.close();
					
					
					break;
				case 3: 
					System.out.println("Introduce el id por el que quieres filtrar la tabla: ");
					int idBuscado = in.nextInt();
					
					Statement stmt2 = con.createStatement();
					ResultSet rs2 = stmt2.executeQuery("SELECT * FROM persona");
					
					while(rs2.next()) {
						int idPersona = rs2.getInt("id");
						String nombrePersona = rs2.getString("nombre");
						int edadPersona = rs2.getInt("edad");
						if(idPersona == idBuscado) 
							System.out.println("Persona de nombre: " + nombrePersona + ", con id: " + idPersona + ", y con edad: " + edadPersona);
							System.out.println();
					}
					rs2.close();
					stmt2.close();
					
					break;
				case 4:
					
					System.out.println("Introduce el id: ");
					int idAIntroducir = in.nextInt();
					System.out.println("Introduce el nombre: ");
					String nombreAIntroducir = in.next();
					System.out.println("Introduce la edad: ");
					int edadAIntroducir = in.nextInt();
					
					PreparedStatement stmt3 = con.prepareStatement("INSERT INTO persona VALUES (?,?,?)");
					
					stmt3.setInt(1, idAIntroducir);
					stmt3.setString(2, nombreAIntroducir);
					stmt3.setInt(3, edadAIntroducir);
					stmt3.executeUpdate();
					stmt3.close();
					
					break;
				case 5:
					System.out.println("Introduce el id de la persona que quieres eliminar: ");
					int idAEliminar = in.nextInt();
					
					PreparedStatement stmt4 = con.prepareStatement("DELETE FROM persona WHERE id=?");
					stmt4.setInt(1, idAEliminar);
					stmt4.executeUpdate();
					stmt4.close();
					
					break;
				case 6: 
					
					System.out.println("Introduce el id de la persona que quieres actualizar: ");
					int idAActualizar = in.nextInt();
					System.out.println("Introduce la edad a la que quieres actualizar: ");
					int edadActualizada = in.nextInt();
					
					PreparedStatement stmt5 = con.prepareStatement("UPDATE persona SET edad=? WHERE id=?");
					stmt5.setInt(1, edadActualizada);
					stmt5.setInt(2, idAActualizar);
					stmt5.executeUpdate();
					stmt5.close();
					
					break;
				case 7:
					System.out.println("Hasta luego!");
					break;
				}
			}while(opcion!=7);
		
		} catch (SQLException e) {
			System.out.println(e);
		}
	
		
	}
}
