package ch.beerpro.presentation.profile.mybeers;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.beerpro.data.repositories.BeersRepository;
import ch.beerpro.data.repositories.CurrentUser;
import ch.beerpro.data.repositories.MyBeersRepository;
import ch.beerpro.data.repositories.RatingsRepository;
import ch.beerpro.data.repositories.WishlistRepository;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.MyBeer;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.domain.models.Wish;

import static androidx.lifecycle.Transformations.map;
import static ch.beerpro.domain.utils.LiveDataExtensions.zip;

public class MyBeersViewModel extends ViewModel implements CurrentUser {

    private static final String TAG = "MyBeersViewModel";
    private final MutableLiveData<String> searchTerm = new MutableLiveData<>();

    private final WishlistRepository wishlistRepository;
    private final LiveData<List<MyBeer>> myFilteredBeers;

    public MyBeersViewModel() {

        wishlistRepository = new WishlistRepository();
        BeersRepository beersRepository = new BeersRepository();
        MyBeersRepository myBeersRepository = new MyBeersRepository();
        RatingsRepository ratingsRepository = new RatingsRepository();

        LiveData<List<Beer>> allBeers = beersRepository.getAllBeers();
        MutableLiveData<String> currentUserId = new MutableLiveData<>();
        LiveData<List<Wish>> myWishlist = wishlistRepository.getMyWishlist(currentUserId);
        LiveData<List<Rating>> myRatings = ratingsRepository.getMyRatings(currentUserId);

        LiveData<List<MyBeer>> myBeers = myBeersRepository.getMyBeers(allBeers, myWishlist, myRatings);

        myFilteredBeers = map(zip(searchTerm, myBeers), MyBeersViewModel::filter);

        currentUserId.setValue(getCurrentUser().getUid());
    }

    private static List<MyBeer> filter(Pair<String, List<MyBeer>> input) {
        String searchTerm1 = input.first;
        List<MyBeer> allBeers = input.second;
        if (Strings.isNullOrEmpty(searchTerm1)) {
            return allBeers;
        }
        if (allBeers == null) {
            return Collections.emptyList();
        }
        ArrayList<MyBeer> filtered = new ArrayList<>();
        ArrayList<MyBeer> filteredByCategory = new ArrayList<>();
        ArrayList<MyBeer> filteredByBrewery = new ArrayList<>();
        for (MyBeer beer : allBeers) {
            if (beer.getBeer().getCategory().toLowerCase().equals(searchTerm1.toLowerCase())) {
                filteredByCategory.add(beer);
            }
            if (beer.getBeer().getManufacturer().toLowerCase().equals((searchTerm1.toLowerCase()))) {
                filteredByBrewery.add(beer);
            }
            if (beer.getBeer().getName().toLowerCase().contains(searchTerm1.toLowerCase())) {
                filtered.add(beer);
            }
        }
        if (filteredByCategory.size() > 0) {
            return filteredByCategory;
        }
        if (filteredByBrewery.size() > 0) {
            return filteredByBrewery;
        }
        return filtered;
    }


    public LiveData<List<MyBeer>> getMyFilteredBeers() {
        return myFilteredBeers;
    }

    public void toggleItemInWishlist(String beerId) {
        wishlistRepository.toggleUserWishlistItem(getCurrentUser().getUid(), beerId);
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm.setValue(searchTerm);
    }
}