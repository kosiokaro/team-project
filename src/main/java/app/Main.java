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
                .addFavoritesView()
                .addRateAndCommentView()
                .addClickingView()
                .addBrowseView()
                .addClickingUseCase()
                .addRandCView()
                .addBrowseUseCase()
                .addLoginUseCase()
                .addSignupUseCase()
                .addCommentUseCase()
                .addLoadWatchListUseCase()
                .addWatchListUseCase()
                .build();

        application.setLocationRelativeTo(null);
    }
}
