package application.maaclab.ac.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.util.Locale;

import application.maaclab.ac.bakingapp.R;
import application.maaclab.ac.bakingapp.activity.MainActivity;
import application.maaclab.ac.bakingapp.activity.RecipesActivity;
import application.maaclab.ac.bakingapp.helper.WidgetCallback;
import application.maaclab.ac.bakingapp.model.Ingredient;

import static android.content.Context.MODE_PRIVATE;
import static application.maaclab.ac.bakingapp.activity.MainActivity.recipesPojo;


public class BakingAppWidget extends AppWidgetProvider {

    static Context mContext;
    static int position = -1;
    static AppWidgetManager appWidgetm;
    static int id;
    public static BakingAppWidget bakingAppWidget = new BakingAppWidget();

//    public BakingAppWidget(WidgetCallbac)

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared", MODE_PRIVATE);
        position = sharedPreferences.getInt("last_opened_recipe", 0);
        appWidgetm = appWidgetManager;
        id = appWidgetId;

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public void callWidget(int pos) {
        if(position == -1) {}
        else {
            position = pos;
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.putExtra("position", position);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_app_widget);
            views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);
            views.setTextViewText(R.id.recipe_name, recipesPojo.get(position).getName());
            StringBuilder sb = new StringBuilder();
            for (Ingredient ingredient : recipesPojo.get(position).getIngredients()) {
                String name = ingredient.getIngredient();
                double quantity = ingredient.getQuantity();
                String measure = ingredient.getMeasure();
                sb.append("\n");
                String line = mContext.getResources().getString(R.string.recipe_details_ingredient_item);
                String quantityStr = String.format(Locale.US, "%s", quantity);
                if (quantity == (long) quantity) {
                    quantityStr = String.format(Locale.US, "%d", (long) quantity);
                }
                sb.append(String.format(Locale.getDefault(), line, name, quantityStr, measure));
            }
            views.setTextViewText(R.id.recipe_details_ingredients, sb.toString());
            appWidgetm.updateAppWidget(id, views);
        }
    }
}

