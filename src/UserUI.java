/**
 * Created by Андрей on 30.07.2017.
 */
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Data.User;
import Controller.UserBean;
/**
 * Created by Андрей on 30.07.2017.
 */
public class UserUI extends JPanel {

    private JTextField idField = new JTextField(10);
    private JTextField fNameField = new JTextField(30);
    private JTextField isActive = new JTextField(5);
    private JTextField lNameField = new JTextField(30);
    private JTextField emailField = new JTextField(30);
    private JTextField birthday = new JTextField(10);
    private JTextField username = new JTextField(30);
    private JTextField password= new JTextField(10);

    private JButton createButton = new JButton("New");
    private JButton updateButton = new JButton("Update");
    private JButton deactivateButton = new JButton("Deactivate");
    private JButton activateButton = new JButton("Activate");
    private UserBean bean = new UserBean();

    public UserUI() {
        setBorder(new TitledBorder
                (new EtchedBorder(),"Person Details"));
        setLayout(new BorderLayout(5, 5));
        add(initFields(), BorderLayout.NORTH);
        add(initButtons(), BorderLayout.CENTER);
    }

    private JPanel initButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
        panel.add(createButton);
        createButton.addActionListener((ActionListener) new ButtonHandler());
        panel.add(updateButton);
        updateButton.addActionListener((ActionListener) new ButtonHandler());
        panel.add(deactivateButton);
        deactivateButton.addActionListener((ActionListener) new ButtonHandler());
        panel.add(activateButton);
        activateButton.addActionListener((ActionListener) new ButtonHandler());
        if (password.equals("admin")){
            panel.setVisible(true);
        }
        else{
            panel.setVisible(false);
            activateButton.setVisible(true);
        }
        return panel;
    }

    private JPanel initFields() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("ID"), "align label");
        panel.add(idField, "wrap");
        idField.setEnabled(false);
        panel.add(new JLabel("First Name"), "align label");
        panel.add(fNameField, "wrap");
        panel.add(new JLabel("isActive"), "align label");
        panel.add(isActive , "wrap");
        panel.add(new JLabel("Last Name"), "align label");
        panel.add(lNameField, "wrap");
        panel.add(new JLabel("Email"), "align label");
        panel.add(emailField, "wrap");
        panel.add(new JLabel("Birthday"), "align label");
        panel.add(birthday, "wrap");
        panel.add(new JLabel("Username"), "align label");
        panel.add(username, "wrap");
        panel.add(new JLabel("Password"), "align label");
        panel.add(password, "wrap");
        return panel;
    }

    private User getFieldData() {
        User u = new User();
        u.setId(Integer.parseInt(idField.getText()));
        u.setFirstname(fNameField.getText());
        u.setIsActive(Boolean.parseBoolean(isActive .getText()));
        u.setLastname(lNameField.getText());
        u.setEmail(emailField.getText());
        u.setBirtday(birthday.getText());
        u.setBirtday(username.getText());
        u.setBirtday(password.getText());
        return u;
    }

    private void setFieldData(User u) {
        idField.setText(String.valueOf(u.getId()));
        fNameField.setText(u.getFirstname());
        isActive .setText(String.valueOf(u.getIsActive()));
        lNameField.setText(u.getLastname());
        emailField.setText(u.getEmail());
        birthday.setText(u.getBirtday());
        username.setText(u.getUsername());
        password.setText(u.getPassword());
    }

    private boolean isEmptyFieldData() {
        return (fNameField.getText().trim().isEmpty()
                && isActive.getText().trim().isEmpty()
                && lNameField.getText().trim().isEmpty()
                && emailField.getText().trim().isEmpty()
                && birthday.getText().trim().isEmpty()
                && username.getText().trim().isEmpty()
                && password.getText().trim().isEmpty());
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            User u = getFieldData();
             switch (e.getActionCommand()) {
                case "Save":
                    if (isEmptyFieldData()) {
                        JOptionPane.showMessageDialog(null,
                                "Cannot create an empty record");
                        return;
                    }
                    if (bean.createUser(u) != null)
                        JOptionPane.showMessageDialog(null,
                                "New person created successfully.");
                    createButton.setText("New");
                    break;
                case "New":
                    u.setId(new Random()
                            .nextInt(Integer.MAX_VALUE) + 1);
                    u.setFirstname("");
                    u.setIsActive(Boolean.parseBoolean(""));
                    u.setLastname("");
                    u.setEmail("");
                    u.setBirtday("");
                    u.setUsername("");
                    u.setPassword("");
                    setFieldData(u);
                    createButton.setText("Save");
                    break;
                case "Edit":
                    if (isEmptyFieldData()) {
                        JOptionPane.showMessageDialog(null,
                                "Cannot edit an empty record");
                        return;
                    }
                    if (bean.editUser (u) != null)
                        JOptionPane.showMessageDialog(
                                null,"Person with ID:" + String.valueOf(u.getId()
                                        + " is updated successfully"));
                    break;
                case "Deactivate":
                    if (isEmptyFieldData()) {
                        JOptionPane.showMessageDialog(null,
                                "Cannot deactivate an empty record");
                        return;
                    }
                    bean.deactivateUser();
                    JOptionPane.showMessageDialog(
                            null,"Person with ID:"
                                    + String.valueOf(u.getId()
                                    + " is deactivated successfully"));
                    break;
                case "Activate":
                    if (isEmptyFieldData()) {
                        JOptionPane.showMessageDialog(null,
                                "Cannot activate an empty record");
                        return;
                    }
                    bean.activateUser();
                    JOptionPane.showMessageDialog(
                            null,"Person with ID:"
                                    + String.valueOf(u.getId()
                                    + " is activated successfully"));
                    break;
                default:
                    JOptionPane.showMessageDialog(null,
                            "invalid command");
            }
        }
    }
}