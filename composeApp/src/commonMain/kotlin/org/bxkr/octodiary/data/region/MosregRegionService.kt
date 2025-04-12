package org.bxkr.octodiary.data.region

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.bxkr.octodiary.ui.viewmodel.AuthViewModel

class MosregRegionService : RegionService {

    @Composable
    override fun Step2(vm: AuthViewModel) {
        Button({ vm.goBack() }) { Text("f ") }
    }

    @Composable
    override fun Step3(vm: AuthViewModel) {
    }

}