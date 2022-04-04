package ru.campus.live.discussion.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.campus.live.core.data.model.AttachmentModel

@Parcelize
data class DiscussionObject(
    var type: Int = 0,
    val id: Int,
    val hidden: Int,
    val author: Int,
    val icon_id: Int,
    val message: String,
    val attachment: AttachmentModel?,
    var rating: Int,
    var vote: Int,
    val parent: Int,
    val answered: Int,
    val relativeTime: String
) : Parcelable
