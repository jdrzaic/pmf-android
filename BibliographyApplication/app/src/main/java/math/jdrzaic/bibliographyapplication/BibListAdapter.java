package math.jdrzaic.bibliographyapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BibListAdapter extends
        RecyclerView.Adapter<BibListAdapter.ViewHolder> {

    public static String BIBADAPTER_TAG = "bibadapter_tag";

    private static ClickListener clickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public TextView nameTextView;
        public ImageButton deleteButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            nameTextView = (TextView) itemView.findViewById(R.id.list_name);
            deleteButton = (ImageButton) itemView.findViewById(R.id.delete_list);
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

    private List<BibList> mBibLists;

    private Context mContext;

    public BibListAdapter(Context context, List<BibList> bibLists) {
        mBibLists = bibLists;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public BibListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_biblist, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(BibListAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        BibList bibList = mBibLists.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(bibList.name);
        ImageButton deleteButton = viewHolder.deleteButton;
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BibListActivity activity = (BibListActivity )mContext;
                    BibList list = mBibLists.get(position);
                    activity.deleteList(list.id);
                    mBibLists = activity.loadLists();

                    activity.rvBibLists.removeViewAt(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mBibLists.size());
                    notifyDataSetChanged();
                } catch (Exception ex) {
                    Log.d(BIBADAPTER_TAG, ex.getMessage());
                }

            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mBibLists.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        BibListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}