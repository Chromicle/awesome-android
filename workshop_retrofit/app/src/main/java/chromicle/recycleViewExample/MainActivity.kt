package chromicle.recycleViewExample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *@author Chromicle
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshLayout.setOnRefreshListener {
            fetchMovies()
        }

        fetchMovies()

    }

    private fun fetchMovies() {
        refreshLayout.setRefreshing(true)

        MoviesApi()
            .getMovies().enqueue(object : Callback<List<Movie>> {
                override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                    refreshLayout.setRefreshing(false)
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                    refreshLayout.setRefreshing(false)
                    val movies = response.body()

                    movies?.let {
                        showMovies(it)
                    }

                }

            })
    }

    private fun showMovies(movies: List<Movie>) {
        recyclerViewMovies.layoutManager = LinearLayoutManager(this)
        recyclerViewMovies.adapter = MoviesAdapter(movies)
    }
}
