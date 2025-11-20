package data_access;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import entity.Comment;
import entity.Media;
import entity.User;
import use_case.rate_and_comment.CommentUserDataAccessInterface;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUserDataAccessObject implements CommentUserDataAccessInterface {

    private final String filePath = "src/main/java/userdata/users.json";
    private final Gson gson = new Gson();
    private final Type mapType = new TypeToken<Map<String, User>>(){}.getType();

    private Map<String, User> readFile() {
        try {
            File file = new File(filePath);

            // 调试信息
            System.out.println("=== File Debug Info ===");
            System.out.println("File exists: " + file.exists());
            System.out.println("File path: " + file.getAbsolutePath());
            System.out.println("Can read: " + file.canRead());

            if (!file.exists()) {
                System.out.println("File not found, returning empty map");
                return new HashMap<>();
            }

            FileReader reader = new FileReader(file);
            Map<String, User> map = gson.fromJson(reader, mapType);
            reader.close();

            // 调试信息
            if (map == null) {
                System.out.println("Gson returned null, returning empty map");
                return new HashMap<>();
            }

            System.out.println("Successfully loaded " + map.size() + " users");
            System.out.println("User keys: " + map.keySet());

            return map;
        } catch (Exception e) {
            System.err.println("Error reading file:");
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private void writeFile(Map<String, User> map) {
        try {
            FileWriter writer = new FileWriter(filePath);
            gson.toJson(map, writer);
            writer.close();
            System.out.println("Successfully wrote " + map.size() + " users to file");
        } catch (Exception e) {
            System.err.println("Error writing file:");
            e.printStackTrace();
        }
    }

    public void createUser(String username, String password, int accountID) {
        Map<String, User> map = readFile();
        if (!map.containsKey(username)) {
            map.put(username, new User(username, password, accountID));
            writeFile(map);
            System.out.println("Created user: " + username);
        } else {
            System.out.println("User already exists: " + username);
        }
    }

    public User getUser(String username) {
        Map<String, User> map = readFile();

        // 调试信息
        System.out.println("=== Get User Debug ===");
        System.out.println("Looking for: '" + username + "'");
        System.out.println("Available users: " + map.keySet());

        User user = map.get(username);
        System.out.println("User found: " + (user != null));

        if (user == null) {
            System.out.println("Trying with trimmed username...");
            user = map.get(username.trim());
            System.out.println("User found after trim: " + (user != null));
        }

        return user;
    }

    public void addToWatchlist(String username, Media movie) {
        Map<String, User> map = readFile();
        User user = map.get(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        user.addWatchlist(movie);
        writeFile(map);
    }

    public void deleteFromWatchlist(String username, Media movie) {
        Map<String, User> map = readFile();
        User user = map.get(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        user.removeWatchList(movie);
        writeFile(map);
    }

    public void addToFavoritelist(String username, Media movie) {
        Map<String, User> map = readFile();
        User user = map.get(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        user.addFavorite(movie);
        writeFile(map);
    }

    public void deleteFromFavoritelist(String username, Media movie) {
        Map<String, User> map = readFile();
        User user = map.get(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        user.removeFavorite(movie);
        writeFile(map);
    }

    public List<Media> getWatchlist(String username) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return user.getWatchlist();
    }

    public List<Media> getFavoritelist(String username) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return user.getFavorites();
    }

    @Override
    public void addComment(String username, Comment comment) {
        Map<String, User> map = readFile();
        if (!map.containsKey(username)) {
            throw new RuntimeException("User '" + username + "' does not exist in users.json");
        }
        map.get(username).addcomment(comment);
        writeFile(map);
    }

    public List<Comment> getComments(String username) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return user.getComments();
    }

    public String getPassword(String username) {
        User user = getUser(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return user.getPassword();
    }

    public void changePassword(String username, String newPassword) {
        Map<String, User> map = readFile();
        User user = map.get(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        user.setPassword(newPassword);
        writeFile(map);
    }
}