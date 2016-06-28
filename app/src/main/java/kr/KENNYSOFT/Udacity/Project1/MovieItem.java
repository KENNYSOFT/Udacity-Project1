package kr.KENNYSOFT.Udacity.Project1;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.simple.JSONObject;

public class MovieItem implements Parcelable
{
	String poster_path,title,release_date,plot_synopsis;
	double vote_average;

	MovieItem(JSONObject object)
	{
		this.poster_path="http://image.tmdb.org/t/p/w185"+object.get("poster_path");
		this.title=(String)object.get("title");
		this.release_date=(String)object.get("release_date");
		this.vote_average=Double.parseDouble(object.get("vote_average").toString());
		this.plot_synopsis=(String)object.get("overview");
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest,int flags)
	{
		dest.writeString(poster_path);
		dest.writeString(title);
		dest.writeString(release_date);
		dest.writeDouble(vote_average);
		dest.writeString(plot_synopsis);
	}

	public static final Parcelable.Creator<MovieItem> CREATOR=new Parcelable.Creator<MovieItem>()
	{
		@Override
		public MovieItem createFromParcel(Parcel in)
		{
			return new MovieItem(in);
		}

		@Override
		public MovieItem[] newArray(int size)
		{
			return new MovieItem[size];
		}
	};

	MovieItem(Parcel in)
	{
		poster_path=in.readString();
		title=in.readString();
		release_date=in.readString();
		vote_average=in.readDouble();
		plot_synopsis=in.readString();
	}
}