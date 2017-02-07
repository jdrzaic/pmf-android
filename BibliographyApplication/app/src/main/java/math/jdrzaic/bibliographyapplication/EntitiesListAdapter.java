package math.jdrzaic.bibliographyapplication;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class EntitiesListAdapter extends
        RecyclerView.Adapter<EntitiesListAdapter.ViewHolder> {

    public static String ENTITIES_TAG = "entities_tag";
    public static String ENTITY_ID = "entity_id";

    private static ClickListener clickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public TextView titleTextView;
        public TextView authorsTextView;
        public ImageButton deleteButton;
        public ImageButton editButton;
        public ImageButton exportButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            titleTextView = (TextView) itemView.findViewById(R.id.entity_title);
            authorsTextView = (TextView) itemView.findViewById(R.id.entity_author);
            deleteButton = (ImageButton) itemView.findViewById(R.id.delete_entity);
            editButton = (ImageButton) itemView.findViewById(R.id.edit_entity);
            exportButton = (ImageButton) itemView.findViewById(R.id.export_entity);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public List<Entity> mEntites;

    private Context mContext;

    public EntitiesListAdapter(Context context, List<Entity> entities) {
        mEntites = entities;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public EntitiesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_entities, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(EntitiesListAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final Entity entity = mEntites.get(position);

        // Set item views based on your views and data model
        viewHolder.titleTextView.setText(entity.title);
        viewHolder.authorsTextView.setText(entity.getAuthors());
        ImageButton deleteButton = viewHolder.deleteButton;
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //try {
                    EntitiesListActivity activity = (EntitiesListActivity ) mContext;
                    activity.deleteEntity(entity.id);
                    mEntites = activity.loadEntities();
                    activity.rvEntitiesView.removeViewAt(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mEntites.size());
                    notifyDataSetChanged();
                //} catch (Exception ex) {
                //    Log.e(ENTITIES_TAG, ex.getLocalizedMessage());
                //}

            }
        });
        ImageButton editButton = viewHolder.editButton;
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditEntityActivity.class);
                intent.putExtra(ENTITY_ID, mEntites.get(position).id);
                mContext.startActivity(intent);
            }
        });
        ImageButton exportButton = viewHolder.exportButton;
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ExportEntityActivity.class);
                intent.putExtra(ENTITY_ID, mEntites.get(position).id);
                mContext.startActivity(intent);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mEntites.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        EntitiesListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}