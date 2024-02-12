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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.SwingConstants;


public class LogInW extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JPanel panel;
	private JTextField name;
	private JTextField email;
	private JTextField phone;
	private JPasswordField password;
	JButton btnNewButton;
	JLabel cmptcree;
	
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
	
	
	
	
	public LogInW() {
		setBounds(100, 100, 400, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
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
		
		JLabel lblNewLabel_1_3 = new JLabel("Password:");
		lblNewLabel_1_3.setFont(new Font("Impact", Font.PLAIN, 20));
		lblNewLabel_1_3.setBounds(79, 357, 90, 20);
		panel.add(lblNewLabel_1_3);
		
		name = new JTextField();
		name.setBounds(79, 165, 211, 26);
		panel.add(name);
		name.setColumns(10);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(79, 243, 211, 26);
		panel.add(email);
		
		phone = new JTextField();
		phone.setColumns(10);
		phone.setBounds(79, 313, 211, 26);
		panel.add(phone);
		
		password = new JPasswordField();
		password.setColumns(10);
		password.setBounds(79, 389, 211, 26);
		panel.add(password);
		
		btnNewButton = new JButton("Sign in");
		btnNewButton.addActionListener(this);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton.setBounds(110, 450, 140, 50);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setBounds(120, 459, 117, 40);
			}
		});
		btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnNewButton.setBackground(Color.GRAY);
		btnNewButton.setBounds(120, 459, 117, 40);
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
			DataBase db = new DataBase();
			Connection conn = db.connect_to_db("JDBC","postgres", "paontri");
			
			String PASS= String.valueOf(password.getPassword());
			System.out.println("creation: "+PASS+hashpassword(PASS));
			
			db.registerTutor(conn, "enseignants", name.getText(), email.getText(), phone.getText(), hashpassword(PASS));
			cmptcree.setText("Prof ajoute");
			dispose();
			
			System.out.println(password.getPassword());
		
			name.setText("");
			email.setText("");
			phone.setText("");
			password.setText("");
		}
		
	}
}
