package kr.KENNYSOFT.Udacity.Project1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class MovieActivity extends AppCompatActivity
{
	MovieItem movieItem;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);

		movieItem=getIntent().getParcelableExtra("movieItem");
		Picasso.with(this).load(movieItem.poster_path).into((ImageView)findViewById(R.id.movie_poster));
		((TextView)findViewById(R.id.movie_title)).setText(movieItem.title);
		((TextView)findViewById(R.id.movie_release_date)).setText(movieItem.release_date.substring(0,4));
		((TextView)findViewById(R.id.movie_vote_average)).setText(String.format(Locale.getDefault(),getString(R.string.movie_vote_format),movieItem.vote_average));
		((TextView)findViewById(R.id.movie_plot_synopsis)).setText(movieItem.plot_synopsis);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}