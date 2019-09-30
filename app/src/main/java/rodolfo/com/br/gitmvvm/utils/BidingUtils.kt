package rodolfo.com.br.gitmvvm.utils

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import rodolfo.com.br.gitmvvm.data.local.entity.User


@BindingAdapter("setUserInformation")
fun TextView.setUserInformation(user: User?){
    user?.let {
        text = "${it.nome} \nNumero de repositórios : ${it.numeroRepositorios}\nSeguidores : ${it.seguidores}"
    }
}

@BindingAdapter("setUserAvatar")
fun ImageView.setUserAvatar(user:User?){
    user?.let {
        Glide.with(this).load(it.avatarURL).into(this)
    }
}

@BindingAdapter("setVisibility")
fun View.setVisibility(boolean: Boolean?){
    boolean?.let {
        if(it) this.visibility = View.VISIBLE else  this.visibility = View.GONE
    }
}

@BindingAdapter("setVisibility")
fun View.setVisibility(user: User?){
    if(user!=null) this.visibility = View.VISIBLE else this.visibility = View.INVISIBLE
}

@BindingAdapter("setVisibility")
fun Button.setVisibility(boolean: Boolean?){
    boolean?.let {
        if(it) this.visibility = View.INVISIBLE else this.visibility = View.VISIBLE
    }
}