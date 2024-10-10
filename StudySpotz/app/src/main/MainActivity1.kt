package net.codebot.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import controller.Controller
import net.codebot.mobile.view.TaskList
import net.codebot.mobile.view.ViewModel
import net.codebot.mobile.ui.theme.MobileTheme
import persistence.DBStorage
import model.Model

class MainActivity1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storage = DBStorage()
        val model = Model(storage)
        val viewModel = ViewModel(model)
        val controller = Controller(model)

        setContent {
            MobileTheme {
                TaskList(viewModel, controller)
            }
        }
    }
}