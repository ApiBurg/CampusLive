package ru.campus.live.core.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.campus.live.gallery.data.repository.GalleryRepository
import ru.campus.live.gallery.data.repository.IGalleryRepository

@Module
class GalleryModule {

    @Provides
    fun provideGalleryRepository(context: Context): GalleryRepository {
        return GalleryRepository(context = context)
    }

    @Provides
    fun provideIGalleryRepository(galleryRepository: GalleryRepository): IGalleryRepository {
        return galleryRepository
    }

}