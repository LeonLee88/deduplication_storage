package com.main.MainWindow;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JList;
import javax.swing.JTable;

import com.deduplication.FileChunk;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * xingyuwu@bu.edu;
 * boheng@bu.edu;
 * shu@bu.edu
 * yuyanzhangswe@gmail.com
 * lihua88@bu.edu*/

public class MainWindow {

	private JFrame frame;
	private JTable table;
	private JPanel panelBanner;
	private JPanel panelButton;
	private JButton btnUpload;
	private JButton btnDownload;
	private JButton btnRemove;
	private JButton btnMove;
	private JButton btnNewFolder;
	private JFileChooser fileOpenChooser;
	private JFileChooser fileSaveChooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
					ImageIcon mianIcon = new ImageIcon("images/cloud.png");
					window.frame.setIconImage(mianIcon.getImage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 545, 352);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		table = CreateTable();
		
		JScrollPane scrollPane = new JScrollPane(table);
		frame.getContentPane().add(scrollPane);// Using JScrollPane to contain the table, otherwise the header of table will not show
		
		JButton button = new JButton("New button");
		scrollPane.setColumnHeaderView(button);
		
		BufferedImage myPicture;
		
		try {
			myPicture = ImageIO.read(new File("images/cloud.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel labelBanner = new JLabel(new ImageIcon(""));// with no imange
		labelBanner.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		panelBanner = new JPanel();
		panelBanner.setLayout(new BoxLayout(panelBanner, BoxLayout.Y_AXIS));
		panelBanner.add(labelBanner);
		panelBanner.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		panelButton = new JPanel();
		panelButton.setLayout(new GridLayout(1, 6));
		
		frame.getContentPane().add(panelBanner, BorderLayout.NORTH);
		panelBanner.add(panelButton);
		
		btnUpload = CreateButton(" Upload", "images/btn_upload.png");
		
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileOpenChooser = new JFileChooser();
				int returnVal = fileOpenChooser.showOpenDialog(frame);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fileOpenChooser.getSelectedFile();
	                byte[] data = FileChunk.readFileInChunk(file);
	                FileChunk.writeChunkInFile("chunks/" + file.getName(), data);
	                
	                //This is where a real application would open the file.
	                Object[] row = {file.getName(),file.length()/1024 + " K",getCurrentTimeStr(),};
	                ((DefaultTableModel)table.getModel()).addRow(row);
	            } else {
	            }
			}
		});
		panelButton.add(btnUpload);
		
		btnDownload = CreateButton(" Download", "images/btn_download.png");
		btnDownload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String fileName = (String) table.getValueAt(table.getSelectedRow(),0);
				//Retrieve chunks based on fileName and prompt to user to save
				fileSaveChooser = new JFileChooser();
				fileSaveChooser.setSelectedFile(new File("C:/" + fileName));

				int returnVal = fileSaveChooser.showSaveDialog(frame);
				
		        
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					   
	            } else {
	            }
			}
		});
		panelButton.add(btnDownload);
		
		btnNewFolder = CreateButton(" New Folder", "images/btn_newfolder.png");
		panelButton.add(btnNewFolder);
		
		btnRemove = CreateButton(" Remove", "images/btn_remove.png");
		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = table.getSelectedRow();
				String fileName = (String) table.getValueAt(selectedRow,0);
				try {
					FileChunk.deleteFile("chunks/" + fileName);
					((DefaultTableModel)table.getModel()).removeRow(selectedRow);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		panelButton.add(btnRemove);
		
		btnMove = CreateButton(" Move", "images/btn_move.png");
		panelButton.add(btnMove);
		
		JLabel label = new JLabel("");
		label.setOpaque(true); // Set it true to color the label
		label.setBackground(Color.WHITE);
		panelButton.add(label);
		
		JLabel label1 = new JLabel("");
		label1.setOpaque(true);
		label1.setBackground(Color.WHITE);
		panelButton.add(label1);

	}
	
	private JButton CreateButton(String text, String iconPath){
		JButton btn = new JButton(text, new ImageIcon(iconPath));
		btn.setBackground(Color.WHITE);
		btn.setFocusable(false);
		btn.setBorderPainted(false);
		return btn;
	}
	
	private JTable CreateTable() {
		
		DefaultTableModel tableModel = new DefaultTableModel() {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		tableModel.addColumn("File Name");
		tableModel.addColumn("File Size");
		tableModel.addColumn("Upload Time");
		
		Object[] row1 = {"diary.txt", "345 K", "Yesterday 19:00 AM"};
		Object[] row2 = {"diary.txt", "4 MB", "Yesterday 19:00 AM"};
		Object[] row3 = {"diary.txt", "5.2 MB", "Yesterday 19:00 AM"};
		Object[] row4 = {"diary.txt", "6 MB", "Yesterday 19:00 AM"};
		Object[] row5 = {"diary.txt", "345 K", "Yesterday 19:00 AM"};
		
		
		tableModel.addRow(row1);
		tableModel.addRow(row2);
		tableModel.addRow(row3);
		tableModel.addRow(row4);
		tableModel.addRow(row5);
		
		JTable table = new JTable(tableModel);
		table.setShowVerticalLines(false);
		
		return table;
	}
	
	public String getCurrentTimeStr(){
		Date date = new Date();
		//DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		DateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
		return sdf.format(date);
	}
}
