package io.material4.gallery;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.material4.widget.MaterialCardView;

/** RecyclerView adapter for the catalogue pages. */
final class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.Holder> {

    private final Activity activity;
    private final List<Sections> sections = List.of(Sections.values());
    private final boolean comparison;

    CatalogAdapter(@NonNull Activity activity, boolean comparison) {
        this.activity = activity;
        this.comparison = comparison;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView card = new MaterialCardView(activity);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = Ui.dp(activity, 8);
        params.setMargins(Ui.dp(activity, 12), margin, Ui.dp(activity, 12), margin);
        card.setLayoutParams(params);
        return new Holder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Sections section = sections.get(position);
        holder.card.removeAllViews();
        holder.card.setTransitionName("m4_" + section.name());
        holder.card.setContentDescription(section.title + " section");
        SectionFactory.build(activity, section, holder.card, comparison);
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    @Override
    public long getItemId(int position) {
        return sections.get(position).ordinal();
    }

    @NonNull
    Sections sectionAt(int position) {
        return sections.get(position);
    }

    static final class Holder extends RecyclerView.ViewHolder {
        final MaterialCardView card;

        Holder(@NonNull MaterialCardView card) {
            super(card);
            this.card = card;
        }
    }
}
