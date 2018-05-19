package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        String mainName = null;
        ArrayList<String> alsoKnownAsList = new ArrayList();
        String placeOfOrigin = null;
        String description = null;
        String imageUrl = null;
        ArrayList<String> ingredientsList = new ArrayList();
        Sandwich sandwich = null;
        try {
            JSONObject jsonRootObj = new JSONObject(json);
            JSONObject jsonNameObj = jsonRootObj.optJSONObject(SandwichUtilsConstants.NAME);
            mainName = jsonNameObj.optString(SandwichUtilsConstants.MAIN_NAME);
            JSONArray knownAsJsonArray = jsonNameObj.optJSONArray(SandwichUtilsConstants.KNOWN_AS);
            for (int i = 0; i < knownAsJsonArray.length(); i++)
                alsoKnownAsList.add(knownAsJsonArray.getString(i));
            placeOfOrigin = jsonRootObj.optString(SandwichUtilsConstants.ORIGIN_PLACE);
            description = jsonRootObj.optString(SandwichUtilsConstants.DESCRIPTION);
            imageUrl = jsonRootObj.optString(SandwichUtilsConstants.IMAGE_SRC);
            JSONArray ingredientsJsonArray = jsonRootObj.optJSONArray(SandwichUtilsConstants.INGREDIENTS);
            for (int i = 0; i < ingredientsJsonArray.length(); i++)
                ingredientsList.add(ingredientsJsonArray.optString(i));

            // Now that we have all the sandwich attributes parsed we are ready to craft a sandwich object and return it.
            sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, imageUrl, ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;

    }
}
