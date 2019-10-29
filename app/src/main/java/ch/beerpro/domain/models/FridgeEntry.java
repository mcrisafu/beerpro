package ch.beerpro.domain.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Date;
import java.util.Objects;

@IgnoreExtraProperties
public class FridgeEntry implements Entity {

    public static final String COLLECTION = "fridgeEntries";
    public static final String FIELD_ID = "id";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_BEER_ID = "beerId";
    public static final String FIELD_ADDED_AT = "addedAt";
    public static final String FIELD_AMOUNT = "amount";

    /**
     * The id is formed by `$userId_$beerId` to make queries easier.
     */
    @Exclude
    private String id;
    private String userId;
    private String beerId;
    private Date addedAt;
    private int amount;

    public FridgeEntry(String userId, String beerId, Date addedAt, int amount) {
        this.userId = userId;
        this.beerId = beerId;
        this.addedAt = addedAt;
        this.amount = amount;
    }

    public FridgeEntry() {
    }

    public static String generateId(String userId, String beerId) {
        return String.format("%s_%s", userId, beerId);
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getBeerId() {
        return this.beerId;
    }

    public Date getAddedAt() {
        return this.addedAt;
    }

    public int getAmount(){
        return this.amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public void increaseAmount(int increaseBy){
        this.amount = this.amount + increaseBy;
    }

    public void decreaseAmount(int decreaseBy){
        if (this.amount == 0){
            return;
        }
        this.amount = this.amount + decreaseBy;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBeerId(String beerId) {
        this.beerId = beerId;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FridgeEntry that = (FridgeEntry) o;
        return amount == that.amount &&
                Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(beerId, that.beerId) &&
                Objects.equals(addedAt, that.addedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, beerId, addedAt, amount);
    }

    private boolean canEqual(final Object other) {
        return other instanceof FridgeEntry;
    }



    @NonNull
    public String toString() {
        return "FridgeEntry(id=" + this.getId() + ", userId=" + this.getUserId() + ", beerId=" + this.getBeerId() + ", addedAt=" + this.getAddedAt() + ", Amount=" + this.amount +")";
    }

}
