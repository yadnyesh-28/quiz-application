package question;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class QuestionsUi extends JFrame {
	private JTable table2;
	private DefaultTableModel Questiontable;
	private QuestionService queService;

	public QuestionsUi(int topicId) throws SQLException {
		// TODO Auto-generated constructor stub

		queService = new QuestionService();
		setTitle("Question Editing Panel");
		setSize(500, 400);
		setBounds(300, 70, 700, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		JLabel label = new JLabel("Topics:");
		label.setBounds(20, 20, 100, 30);
		add(label);
		
		
		JButton BackToTopicButton = new JButton("Back to Topics");
		

		BackToTopicButton.addActionListener(e -> {
			try {
				new AdminUI().setVisible(true);
				dispose();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		Questiontable = new DefaultTableModel(new String[] { "ID", "Question Name" }, 0);
		table2 = new JTable(Questiontable);
		JScrollPane scrollPane1 = new JScrollPane(table2);
		scrollPane1.setBounds(20, 60, 600, 300);
		add(scrollPane1);
		
		BackToTopicButton.setBounds(640, 200, 100, 30);
		add(BackToTopicButton);

		JButton editButton = new JButton("Edit");
		editButton.setBounds(640, 100, 100, 30);
		add(editButton);

		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(640, 150, 100, 30);
		add(deleteButton);

		loadQuestions(topicId);

		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table2.getSelectedRow();
				if (selectedRow != -1) {
					int questionId = Integer.parseInt(Questiontable.getValueAt(selectedRow, 0).toString());
					String newName = JOptionPane.showInputDialog("Enter new Questions:");
					try {
						queService.updateQuestion(questionId, newName);
						JOptionPane.showMessageDialog(null, "Question updated successfully.");
						new QuestionsUi(topicId).setVisible(true);
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null, "Error updating topic: " + ex.getMessage());
					}
				}
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table2.getSelectedRow();
				if (selectedRow != -1) {
					int queId = Integer.parseInt(Questiontable.getValueAt(selectedRow, 0).toString());
					try {
						queService.deleteQuestion(queId);
						new QuestionsUi(topicId).setVisible(true);
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null, "Error deleting topic: " + ex.getMessage());
					}
				}
			}
		});

	}

	private void loadQuestions(int topicId) {
		try {
			Questiontable.setRowCount(0); // Clear existing rows
			ResultSet rs = queService.getQuestionssByTopic(topicId);
			while (rs.next()) {
				Questiontable.addRow(new Object[] { rs.getInt("id"), rs.getString("question"), rs.getString("option1"),
						rs.getString("option2"), rs.getString("option3"), rs.getString("option4"),
						rs.getInt("correct_option") });
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error loading Questions: " + e.getMessage());
		}
	}

}
