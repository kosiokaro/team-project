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
                .addRandCView()
                .addLoginUseCase()
                .addSignupUseCase()
                .addCommentUseCase()
                .addClickingUseCase()
                .build();

        application.setLocationRelativeTo(null);
    }
}
