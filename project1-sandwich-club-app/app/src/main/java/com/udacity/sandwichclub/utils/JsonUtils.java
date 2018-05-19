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
            JSONObject jsonNameObj = jsonRootObj.getJSONObject(SandwichUtilsConstants.NAME);
            mainName = jsonNameObj.getString(SandwichUtilsConstants.MAIN_NAME);
            JSONArray knownAsJsonArray = jsonNameObj.getJSONArray(SandwichUtilsConstants.KNOWN_AS);
            for (int i = 0;i<knownAsJsonArray.length();i++)
                alsoKnownAsList.add(knownAsJsonArray.getString(i));
            placeOfOrigin = jsonRootObj.getString(SandwichUtilsConstants.ORIGIN_PLACE);
            description = jsonRootObj.getString(SandwichUtilsConstants.DESCRIPTION);
            imageUrl = jsonRootObj.getString(SandwichUtilsConstants.IMAGE_SRC);
            JSONArray ingredientsJsonArray = jsonRootObj.getJSONArray(SandwichUtilsConstants.INGREDIENTS);
            for (int i = 0;i<ingredientsJsonArray.length();i++)
                ingredientsList.add(ingredientsJsonArray.getString(i));

            // Now that we have all the sandwich attributes parsed we are ready to craft a sandwich object and return it.
            sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, imageUrl, ingredientsList);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return sandwich;

    }
}
