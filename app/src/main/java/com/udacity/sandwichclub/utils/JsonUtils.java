package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonSandwich = new JSONObject(json);
            JSONObject nameJsonSandwich = jsonSandwich.getJSONObject("name");
            String mainName = nameJsonSandwich.getString("mainName");

            ArrayList<String> alsoKnownAsList = new ArrayList<String>();
            JSONArray alsoKnownAsJsonSandwichArray = nameJsonSandwich.getJSONArray("alsoKnownAs");

            String alsoKnownAs = null;
            for (int i = 0; i < alsoKnownAsJsonSandwichArray.length(); i++)
            {
                alsoKnownAs = alsoKnownAsJsonSandwichArray.getString(i);
                alsoKnownAsList.add(alsoKnownAs);
            }

            String placeOfOrigin = jsonSandwich.getString("placeOfOrigin");

            String description = jsonSandwich.getString("description");

            String image = jsonSandwich.getString("image");

            ArrayList<String> ingredients = new ArrayList<String>();
            JSONArray ingredientsJsonSandwichArray = jsonSandwich.getJSONArray("ingredients");
            String ingredient;
            for (int i = 0; i < ingredientsJsonSandwichArray.length(); i++)
            {
                ingredient = ingredientsJsonSandwichArray.getString(i);
                ingredients.add(ingredient);
            }

            Sandwich sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredients);
            return sandwich;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "exception occurred", e);
        }
        return null;
    }
}
