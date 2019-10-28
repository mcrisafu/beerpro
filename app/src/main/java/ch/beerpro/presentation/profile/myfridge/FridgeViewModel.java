package ch.beerpro.presentation.profile.myfridge;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;

import java.util.List;

import ch.beerpro.data.repositories.BeersRepository;
import ch.beerpro.data.repositories.CurrentUser;
import ch.beerpro.data.repositories.FridgeRepository;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.FridgeEntry;

public class FridgeViewModel extends ViewModel implements CurrentUser {

    private static final String TAG = "FridgeViewModel";

    private final MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private final FridgeRepository fridgeRepository;
    private final BeersRepository beersRepository;
    private final LiveData<List<FridgeEntry>> myFridge;


    public FridgeViewModel() {
        fridgeRepository = new FridgeRepository();
        beersRepository = new BeersRepository();
        myFridge = fridgeRepository.getMyFridge(currentUserId);

        currentUserId.setValue(getCurrentUser().getUid());
    }

    public LiveData<List<Pair<FridgeEntry, Beer>>> getMyFridgeWithBeers() {
        return fridgeRepository.getMyFridgeWithBeers(currentUserId, beersRepository.getAllBeers());
    }

    public Task<Void> toggleItemInFridge(String itemId) {
        return fridgeRepository.toggleUserFridgeItem(getCurrentUser().getUid(), itemId);
    }

    public Task<Void> increaseItemAmountInFridge(String itemId, int byAmount){
        return fridgeRepository.changeAmountFridgeItem(getCurrentUser().getUid(), itemId, byAmount);
    }


}