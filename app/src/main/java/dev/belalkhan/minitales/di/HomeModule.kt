package dev.belalkhan.minitales.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.belalkhan.minitales.home.data.StoryRepository
import dev.belalkhan.minitales.home.data.StoryRepositoryImpl

@InstallIn(ViewModelComponent::class)
@Module
object HomeModule {

    @Provides
    fun provideStoryRepository(impl: StoryRepositoryImpl): StoryRepository = impl
}
