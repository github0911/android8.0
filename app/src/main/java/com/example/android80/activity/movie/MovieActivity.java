package com.example.android80.activity.movie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.example.android80.R;
import com.example.android80.activity.base.BaseActivity;
import com.example.android80.api.MovieService;
import com.example.android80.entity.MovieEntity;
import com.example.android80.fragment.MovieFragment;
import com.example.android80.fragment.WebViewFragment;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieActivity extends BaseActivity implements MovieFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            MovieFragment firstFragment = MovieFragment.newInstance();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }

    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MovieActivity.class);
        return intent;
    }

    @Override
    public void onItemClick(MovieEntity.SubjectsBean subjectsBean) {
        if (subjectsBean != null) {
            toWebViewFragment(subjectsBean.getAlt());
        }
    }

    private void toWebViewFragment(String link) {
        if (TextUtils.isEmpty(link)) {
            return;
        }

        WebViewFragment webViewFragment = (WebViewFragment)
                getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        if (webViewFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            webViewFragment.reload(link);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            WebViewFragment newFragment = WebViewFragment.newInstance(link);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
}
