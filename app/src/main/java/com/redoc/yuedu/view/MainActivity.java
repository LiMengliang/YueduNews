package com.redoc.yuedu.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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
import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.model.Category;
import com.redoc.yuedu.presenter.CategoriesManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private CategoriesManager categoriesManager;
    private LinearLayout categorySelectorBar;
    private LayoutInflater layoutInflater;
    private Map<View, Category> categoryButtonAndName = new HashMap<>();
    private Fragment currentActiveFragment;
    private Category currentSelectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        categorySelectorBar = (LinearLayout)findViewById(R.id.categorySelectorBar);
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        categoriesManager = YueduApplication.getCategoriesManager();
        initializeCategories();
        addFirstCategoryFragment(categoriesManager.getCategories().get(0));
    }

    private void initializeCategories() {
        int index = 0;
        for(Category category : categoriesManager.getCategories()) {
            // TODO: I should create a CategoryButton instead of operating on resource file directly.
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
        currentSelectedCategory = category;
    }

    private void addFirstCategoryFragment(Category category) {
        Fragment fragment = categoriesManager.getOrCreateFragment(category);
        getSupportFragmentManager().beginTransaction().add(R.id.contentView, fragment).commit();
        currentActiveFragment = fragment;
        currentSelectedCategory = category;
        View categoryButton = null;
        for(Map.Entry entry : categoryButtonAndName.entrySet()) {
            if(entry.getValue() == category) {
                categoryButton = (View)entry.getKey();
                break;
            }
        }
        if(categoryButton != null) {
            LinearLayout cateogryButtonView = (LinearLayout)(categoryButton.findViewById(R.id.category_button));
            LinearLayout refreshButtonView = (LinearLayout)(categoryButton.findViewById(R.id.refresh_button));
            cateogryButtonView.setVisibility(View.GONE);
            refreshButtonView.setVisibility(View.VISIBLE);
        }
    }

    class CategoryButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Category category = categoryButtonAndName.get(v);
            Fragment fragment = categoriesManager.getOrCreateFragment(category);
            if(category == currentSelectedCategory) {
                if(fragment instanceof RefreshableCategory) {
                    ((RefreshableCategory)fragment).refresh();
                }
            }
            else {
                // switch to category
                switchCategoryFragments(categoryButtonAndName.get(v));
                for (View button : categoryButtonAndName.keySet()) {
                    LinearLayout cateogryButtonView = (LinearLayout) button.findViewById(R.id.category_button);
                    LinearLayout refreshButtonView = (LinearLayout) button.findViewById(R.id.refresh_button);
                    if (button == v) {
                        if(fragment instanceof RefreshableCategory) {
                            cateogryButtonView.setVisibility(View.GONE);
                            refreshButtonView.setVisibility(View.VISIBLE);
                        }
                        else {
                            cateogryButtonView.setVisibility(View.VISIBLE);
                            refreshButtonView.setVisibility(View.GONE);
                        }
                    }
                    else {
                        cateogryButtonView.setVisibility(View.VISIBLE);
                        refreshButtonView.setVisibility(View.GONE);
                    }
                }
            }
        }
    }
}
