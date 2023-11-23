package dev.belalkhan.minitales.home.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.belalkhan.minitales.common.domain.models.Story
import dev.belalkhan.minitales.theme.MiniTalesTheme
import dev.belalkhan.minitales.theme.components.MiniTalesPreview

@Composable
internal fun HomeTab(
    viewModel: HomeTabViewModel,
    onSelectStory: (id: Int) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    LazyVerticalGrid(
        modifier = Modifier.padding(4.dp),
        columns = GridCells.Adaptive(minSize = 160.dp),
    ) {
        items(uiState.value.drafts) { story ->
            StoryCard(
                story = story,
                onSelectStory = { onSelectStory(story.id) },
            )
        }
    }
}

@Composable
private fun StoryCard(story: Story, onSelectStory: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onSelectStory() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(8.dp),
        ) {
            Text(
                text = story.title,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = story.createdAt,
                maxLines = 1,
                style = MaterialTheme.typography.labelSmall,
                fontStyle = FontStyle.Italic,
            )

            Spacer(modifier = Modifier.size(12.dp))

            Text(
                text = story.content,
                maxLines = 4,
                style = MaterialTheme.typography.labelSmall,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@MiniTalesPreview
@Composable
private fun StoryCardPreview() {
    MiniTalesTheme {
        Surface {
            StoryCard(
                Story(
                    1,
                    "A trip to Ooty",
                    "Mini Tales is an Android app showcasing a multi-modular architecture for personalized profiles and captivating short stories. Built with Jetpack Compose and Kotlin, it leverages Hilt for dependency injection. Experience modern Android development with Mini Tales.",
                    "10 mins ago",
                    true,
                ),
            ) {}
        }
    }
}
