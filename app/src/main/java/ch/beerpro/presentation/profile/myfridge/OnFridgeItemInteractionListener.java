package ch.beerpro.presentation.profile.myfridge;

import android.widget.ImageView;

import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.FridgeEntry;

public interface OnFridgeItemInteractionListener {

    void onMoreClickedListener(ImageView photo, Beer beer);

    void onFridgeEntryClickedListener(Beer beer);

    void onChangeItemByAmountClickedListener(Beer beer, int amount);
}
