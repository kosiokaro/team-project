package view;

import interface_adapter.RandC_success_submit.RandCSuccessState;
import interface_adapter.RandC_success_submit.RandCSuccessViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.clicking.ClickingState;
import interface_adapter.clicking.ClickingViewModel;
import interface_adapter.home.HomeState;
import interface_adapter.home.HomeViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RandCSuccessSubmitView extends JPanel implements PropertyChangeListener {
    private String movieName;
    private JButton returnButton;
    private JButton homeButton;
    private JLabel messageLabel;
    private JLabel movieNameLabel;
    private ViewManagerModel viewManagerModel;
    private RandCSuccessViewModel randCSuccessViewModel;
    private ClickingViewModel clickingViewModel;
    private HomeViewModel homeViewModel;
    private String viewname = "RandC";

    // 现代配色
    private static final Color PRIMARY_BG = new Color(248, 250, 252);
    private static final Color SUCCESS_GREEN = new Color(34, 197, 94);
    private static final Color ACCENT_BLUE = new Color(59, 130, 246);
    private static final Color TEXT_PRIMARY = new Color(30, 41, 59);
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private static final Color CARD_BG = Color.WHITE;

    public RandCSuccessSubmitView(ViewManagerModel viewManagerModel, RandCSuccessViewModel randCSuccessViewModel,
                                  ClickingViewModel clickingViewModel, HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.randCSuccessViewModel = randCSuccessViewModel;
        this.clickingViewModel = clickingViewModel;
        this.homeViewModel = homeViewModel;
        this.randCSuccessViewModel.addPropertyChangeListener(this);
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        setBackground(PRIMARY_BG);
        GridBagConstraints gbc = new GridBagConstraints();

        // ====== 主卡片容器 ======
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(CARD_BG);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(40, 50, 40, 50)
        ));
        cardPanel.setPreferredSize(new Dimension(500, 400));

        // ====== 成功图标 ======
        JPanel iconPanel = createSuccessIcon();
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(iconPanel);

        cardPanel.add(Box.createVerticalStrut(25));

        // ====== 成功标题 ======
        JLabel successTitle = new JLabel("Success!");
        successTitle.setFont(new Font("SansSerif", Font.BOLD, 32));
        successTitle.setForeground(SUCCESS_GREEN);
        successTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(successTitle);

        cardPanel.add(Box.createVerticalStrut(15));

        // ====== 提示消息 ======
        messageLabel = new JLabel("Your review has been submitted for");
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        messageLabel.setForeground(TEXT_SECONDARY);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(messageLabel);

        cardPanel.add(Box.createVerticalStrut(10));

        // ====== 电影名称（高亮显示）======
        movieNameLabel = new JLabel(movieName != null ? "\"" + movieName + "\"" : "");
        movieNameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        movieNameLabel.setForeground(ACCENT_BLUE);
        movieNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(movieNameLabel);

        cardPanel.add(Box.createVerticalStrut(10));

        // ====== 感谢消息 ======
        JLabel thankYouLabel = new JLabel("Thank you for sharing your thoughts!");
        thankYouLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        thankYouLabel.setForeground(TEXT_SECONDARY);
        thankYouLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(thankYouLabel);

        cardPanel.add(Box.createVerticalStrut(35));

        // ====== 按钮面板 ======
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(buttonPanel);

        // 添加卡片到主面板
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(cardPanel, gbc);
    }

    /**
     * 创建成功图标（对勾）
     */
    private JPanel createSuccessIcon() {
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int size = 80;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;

                // 绘制圆形背景
                g2d.setColor(new Color(34, 197, 94, 30)); // 浅绿色背景
                g2d.fillOval(x, y, size, size);

                // 绘制边框
                g2d.setColor(SUCCESS_GREEN);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawOval(x + 2, y + 2, size - 4, size - 4);

                // 绘制对勾
                g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int checkX = x + size / 2 - 15;
                int checkY = y + size / 2;

                // 对勾的左边
                g2d.drawLine(checkX, checkY, checkX + 10, checkY + 10);
                // 对勾的右边
                g2d.drawLine(checkX + 10, checkY + 10, checkX + 25, checkY - 8);
            }
        };
        iconPanel.setPreferredSize(new Dimension(100, 100));
        iconPanel.setMaximumSize(new Dimension(100, 100));
        iconPanel.setBackground(CARD_BG);
        return iconPanel;
    }

    /**
     * 创建按钮面板
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(CARD_BG);

        // Return 按钮
        returnButton = createStyledButton("Back to Movie", ACCENT_BLUE, Color.WHITE, true);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randCSuccessViewModel.setState(new RandCSuccessState());
                randCSuccessViewModel.firePropertyChange();
                viewManagerModel.setState(clickingViewModel.getViewName());
                viewManagerModel.firePropertyChange();
            }
        });

        // Home 按钮
        homeButton = createStyledButton("Go to Home", new Color(100, 116, 139), Color.WHITE, false);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randCSuccessViewModel.setState(new RandCSuccessState());
                randCSuccessViewModel.firePropertyChange();
                clickingViewModel.setState(new ClickingState());
                clickingViewModel.firePropertyChange();
                viewManagerModel.setState(homeViewModel.getViewName());
                viewManagerModel.firePropertyChange();
            }
        });

        buttonPanel.add(returnButton);
        buttonPanel.add(homeButton);

        return buttonPanel;
    }

    /**
     * 创建样式化按钮
     */
    private JButton createStyledButton(String text, Color bgColor, Color fgColor, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 44));

        // 悬停效果
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isPrimary) {
                    button.setBackground(bgColor.darker());
                } else {
                    button.setBackground(bgColor.brighter());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        RandCSuccessState state = (RandCSuccessState) evt.getNewValue();
        movieName = state.getMedianame();
        movieNameLabel.setText(movieName != null ? "\"" + movieName + "\"" : "");
    }

    public String getViewName() {
        return this.viewname;
    }
}