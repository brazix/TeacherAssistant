import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class notes extends JFrame {

	private JPanel contentPane;
	JTable table;
	
	Object[][] data= new String[0][1];
	
	Object[] Columnnames = {"cour","note"};
	DefaultTableModel model = new DefaultTableModel(data,Columnnames);
	JFrame frame = new JFrame();
	
	public void getCoursNotes(Connection conn, String table_name) {
		Statement statement;
		
		ResultSet rs = null;
		try {
			String query = String.format("select nom_cour from %s;", table_name);
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			int i = 0;
			while(rs.next()) {
				System.out.println(rs.getString("nom_cour"));
//				data[i][0] = rs.getString("nom_cour");
//				i++;
				Object[] NewRow = {rs.getString("nom_cour"),"0"};
				model.addRow(NewRow);
				
			}
		}
		catch(Exception e) {
			System.out.print(e);
		}
	}
	
	
	public notes() {
		DataBase db = new DataBase();
		Connection conn = db.connect_to_db("JDBC","postgres", "paontri");
		
		getCoursNotes(conn,"cours");
		
		frame.setTitle("ETUDIANTS");
		frame.setSize(500,250);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(99, 165, 255));
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		table = new JTable(model);
		table.setBackground(new Color(95, 137, 209));
		table.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		
		table.setModel(model);

		
		JScrollPane pane = new  JScrollPane(table);
		pane.setForeground(Color.red);
		pane.setBackground(Color.black);
		pane.setBounds(6,55,488,161);
		
		frame.getContentPane().add(pane);
		
		JLabel lblNewLabel = new JLabel("NOTES:");
		lblNewLabel.setFont(new Font("Chalkboard SE", Font.BOLD, 17));
		lblNewLabel.setBounds(218, 15, 87, 23);
		frame.getContentPane().add(lblNewLabel);
		
		frame.setResizable(false);
		frame.setVisible(true);
		
	}
}
