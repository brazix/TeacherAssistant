import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTextField;



public class DataBase {
	String email;
	String tele;
	
	public Connection connect_to_db(String dbname,String user,String pass) {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,user,pass);
			if(conn != null) {
				System.out.println("Connection Established");
			}
			else {
				System.out.println("Connection Failed");
			}
		}catch(Exception e) {
			System.out.println("Error: "+e);
		}
		return conn;
	}
	
	public void registerStudent(Connection conn, String table_name,String name, String email,String phone, char[] password) {
		Statement statement;
		try {
			String query = String.format("insert into %s(nom_et,email_etudiant,telephone_etudiant,password,absences) values('%s','%s','%s','%s','0');", table_name,name,email,phone,password);
			statement = conn.createStatement();
			statement.executeUpdate(query);
			System.out.println("Eleve ajoute");
		
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void registerTutor(Connection conn, String table_name,String name, String email,String phone, String password) {
		Statement statement;
		try {
			String query = String.format("insert into %s(nom_enseignant,email_enseignant,telephone_enseignant,password) values('%s','%s','%s','%s');", table_name,name,email,phone,password);
			statement = conn.createStatement();
			statement.executeUpdate(query);
			System.out.println("prof ajoute");
		
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void searchStudentByName(Connection conn,String table_name, String name) {  
        
		Statement statement;
		ResultSet rs = null;
		try {
			String query = String.format("select * from %s where nom_et = '%s'", table_name,name);
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next()) {
				email = rs.getString("email_etudiant");
				tele = rs.getString("telephone_etudiant");
				
			}
			
		}
		catch(Exception e) {
			System.out.print(e);
		}
    }  
	
	public String getemail() {
        return email;
    }

    public String gettele() {
        return tele;
    }
//	public void updateCombo(JCombobox comboBox) {
//		
//		String sql = "select * from etudiants";
//		try {
//			pst = conn.prepareStatement(sql);
//			rs = pst.executeQuery();
//			while(rs.next()) {
//				comboBox.addItem(rs.getString("nom_et"));
//			}
//		}
//		catch(Exception e) {
//			
//		}
//	}
	
	
}
