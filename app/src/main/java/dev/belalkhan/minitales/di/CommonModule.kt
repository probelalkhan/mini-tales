package dev.belalkhan.minitales.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.belalkhan.minitales.common.data.repositories.UserRepository
import dev.belalkhan.minitales.common.data.repositories.UserRepositoryImpl

@InstallIn(ViewModelComponent::class)
@Module
object CommonModule {
    @Provides
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository = impl
}
