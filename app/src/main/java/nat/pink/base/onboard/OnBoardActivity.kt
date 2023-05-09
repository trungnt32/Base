package nat.pink.base.onboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import nat.pink.base.R
import nat.pink.base.utils.setupSystemWindowInset

class OnBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board)
        setupSystemWindowInset()
    }


}