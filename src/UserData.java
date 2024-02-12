import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.SwingConstants;


public class UserData extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JPanel panel;
	private JTextField name;
	private JTextField email;
	private JTextField phone;
	JButton btnNewButton;
	JLabel cmptcree;
	String UserName;
	String UserEmail;
	String UserTele;
	DataBase db;
	Connection conn;
	
	
	public void getUserData(Connection conn,String table_name,String User) {
		Statement statement;
		
		ResultSet rs = null;
		
		try {
			String query = String.format("select * from %s where nom_enseignant = '%s';", table_name,User);
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next()) {
				UserEmail = rs.getString("email_enseignant");
				UserTele = rs.getString("telephone_enseignant");
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void UpdateUserData(Connection conn,String table_name,String User,String Name,String email,String tele) {
		Statement statement;
		try {
			String query = String.format("update %s set nom_enseignant = '%s' where nom_enseignant = '%s'; update %s set email_enseignant='%s' where nom_enseignant = '%s'; update %s set telephone_enseignant= '%s'  where nom_enseignant = '%s';", table_name,Name,User,table_name,email,User,table_name,tele,User);
			statement = conn.createStatement();
			statement.executeUpdate(query);
			System.out.println("modified");
			
		
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	
	public UserData(String User) {
		setBounds(100, 100, 400, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		db = new DataBase();
		conn = db.connect_to_db("JDBC","postgres", "paontri");
		
		UserName = User;
		
		getUserData(conn,"enseignants",User);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(116, 144, 181));
		panel.setBounds(0, 0, 400, 572);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("WELCOME:");
		lblNewLabel.setFont(new Font("Impact", Font.PLAIN, 29));
		lblNewLabel.setBounds(133, 49, 128, 54);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setFont(new Font("Impact", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(79, 137, 61, 16);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Phone number:");
		lblNewLabel_1_1.setFont(new Font("Impact", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(79, 281, 158, 20);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Email:");
		lblNewLabel_1_2.setFont(new Font("Impact", Font.PLAIN, 20));
		lblNewLabel_1_2.setBounds(79, 215, 61, 16);
		panel.add(lblNewLabel_1_2);
		
		name = new JTextField(User);
		name.setBounds(79, 165, 211, 26);
		panel.add(name);
		name.setColumns(10);
		
		email = new JTextField(UserEmail);
		email.setColumns(10);
		email.setBounds(79, 243, 211, 26);
		panel.add(email);
		
		phone = new JTextField(UserTele);
		phone.setColumns(10);
		phone.setBounds(79, 313, 211, 26);
		panel.add(phone);
		
		btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(this);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
		});
		btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnNewButton.setBackground(Color.GRAY);
		btnNewButton.setBounds(120, 419, 117, 40);
		panel.add(btnNewButton);
		
		cmptcree = new JLabel("");
		cmptcree.setForeground(new Color(59, 195, 49));
		cmptcree.setFont(new Font("ITF Devanagari Marathi", Font.BOLD, 20));
		cmptcree.setHorizontalAlignment(SwingConstants.CENTER);
		cmptcree.setBounds(0, 511, 370, 34);
		panel.add(cmptcree);
		setResizable(false);
		setVisible(true);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnNewButton) {
			UpdateUserData(conn,"enseignants",UserName,name.getText(),email.getText(),phone.getText());
		}
		
	}
}

