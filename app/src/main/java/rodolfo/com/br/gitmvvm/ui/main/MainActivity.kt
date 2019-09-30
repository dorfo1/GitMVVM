package rodolfo.com.br.gitmvvm.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.facebook.stetho.Stetho
import rodolfo.com.br.gitmvvm.R

class MainActivity : AppCompatActivity() {


    private lateinit var navcontroler: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)

        navcontroler = Navigation.findNavController(this,R.id.fragment)
        setUpActionBar(navcontroler)
    }

    private fun setUpActionBar(navController: NavController) {
       NavigationUI.setupActionBarWithNavController(this,navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navcontroler,null)
    }
}
