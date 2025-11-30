package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addLoginView()
                .addSignUpView()
                .addHomepageView()
                .addWatchlistView()
                .addLoadFavoritesUseCase()
                .addFavoritesUseCase()
                .addTestFavoritesView()
                .addRateAndCommentView()
                .addClickingView()
                .addClickingUseCase()
                .addBrowseView()
                .addRandCView()
                .addBrowseUseCase()
                .addLoginUseCase()
                .addSignupUseCase()
                .addCommentUseCase()
                .addClickingUseCase()
                .addBrowseUseCase()
                .build();

        application.setLocationRelativeTo(null);
    }
}
