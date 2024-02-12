import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Arrays;

import javax.swing.UIManager;
import javax.swing.JTextPane;

public class authW extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField password_1;
	JPanel panel1;
	JLabel lblNewLabel;
	JLabel lblNewLabel_1;
	JButton login;
	JLabel create_account;
	JLabel lblWelcomeTeachers;
	JPanel panel2;
	JLabel lblNewLabel_3;
	JButton signInButton;
	
	
	String EMAIL = "";
	DataBase db;
	Connection conn;
	String User;
	
	public String hashpassword(String passwordToHash) {
	    String generatedPassword = null;

	    try 
	    {
	      
	      MessageDigest md = MessageDigest.getInstance("MD5");
	     
	      md.update(passwordToHash.getBytes());
	     
	      byte[] bytes = md.digest();
	      
	      StringBuilder sb = new StringBuilder();
	      for (int i = 0; i < bytes.length; i++) {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	      }
   
	      generatedPassword = sb.toString();
	      return generatedPassword;
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }
		return generatedPassword;
	    
	  }
	
	
	public boolean Auth(Connection conn,String table_name, String email,String password) {  
        
		Statement statement;
		ResultSet rs = null;
		try {
			String query = String.format("select * from %s where email_enseignant = '%s' and password = '%s';", table_name,email,password);
			
			
            statement = conn.createStatement();
			rs = statement.executeQuery(query);
			if(rs.next()) {
					User = rs.getString("nom_enseignant");
					
					return true;
					
			}
			else {
				return false;
			}	
			
		}
		catch(Exception e) {
			System.out.print(e);
			return false;
		}
		
    } 
	
	
	
	public authW() {
		db = new DataBase();
		conn = db.connect_to_db("JDBC","postgres", "paontri");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(222, 219, 195));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel1 = new JPanel();
		panel1.setBounds(0, 0, 451, 372);
		panel1.setBackground(new Color(116, 144, 181));
		contentPane.add(panel1);
		panel1.setLayout(null);
		
		lblNewLabel = new JLabel("Email:");
		lblNewLabel.setBounds(114, 109, 61, 16);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		panel1.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(111, 125, 200, 26);
		textField.setToolTipText("");
		panel1.add(textField);
		textField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(114, 163, 69, 16);
		panel1.add(lblNewLabel_1);
		
		password_1 = new JPasswordField();
		password_1.setToolTipText("");
		password_1.setColumns(10);
		password_1.setBounds(111, 182, 200, 26);
		panel1.add(password_1);
		
		login = new JButton("LOG IN");
		login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				login.setForeground(new Color(6, 47, 99));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				login.setForeground(Color.black);
			}
		});
		login.setBackground(new Color(154, 162, 242));
		login.setFont(new Font("Krungthep", Font.PLAIN, 13));
		login.setBounds(156, 264, 117, 29);
		login.addActionListener(this);
		panel1.add(login);
		
		create_account = new JLabel(" create new account ?");
		create_account.setFont(new Font("AppleMyungjo", Font.PLAIN, 10));
		create_account.setBounds(114, 209, 103, 16);
		panel1.add(create_account);
		
		signInButton = new JButton();
		signInButton.setBounds(114, 209, 103, 16);
		signInButton.setVisible(true);
		signInButton.addActionListener(this);
		panel1.add(signInButton);
		
		
		lblWelcomeTeachers = new JLabel("Welcome Teachers");
		lblWelcomeTeachers.setForeground(Color.BLACK);
		lblWelcomeTeachers.setFont(new Font("Oriya Sangam MN", Font.BOLD | Font.ITALIC, 23));
		lblWelcomeTeachers.setBounds(105, 36, 227, 41);
		lblWelcomeTeachers.setVisible(true);
		panel1.add(lblWelcomeTeachers);
		
		panel2 = new JPanel();
		panel2.setBackground(new Color(86, 135, 199));
		panel2.setBounds(451, 0, 361, 372);
		contentPane.add(panel2);
		panel2.setLayout(null);
		
		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("/Users/mac/Desktop/SchoolAssistant/icons/prof_50.png"));
		lblNewLabel_3.setBounds(65, 45, 256, 290);
		panel2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("Copyright © 2023 SchoolAssistant, LLC");
		lblNewLabel_2.setBounds(75, 350, 256, 16);
		panel2.add(lblNewLabel_2);
		setResizable(false);
		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==signInButton) {
			LogInW loginwindow = new LogInW();
			
		}
		if(e.getSource()==login) {
			String PASS= String.valueOf(password_1.getPassword());
			System.out.println("verification: "+PASS+hashpassword(PASS));
			if(Auth(conn,"enseignants",textField.getText(),hashpassword(PASS)) == true) {
				dispose();
				TeacherW window = new TeacherW(User);
				//window.setUser(UserName);
			}
			
			else {
				System.out.println("password or email not correct");
			};
				
			
		}
	}
}
