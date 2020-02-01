package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnownAsLabel;
    private TextView placeOfOriginLabel;

    private TextView alsoKnownAsTextView;
    private TextView placeOfOriginTextView;
    private TextView ingredientsTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        alsoKnownAsLabel = (TextView) findViewById(R.id.textView2);
        placeOfOriginLabel = (TextView) findViewById(R.id.textView);

        alsoKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);
        placeOfOriginTextView = (TextView) findViewById(R.id.origin_tv);
        ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        descriptionTextView = (TextView) findViewById(R.id.description_tv);

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

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /* clear array texts
       because we don't want to keep any
       previously clicked sandwich data */
    private void clearViews()
    {
        alsoKnownAsTextView.setText("");
        ingredientsTextView.setText("");
    }

    /* set visibility of text
       depending on whether the corresponding
       TextView is empty or not
     */
    private void setVisibility(Sandwich sw)
    {
        if (sw.getAlsoKnownAs().isEmpty())
        {
            alsoKnownAsLabel.setVisibility(View.GONE);
            alsoKnownAsTextView.setVisibility(View.GONE);
        }
        else
        {
            alsoKnownAsLabel.setVisibility(View.VISIBLE);
            alsoKnownAsTextView.setVisibility(View.VISIBLE);
        }

        if (sw.getPlaceOfOrigin().equals(""))
        {
            placeOfOriginLabel.setVisibility(View.GONE);
            placeOfOriginTextView.setVisibility(View.GONE);
        }
        else
        {
            placeOfOriginLabel.setVisibility(View.VISIBLE);
            placeOfOriginTextView.setVisibility(View.VISIBLE);
        }
    }

    private void populateUI(Sandwich sandwich) {

        clearViews();

        setVisibility(sandwich);

        for (String alsoKnownAs : sandwich.getAlsoKnownAs())
        {
            alsoKnownAsTextView.append(alsoKnownAs + "\n");
        }

        placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());

        for (String ingredient: sandwich.getIngredients())
        {
            ingredientsTextView.append(ingredient + "\n");
        }

        descriptionTextView.setText(sandwich.getDescription());
    }
}
