package dev.belalkhan.minitales.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.belalkhan.minitales.common.domain.models.User
import dev.belalkhan.minitales.theme.MiniTalesTheme
import dev.belalkhan.minitales.theme.components.FullScreenCircularProgressIndicator
import dev.belalkhan.minitales.theme.components.MiniTalesPreview

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, onLogout: () -> Unit) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    when (val state = uiState.value) {
        ProfileUiState.Error -> {
        }

        ProfileUiState.Loading -> {
            FullScreenCircularProgressIndicator()
        }

        is ProfileUiState.Profile -> {
            Profile(
                user = state.user,
                onLogout = {
                    viewModel.logout()
                    onLogout()
                },
            )
        }
    }
}

@Composable
private fun Profile(
    user: User,
    onLogout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        Divider()
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (box, image) = createRefs()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(12.dp)
                    .constrainAs(box) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
            )

            Image(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(60.dp))
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(box.bottom, (-50).dp)
                    },
                painter = painterResource(id = R.drawable.dummy_profile_image),
                contentDescription = "profile image",
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = user.fullName,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )

            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = "Crafting worlds with words, one tale at a time",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.size(24.dp))

            Row(modifier = Modifier.padding(top = 14.dp)) {
                Icon(imageVector = Icons.Filled.Email, "email")

                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = user.email,
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Row(modifier = Modifier.padding(top = 14.dp)) {
                Icon(imageVector = Icons.Filled.Phone, "phone")

                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = "+91 7754347812",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Row(modifier = Modifier.padding(top = 14.dp)) {
                Icon(imageVector = Icons.Filled.LocationOn, "location")

                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = "Bangalore, India",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            OutlinedButton(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .align(CenterHorizontally),
                onClick = { onLogout() },
            ) {
                Text(text = stringResource(R.string.logout))
            }
        }
    }
}

@MiniTalesPreview
@Composable
fun ProfilePreview() {
    MiniTalesTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Profile(User("", "", "", "", 1)) {}
        }
    }
}
