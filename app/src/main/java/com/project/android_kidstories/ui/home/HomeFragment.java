package com.project.android_kidstories.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.project.android_kidstories.R;
import com.project.android_kidstories.Views.main.MainActivity;
import com.project.android_kidstories.ui.home.Adapters.SectionsPageAdapter;
import com.project.android_kidstories.ui.home.Fragments.CategoriesFragment;
import com.project.android_kidstories.ui.home.Fragments.NewStoriesFragment;
import com.project.android_kidstories.ui.home.Fragments.PopularStoriesFragment;

import static com.project.android_kidstories.Views.main.MainActivity.lastTabPosition;

public class HomeFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    private BottomNavigationView bottomNavigationView;
    private com.project.android_kidstories.ui.home.HomeViewModel homeViewModel;
    private SectionsPageAdapter adapter;


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        lastTabPosition = tabLayout.getSelectedTabPosition();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(com.project.android_kidstories.ui.home.HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        tabLayout = root.findViewById(R.id.home_frag_tablayout);
        viewPager = root.findViewById(R.id.home_frag_container);
        appBarLayout = root.findViewById(R.id.home_frag_appbar);

        adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(NewStoriesFragment.newInstance(), "New Stories");
        adapter.addFragment(PopularStoriesFragment.newInstance(), "Popular Stories");
        adapter.addFragment(CategoriesFragment.newInstance(), "Categories");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(lastTabPosition).select();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition()==0){
                    MainActivity.setCurrentFragment(MainActivity.FRAGMENT_NEW);
                }else if(tab.getPosition()==1){
                    MainActivity.setCurrentFragment(MainActivity.FRAGMENT_POPULAR);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBarLayout.removeView(tabLayout);
    }
}