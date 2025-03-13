package snakegameeval;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private boolean authenticated;

    public LoginForm() {
        super("Login");

        setLayout(new GridLayout(3, 2));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        JLabel usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        loginButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        if (authenticate(username, password)) {
            authenticated = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(LoginForm.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean authenticate(String username, String password) {
        return "Fatima".equals(username) && "7211".equals(password);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
