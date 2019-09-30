package rodolfo.com.br.gitmvvm.ui.main.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.fragment_user.*

import rodolfo.com.br.gitmvvm.R
import rodolfo.com.br.gitmvvm.data.remote.getGitHubAPI
import rodolfo.com.br.gitmvvm.databinding.FragmentUserBinding
import rodolfo.com.br.gitmvvm.ui.main.viewmodel.UserViewModel


class UserFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var binding : FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentUserBinding>(inflater,R.layout.fragment_user,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        binding.lifecycleOwner = this
        binding.userViewModel = userViewModel


        btnBuscar.setOnClickListener {
            if (etUsuario.text.isNotEmpty()) {
                userViewModel.fetchUser(etUsuario.text.toString())
                etUsuario.onEditorAction(EditorInfo.IME_ACTION_DONE);
            } else {
                Toast.makeText(context, "Digite usuário", Toast.LENGTH_LONG).show()
            }
        }

        btnRepositorios.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(UserFragmentDirections.actionUserFragmentToRepositoriosFragment(userViewModel.user.value!!.login))
        }

    }

}
