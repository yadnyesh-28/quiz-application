package question;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class AdminUI extends JFrame {
	private JTable table;
	private DefaultTableModel tableModel;
	private QuestionService queService;
	private int topicId;

	public AdminUI() throws SQLException {
		// TODO Auto-generated constructor stub
		queService = new QuestionService();
		setTitle("Admin Panel");
		setSize(500, 400);
		setBounds(300, 70, 700, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		JLabel label = new JLabel("Topics:");
		label.setBounds(20, 20, 100, 30);
		setResizable(false);
		add(label);
		
		tableModel = new DefaultTableModel(new String[] { "ID", "Topic Name" }, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(20, 60, 600, 300);
		add(scrollPane);

		
		
		JButton editButton = new JButton("Edit");
		editButton.setBounds(640, 100, 100, 30);
		add(editButton);

		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(640, 150, 100, 30);
		add(deleteButton);
		
		JButton ShowQuestionsButton = new JButton("Show Questions");
		ShowQuestionsButton.setBounds(640, 200, 100, 30);
		add(ShowQuestionsButton);
		

		loadTopics();

		JButton addTopicButton = new JButton("Add Topic");
		JButton addQuestionButton = new JButton("Add Question");

		addTopicButton.addActionListener(e -> openAddTopicDialog());
		addQuestionButton.addActionListener(e -> openAddQuestionDialog());

		add(addTopicButton);
		add(addQuestionButton);
		
		ShowQuestionsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                	 topicId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    try {
                        //queService.getQuestionsByTopic(topicId);
                        new QuestionsUi(topicId).setVisible(true);
                        dispose();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error updating topic: " + ex.getMessage());
                    }
                }
            }
        });		
		editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                	int topicId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    String newName = JOptionPane.showInputDialog("Enter new topic name:");
                    
                    try {
                        queService.updateTopic(topicId, newName);
                        loadTopics();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error updating topic: " + ex.getMessage());
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int topicId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    try {
                        queService.deleteTopic(topicId);
                        loadTopics();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error deleting topic: " + ex.getMessage());
                    }
                }
            }
        });
    }
	
	private void loadTopics() {
        try {
            tableModel.setRowCount(0); // Clear existing rows
            ResultSet rs = queService.getTopic();
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getInt("topic_id"), rs.getString("topic_name")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading topics: " + e.getMessage());
        }
    }
	
	
	private void openAddTopicDialog() {
		JDialog dialog = new JDialog(this, "Add Topic", true);
		dialog.setResizable(false);
		dialog.setSize(300, 150);
		dialog.setLayout(new FlowLayout());

		JTextField topicField = new JTextField(20);
		JButton submitButton = new JButton("Submit");

		submitButton.addActionListener(e -> {
			String topicName = topicField.getText();
			try {
				queService.addTopic(topicName);
				JOptionPane.showMessageDialog(dialog, "Topic added successfully.");
				dialog.dispose();
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(dialog, "Error adding topic.");
			}
		});

		dialog.add(new JLabel("Enter Topic Name:"));
		dialog.add(topicField);
		dialog.add(submitButton);

		dialog.setVisible(true);
	}

	private void openAddQuestionDialog() {
		JDialog dialog = new JDialog(this, "Add Question", true);
		dialog.setSize(600, 400);
		dialog.setResizable(false);
		dialog.setBounds(400, 200, 600, 400);
		dialog.setLayout(new GridLayout(8, 2));

		JComboBox<String> topicComboBox = new JComboBox<>();
		
		JTextField questionField = new JTextField(20);
		JTextField option1Field = new JTextField(20);
		JTextField option2Field = new JTextField(20);
		JTextField option3Field = new JTextField(20);
		JTextField option4Field = new JTextField(20);
		JTextField correctOptionField = new JTextField(20);

		try {
			ResultSet topics = queService.getTopic();
			while (topics.next()) {
				topicComboBox.addItem(topics.getString("topic_id") + " - " + topics.getString("topic_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JButton submitButton = new JButton("Submit");

		submitButton.addActionListener(e -> {
			String selectedTopic = (String) topicComboBox.getSelectedItem();
			int topicId = Integer.parseInt(selectedTopic.split(" - ")[0]);
			String question = questionField.getText();
			String option1 = option1Field.getText();
			String option2 = option2Field.getText();
			String option3 = option3Field.getText();
			String option4 = option4Field.getText();
			int correctOption = Integer.parseInt(correctOptionField.getText());

			try {
				queService.addQuestion(topicId, question, option1, option2, option3, option4, correctOption);
				JOptionPane.showMessageDialog(dialog, "Question added successfully.");
				dialog.dispose();
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(dialog, "Error adding question.");
			}
		});

		dialog.add(new JLabel("Select Topic:"));
		dialog.add(topicComboBox);
		dialog.add(new JLabel("Enter Question:"));
		dialog.add(questionField);
		dialog.add(new JLabel("Option 1:"));
		dialog.add(option1Field);
		dialog.add(new JLabel("Option 2:"));
		dialog.add(option2Field);
		dialog.add(new JLabel("Option 3:"));
		dialog.add(option3Field);
		dialog.add(new JLabel("Option 4:"));
		dialog.add(option4Field);
		dialog.add(new JLabel("Correct Option (1-4):"));
		dialog.add(correctOptionField);
		dialog.add(new JLabel());
		dialog.add(submitButton);

		dialog.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			AdminUI adminUI = null;
			try {
				adminUI = new AdminUI();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			adminUI.setVisible(true);
		});
	}
}
