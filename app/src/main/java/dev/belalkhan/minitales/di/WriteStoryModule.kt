package dev.belalkhan.minitales.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.belalkhan.minitales.writestory.data.WriteStoryRepository
import dev.belalkhan.minitales.writestory.data.WriteStoryRepositoryImpl

@InstallIn(ViewModelComponent::class)
@Module
object WriteStoryModule {

    @Provides
    fun provideWriteStoryRepository(impl: WriteStoryRepositoryImpl): WriteStoryRepository = impl
}
