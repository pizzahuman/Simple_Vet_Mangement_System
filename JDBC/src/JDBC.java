import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JDBC {

	private JFrame frmRF;
	private JTextField tfname;
	private JTextField tfcontact;
	private JTextField tfanimal;
	private JTable table;
	private JTextField tfanimalID;
	
	private JTextField tfillness;
	private JTextField tfage;
	private JTextField tfaddress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JDBC window = new JDBC();
					window.frmRF.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JDBC() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vetdb", "root","");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void table_load()
    {
     try
     {
    pst = con.prepareStatement("select * from vet");
    rs = pst.executeQuery();
    table.setModel(DbUtils.resultSetToTableModel(rs));
}
     catch (SQLException e)
     {
     e.printStackTrace();
  }
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRF = new JFrame();
		frmRF.getContentPane().setBackground(Color.PINK);
		frmRF.setBackground(Color.PINK);
		frmRF.setTitle("Registration Form");
		frmRF.setBounds(100, 100, 960, 627);
		frmRF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRF.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("VETERINARY CLINIC");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 11, 946, 53);
		frmRF.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(35, 60, 856, 217);
		frmRF.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lbname = new JLabel("Name");
		lbname.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbname.setBounds(22, 37, 74, 23);
		panel.add(lbname);
		
		JLabel lblNewLabel_2 = new JLabel("Contact");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(22, 92, 74, 23);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Animal");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(22, 146, 74, 23);
		panel.add(lblNewLabel_3);
		
		tfname = new JTextField();
		tfname.setBounds(127, 33, 215, 30);
		panel.add(tfname);
		tfname.setColumns(10);
		
		tfcontact = new JTextField();
		tfcontact.setBounds(127, 88, 215, 30);
		panel.add(tfcontact);
		tfcontact.setColumns(10);
		
		tfanimal = new JTextField();
		tfanimal.setBounds(127, 142, 215, 30);
		panel.add(tfanimal);
		tfanimal.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name,contact,address,animal,illness,age;
				name = tfname.getText();
				address = tfaddress.getText();
				contact = tfcontact.getText();
				animal = tfanimal.getText();
				illness = tfillness.getText();
				age = tfage.getText();
				
				try {
					pst = con.prepareStatement("insert into vet(Name,Address,Contact,Animal,Illness,Age) values(?,?,?,?,?,?)");
					pst.setString(1, name);
					pst.setString(2, address);
					pst.setString(3, contact);
					pst.setString(4, animal);
					pst.setString(5, illness);
					pst.setString(6, age);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added!");
					table_load();
					tfname.setText("");
					tfaddress.setText("");
					tfcontact.setText("");
					tfanimal.setText("");
					tfillness.setText("");
					tfage.setText("");
					tfname.requestFocus();//when text field is cleared it will blink on the name text field
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnSave.setBounds(275, 183, 89, 23);
		panel.add(btnSave);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(381, 183, 89, 23);
		panel.add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfname.setText("");
				tfaddress.setText("");
				tfcontact.setText("");
				tfanimal.setText("");
				tfillness.setText("");
				tfage.setText("");
				tfname.requestFocus();
				
			}
		});
		btnClear.setBounds(487, 183, 89, 23);
		panel.add(btnClear);
		
		JLabel lbaddress = new JLabel("Address");
		lbaddress.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbaddress.setBounds(447, 33, 79, 22);
		panel.add(lbaddress);
		
		JLabel lbillness = new JLabel("Illness");
		lbillness.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbillness.setBounds(447, 88, 79, 27);
		panel.add(lbillness);
		
		tfillness = new JTextField();
		tfillness.setBounds(571, 88, 215, 30);
		panel.add(tfillness);
		tfillness.setColumns(10);
		
		JLabel lbage = new JLabel("Age");
		lbage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbage.setBounds(447, 142, 79, 27);
		panel.add(lbage);
		
		tfage = new JTextField();
		tfage.setBounds(571, 142, 215, 30);
		panel.add(tfage);
		tfage.setColumns(10);
		
		tfaddress = new JTextField();
		tfaddress.setBounds(571, 33, 215, 30);
		panel.add(tfaddress);
		tfaddress.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 287, 856, 228);
		frmRF.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(35, 526, 370, 53);
		frmRF.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel searchlb = new JLabel("ID");
		searchlb.setFont(new Font("Tahoma", Font.PLAIN, 13));
		searchlb.setHorizontalAlignment(SwingConstants.CENTER);
		searchlb.setBounds(10, 17, 59, 16);
		panel_1.add(searchlb);
		
		tfanimalID = new JTextField();
		tfanimalID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
			          
		            String ID = tfanimalID.getText();
		 
		                pst = con.prepareStatement("select Name,Address,Contact,Animal,Illness,Age from vet where ID=?");
		                pst.setString(1, ID);
		                ResultSet rs = pst.executeQuery();
		                //pst.setString(1, ID);
		 
		            if(rs.next()==true)
		            {
		              
		                String Name = rs.getString(1);
		                String Address = rs.getString(2);
		                String Contact = rs.getString(3);
		                String Animal = rs.getString(4);
		                String Illness= rs.getString(5);
		                String Age = rs.getString(6);
		                
		                
		                tfname.setText(Name);
		                tfaddress.setText(Address);
		                tfcontact.setText(Contact);
		                tfanimal.setText(Animal);
		                tfillness.setText(Illness);
		                tfage.setText(Age);
		                
		                
		                
		            }  
		            else
		            {
		                tfname.setText("");
		                tfaddress.setText("");
		                tfcontact.setText("");
		                tfanimal.setText("");
		                tfillness.setText("");
		                tfage.setText("");

		                
		            }
		            
		 
		 
		        }
		catch (SQLException ex) {
		          
		        }
			}
		});
		tfanimalID.setBounds(111, 11, 234, 31);
		panel_1.add(tfanimalID);
		tfanimalID.setColumns(10);
		
		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name,contact,address,animal,illness,age,ID;
				name = tfname.getText();
				address = tfaddress.getText();
				contact = tfcontact.getText();
				animal = tfanimal.getText();
				illness = tfillness.getText();
				age = tfage.getText();
				ID = tfanimalID.getText();
				
				try {
					pst = con.prepareStatement("update vet set Name=?,Address=?,Contact=?,Animal=?,Illness=?,Age=? where id=?");
					pst.setString(1, name);
					pst.setString(2, address);
					pst.setString(3, contact);
					pst.setString(4, animal);
					pst.setString(5, illness);
					pst.setString(6, age);
					pst.setString(7, ID);
					
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated!");
					table_load();
					tfname.setText("");
					tfaddress.setText("");
					tfcontact.setText("");
					tfanimal.setText("");
					tfillness.setText("");
					tfage.setText("");
					tfname.requestFocus();//when text field is cleared it will blink on the name text field
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		update.setBounds(626, 541, 118, 38);
		frmRF.getContentPane().add(update);
		
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String ID;

				ID = tfanimalID.getText();
				
				try {
					pst = con.prepareStatement("delete from vet where id=?");

					pst.setString(1, ID);
					
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted!");
					table_load();
					tfname.setText("");
					tfaddress.setText("");
					tfcontact.setText("");
					tfanimal.setText("");
					tfillness.setText("");
					tfage.setText("");
					tfname.requestFocus();//when text field is cleared it will blink on the name text field
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		delete.setBounds(773, 541, 118, 38);
		frmRF.getContentPane().add(delete);
	}
}
