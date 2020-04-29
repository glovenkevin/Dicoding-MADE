package picodiploma.dicoding.mysubmissiontwo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import picodiploma.dicoding.mysubmissiontwo.db.movieHelper;

import static picodiploma.dicoding.mysubmissiontwo.MainActivity.SEARCH_PARAM;

public class favorite_activity extends AppCompatActivity {

    private movieHelper MovieHelper;
    private String selectedFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;

            switch (menuItem.getItemId()){
                case R.id.navigation_tvShow:
                    selectedFragment = "tvMoviesFavorite";
                    fragment = new tvMoviesFavorite();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout_favorit, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_movies:
                    selectedFragment = "homeMoviesFavorite";
                    fragment = new homeMoviesFavorite();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout_favorit, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_activity);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_bar_favorit);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if (savedInstanceState == null){
            bottomNavigationView.setSelectedItemId(R.id.navigation_movies);
        }

        MovieHelper = movieHelper.getInstance(getApplicationContext());
        MovieHelper.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_favorite, menu);
        MenuItem item = menu.findItem(R.id.search_movies);
        SearchView searchView = (SearchView) item.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.nameMovie));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String param) {

                    if (!param.isEmpty()){
                        Bundle bundle = new Bundle();
                        bundle.putString(SEARCH_PARAM, param);

                        if (selectedFragment == "homeMoviesFavorite"){
                            Fragment fragment = new homeMoviesFavorite();
                            fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container_layout_favorit, fragment, fragment.getClass().getSimpleName())
                                    .commit();
                        } else if (selectedFragment == "tvMoviesFavorite") {
                            Fragment fragment = new tvMoviesFavorite();
                            fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container_layout_favorit, fragment, fragment.getClass().getSimpleName())
                                    .commit();
                        }
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });

            item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    Fragment fragment;
                    switch (selectedFragment){
                        case "homeMoviesFavorite":
                            fragment = new homeMoviesFavorite();
                            getSupportFragmentManager().popBackStack();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container_layout_favorit, fragment, fragment.getClass().getSimpleName())
                                    .commit();
                            return true;
                        case "tvMoviesFavorite":
                            fragment = new tvMoviesFavorite();
                            getSupportFragmentManager().popBackStack();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container_layout_favorit, fragment, fragment.getClass().getSimpleName())
                                    .commit();
                            return true;
                    }
                    return true;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_language) {
            Intent myIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(myIntent);
        } else if (item.getItemId() == R.id.home_item) {
            Intent intentToMain = new Intent(favorite_activity.this, MainActivity.class);
            startActivity(intentToMain);
        }
        return super.onOptionsItemSelected(item);
    }
}
