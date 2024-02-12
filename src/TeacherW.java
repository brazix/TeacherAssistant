import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JComboBox;

public class TeacherW extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	JComboBox comboBox;
	JPanel panel_result;
	String NAME;
	String Student_email;
	String Student_tele;
	String Student_abs;
	JLabel lblNewLabel_4_2;
	JLabel lblNewLabel_4_1_1;
	JLabel lblNewLabel_3_2;
	JLabel absenceResult;
	Integer Abs=0;
	String currentStudent;
	String UserName;
	JLabel UserDataName;
	JLabel UserDataEmail;
	JLabel UserDataTele;
	String UserEmail;
	String UserTele;
	
	
	
	
	
	public void getStudents(Connection conn, String table_name,JComboBox ComboBox) {
		Statement statement;
		
		ResultSet rs = null;
		try {
			String query = String.format("select nom_et from %s", table_name);
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next()) {
				System.out.println(rs.getString("nom_et"));
				ComboBox.addItem(rs.getString("nom_et"));
			}
		}
		catch(Exception e) {
			System.out.print(e);
		}
	}
	
	public void ajouterCours(Connection conn, String table_name,String cours,String prof) {
		Statement statement;
		try {
			String query = String.format("insert into %s(nom_cour,nom_enseignant) values('%s','%s');", table_name,cours,prof);
			statement = conn.createStatement();
			statement.executeUpdate(query);
			System.out.println("Cours ajoute\n");
		
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public int getAbsStudent(Connection conn,String table_name,String Student) {
		Statement statement;
		ResultSet rs = null;
		
		try {
			String query = String.format("select Absences from %s where nom_et = '%s'", table_name,Student);
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next()) {
				Abs = ((Number) rs.getObject(1)).intValue();
				System.out.println(Abs);
			}
			
			return Abs;
		
		}
		catch(Exception e){
			System.out.println(e);
		}
		return Abs;
	}

	
	
	public void addAbs(Connection conn,String table_name,String Student) {
		Statement statement;
		try {
			String query = String.format("update %s set Absences='%s' where nom_et = '%s';", table_name,getAbsStudent(conn,"etudiants",Student)+1,Student);
			statement = conn.createStatement();
			statement.executeUpdate(query);
			System.out.println("Cours ajoute\n");
			Abs++;
		
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void removeStudent(Connection conn,String table_name,String Student) {
		Statement statement;
		try {
			String query = String.format("delete from %s where nom_et = '%s';", table_name,Student);
			statement = conn.createStatement();
			statement.executeUpdate(query);
			panel_result.setVisible(false);
			
		
		}
		catch(Exception e){
			System.out.println(e);
		}
	}


	public void showUserData(Connection conn,String table_name,String User) {
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherW frame = new TeacherW("karra");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TeacherW(String Username) {
		
		UserName = Username;

		System.out.println("User:     "+UserName);
		
		DataBase db = new DataBase();
		Connection conn = db.connect_to_db("JDBC","postgres", "paontri");
		
		//getAbsStudent(conn, "etudiants","ahmed");
		
		showUserData(conn,"enseignants",UserName);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setVisible(true);
		setResizable(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 990, 50);
		panel.setBackground(new Color(89, 144, 255));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("GESTION DES ETUDIANTS");
		lblNewLabel.setBounds(396, 6, 195, 40);
		lblNewLabel.setFont(new Font("Marker Felt", Font.PLAIN, 20));
		panel.add(lblNewLabel);
		
		JButton btnNewButton_5 = new JButton("Refresh");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new TeacherW(UserName);
			}
		});
		btnNewButton_5.setBounds(852, 15, 117, 29);
		panel.add(btnNewButton_5);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(137, 182, 247));
		panel_1.setBounds(6, 54, 989, 413);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textField.setBounds(244, 18, 340, 47);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Chercher un etudiant:");
		lblNewLabel_1.setFont(new Font("Herculanum", Font.BOLD, 18));
		lblNewLabel_1.setBounds(16, 18, 245, 44);
		panel_1.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				db.searchStudentByName(conn, "etudiants", textField.getText());
				Student_email = db.getemail();
				Student_tele = db.gettele();
				lblNewLabel_4_2.setText("Email: "+ Student_email); 
				lblNewLabel_4_1_1.setText("GSM: "+ Student_tele);
				Abs = getAbsStudent(conn, "etudiants",textField.getText());
				currentStudent = textField.getText();
				absenceResult.setText("Absence: "+Abs);
				lblNewLabel_3_2.setText(textField.getText());
				panel_result.setVisible(true);
				
			}
		});
		
		System.out.println(Student_email + Student_tele);
		btnNewButton.setIcon(new ImageIcon("/Users/mac/Desktop/SchoolAssistant/icons/icons8-search-client-24.png"));
		btnNewButton.setBounds(585, 18, 50, 50);
		panel_1.add(btnNewButton);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(0, 0, 0));
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("List.noFocusBorder"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(703, 0, 286, 413);
		panel_1.add(panel_4);
		panel_4.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(6, 6, 274, 401);
		panel_4.add(panel_2);
		panel_2.setBackground(new Color(149, 170, 235));
		panel_2.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Modifier les informations");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UserData(UserName);
			}
		});
		btnNewButton_1.setBounds(44, 341, 210, 29);
		panel_2.add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("/Users/mac/Desktop/SchoolAssistant/icons/icons8-male-user-96.png"));
		lblNewLabel_2.setBounds(94, 23, 102, 88);
		panel_2.add(lblNewLabel_2);
		
		UserDataName = new JLabel(UserName);
		UserDataName.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		UserDataName.setHorizontalAlignment(SwingConstants.CENTER);
		UserDataName.setBounds(6, 131, 272, 20);
		panel_2.add(UserDataName);
		
		UserDataEmail = new JLabel("Email: "+UserEmail);
		UserDataEmail.setHorizontalAlignment(SwingConstants.CENTER);
		UserDataEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		UserDataEmail.setBounds(6, 197, 272, 29);
		panel_2.add(UserDataEmail);
		
		UserDataTele = new JLabel("GSM: "+UserTele);
		UserDataTele.setHorizontalAlignment(SwingConstants.CENTER);
		UserDataTele.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		UserDataTele.setBounds(6, 271, 272, 29);
		panel_2.add(UserDataTele);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(0, 0, 0));
		panel_5.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(-6, -18, 716, 107);
		panel_1.add(panel_5);
		panel_5.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 24, 694, 77);
		panel_5.add(panel_3);
		panel_3.setBackground(new Color(0x95aaeb));
		panel_3.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon("/Users/mac/Desktop/SchoolAssistant/icons/icons8-add-new-24.png"));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new newStudent();
			}
		});
		btnNewButton_2.setBounds(638, 12, 50, 50);
		panel_3.add(btnNewButton_2);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(0, 0, 0));
		panel_7.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.setBounds(-2, 72, 342, 208);
		panel_1.add(panel_7);
		panel_7.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(6, 18, 330, 184);
		panel_7.add(panel_6);
		panel_6.setBackground(new Color(0x95aaeb));
		panel_6.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("Ajouter un cours:");
		lblNewLabel_1_1.setFont(new Font("Herculanum", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(6, 6, 185, 44);
		panel_6.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("Nom:");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_1.setBounds(29, 62, 51, 20);
		panel_6.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Enseignant:");
		lblNewLabel_3_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3_1_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_1_1.setBounds(29, 129, 104, 20);
		panel_6.add(lblNewLabel_3_1_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(81, 60, 182, 26);
		panel_6.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(133, 127, 130, 26);
		panel_6.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton_2_1 = new JButton("");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ajouterCours(conn,"cours",textField_1.getText(),textField_2.getText());
			}
		});
		btnNewButton_2_1.setIcon(new ImageIcon("/Users/mac/Desktop/SchoolAssistant/icons/icons8-add-new-24.png"));
		btnNewButton_2_1.setBounds(274, 6, 50, 50);
		panel_6.add(btnNewButton_2_1);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(new Color(0, 0, 0));
		panel_9.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_9.setBounds(0, 264, 340, 149);
		panel_1.add(panel_9);
		panel_9.setLayout(null);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBounds(3, 16, 332, 130);
		panel_9.add(panel_8);
		panel_8.setBackground(new Color(0x95aaeb));
		panel_8.setLayout(null);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Absence:");
		lblNewLabel_1_1_1.setFont(new Font("Herculanum", Font.BOLD, 18));
		lblNewLabel_1_1_1.setBounds(6, 6, 85, 44);
		panel_8.add(lblNewLabel_1_1_1);
		
		comboBox = new JComboBox();
		comboBox.setBounds(90, 65, 150, 27);
		getStudents(conn,"etudiants",comboBox);
		panel_8.add(comboBox);
		
		
		
		JLabel lblNewLabel_3_1_1_1 = new JLabel("Etudiant:");
		lblNewLabel_3_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3_1_1_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_1_1_1.setBounds(16, 66, 104, 20);
		panel_8.add(lblNewLabel_3_1_1_1);
		
		JButton btnNewButton_2_1_1 = new JButton("");
		btnNewButton_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btnNewButton_2_1_1) {
					addAbs(conn,"etudiants",comboBox.getSelectedItem().toString());
				}
			}
		});
		btnNewButton_2_1_1.setIcon(new ImageIcon("/Users/mac/Desktop/SchoolAssistant/icons/icons8-add-new-24.png"));
		btnNewButton_2_1_1.setBounds(252, 53, 50, 50);
		panel_8.add(btnNewButton_2_1_1);
		
		panel_result = new JPanel();
		panel_result.setBackground(new Color(145, 180, 242));
		panel_result.setBounds(340, 89, 364, 324);
		panel_1.add(panel_result);
		panel_result.setVisible(false);
		panel_result.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon("/Users/mac/Desktop/SchoolAssistant/icons/icons8-male-user-96.png"));
		lblNewLabel_5.setBounds(6, 6, 101, 88);
		panel_result.add(lblNewLabel_5);
		
		lblNewLabel_3_2 = new JLabel("yassine labrazi");
		lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_2.setBounds(92, 41, 272, 20);
		panel_result.add(lblNewLabel_3_2);
		
		lblNewLabel_4_2 = new JLabel("Email: "+ Student_email);
		lblNewLabel_4_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4_2.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel_4_2.setBounds(0, 106, 364, 29);
		panel_result.add(lblNewLabel_4_2);
		
		lblNewLabel_4_1_1 = new JLabel("GSM: "+ Student_tele);
		lblNewLabel_4_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4_1_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel_4_1_1.setBounds(0, 147, 364, 29);
		panel_result.add(lblNewLabel_4_1_1);
		
		absenceResult = new JLabel("Absence: "+Abs);
		absenceResult.setHorizontalAlignment(SwingConstants.CENTER);
		absenceResult.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		absenceResult.setBounds(0, 188, 364, 29);
		panel_result.add(absenceResult);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeStudent(conn,"etudiants",currentStudent);
			}
		});
		btnNewButton_3.setIcon(new ImageIcon("/Users/mac/Desktop/SchoolAssistant/icons/icons8-remove-48.png"));
		btnNewButton_3.setBounds(32, 241, 50, 50);
		panel_result.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Afficher les notes");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new notes();
			}
		});
		btnNewButton_4.setBounds(127, 241, 182, 50);
		panel_result.add(btnNewButton_4);
		
	}
}
