package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton logIn;
    private final JButton cancel;
    private LoginController loginController = null;

    // 现代配色方案
    private static final Color PRIMARY_BG = new Color(248, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color ACCENT_BLUE = new Color(59, 130, 246);
    private static final Color TEXT_PRIMARY = new Color(30, 41, 59);
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private static final Color ERROR_RED = new Color(239, 68, 68);
    private static final Color BORDER_COLOR = new Color(226, 232, 240);
    private static final Color INPUT_BG = new Color(249, 250, 251);

    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        setLayout(new GridBagLayout());
        setBackground(PRIMARY_BG);
        GridBagConstraints gbc = new GridBagConstraints();

        // ====== 登录卡片容器 ======
        JPanel loginCard = new JPanel();
        loginCard.setLayout(new BoxLayout(loginCard, BoxLayout.Y_AXIS));
        loginCard.setBackground(CARD_BG);
        loginCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(40, 50, 40, 50)
        ));
        loginCard.setPreferredSize(new Dimension(420, 450));

        // ====== 顶部标题（无图标）======
        JPanel headerPanel = createHeaderPanel();
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginCard.add(headerPanel);

        loginCard.add(Box.createVerticalStrut(30));

        // ====== 用户名输入 ======
        JPanel usernamePanel = createInputPanel("Username", usernameInputField, usernameErrorField);
        usernamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginCard.add(usernamePanel);

        loginCard.add(Box.createVerticalStrut(20));

        // ====== 密码输入 ======
        JPanel passwordPanel = createInputPanel("Password", passwordInputField, passwordErrorField);
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginCard.add(passwordPanel);

        loginCard.add(Box.createVerticalStrut(30));

        // ====== 按钮面板 ======
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login 按钮
        logIn = createStyledButton("Log In", ACCENT_BLUE, Color.WHITE, true);
        logIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(logIn)) {
                    final LoginState currentState = loginViewModel.getState();
                    loginController.execute(
                            currentState.getUsername(),
                            currentState.getPassword()
                    );
                    // 清除密码
                    passwordInputField.setText("");
                }
            }
        });

        buttonPanel.add(logIn);
        buttonPanel.add(Box.createVerticalStrut(12));

        // Cancel 按钮
        cancel = createStyledButton("Cancel", new Color(226, 232, 240), TEXT_SECONDARY, false);
        cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancel.addActionListener(this);

        buttonPanel.add(cancel);

        loginCard.add(buttonPanel);

        // 添加卡片到主面板
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(loginCard, gbc);

        // ====== 文档监听器 ======
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    /**
     * 创建头部面板
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(CARD_BG);

        // 标题
        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 副标题
        JLabel subtitleLabel = new JLabel("Please login to your account");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(subtitleLabel);

        return headerPanel;
    }

    /**
     * 创建输入面板
     */
    private JPanel createInputPanel(String labelText, JTextField inputField, JLabel errorLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BG);
        panel.setMaximumSize(new Dimension(320, 90));

        // 标签
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 输入框样式
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputField.setForeground(TEXT_PRIMARY);
        inputField.setBackground(INPUT_BG);
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(10, 12, 10, 12)
        ));
        inputField.setMaximumSize(new Dimension(320, 42));
        inputField.setPreferredSize(new Dimension(320, 42));
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 焦点效果
        inputField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACCENT_BLUE, 2),
                        new EmptyBorder(10, 12, 10, 12)
                ));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                inputField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        new EmptyBorder(10, 12, 10, 12)
                ));
            }
        });

        // 错误提示
        errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        errorLabel.setForeground(ERROR_RED);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(inputField);
        panel.add(Box.createVerticalStrut(5));
        panel.add(errorLabel);

        return panel;
    }

    /**
     * 创建样式化按钮
     */
    private JButton createStyledButton(String text, Color bgColor, Color fgColor, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(320, 44));
        button.setMaximumSize(new Dimension(320, 44));

        // 悬停效果
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isPrimary) {
                    button.setBackground(bgColor.darker());
                } else {
                    button.setBackground(bgColor.darker());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getLoginError());
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}