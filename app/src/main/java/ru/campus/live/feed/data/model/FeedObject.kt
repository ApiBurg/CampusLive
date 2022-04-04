package ru.campus.live.feed.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import ru.campus.live.core.data.model.AttachmentModel
import ru.campus.live.location.data.model.LocationDataObject

@Parcelize
data class FeedObject(
    val type: Int = 1,
    val location: LocationDataObject? = null,
    val id: Int = 0,
    val message: String = "",
    val attachment: AttachmentModel? = null,
    var rating: Int = 0,
    val comments: Int = 0,
    var vote: Int = 0,
    val relativeTime: String = ""
) : Parcelable
