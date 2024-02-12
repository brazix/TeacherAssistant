
public class main {

	public static void main(String[] args) {
		
		DataBase db = new DataBase();
		db.connect_to_db("JDBC","postgres", "paontri");
		authW authwindow = new authW();
		
//		new notes();

	}

}
