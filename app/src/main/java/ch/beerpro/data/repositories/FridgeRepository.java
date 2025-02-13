package ch.beerpro.data.repositories;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Entity;
import ch.beerpro.domain.models.FridgeEntry;
import ch.beerpro.domain.utils.FirestoreQueryLiveData;
import ch.beerpro.domain.utils.FirestoreQueryLiveDataArray;
import ch.beerpro.presentation.utils.EntityClassSnapshotParser;

import static androidx.lifecycle.Transformations.map;
import static androidx.lifecycle.Transformations.switchMap;
import static ch.beerpro.domain.utils.LiveDataExtensions.combineLatest;

public class FridgeRepository {

    private final static EntityClassSnapshotParser<FridgeEntry> parser = new EntityClassSnapshotParser<>(FridgeEntry.class);

    private static LiveData<List<FridgeEntry>> getFridgeEntryByUser(String userId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(FridgeEntry.COLLECTION).orderBy(FridgeEntry.FIELD_ADDED_AT, Query.Direction.DESCENDING).whereEqualTo(FridgeEntry.FIELD_USER_ID, userId), FridgeEntry.class);
    }

    private static LiveData<FridgeEntry> getUserFridgeFor(Pair<String, Beer> input) {
        String userId = input.first;
        Beer beer = input.second;
        DocumentReference document = FirebaseFirestore.getInstance().collection(FridgeEntry.COLLECTION).document(FridgeEntry.generateId(userId, beer.getId()));
        return new FirestoreQueryLiveData<>(document, FridgeEntry.class);
    }

    public Task<Void> toggleUserFridgeItem(String userId, String itemId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String fridgeEntryId = FridgeEntry.generateId(userId, itemId);

        DocumentReference fridgeEntryQuery = db.collection(FridgeEntry.COLLECTION).document(fridgeEntryId);

        return fridgeEntryQuery.get().continueWithTask(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                return fridgeEntryQuery.delete();
            } else if (task.isSuccessful()) {
                return fridgeEntryQuery.set(new FridgeEntry(userId, itemId, new Date(), 1));
            } else {
                throw task.getException();
            }
        });
    }

    public Task<Void> changeAmountFridgeItem(String userId, String itemId, int changeAmount) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String fridgeEntryId = FridgeEntry.generateId(userId, itemId);
        final DocumentReference fridgeEntryQuery = db.collection(FridgeEntry.COLLECTION).document(fridgeEntryId);

        db.runTransaction((Transaction.Function<Void>) transaction -> {
            FridgeEntry fridgeEntry = parser.parseSnapshot(transaction.get(fridgeEntryQuery));
            int amount = fridgeEntry.getAmount();
            transaction.update(fridgeEntryQuery, FridgeEntry.FIELD_AMOUNT, amount + changeAmount);
            return null;
        });
        return null;
    }



    public LiveData<List<Pair<FridgeEntry, Beer>>> getMyFridgeWithBeers(LiveData<String> currentUserId,
                                                                   LiveData<List<Beer>> allBeers) {
        return map(combineLatest(getMyFridge(currentUserId), map(allBeers, Entity::entitiesById)), input -> {
            List<FridgeEntry> fridgeEntries = input.first;
            HashMap<String, Beer> beersById = input.second;

            ArrayList<Pair<FridgeEntry, Beer>> result = new ArrayList<>();
            for (FridgeEntry fridgeEntry : fridgeEntries) {
                Beer beer = beersById.get(fridgeEntry.getBeerId());
                result.add(Pair.create(fridgeEntry, beer));
            }
            return result;
        });
    }

    public LiveData<List<FridgeEntry>> getMyFridge(LiveData<String> currentUserId) {
        return switchMap(currentUserId, FridgeRepository::getFridgeEntryByUser);
    }


    public LiveData<FridgeEntry> getMyFridgeEntryForBeer(LiveData<String> currentUserId, LiveData<Beer> beer) {
        return switchMap(combineLatest(currentUserId, beer), FridgeRepository::getUserFridgeFor);
    }

}
