# Retrofit-Part-Two Code Step

1.  gradel dependancy

    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.4.0' 

2. internet permission from menifaste file
  
  <uses-permission android:name="android.permission.INTERNET"/>

  android:usesCleartextTraffic="true"  // into application of manifaste file

3. make network packeg into java file

4. make RetrofitApiClient.java class into network packeg


public class RetrofitApiClient {

    private static final String BASE_URL = "http://jsonplaceholder.typicode.com";
    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private RetrofitApiClient() {} // So that nobody can create an object with constructor

    public static Retrofit getClient() {
        if (retrofit == null) {
            synchronized (RetrofitApiClient.class) { //thread safe Singleton implementation
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                }
            }
        }

        return retrofit;
    }

}


5. Make ApiInterface.java into network packeg -> select Interface when create class

public interface ApiInterface {

        @GET("posts") //Here, "posts" is the End point
        Call<List<Post>> getAllPost();   // hare Post will be the model data class

}


6. make a models packeg for putting Data Model Classes, Copy the Model Data class name Post and make a class named as Post.java

7. make model class from https://www.jsonschema2pojo.org/ -> put class name and select Source Type = JSON , annotation style = Gson,
   Make classes serializable will be check, Include getters and setters will be check

   Copy the class and paste into Post.java class


8. Go to main activity and call the network and test the data with Logcat of Log.d

	// inside of OnCreate
	ApiInterface apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        Call<List<Post>> call = apiInterface.getAllPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                Log.d("msg_one", "Response : " + response.body().get(0).getId());


            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });


------------------------------------------------------------- After Getting Data from Network, Show data using For Loop -------------------------



9. Go to activityMain.xml
	
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <TextView
            android:id="@+id/tvDataShow"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Retrofit part two"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    </ScrollView>

</LinearLayout>



10. Go to main activity and show the data with textView

	 TextView tvDataShow;

	// indside of OnCreate

	tvDataShow = findViewById(R.id.tvDataShow);

        ApiInterface apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        Call<List<Post>> call = apiInterface.getAllPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                Log.d("msg_one", "Response : " + response.body().get(0).getId());
                if (!response.isSuccessful()) {
                    tvDataShow.setText("Code : " + response.code());
                    return;
                }
                List<Post> allposts = response.body ();
                for (Post post : allposts) {
                    String content = "";
                    content += "ID : " + post.getId() + "\n";
                    content += "User Id : " + post.getUserId() + "\n";
                    content += "Title : " + post.getTitle() + "\n";
                    content += "Text : " + post.getBody() + "\n\n";

                    tvDataShow.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tvDataShow.setText(t.getMessage());
            }
        });



-------------------------------------------------- Now we will try to Show Data with RecyclerView with Adapter -----------------------------


11. activity_main.Xml new code

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/show_ip_button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="10dp"
        android:text="Show All Post"/>


    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recycler"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        tools:listitem="@layout/layout_of_recyclerview"/>


</LinearLayout>


12. take new layout for recyclerView named as : layout_of_recyclerview.xml

<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="10dp"
    android:background="#CC3C3C"
    android:orientation="vertical">

    <TextView
        android:id="@+id/tvIdOne"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"

        android:text="text one"/>
    <TextView
        android:id="@+id/tvIdTwo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="text one"/>
    <TextView
        android:id="@+id/tvIdThree"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="text one"/>
    <TextView
        android:id="@+id/tvIdFour"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="text one"/>

</LinearLayout>


13. make Adapter anmed as : RetrofitAdapter.java 


public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.MyViewHolder> {

    Context context;
    private List<Post> posts;

    public RetrofitAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_of_recyclerview, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Post post = posts.get(position);
        Log.d("test_otne","massage : "+ post);
        Log.d("test_otne","massage : "+ post.getTitle());
        holder.tv_One.setText(post.getTitle());
        holder.tv_Two.setText(String.valueOf(post.getId()));
        holder.tv_Three.setText(post.getBody());
        holder.tv_four.setText(String.valueOf(post.getUserId()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_One, tv_Two, tv_Three, tv_four;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_One =  itemView.findViewById(R.id.tvIdOne);
            tv_Two = itemView.findViewById(R.id.tvIdTwo);
            tv_Three =  itemView.findViewById(R.id.tvIdThree);
            tv_four =  itemView.findViewById(R.id.tvIdFour);
        }

    }
}



14. into MainActivity.java where we call the Retrofit Client

	// Outside OnCreate	
	private RetrofitAdapter retrofitAdapter;
        private RecyclerView recyclerView;


	// find view by id
	recyclerView = findViewById(R.id.recycler);

		
		// inside of OnResponse of retrofit
		List<Post> allposts = response.body ();
                retrofitAdapter = new RetrofitAdapter(MainActivity.this, allposts);
                recyclerView.setAdapter(retrofitAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                        LinearLayoutManager.VERTICAL, false));


*** full code

public class MainActivity extends AppCompatActivity {


    private RetrofitAdapter retrofitAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tvDataShow = findViewById(R.id.tvDataShow);

        recyclerView = findViewById(R.id.recycler);

        ApiInterface apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        Call<List<Post>> call = apiInterface.getAllPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                Log.d("msg_one", "Response : " + response.body().get(0).getId());
                if (!response.isSuccessful()) {
                    //tvDataShow.setText("Code : " + response.code());
                    Log.d("msg_one", "Response : " + response.code());
                    return;
                }
                List<Post> allposts = response.body ();
                retrofitAdapter = new RetrofitAdapter(MainActivity.this, allposts);
                recyclerView.setAdapter(retrofitAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                        LinearLayoutManager.VERTICAL, false));
                
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                //tvDataShow.setText(t.getMessage());
                Log.d("msg_one", "Response : " + t.getMessage());
            }
        });

    }
}


15. if we want to set a button for calling Api and Show data

*** Full code

public class MainActivity extends AppCompatActivity {


    private RetrofitAdapter retrofitAdapter;
    private RecyclerView recyclerView;
    private Button show_ip_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tvDataShow = findViewById(R.id.tvDataShow);

        // --------------------------- find view by id -------------------------
        recyclerView = findViewById(R.id.recycler);
        show_ip_button = findViewById(R.id.show_ip_button);


        //----------------------------- set listener ----------------------------
        show_ip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

                Call<List<Post>> call = apiInterface.getAllPost();

                call.enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                        Log.d("msg_one", "Response : " + response.body().get(0).getId());
                        if (!response.isSuccessful()) {
                            //tvDataShow.setText("Code : " + response.code());
                            Log.d("msg_one", "Response : " + response.code());
                            return;
                        }
                        List<Post> allposts = response.body ();
                        retrofitAdapter = new RetrofitAdapter(MainActivity.this, allposts);
                        recyclerView.setAdapter(retrofitAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                                LinearLayoutManager.VERTICAL, false));

                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        //tvDataShow.setText(t.getMessage());
                        Log.d("msg_one", "Response : " + t.getMessage());
                    }
                });
            }
        });






    }
}


 
