package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mSandwichDescription;
    private TextView mSandwichOrigin;
    private TextView mSandwichIngredients;
    private TextView mSandwichAlsoKnownAs;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mSandwichDescription = findViewById(R.id.description_tv);
        mSandwichOrigin = findViewById(R.id.origin_tv);
        mSandwichIngredients = findViewById(R.id.ingredients_tv);
        mSandwichAlsoKnownAs = findViewById(R.id.also_known_tv);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
        mSandwichOrigin.setText(sandwich.getPlaceOfOrigin());
        mSandwichAlsoKnownAs.setText(sandwich.getAlsoKnownAs().toString().replaceAll("\\[", "").replaceAll("\\]",""));
        mSandwichIngredients.setText(sandwich.getIngredients().toString().replaceAll("\\[", "").replaceAll("\\]",""));
        mSandwichDescription.setText(sandwich.getDescription());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
