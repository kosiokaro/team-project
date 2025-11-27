package entity;

import java.util.ArrayList;

public class User implements FavoriteList, Watchlist {
    private String username;
    private String password;
    private final int accountID;
    private final ArrayList<Integer> favorites;
    private final ArrayList<Integer> watchlist;
    private ArrayList<Comment> comments = new ArrayList<>();

    public User(String username, String password, int accountID) {
        this.username = username;
        this.password = password;
        this.accountID = accountID;
        this.favorites = new ArrayList<>();
        this.watchlist =new ArrayList<>();
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public int getAccountID() {return accountID;}
    public ArrayList<Integer> getFavorites() {return favorites;}
    public ArrayList<Integer> getWatchlist() {return watchlist;}
    public ArrayList<Comment> getComments() {return comments;}

    @Override
    public void addFavorite(int refnumber) {
        favorites.add(refnumber);
    }

    @Override
    public void removeFavorite(int refnumber) {
        favorites.remove(refnumber);
    }

    @Override
    public void addWatchlist(int refnumber) {
        watchlist.add(refnumber);
    }

    @Override
    public void removeWatchList(int refnumber) {
        watchlist.remove(refnumber);
    }


    public void addcomment(Comment comment) {
        comments.add(comment);
    }

    public void setPassword(String password) {this.password = password;}


}
