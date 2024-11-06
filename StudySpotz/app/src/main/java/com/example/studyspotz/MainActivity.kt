package com.example.studyspotz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.studyspotz.ui.theme.MobileTheme
import com.example.studyspotz.composables.LoginScreen
import com.example.studyspotz.model.StudySpotsModel
import com.example.studyspotz.view.StudySpotViewModel

class MainActivity : ComponentActivity() {
    private val authViewModel = AuthViewModel()
    private val studySpotModel = StudySpotsModel()
    private val studySpotViewModel = StudySpotViewModel(studySpotModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileTheme {
                Surface {
                    // Initialize Voyager Navigator with ListScreen
                    Navigator(screen = LoginScreen(Modifier, authViewModel, studySpotViewModel)) { navigator ->
                        SlideTransition(navigator)
                    }
                }
            }
        }
    }
}
