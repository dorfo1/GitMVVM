package rodolfo.com.br.gitmvvm.ui.main.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_repositorios.*

import rodolfo.com.br.gitmvvm.R
import rodolfo.com.br.gitmvvm.data.local.entity.Repositorio
import rodolfo.com.br.gitmvvm.ui.main.adapter.RepositoriosAdapter
import rodolfo.com.br.gitmvvm.ui.main.viewmodel.RepositoriosViewModel

class RepositoriosFragment : Fragment() {

    private lateinit var repositoriosViewModel: RepositoriosViewModel
    private lateinit var adapter : RepositoriosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repositorios, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var args = arguments?.let { RepositoriosFragmentArgs.fromBundle(it) }
        repositoriosViewModel = ViewModelProviders.of(this).get(RepositoriosViewModel::class.java)

        adapter = RepositoriosAdapter(ArrayList())
        rvRepositorios.adapter = adapter
        rvRepositorios.layoutManager = LinearLayoutManager(context)

        args?.username?.let {
            Log.d("USER",it)
            repositoriosViewModel.fetchRepositorios(it)
        }
        setObservers()


        adapter.setOnRepositorioClickListener(object : RepositoriosAdapter.RepositorioClickListener{
            override fun onRepositorioClickListener(repositorio: Repositorio) {
                Toast.makeText(context,"Linguagem "+repositorio.language,Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun setObservers() {
        repositoriosViewModel.repositorios.observe(this, Observer {repositorios ->
            if(repositorios !=null && repositorios.isNotEmpty()){
                adapter.addRepositorios(repositorios)
            }
        })


        repositoriosViewModel.loading.observe(this, Observer { loading ->
            if (loading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        })

        repositoriosViewModel.error.observe(this, Observer {error ->
            if(error){
                tvMsgErro.visibility = View.VISIBLE
            }else{
                tvMsgErro.visibility = View.INVISIBLE
            }

        })
    }

}


