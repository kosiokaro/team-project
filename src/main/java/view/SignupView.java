package view;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

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
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private SignupController signupController = null;

    private final JButton signUp;
    private final JButton cancel;
    private final JButton toLogin;

    // 现代配色方案
    private static final Color PRIMARY_BG = new Color(248, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color ACCENT_BLUE = new Color(59, 130, 246);
    private static final Color ACCENT_GREEN = new Color(34, 197, 94);
    private static final Color TEXT_PRIMARY = new Color(30, 41, 59);
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private static final Color ERROR_RED = new Color(239, 68, 68);
    private static final Color BORDER_COLOR = new Color(226, 232, 240);
    private static final Color INPUT_BG = new Color(249, 250, 251);

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        setLayout(new GridBagLayout());
        setBackground(PRIMARY_BG);
        GridBagConstraints gbc = new GridBagConstraints();

        // ====== 注册卡片容器 ======
        JPanel signupCard = new JPanel();
        signupCard.setLayout(new BoxLayout(signupCard, BoxLayout.Y_AXIS));
        signupCard.setBackground(CARD_BG);
        signupCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(40, 50, 40, 50)
        ));
        signupCard.setPreferredSize(new Dimension(420, 550));

        // ====== 顶部标题（无图标）======
        JPanel headerPanel = createHeaderPanel();
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupCard.add(headerPanel);

        signupCard.add(Box.createVerticalStrut(25));

        // ====== 用户名输入 ======
        JPanel usernamePanel = createInputPanel(
                SignupViewModel.USERNAME_LABEL,
                usernameInputField,
                new JLabel()
        );
        usernamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupCard.add(usernamePanel);

        signupCard.add(Box.createVerticalStrut(18));

        // ====== 密码输入 ======
        JPanel passwordPanel = createInputPanel(
                SignupViewModel.PASSWORD_LABEL,
                passwordInputField,
                new JLabel()
        );
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupCard.add(passwordPanel);

        signupCard.add(Box.createVerticalStrut(18));

        // ====== 确认密码输入 ======
        JPanel repeatPasswordPanel = createInputPanel(
                SignupViewModel.REPEAT_PASSWORD_LABEL,
                repeatPasswordInputField,
                new JLabel()
        );
        repeatPasswordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupCard.add(repeatPasswordPanel);

        signupCard.add(Box.createVerticalStrut(25));

        // ====== 按钮面板 ======
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sign Up 按钮（主要操作）
        signUp = createStyledButton(
                SignupViewModel.SIGNUP_BUTTON_LABEL,
                ACCENT_GREEN,
                Color.WHITE,
                true
        );
        signUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(signUp)) {
                    final SignupState currentState = signupViewModel.getState();
                    signupController.execute(
                            currentState.getUsername(),
                            currentState.getPassword(),
                            currentState.getRepeatPassword()
                    );
                }
            }
        });

        buttonPanel.add(signUp);
        buttonPanel.add(Box.createVerticalStrut(12));

        // Cancel 按钮
        cancel = createStyledButton(
                SignupViewModel.CANCEL_BUTTON_LABEL,
                new Color(226, 232, 240),
                TEXT_SECONDARY,
                false
        );
        cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancel.addActionListener(this);

        buttonPanel.add(cancel);

        signupCard.add(buttonPanel);

        signupCard.add(Box.createVerticalStrut(20));

        // ====== 切换到登录的链接 ======
        JPanel loginLinkPanel = createLoginLinkPanel();
        loginLinkPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupCard.add(loginLinkPanel);

        // 添加卡片到主面板
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(signupCard, gbc);

        // ====== 文档监听器 ======
        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();

        // ====== To Login 按钮逻辑 ======
        toLogin = new JButton(); // 创建但不显示在传统位置
        toLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                signupController.switchToLoginView();
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
        JLabel titleLabel = new JLabel(SignupViewModel.TITLE_LABEL);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 副标题
        JLabel subtitleLabel = new JLabel("Create your account to get started");
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
        panel.setMaximumSize(new Dimension(320, 80));

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
                        BorderFactory.createLineBorder(ACCENT_GREEN, 2),
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

        return panel;
    }

    /**
     * 创建登录链接面板
     */
    private JPanel createLoginLinkPanel() {
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        linkPanel.setBackground(CARD_BG);

        JLabel promptLabel = new JLabel("Already have an account?");
        promptLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        promptLabel.setForeground(TEXT_SECONDARY);

        JLabel loginLink = new JLabel(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
        loginLink.setFont(new Font("SansSerif", Font.BOLD, 13));
        loginLink.setForeground(ACCENT_BLUE);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 下划线效果
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginLink.setText("<html><u>" + SignupViewModel.TO_LOGIN_BUTTON_LABEL + "</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginLink.setText(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                signupController.switchToLoginView();
            }
        });

        linkPanel.add(promptLabel);
        linkPanel.add(loginLink);

        return linkPanel;
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

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
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

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
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

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(currentState);
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

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}