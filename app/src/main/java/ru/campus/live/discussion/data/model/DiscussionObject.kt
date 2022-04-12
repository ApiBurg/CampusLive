package ru.campus.live.discussion.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.campus.live.core.data.model.AttachmentModel

@Parcelize
data class DiscussionObject(
    var type: DiscussionViewType = DiscussionViewType.PARENT_SHIMMER,
    val id: Int = 0,
    val hidden: Int = 0,
    val author: Int = 0,
    val icon_id: Int = 0,
    val message: String = "",
    val attachment: AttachmentModel? = null,
    var rating: Int = 0,
    var vote: Int = 0,
    val parent: Int = 0,
    val answered: Int = 0,
    val relativeTime: String = ""
) : Parcelable
