package rodolfo.com.br.gitmvvm.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_repositorio.view.*
import rodolfo.com.br.gitmvvm.R
import rodolfo.com.br.gitmvvm.data.local.entity.Repositorio
import rodolfo.com.br.gitmvvm.databinding.RowRepositorioBinding

class RepositoriosAdapter(var repositorios: MutableList<Repositorio>) : RecyclerView.Adapter<RepositoriosAdapter.RepositoriosHolder>() {


    private lateinit var listener :RepositorioClickListener

    interface RepositorioClickListener {
        fun onRepositorioClickListener(repositorio: Repositorio)
    }


    fun setOnRepositorioClickListener(listener: RepositorioClickListener) {
        this.listener = listener
    }

    fun addRepositorios(repositorios: List<Repositorio>){
        this.repositorios.clear()
        this.repositorios.addAll(repositorios)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriosHolder {
        val inflater = LayoutInflater.from(parent.context)
        val biding = DataBindingUtil.inflate<RowRepositorioBinding>(inflater,R.layout.row_repositorio,parent,false)
        return RepositoriosHolder(biding)
    }

    override fun getItemCount(): Int {
       return repositorios.size
    }

    override fun onBindViewHolder(holder: RepositoriosHolder, position: Int) {
        holder.binding.repositorio = repositorios[position]
        holder.binding.listener = listener

    }

    class RepositoriosHolder(val binding: RowRepositorioBinding) : RecyclerView.ViewHolder(binding.root)
}