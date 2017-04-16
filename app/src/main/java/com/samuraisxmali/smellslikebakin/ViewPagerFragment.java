package com.samuraisxmali.smellslikebakin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerFragment extends Fragment {
    public static final String KEY_RECIPE_INDEX = "recipe_index";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        int index = getArguments().getInt(KEY_RECIPE_INDEX); // ასე ვიღებთ ფრაგმენტში MainActivity-დან ჩადებულ არგუმენტებს
        getActivity().setTitle(Recipes.names[index]); // actionbar-ზე სახელს ვცვლით რეცეპტის სახელით

        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);

        final IngredientsFragment ingredientsFragment = new IngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_RECIPE_INDEX, index);
        ingredientsFragment.setArguments(bundle);

        final DirectionsFragment directionsFragment = new DirectionsFragment();
        bundle = new Bundle();
        bundle.putInt(KEY_RECIPE_INDEX, index);
        directionsFragment.setArguments(bundle);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        // როცა ფრაგმენტის შიგნით სხვა ფრაგმენტს ვიყენებთ უნდა გამოვიყენოთ getChildFragmentManager და არა getFragmentManager
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                /*if (position == 0){
                    return ingredientsFragment;
                }
                else {
                    return directionsFragment; // სულ 2 აითემია და გვერდიგვერდ იქნებიან, პირველი ინგრედიენტები და მეორე დირექშენები
                }*/

                //ერთ ხაზზე
                return position == 0 ? ingredientsFragment : directionsFragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return position == 0 ? "Ingredients" : "Directions";
            }

            @Override
            public int getCount() {
                return 2; // ვაბრუნებთ იმდენ ფრაგმენტს რამდენიც გვინდა. ჩვენ გვინდა 2 - ingredients და directions.
            }
        });

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        getActivity().setTitle(getResources().getString(R.string.app_name)); // უკან დაბრუნებისას (onStop-ზე როცა იქნება ეს ფრაგმენტი) სახელს ისევ ვცვლით
    }
}







