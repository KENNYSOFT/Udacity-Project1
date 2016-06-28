package kr.KENNYSOFT.Udacity.Project1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
	final String API_KEY="YOUR_API_KEY";

	public GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar actionBar=getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.mode)),new ActionBar.OnNavigationListener()
		{
			@Override
			public boolean onNavigationItemSelected(int itemPosition,long itemId)
			{
				switch(itemPosition)
				{
				case 0:
					new MovieTask(MainActivity.this).execute("http://api.themoviedb.org/3/movie/popular?api_key="+API_KEY);
					break;
				case 1:
					new MovieTask(MainActivity.this).execute("http://api.themoviedb.org/3/movie/top_rated?api_key="+API_KEY);
					break;
				}
				return true;
			}
		});

		gridView=(GridView)findViewById(R.id.gridview);
		new MovieTask(this).execute("http://api.themoviedb.org/3/movie/popular?api_key="+API_KEY);
	}
}

class MovieTask extends AsyncTask<String,Void,String>
{
	Context context;
	List<MovieItem> movieItemList=new ArrayList<>();

	MovieTask(Context context)
	{
		this.context=context;
	}

	@Override
	protected String doInBackground(String... urls)
	{
		String html="";
		try
		{
			URLConnection connection=new URL(urls[0]).openConnection();
			InputStream is=connection.getInputStream();
			BufferedReader in=new BufferedReader(new InputStreamReader(is));
			String line;
			while((line=in.readLine())!=null)html=html+line+"\n";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return html;
	}

	@Override
	protected void onPostExecute(String html)
	{
		try
		{
			JSONObject json=(JSONObject)new JSONParser().parse(html);
			JSONArray results=(JSONArray)json.get("results");
			for(Object item : results)movieItemList.add(new MovieItem((JSONObject)item));
			((MainActivity)context).gridView.setAdapter(new MovieItemAdapter(context,movieItemList));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

class MovieItemAdapter extends ArrayAdapter<MovieItem>
{
	Context context;

	MovieItemAdapter(Context context,List<MovieItem> MovieItems)
	{
		super(context,0,MovieItems);
		this.context=context;
	}

	@Override
	public View getView(int position,View convertView,ViewGroup parent)
	{
		final MovieItem movieItem=getItem(position);
		if(convertView==null)convertView=LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
		ImageView imageView=(ImageView)convertView.findViewById(R.id.list_item_image);
		Picasso.with(getContext()).load(movieItem.poster_path).into(imageView);
		imageView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				context.startActivity(new Intent(context,MovieActivity.class).putExtra("movieItem",movieItem));
			}
		});
		return convertView;
	}
}