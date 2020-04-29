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
import picodiploma.dicoding.mysubmissiontwo.SearchMovie.SearchMovie;
import picodiploma.dicoding.mysubmissiontwo.notif.settingNotif;

public class MainActivity extends AppCompatActivity {

    public static final String SEARCH_PARAM = "search";
    public static final String SEARCH_TYPE = "type_search";

    private movieHelper MovieHelper;
    public String selectedFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()){
                case R.id.navigation_movies:
                    selectedFragment = "homeMovies";
                    fragment = new homeMovies();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .disallowAddToBackStack()
                            .commit();
                    return true;
                case R.id.navigation_tvShow:
                    selectedFragment = "tvMovies";
                    fragment = new tvMoviesFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .disallowAddToBackStack()
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if (savedInstanceState == null){
            bottomNavigationView.setSelectedItemId(R.id.navigation_movies);
        }

        MovieHelper = movieHelper.getInstance(getApplicationContext());
        MovieHelper.open();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

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

                        if (selectedFragment == "homeMovies"){
                            bundle.putString(SEARCH_TYPE, "homeMovies");
                        } else if (selectedFragment == "tvMovies") {
                            bundle.putString(SEARCH_TYPE, "tvMovies");
                        }

                        Fragment fragment = new SearchMovie();
                        fragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                .disallowAddToBackStack()
                                .commit();
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
                        case "homeMovies":
                            fragment = new homeMovies();
                            getSupportFragmentManager().popBackStack();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                    .disallowAddToBackStack()
                                    .commit();

                            return true;
                        case "tvMovies":
                            fragment = new tvMoviesFragment();
                            getSupportFragmentManager().popBackStack();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                    .disallowAddToBackStack()
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
        } else if (item.getItemId() == R.id.favorite_item) {
            Intent intentToFavorite = new Intent(MainActivity.this, favorite_activity.class);
            startActivity(intentToFavorite);
        } else if (item.getItemId() == R.id.Reminder) {
            Intent intentToReminder = new Intent(MainActivity.this, settingNotif.class);
            startActivity(intentToReminder);
        }
        return super.onOptionsItemSelected(item);
    }
}
