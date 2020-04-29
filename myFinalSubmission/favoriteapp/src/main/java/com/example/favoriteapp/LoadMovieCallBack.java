package com.example.favoriteapp;

import java.util.ArrayList;

public interface LoadMovieCallBack {
    void preExecute();
    void postExecute(ArrayList<Movie> movieArrayList);
}
