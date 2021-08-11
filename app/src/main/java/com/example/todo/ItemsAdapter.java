package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//show data from model in a row in recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnLongClickListener {
        void onItemLongClicked(int position); //main activity needs to know where to delete
    }

    List<String> items;
    OnLongClickListener longClickListener;

    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    //creates each view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //use layout inflater, take context from parent, which is passed as a parameter, then inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        //put view in ViewHolder + return
        return new ViewHolder(todoView);
    }

    @Override
    //bind data from position to the given ViewHolder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //grab item from position
        String item = items.get(position);

        //bind to ViewHolder
        holder.bind(item);
    }

    @Override
    //# of items in list
    public int getItemCount() {
        return items.size();
    }

    //container: access to views representing each row in list
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        //update view inside the view holder w this data
        //the view holder we are using is simple list item 1
        //it has a text id of: text1, which we can use to refer to the text view;
        //use text1 to initialize the ViewHolder
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //tell listener which position is clicked
                    longClickListener.onItemLongClicked(getBindingAdapterPosition());
                    return true;
                }
            });
        }
    }

}
