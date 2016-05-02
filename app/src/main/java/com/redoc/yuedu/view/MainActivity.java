package com.redoc.yuedu.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

// import com.android.volley.RequestQueue;
// import com.android.volley.toolbox.Volley;
// import com.redoc.yuedu.bean.BaseCategoryModel;
// import com.redoc.yuedu.controller.CategoriesManager;
// import com.redoc.yuedu.news.view.NewsFragment;
// import com.redoc.yuedu.R;
// import com.redoc.yuedu.news.view.channels.BitmapCache;

import com.redoc.yuedu.R;
import com.redoc.yuedu.bean.Category;
import com.redoc.yuedu.controller.CategoriesManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private CategoriesManager categoriesManager = new CategoriesManager(this);
    private LinearLayout categorySelectorBar;
    private LayoutInflater layoutInflater;
    private Map<View, Category> categoryButtonAndName = new HashMap<View, Category>();
    private Fragment currentActiveFragment;
//
    // public static RequestQueue VolleyRequestQueue;
    // public static BitmapCache BitmapCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categorySelectorBar = (LinearLayout)findViewById(R.id.categorySelectorBar);
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        initializeCategories();
        addFirstCategoryFragment(categoriesManager.getCategories().get(0));
        // VolleyRequestQueue = Volley.newRequestQueue(this);
        // BitmapCache = new BitmapCache();
    }

    private void initializeCategories() {
        int index = 0;
        for(Category category : categoriesManager.getCategories()) {
            View categoryButton = layoutInflater.inflate(R.layout.widget_category_button, null);
            TextView categoryNameTextView = (TextView) categoryButton.findViewById(R.id.category_name);
            categoryNameTextView.setText(category.getCategoryName());
            ImageView categoryIcon = (ImageView) categoryButton.findViewById(R.id.category_icon);
            categoryIcon.setImageResource(category.getCategoryIconResourceId());
            categoryButton.setOnClickListener(new CategoryButtonClickListener());
            categoryButtonAndName.put(categoryButton, category);
            LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParameters.weight = 1;
            categoryButton.setLayoutParams(layoutParameters);
            categorySelectorBar.addView(categoryButton, index++);
        }
    }

    private void switchCategoryFragments(Category category) {
        Fragment fragment = categoriesManager.getOrCreateFragment(category);
        if(fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().hide(currentActiveFragment).show(fragment).commit();
        }
        else {
         getSupportFragmentManager().beginTransaction().hide(currentActiveFragment).add(R.id.contentView, fragment).commit();
        }
        currentActiveFragment = fragment;
    }

    private void addFirstCategoryFragment(Category category) {
        Fragment fragment = categoriesManager.getOrCreateFragment(category);
        getSupportFragmentManager().beginTransaction().add(R.id.contentView, fragment).commit();
        currentActiveFragment = fragment;
    }

    class CategoryButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switchCategoryFragments(categoryButtonAndName.get(v));
        }
    }
}
