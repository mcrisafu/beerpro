package ch.beerpro.presentation.profile.myfridge;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;

import java.text.DateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.GlideApp;
import ch.beerpro.R;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.FridgeEntry;
import ch.beerpro.presentation.utils.EntityPairDiffItemCallback;


public class FridgeRecyclerViewAdapter extends ListAdapter<Pair<FridgeEntry, Beer>, FridgeRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "FridgeRecyclerViewAda";

    private static final DiffUtil.ItemCallback<Pair<FridgeEntry, Beer>> DIFF_CALLBACK = new EntityPairDiffItemCallback<>();

    private final OnFridgeItemInteractionListener listener;

    public FridgeRecyclerViewAdapter(OnFridgeItemInteractionListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_my_fridge_listentry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Pair<FridgeEntry, Beer> item = getItem(position);
        holder.bind(item.first, item.second, listener);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.manufacturer)
        TextView manufacturer;

        @BindView(R.id.category)
        TextView category;

        @BindView(R.id.photo)
        ImageView photo;

        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        @BindView(R.id.numRatings)
        TextView numRatings;

        @BindView(R.id.addedAt)
        TextView addedAt;

        @BindView(R.id.removeFromWishlist)
        Button remove;

        @BindView(R.id.amountInFridge)
        TextView amountInFridge;

        @BindView(R.id.increaseFridgePlusOne)
        Button increaseFridgePlusOne;

        @BindView(R.id.decreaseFridgeMinusOne)
        Button decreaseFridgeMinusOne;

        @BindView(R.id.increaseFridgePlusSix)
        Button increaseFridgePlusSix;

        @BindView(R.id.decreaseFridgeMinusSix)
        Button decreaseFridgeMinusSix;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        void bind(FridgeEntry fridgeEntry, Beer item, OnFridgeItemInteractionListener listener) {
            Log.v(TAG, "Tests");
            name.setText(item.getName());
            manufacturer.setText(item.getManufacturer());
            category.setText(item.getCategory());
            name.setText(item.getName());
            GlideApp.with(itemView).load(item.getPhoto()).apply(new RequestOptions().override(240, 240).centerInside())
                    .into(photo);
            ratingBar.setNumStars(5);
            ratingBar.setRating(item.getAvgRating());
            numRatings.setText(itemView.getResources().getString(R.string.fmt_num_ratings, item.getNumRatings()));
            itemView.setOnClickListener(v -> listener.onMoreClickedListener(photo, item));

            String formattedDate =
                    DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(fridgeEntry.getAddedAt());
            addedAt.setText(formattedDate);
            remove.setOnClickListener(v -> listener.onFridgeEntryClickedListener(item));
            Log.v(TAG, String.valueOf(fridgeEntry.getAmount()));
            Log.v(TAG, fridgeEntry.toString());
            amountInFridge.setText(String.valueOf(fridgeEntry.getAmount()));
            increaseFridgePlusOne.setOnClickListener(v -> listener.onChangeItemByAmountClickedListener(item, 1));
            decreaseFridgeMinusOne.setOnClickListener(v -> listener.onChangeItemByAmountClickedListener(item, -1));
            increaseFridgePlusSix.setOnClickListener(v -> listener.onChangeItemByAmountClickedListener(item, 6));
            decreaseFridgeMinusSix.setOnClickListener(v -> listener.onChangeItemByAmountClickedListener(item, -6));
        }

    }
}
