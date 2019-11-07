package toocowe.`fun`.instaclone

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.parse.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnKeyListener, OnClickListener {

    // Globals
    private var isLogIn = true

    // functions
    private fun userLogin() {
        ParseUser.logInInBackground(editTextUser.text.toString(), editTextPass.text.toString(),
            LogInCallback { user, e ->
                if (user != null) {
                    Log.i("success","user ${user.username} logged in")
                    Toast.makeText(this,"You are logged in!",Toast.LENGTH_SHORT)
                } else {
                    Log.e("login","failed to log in")
                    Toast.makeText(this,"Log in failed",Toast.LENGTH_SHORT)
                    e.printStackTrace()
                }
            })
    }

    private fun userSignUp() {
        Log.i("DEBUG", "signing up")
        var user = ParseUser()
        with(user) {
            username = editTextUser.text.toString()
            setPassword(editTextPass.text.toString())

            signUpInBackground { e ->
                if (e == null) {
                    Log.i("success", "user ${user.username} created")
                } else {
                    e.printStackTrace()
                    Log.e("signup", "failed to log in")
                }
            }
        }
    }

    override fun onClick(view: View) {
        Log.i("onClick","detected $view")
        when (view!!.id) {
            R.id.button -> {
                Log.i("onClick","button")
                if (editTextPass.text.isEmpty() || editTextPass.text.isEmpty()) {
                    return
                }
                when (isLogIn) {
                    true -> userLogin()
                    false -> userSignUp()
                }
            }
            R.id.textViewSwap -> {
                Log.i("onClick","textView")
                isLogIn = !isLogIn
                Log.i("swap","swap is $isLogIn")
                if (isLogIn) {
                    button.text = "LOGIN"
                    textViewSwap.text = "or, sign up"
                } else {
                    button.text = "SIGNUP"
                    textViewSwap.text = "or, log in"
                }
            }
            R.id.backgroundLayout -> {
                Log.i("onClick","background")
                var keyboard: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    keyboard.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
    }


    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        Log.i("onKey","called")
        if (p1 == KeyEvent.KEYCODE_ENTER && p2?.action == KeyEvent.ACTION_DOWN) {
            onClick(p0!!)
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener(this)
        backgroundLayout.setOnClickListener(this)
        textViewSwap.setOnClickListener(this)


        ParseAnalytics.trackAppOpenedInBackground(intent)
        Log.i("onCreate","complete")
    }


}