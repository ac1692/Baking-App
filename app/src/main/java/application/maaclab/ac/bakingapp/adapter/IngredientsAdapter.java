package application.maaclab.ac.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import application.maaclab.ac.bakingapp.R;

import static application.maaclab.ac.bakingapp.activity.MainActivity.recipesPojo;

/**
 * Created by Wipro on 19-09-2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ItemHolder> {

    private Context context;
    private int positionRecipe;
    public IngredientsAdapter(Context context, int positionRecipe) {
        this.context = context;
        this.positionRecipe = positionRecipe;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_item, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

        holder.ingredients.setText("Ingredients: " + recipesPojo.get(positionRecipe).getIngredients().get(position).getIngredient());
        holder.measure.setText("Measue: " + recipesPojo.get(positionRecipe).getIngredients().get(position).getMeasure());
        holder.quantity.setText("Quantity: " + recipesPojo.get(positionRecipe).getIngredients().get(position).getQuantity());

    }

    @Override
    public int getItemCount() {
        return recipesPojo.get(positionRecipe).getIngredients().size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView ingredients, quantity, measure;
        public ItemHolder(View view){
            super(view);
            ingredients = (TextView) view.findViewById(R.id.ingredient);
            measure = (TextView) view.findViewById(R.id.measure);
            quantity = (TextView) view.findViewById(R.id.quantity);
        }
    }
}
