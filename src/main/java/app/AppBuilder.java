package app;


import data_access.BrowseDataAccess;
import data_access.ClickingDataAccessTMDb;
import data_access.FileUserDataAccessObject;


import data_access.WatchlistMovieDataAccess;
import entity.MediaDetailsResponse;
import interface_adapter.RandC_success_submit.RandCSuccessViewModel;
import interface_adapter.ViewManagerModel;

import interface_adapter.browse.BrowseController;
import interface_adapter.browse.BrowsePresenter;
import interface_adapter.browse.BrowseViewModel;
import interface_adapter.home.HomeViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.rate_and_comment.CommentController;
import interface_adapter.rate_and_comment.CommentPresenter;
import interface_adapter.rate_and_comment.CommentViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.watchlist.*;
import interface_adapter.watchlist.load.LoadWatchListController;
import interface_adapter.watchlist.load.LoadWatchListPresenter;
import interface_adapter.watchlist.load.LoadWatchListViewModel;
import use_case.browse.BrowseInputBoundary;
import use_case.browse.BrowseInteractor;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;

import interface_adapter.clicking.ClickingState;
import interface_adapter.clicking.ClickingPresenter;
import interface_adapter.clicking.ClickingController;
import interface_adapter.clicking.ClickingViewModel;
import use_case.clicking.*;
import use_case.rate_and_comment.CommentInputBoundary;
import use_case.rate_and_comment.CommentInteractor;
import use_case.rate_and_comment.CommentOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.watchlist.addToWatchList.AddToWatchListDataAccessInterface;
import use_case.watchlist.addToWatchList.AddToWatchListInputBoundaryData;
import use_case.watchlist.addToWatchList.AddToWatchListInteractor;
import use_case.watchlist.addToWatchList.AddToWatchListOutputBoundary;
import use_case.watchlist.deleteFromWatchList.DeleteFromWatchListDataAccessInterface;
import use_case.watchlist.deleteFromWatchList.DeleteFromWatchListInputBoundaryData;
import use_case.watchlist.deleteFromWatchList.DeleteFromWatchListInteractor;
import use_case.watchlist.deleteFromWatchList.DeleteFromWatchListOutputBoundary;
import use_case.watchlist.loadWatchList.LoadWatchListDataAccessInterface;
import use_case.watchlist.loadWatchList.LoadWatchListInputBoundaryData;
import use_case.watchlist.loadWatchList.LoadWatchListInteractor;
import use_case.watchlist.loadWatchList.LoadWatchListOutputBoundaryData;
import view.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);
    final BrowseDataAccess browseDataAccess = new BrowseDataAccess();
    final FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject();
    //views and view models
    private SignupView signupView;
    private SignupViewModel signupViewModel;

    private LoginView loginView;
    private LoginViewModel  loginViewModel;

    private WatchlistView watchlistView;
    private LoadWatchListViewModel loadWatchListViewModel;
    private LoadWatchListController loadWatchListController;
    private ClickingView clickingView;
    private AddToWatchListViewModel addToWatchListViewModel;
    private DeleteFromWatchListViewModel deleteFromWatchListViewModel;

    private FavoritesView favoritesView;

    private BrowseView browseView;
    private BrowseViewModel browseViewModel;
    private BrowsePresenter browsePresenter;


    private HomepageView homepageView;
    private HomeViewModel homeViewModel;

    private RateAndCommentView rateAndCommentView;
    private CommentViewModel commentViewModel;

    private RandCSuccessSubmitView randCSuccessSubmitView;
    private RandCSuccessViewModel randCSuccessViewModel;

    private ClickingViewModel clickingViewModel;
    private ClickingController clickingController;



    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        //view models must be set up here to make the main run without concern of the order of the view's set up.
        signupViewModel = new SignupViewModel();
        loginViewModel = new LoginViewModel();
        clickingViewModel = new ClickingViewModel();
        commentViewModel = new CommentViewModel();
        randCSuccessViewModel = new RandCSuccessViewModel();
        homeViewModel = new HomeViewModel();
        browseViewModel = new BrowseViewModel();
        loadWatchListViewModel = new LoadWatchListViewModel();
        addToWatchListViewModel = new AddToWatchListViewModel();
        deleteFromWatchListViewModel = new DeleteFromWatchListViewModel();
    }

    public AppBuilder addSignUpView() {

        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {

        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addClickingView() {

        clickingView = new ClickingView(clickingViewModel,commentViewModel,viewManagerModel);
        cardPanel.add(clickingView, clickingView.getViewName());

        ClickingDataAccessInterface dataAccess = new ClickingDataAccessTMDb();
        new Thread(() -> {
            MediaDetailsResponse movie = dataAccess.fetchDetailsById(550);
            if (movie != null) {
                SwingUtilities.invokeLater(() -> {
                    ClickingState initState = clickingViewModel.getState();
                    initState.setTitle(movie.getTitle());
                    initState.setOverview(movie.getOverview());
                    initState.setYear(movie.getReleaseYear());
                    initState.setRating(Double.parseDouble(String.valueOf(movie.getRating())));
                    initState.setLanguage(movie.getLanguage());
                    initState.setGenres(movie.getGenres());
                    initState.setPosterUrl(movie.getPosterUrl());
                    clickingViewModel.firePropertyChange();
                });
            }
        }).start();

        return this;
    }

    public AppBuilder addClickingUseCase() {
        final ClickingDataAccessInterface clickingDataAccess = new ClickingDataAccessTMDb();

        final ClickingOutputBoundary clickingPresenter = new ClickingPresenter(
                clickingViewModel, viewManagerModel);
        final ClickingInputBoundary clickingInteractor = new ClickingInteractor(
                clickingPresenter, clickingDataAccess);

        this.clickingController = new ClickingController(clickingInteractor);
        clickingView.setClickingController(clickingController);
        return this;
    }

    public AppBuilder addWatchlistView() {
        watchlistView = new WatchlistView(loadWatchListViewModel);
        cardPanel.add(watchlistView, watchlistView.getViewName());

        watchlistView.setswitchtofavButtonListener(e -> {
            viewManagerModel.setState(favoritesView.getViewName());
            viewManagerModel.firePropertyChange();
        });

        watchlistView.sethomeButtonListener(e -> {
            viewManagerModel.setState(homepageView.getViewName());
            viewManagerModel.firePropertyChange();
        });

        if (clickingController != null) {
            watchlistView.setClickingController(clickingController);
        } else {
            System.err.println("Warning: clickingController is null when setting up WatchlistView");
        }

        return this;
    }

    public AppBuilder addFavoritesView() {
        favoritesView = new FavoritesView();
        cardPanel.add(favoritesView, favoritesView.getViewName());

        favoritesView.setswitchtowatchButtonListener(e -> {
            viewManagerModel.setState(watchlistView.getViewName());
            viewManagerModel.firePropertyChange();
        });

        favoritesView.sethomeButtonListener(e -> {
            viewManagerModel.setState(homepageView.getViewName());
            viewManagerModel.firePropertyChange();
        });

        return this;
    }

    public AppBuilder addRateAndCommentView() {

        rateAndCommentView = new RateAndCommentView(viewManagerModel,commentViewModel,clickingViewModel);
        cardPanel.add(rateAndCommentView,rateAndCommentView.getViewName());
        return this;
    }

    public AppBuilder addRandCView() {

        randCSuccessSubmitView = new RandCSuccessSubmitView(viewManagerModel, randCSuccessViewModel, clickingViewModel,
                homeViewModel);
        cardPanel.add(randCSuccessSubmitView, randCSuccessSubmitView.getViewName());
        return this;
    }

    public AppBuilder addBrowseView(){
        browseView = new BrowseView(browseViewModel, addToWatchListViewModel);
        cardPanel.add(browseView, browseView.getViewName());
        return this;
    }


    public AppBuilder addHomepageView() {

        homepageView = new HomepageView(homeViewModel, viewManagerModel);
        cardPanel.add(homepageView, homepageView.getViewName());

        // Use the actual getViewName() for the two existing views.
        // For browse: you said it's "in progress" so either add a browse view
        // or use homepageView.getViewName() as a placeholder.
        homepageView.setBrowseButtonListener(e -> {
            viewManagerModel.setState(browseView.getViewName());
            viewManagerModel.firePropertyChange();
        });

        // These two will use the actual view names (safer than hard-coded strings)
        homepageView.setWatchlistButtonListener(e -> {
            System.out.println("=== Watchlist button clicked ===");
            String username = homeViewModel.getState().getUsername();
            System.out.println("Username: " + username);

            if (AppBuilder.this.loadWatchListController != null && username != null && !username.isEmpty()) {
                System.out.println("Loading watchlist for: " + username);

                // Trigger the load (controller creates the InputData internally)
                AppBuilder.this.loadWatchListController.loadWatchlist(username);
            } else {
                System.err.println("Cannot load watchlist - controller: " +
                        (AppBuilder.this.loadWatchListController != null ? "SET" : "NULL") +
                        ", username: " + (username != null ? username : "NULL"));
            }

            // Switch to watchlist view
            viewManagerModel.setState(watchlistView.getViewName());
            viewManagerModel.firePropertyChange();
        });

        homepageView.setFavoritesButtonListener(e -> {
            viewManagerModel.setState(favoritesView.getViewName());
            viewManagerModel.firePropertyChange();
        });

        return this;
    }

    public AppBuilder addBrowseUseCase(){
        BrowsePresenter browsePresenter = new BrowsePresenter(browseViewModel,viewManagerModel,clickingViewModel,clickingController);
        this.browsePresenter = browsePresenter;

        final BrowseInputBoundary browseInputBoundary = new BrowseInteractor(browseDataAccess,browsePresenter);
        BrowseController browseController = new BrowseController(browseInputBoundary);
        browseView.setBrowseController(browseController);
        browseView.setBrowsePresenter(browsePresenter);
        return this;
    }

    public AppBuilder addSignupUseCase(){
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary signupInputBoundary = new SignupInteractor(userDataAccessObject,
                signupOutputBoundary);

        SignupController signupController = new SignupController(signupInputBoundary);
        signupView.setSignupController(signupController);
        return this;
    }

    public AppBuilder addLoginUseCase(){
        final LoginPresenter loginPresenter = new LoginPresenter(viewManagerModel,
                homeViewModel, loginViewModel);
        loginPresenter.setWatchlistView(watchlistView);
        loginPresenter.setBrowseView(browseView);
        loginPresenter.setBrowsePresenter(browsePresenter);

        final LoginInputBoundary loginInputBoundary = new LoginInteractor(userDataAccessObject,
                loginPresenter);  // ← Use loginPresenter, not loginOutputBoundary

        LoginController loginController = new LoginController(loginInputBoundary);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addCommentUseCase(){
        final CommentOutputBoundary commentOutputBoundary = new CommentPresenter(commentViewModel,
                randCSuccessViewModel, viewManagerModel);
        final CommentInputBoundary commentInputBoundary = new CommentInteractor(userDataAccessObject,
                commentOutputBoundary);

        CommentController commentController = new CommentController(commentInputBoundary);
        rateAndCommentView.setCommentController(commentController);
        return this;
    }

    public AppBuilder addLoadWatchListUseCase() {
        // Movie data access (for fetching movie details from API)
        final LoadWatchListDataAccessInterface loadWatchListDataAccess =
                new WatchlistMovieDataAccess("Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNzJmZGIzYmQ2OWNmNmFmZDRhYmI5NzZiNTdjMWIxYSIsIm5iZiI6MTc2MTkxODY4MC4xMzMsInN1YiI6IjY5MDRiZWQ4MzU3M2VmMTQ4MDQ2MzY5MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.GQkgkyQZ6-GvLMOJqIOu0jfwYXjuHjrdNDBBbuzswsM");

        final AddToWatchListDataAccessInterface userDataAccess = userDataAccessObject;

        final LoadWatchListOutputBoundaryData loadWatchListPresenter =
                new LoadWatchListPresenter(loadWatchListViewModel);

        final LoadWatchListInputBoundaryData loadWatchListInteractor =
                new LoadWatchListInteractor(userDataAccess, loadWatchListDataAccess, loadWatchListPresenter);

        final LoadWatchListController loadWatchListController =
                new LoadWatchListController(loadWatchListInteractor);

        this.loadWatchListController = new LoadWatchListController(loadWatchListInteractor);


        watchlistView.setLoadController(loadWatchListController);
        System.out.println("LoadWatchListController set on watchlistView");

        return this;
    }

    public AppBuilder addWatchListUseCase() {
        // Add to watchlist
        final AddToWatchListDataAccessInterface addToWatchListDataAccess = userDataAccessObject;
        final AddToWatchListOutputBoundary addToWatchListPresenter =
                new AddToWatchListPresenter(addToWatchListViewModel);
        final AddToWatchListInputBoundaryData addToWatchListInteractor =
                new AddToWatchListInteractor(addToWatchListDataAccess, addToWatchListPresenter);

        final DeleteFromWatchListDataAccessInterface deleteFromWatchListDataAccess = userDataAccessObject;
        final DeleteFromWatchListOutputBoundary deleteFromWatchListPresenter =
                new DeleteFromWatchListPresenter(deleteFromWatchListViewModel);
        final DeleteFromWatchListInputBoundaryData deleteFromWatchListInteractor =
                new DeleteFromWatchListInteractor(deleteFromWatchListDataAccess, deleteFromWatchListPresenter);

        // Create single controller with BOTH interactors
        final WatchListController watchListController =
                new WatchListController(addToWatchListInteractor, deleteFromWatchListInteractor);

        // Set controller on watchlist view (for removing movies)
        watchlistView.setController(watchListController);

        browsePresenter.setWatchListController(watchListController);
        return this;
    }



    public JFrame build() {
        final JFrame application = new JFrame("Movie App");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        application.setSize(1200, 1000);
        application.setLocationRelativeTo(null);


        SwingUtilities.invokeLater(() -> {
            if (clickingView != null) {
                viewManagerModel.setState(loginView.getViewName());
                viewManagerModel.firePropertyChange();
            }
        });

        application.setVisible(true);
        return application;
    }
}