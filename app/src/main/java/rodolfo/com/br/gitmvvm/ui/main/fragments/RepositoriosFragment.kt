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


    private val adapter : RepositoriosAdapter by lazy {
        RepositoriosAdapter(ArrayList())
    }

    private val factory : RepositoriosViewModel.Factory by lazy {
        RepositoriosViewModel.Factory()
    }

    private val repositoriosViewModel : RepositoriosViewModel by lazy {
        ViewModelProviders.of(this,factory).get(RepositoriosViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repositorios, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var args = arguments?.let { RepositoriosFragmentArgs.fromBundle(it) }

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
        repositoriosViewModel.repositorios.observe(viewLifecycleOwner, Observer {repos ->
            if(repos !=null && repos.isNotEmpty()){
                adapter.addRepositorios(repos)
            }
        })

        repositoriosViewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            Log.d("Teste","Loading:{?$loading}")
            if (loading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        })

        repositoriosViewModel.error.observe(viewLifecycleOwner, Observer {error ->
            if(error){
                tvMsgErro.visibility = View.VISIBLE
            }else{
                tvMsgErro.visibility = View.INVISIBLE
            }

        })
    }

}


