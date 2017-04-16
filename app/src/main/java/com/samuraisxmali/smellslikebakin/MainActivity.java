package com.samuraisxmali.smellslikebakin;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements ListFragment.OnRecipeSelectedInterface, GridFragment.OnRecipeSelectedInterface {

    public static final String LIST_FRAGMENT = "list_fragment";
    public static final String VIEWPAGER_FRAGMENT = "viewpager_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        if (!isTablet){
            // აქ ვქმნითახალ ცვლადს savedFragment, რომელიც ეძებს კონკრეტულ ListFragment ფრაგმენტს აიდის მიხედვით და თუ ვერ იპოვა
            // აბრუნებს null-ს. თუ null-ს არ აბრუნებს მაშასადამე ცარიელი არ არის ფრაგმენტი და
            // უკვე დამატებული ყოფილა ავტომატურად, მაგალითად ეკრანის შემოტრიალების დროს. შესაბამისად თუ ცარიელია
            // მხოლოდ ამ შემთხვევაში ვქმნით ახალ ფრაგმენტს. ეს რომ არ შევამოწმოთ მივიღებთ რომ ეკრანის ყოველი შემოტრიალებისას შეიქმნება
            // ახალი ფრაგმენტი და ამავდროულად სისტემა ავტომატურად დაამატებს უკვე არსებულ ფრაგმენტს და მივიღებთ 1-ით მეტ ფრაგმენტს
            // ყოველ შემოტრიალებაზე. აქ აიდად ვიყენებთ ფლეისჰოლდერს (R.id.placeHolder) რადგან კოდიდან
            // ( fragmentTransaction.add(R.id.placeHolder, fragment); ) ვქმნით ახალ ფრაგმენტს.
            // XML ფაილიდან რომ ვქმნიდეთ, მაშინ მისი აიდი დაგვჭირდებოდა და არა ფლეისჰოლდერის.
            // როცა გვაქვს ფრაგმენტების ტეგები, შეგვიძლია findFragmentByTag გამოვიყენოთ აიდის მაგივრად
            ListFragment savedFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT);
            if (savedFragment == null) {
                ListFragment fragment = new ListFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.placeHolder, fragment, LIST_FRAGMENT); // მესამე არგუმენტი - კონკრეტული ფრაგმენტის ტეგი - აქ და replace-ში გვჭირდება იმისთვის, რომ placeholder-მა იცოდეს რომელი ფრაგმენტია სახეზე. წინააღმდეგ შემთხვევაში აპლიკაცია გაიქრაშება შემოტრიალებისას.
                fragmentTransaction.commit();
            }
        }
        else{
            GridFragment savedFragment = (GridFragment) getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT);
            if (savedFragment == null) {
                GridFragment fragment = new GridFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.placeHolder, fragment, LIST_FRAGMENT); // მესამე არგუმენტი - კონკრეტული ფრაგმენტის ტეგი - აქ და replace-ში გვჭირდება იმისთვის, რომ placeholder-მა იცოდეს რომელი ფრაგმენტია სახეზე. წინააღმდეგ შემთხვევაში აპლიკაცია გაიქრაშება შემოტრიალებისას.
                fragmentTransaction.commit();
            }
        }

    }

    @Override
    public void onListRecipeSelected(int index) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        // ფრაგმენტებში კონსტრუქტორს არ ვქმნით, მის მაგივრად ფრაგმენტს ბანდლით გადაეცემა არგუმენტები. თავად ბანდლში გასაღებად
        // გამოიყენება ფრაგმენტში განსაზღვრული გასაღები (ViewPagerFragment.KEY_RECIPE_INDEX).
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerFragment.KEY_RECIPE_INDEX, index);
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeHolder, fragment, VIEWPAGER_FRAGMENT);
        fragmentTransaction.addToBackStack(null); // null ნიშნავს რომ თითო-თითოდ დაბრუნდება უკან და არა სპეციფიკური სახელის მქონე ფრაგმენტზე
        fragmentTransaction.commit();
    }

    @Override
    public void onGridRecipeSelected(int index) {
        DualPaneFragment fragment = new DualPaneFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerFragment.KEY_RECIPE_INDEX, index);
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeHolder, fragment, VIEWPAGER_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
