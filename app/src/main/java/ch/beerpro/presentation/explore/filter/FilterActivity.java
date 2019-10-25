package ch.beerpro.presentation.explore.filter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.common.base.Strings;

import ch.beerpro.R;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.presentation.details.DetailsActivity;
import ch.beerpro.presentation.explore.beers.ExploreResultFragment;
import ch.beerpro.presentation.explore.search.SearchViewModel;
import ch.beerpro.presentation.explore.search.ViewPagerAdapter;
import ch.beerpro.presentation.explore.search.suggestions.SearchSuggestionsFragment;
import ch.beerpro.presentation.profile.mybeers.MyBeersViewModel;
import ch.beerpro.presentation.profile.mybeers.OnMyBeerItemInteractionListener;

public class FilterActivity extends AppCompatActivity
        implements ExploreResultFragment.OnItemSelectedListener, SearchSuggestionsFragment.OnItemSelectedListener,
        OnMyBeerItemInteractionListener {

    private SearchViewModel searchViewModel;
    private ViewPagerAdapter adapter;
    private MyBeersViewModel myBeersViewModel;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        myBeersViewModel = ViewModelProviders.of(this).get(MyBeersViewModel.class);

        ViewPager viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setSaveFromParentEnabled(false);

        handleSearch("IPA");
    }

    private void handleSearch(String text) {
        searchViewModel.setSearchTerm(text);
        myBeersViewModel.setSearchTerm(text);
        adapter.setShowSuggestions(Strings.isNullOrEmpty(text));
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onSearchResultListItemSelected(View animationSource, Beer item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.ITEM_ID, item.getId());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, animationSource, "image");
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onMoreClickedListener(ImageView photo, Beer item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.ITEM_ID, item.getId());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, photo, "image");
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onWishClickedListener(Beer item) {
        searchViewModel.toggleItemInWishlist(item.getId());
    }

    @Override
    public void onSearchSuggestionListItemSelected(String text) {

    }
}
