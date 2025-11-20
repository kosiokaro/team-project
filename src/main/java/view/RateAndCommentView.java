package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.clicking.ClickingViewModel;
import interface_adapter.rate_and_comment.CommentController;
import interface_adapter.rate_and_comment.CommentState;
import interface_adapter.rate_and_comment.CommentViewModel;

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

public class RateAndCommentView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "comment";
    private final CommentViewModel commentViewModel;
    private final ClickingViewModel clickingViewModel;
    private final ViewManagerModel viewManagerModel;

    private String medianame;
    private int rating = 0;
    private JLabel[] stars = new JLabel[5];
    private JTextArea reviewArea;
    private JButton submitButton;
    private JButton returnButton;
    private JLabel ratingLabel;

    private CommentController commentController = null;

    // 现代配色方案
    private static final Color PRIMARY_BG = new Color(248, 250, 252);      // 浅灰蓝背景
    private static final Color CARD_BG = Color.WHITE;                       // 卡片白色
    private static final Color ACCENT_BLUE = new Color(59, 130, 246);      // 蓝色主题
    private static final Color ACCENT_GREEN = new Color(34, 197, 94);      // 绿色成功
    private static final Color TEXT_PRIMARY = new Color(30, 41, 59);       // 深色文字
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);  // 次要文字
    private static final Color STAR_ACTIVE = new Color(251, 191, 36);      // 金色星星
    private static final Color STAR_INACTIVE = new Color(203, 213, 225);   // 灰色星星
    private static final Color BORDER_COLOR = new Color(226, 232, 240);    // 边框颜色

    public RateAndCommentView(ViewManagerModel viewManagerModel, CommentViewModel commentViewModel,
                              ClickingViewModel clickingViewModel) {
        this.clickingViewModel = clickingViewModel;
        this.viewManagerModel = viewManagerModel;
        this.commentViewModel = commentViewModel;
        this.commentViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(0, 0));
        setBackground(PRIMARY_BG);

        // ====== 主容器 - 添加边距和卡片效果 ======
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout(0, 20));
        mainContainer.setBackground(PRIMARY_BG);
        mainContainer.setBorder(new EmptyBorder(30, 40, 30, 40));

        // ====== 标题部分 ======
        JPanel headerPanel = createHeaderPanel();
        mainContainer.add(headerPanel, BorderLayout.NORTH);

        // ====== 内容卡片 ======
        JPanel contentCard = new JPanel();
        contentCard.setLayout(new BorderLayout(0, 25));
        contentCard.setBackground(CARD_BG);
        contentCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(30, 30, 30, 30)
        ));

        // 评分面板
        JPanel ratingPanel = createRatingPanel();
        contentCard.add(ratingPanel, BorderLayout.NORTH);

        // 评论文本框
        JPanel reviewPanel = createReviewPanel();
        contentCard.add(reviewPanel, BorderLayout.CENTER);

        mainContainer.add(contentCard, BorderLayout.CENTER);

        // ====== 底部按钮 ======
        JPanel buttonPanel = createButtonPanel();
        mainContainer.add(buttonPanel, BorderLayout.SOUTH);

        add(mainContainer, BorderLayout.CENTER);
    }

    /**
     * 创建标题面板
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(PRIMARY_BG);

        // 标题
        JLabel titleLabel = new JLabel("Rate & Comment");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);

        // 副标题
        JLabel subtitleLabel = new JLabel("Share your thoughts about this movie");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);

        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.setBackground(PRIMARY_BG);
        titleContainer.add(titleLabel);
        titleContainer.add(Box.createVerticalStrut(5));
        titleContainer.add(subtitleLabel);

        headerPanel.add(titleContainer, BorderLayout.WEST);

        return headerPanel;
    }

    /**
     * 创建评分面板
     */
    private JPanel createRatingPanel() {
        JPanel ratingContainer = new JPanel();
        ratingContainer.setLayout(new BoxLayout(ratingContainer, BoxLayout.Y_AXIS));
        ratingContainer.setBackground(CARD_BG);

        // "Your Rating" 标签
        JLabel ratingTitleLabel = new JLabel("Your Rating");
        ratingTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        ratingTitleLabel.setForeground(TEXT_PRIMARY);
        ratingTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 星星面板
        JPanel starsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        starsPanel.setBackground(CARD_BG);

        for (int i = 0; i < 5; i++) {
            JLabel star = new JLabel("★");
            star.setFont(new Font("SansSerif", Font.PLAIN, 40));
            star.setForeground(STAR_INACTIVE);
            star.setCursor(new Cursor(Cursor.HAND_CURSOR));

            final int index = i;
            star.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setRating(index + 1);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    highlightStars(index + 1);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    highlightStars(rating);
                }
            });
            stars[i] = star;
            starsPanel.add(star);
        }

        // 评分文字提示
        ratingLabel = new JLabel("Click to rate");
        ratingLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        ratingLabel.setForeground(TEXT_SECONDARY);
        ratingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ratingContainer.add(ratingTitleLabel);
        ratingContainer.add(Box.createVerticalStrut(15));
        ratingContainer.add(starsPanel);
        ratingContainer.add(Box.createVerticalStrut(10));
        ratingContainer.add(ratingLabel);

        return ratingContainer;
    }

    /**
     * 创建评论文本框面板
     */
    private JPanel createReviewPanel() {
        JPanel reviewContainer = new JPanel();
        reviewContainer.setLayout(new BorderLayout(0, 10));
        reviewContainer.setBackground(CARD_BG);

        // "Your Review" 标签
        JLabel reviewTitleLabel = new JLabel("Your Review");
        reviewTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        reviewTitleLabel.setForeground(TEXT_PRIMARY);

        // 文本框
        reviewArea = new JTextArea("", 8, 20);
        reviewArea.setLineWrap(true);
        reviewArea.setWrapStyleWord(true);
        reviewArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        reviewArea.setForeground(TEXT_PRIMARY);
        reviewArea.setBackground(new Color(249, 250, 251));
        reviewArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(12, 12, 12, 12)
        ));

        // 添加焦点效果
        reviewArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                reviewArea.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACCENT_BLUE, 2),
                        new EmptyBorder(12, 12, 12, 12)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                reviewArea.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        new EmptyBorder(12, 12, 12, 12)
                ));
            }
        });

        JScrollPane scrollPane = new JScrollPane(reviewArea);
        scrollPane.setBorder(null);
        scrollPane.setBackground(CARD_BG);

        reviewArea.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final CommentState currentState = commentViewModel.getState();
                currentState.setComment(reviewArea.getText());
                commentViewModel.setState(currentState);
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

        reviewContainer.add(reviewTitleLabel, BorderLayout.NORTH);
        reviewContainer.add(scrollPane, BorderLayout.CENTER);

        return reviewContainer;
    }

    /**
     * 创建按钮面板
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(PRIMARY_BG);

        // Return 按钮
        returnButton = createStyledButton("Return", new Color(100, 116, 139), Color.WHITE);
        returnButton.addActionListener(e -> {
            setRating(0);
            setMedianame("");
            reviewArea.setText("");
            commentViewModel.setState(new CommentState());
            viewManagerModel.setState(clickingViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        });

        // Submit 按钮
        submitButton = createStyledButton("Submit Review", ACCENT_BLUE, Color.WHITE);
        submitButton.addActionListener(evt -> {
            if (evt.getSource().equals(submitButton)) {
                if (reviewArea.getText().trim().isEmpty()) {
                    showStyledDialog("Please write a comment before submitting!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    commentController.execute(medianame, reviewArea.getText(), rating);
                }
            }
        });

        buttonPanel.add(returnButton);
        buttonPanel.add(submitButton);

        return buttonPanel;
    }

    /**
     * 创建样式化按钮
     */
    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 42));

        // 悬停效果
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    /**
     * 显示样式化对话框
     */
    private void showStyledDialog(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                messageType
        );
    }

    /**
     * 设置评分
     */
    private void setRating(int newRating) {
        rating = newRating;
        highlightStars(newRating);

        // 更新提示文字
        if (newRating == 0) {
            ratingLabel.setText("Click to rate");
            ratingLabel.setForeground(TEXT_SECONDARY);
        } else {
            String[] descriptions = {"Poor", "Fair", "Good", "Very Good", "Excellent"};
            ratingLabel.setText(newRating + " / 5 - " + descriptions[newRating - 1]);
            ratingLabel.setForeground(STAR_ACTIVE);
        }

        // 更新 ViewModel
        CommentState currentState = commentViewModel.getState();
        currentState.setRate(newRating);
        commentViewModel.setState(currentState);
    }

    /**
     * 高亮星星
     */
    private void highlightStars(int count) {
        for (int i = 0; i < 5; i++) {
            if (i < count) {
                stars[i].setForeground(STAR_ACTIVE);
            } else {
                stars[i].setForeground(STAR_INACTIVE);
            }
        }
    }

    private void setMedianame(String mn) {
        medianame = mn;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CommentState state = (CommentState) evt.getNewValue();
        reviewArea.setText(state.getComment());
        setRating(state.getRate());
        setMedianame(state.getMedianame());
    }

    public String getViewName() {
        return viewName;
    }

    public void setCommentController(CommentController commentController) {
        this.commentController = commentController;
    }
}