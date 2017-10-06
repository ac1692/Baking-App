package application.maaclab.ac.bakingapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import application.maaclab.ac.bakingapp.R;
import application.maaclab.ac.bakingapp.activity.RecipesActivity;

import static application.maaclab.ac.bakingapp.activity.MainActivity.recipesPojo;

/**
 * Created by Wipro on 18-09-2017.
 */

public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.ItemHolder> {

    private Context context;
    private Activity activity;
    public BakingAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baking_item, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.text.setText(recipesPojo.get(position).getName());
        if(!recipesPojo.get(position).getImage().equals(""))
            Picasso.with(context).load(recipesPojo.get(position).getImage()).into(holder.img);
        else
            holder.img.setImageResource(R.drawable.recipe_image);
    }

    @Override
    public int getItemCount() {
        return recipesPojo.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView img;
        private TextView text;
        public ItemHolder(View view){
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
            text = (TextView) view.findViewById(R.id.text);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(activity, RecipesActivity.class);
            intent.putExtra("position", getAdapterPosition());
            activity.startActivity(intent);

        }
    }
}
