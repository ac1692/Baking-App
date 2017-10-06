package application.maaclab.ac.bakingapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import application.maaclab.ac.bakingapp.R;
import application.maaclab.ac.bakingapp.fragment.VideoFragment;

import static application.maaclab.ac.bakingapp.activity.MainActivity.recipesPojo;

/**
 * Created by Wipro on 19-09-2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ItemHolder> {

    private Context context;
    private int positionRecipe;
    private Activity activity;
    public StepsAdapter(Context context, int positionRecipe, Activity activity) {
        this.context = context;
        this.positionRecipe = positionRecipe;
        this.activity = activity;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_item, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

        holder.shortDesc.setText(recipesPojo.get(positionRecipe).getSteps().get(position).getShortDescription());
        holder.fullDesc.setText(recipesPojo.get(positionRecipe).getSteps().get(position).getDescription());
        if(!recipesPojo.get(positionRecipe).getSteps().get(position).getThumbnailURL().equals("")) {
            Picasso.with(context).load(recipesPojo.get(positionRecipe).getSteps().get(position).getThumbnailURL()).into(holder.imageView);
        }else {
            holder.imageView.setImageResource(R.drawable.recipe_image);
        }
    }

    @Override
    public int getItemCount() {
        return recipesPojo.get(positionRecipe).getSteps().size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView shortDesc, fullDesc;
        ImageView imageView;
        public ItemHolder(View view){
            super(view);
            shortDesc = (TextView) view.findViewById(R.id.short_desc);
            fullDesc = (TextView) view.findViewById(R.id.full_desc);
            imageView = (ImageView) view.findViewById(R.id.img);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            VideoFragment videoFragment = new VideoFragment();
            Bundle  bundle = new Bundle();
            bundle.putString("link", recipesPojo.get(positionRecipe).getSteps().get(getAdapterPosition()).getVideoURL());
            bundle.putInt("position", positionRecipe);
            bundle.putInt("position_adapter", getAdapterPosition());
            videoFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.recipes_container, videoFragment, VideoFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }
}

