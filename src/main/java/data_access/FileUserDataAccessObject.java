package data_access;

import com.google.gson.Gson;
import entity.Comment;
import entity.Media;
import entity.User;
import use_case.login.LoginUserDataAccessInterface;
import use_case.rate_and_comment.CommentUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优化版用户数据访问对象
 * 使用分级文件管理：每个用户一个独立的 JSON 文件
 * 使用索引文件加速用户名和 accountID 查找
 */
public class FileUserDataAccessObject implements CommentUserDataAccessInterface,
        SignupUserDataAccessInterface, LoginUserDataAccessInterface {

    private final String userDataDir = "src/main/java/userdata/users/";
    private final String indexFilePath = "src/main/java/userdata/index.json";
    private final Gson gson = new Gson();
    private String currentUser = "Eil";

    // 索引结构：存储用户名和 accountID 的映射关系
    private static class UserIndex {
        Map<String, Integer> usernameToAccountID = new HashMap<>();
        Map<Integer, String> accountIDToUsername = new HashMap<>();
    }

    public FileUserDataAccessObject() {
        // 确保目录存在
        File dir = new File(userDataDir);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Created user data directory: " + dir.getAbsolutePath());
        }
    }

    // ==================== 索引管理 ====================

    private UserIndex readIndex() {
        File indexFile = new File(indexFilePath);
        if (!indexFile.exists()) {
            return new UserIndex();
        }

        try (FileReader reader = new FileReader(indexFile)) {
            UserIndex index = gson.fromJson(reader, UserIndex.class);
            return index != null ? index : new UserIndex();
        } catch (Exception e) {
            System.err.println("Error reading index file: " + e.getMessage());
            return new UserIndex();
        }
    }

    private void writeIndex(UserIndex index) {
        try (FileWriter writer = new FileWriter(indexFilePath)) {
            gson.toJson(index, writer);
        } catch (IOException e) {
            System.err.println("Error writing index file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== 单用户文件操作 ====================

    private String getUserFilePath(String username) {
        return userDataDir + username + ".json";
    }

    private User readUserFile(String username) {
        File userFile = new File(getUserFilePath(username));

        if (!userFile.exists()) {
            System.out.println("User file not found: " + username);
            return null;
        }

        try (FileReader reader = new FileReader(userFile)) {
            User user = gson.fromJson(reader, User.class);
            System.out.println("Successfully loaded user: " + username);
            return user;
        } catch (Exception e) {
            System.err.println("Error reading user file for " + username + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void writeUserFile(User user) {
        String filePath = getUserFilePath(user.getUsername());

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(user, writer);
            System.out.println("Successfully saved user: " + user.getUsername());
        } catch (IOException e) {
            System.err.println("Error writing user file for " + user.getUsername() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== 接口实现 ====================

    @Override
    public void setCurrentUser(String username) {
        this.currentUser = username;
    }

    @Override
    public String getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public void createUser(String username, String password, int accountID) {
        if (existsByName(username)) {
            System.out.println("User already exists: " + username);
            return;
        }

        // 创建新用户
        User newUser = new User(username, password, accountID);
        writeUserFile(newUser);

        // 更新索引
        UserIndex index = readIndex();
        index.usernameToAccountID.put(username, accountID);
        index.accountIDToUsername.put(accountID, username);
        writeIndex(index);

        System.out.println("Created user: " + username);
    }

    @Override
    public boolean existsByName(String username) {
        UserIndex index = readIndex();
        return index.usernameToAccountID.containsKey(username);
    }

    @Override
    public boolean existsByAccountID(int accountID) {
        UserIndex index = readIndex();
        return index.accountIDToUsername.containsKey(accountID);
    }

    @Override
    public User getUser(String username) {
        System.out.println("=== Get User ===");
        System.out.println("Looking for: '" + username + "'");

        User user = readUserFile(username);

        if (user == null) {
            System.out.println("User not found, trying trimmed username...");
            user = readUserFile(username.trim());
        }

        System.out.println("User found: " + (user != null));
        return user;
    }

    // ==================== 观看列表管理 ====================

    public void addToWatchlist(String username, Media movie) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        user.addWatchlist(movie.getReferenceNumber());
        writeUserFile(user);
    }

    public void deleteFromWatchlist(String username, Media movie) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        user.removeWatchList(movie.getReferenceNumber());
        writeUserFile(user);
    }

    public List<Integer> getWatchlist(String username) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return user.getWatchlist();
    }

    // ==================== 收藏列表管理 ====================

    public void addToFavoritelist(String username, Media movie) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        user.addFavorite(movie.getReferenceNumber());
        writeUserFile(user);
    }

    public void deleteFromFavoritelist(String username, Media movie) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        user.removeFavorite(movie.getReferenceNumber());
        writeUserFile(user);
    }

    public List<Integer> getFavoritelist(String username) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return user.getFavorites();
    }

    // ==================== 评论管理 ====================

    @Override
    public void addComment(String username, Comment comment) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User '" + username + "' does not exist");
        }
        user.addcomment(comment);
        writeUserFile(user);
    }

    public List<Comment> getComments(String username) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return user.getComments();
    }

    // ==================== 密码管理 ====================

    public String getPassword(String username) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return user.getPassword();
    }

    public void changePassword(String username, String newPassword) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        user.setPassword(newPassword);
        writeUserFile(user);
    }

}