package ch.beerpro.domain.models;

import androidx.annotation.NonNull;

import java.util.Date;

public class MyBeerFromFridge implements MyBeer {
    private FridgeEntry fridgeEntry;
    private Beer beer;

    public MyBeerFromFridge(FridgeEntry fridgeEntry, Beer beer) {
        this.fridgeEntry = fridgeEntry;
        this.beer = beer;
    }

    @Override
    public String getBeerId() {
        return fridgeEntry.getBeerId();
    }

    @Override
    public Date getDate() {
        return fridgeEntry.getAddedAt();
    }

    public FridgeEntry getFridgeEntry() {
        return this.fridgeEntry;
    }

    public Beer getBeer() {
        return this.beer;
    }

    public void setFridgeEntry(FridgeEntry fridgeEntry) {
        this.fridgeEntry = fridgeEntry;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MyBeerFromFridge)) return false;
        final MyBeerFromFridge other = (MyBeerFromFridge) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$fridgeEntry = this.getFridgeEntry();
        final Object other$fridgeEntry = other.getFridgeEntry();
        if (this$fridgeEntry == null ? other$fridgeEntry != null : !this$fridgeEntry.equals(other$fridgeEntry)) return false;
        final Object this$beer = this.getBeer();
        final Object other$beer = other.getBeer();
        return this$beer == null ? other$beer == null : this$beer.equals(other$beer);
    }

    private boolean canEqual(final Object other) {
        return other instanceof MyBeerFromFridge;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $fridgeEntry = this.getFridgeEntry();
        result = result * PRIME + ($fridgeEntry == null ? 43 : $fridgeEntry.hashCode());
        final Object $beer = this.getBeer();
        result = result * PRIME + ($beer == null ? 43 : $beer.hashCode());
        return result;
    }

    @NonNull
    public String toString() {
        return "MyBeerFromFridge(fridgeEntry=" + this.getFridgeEntry() + ", beer=" + this.getBeer() + ")";
    }
}
