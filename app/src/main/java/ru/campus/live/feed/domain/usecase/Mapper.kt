package ru.campus.live.feed.domain.usecase

import ru.campus.live.core.data.model.AttachmentModel
import ru.campus.live.feed.data.model.FeedModel
import ru.campus.live.feed.data.model.FeedViewType
import ru.campus.live.feed.db.Publication
import ru.campus.live.location.data.model.LocationModel

class Mapper {

    fun toFeedObject(params: List<Publication>): ArrayList<FeedModel> {
        val model = ArrayList<FeedModel>()
        params.forEach { item ->
            model.add(
                FeedModel(
                    viewType = FeedViewType.values()[item.type],
                    location = LocationModel(
                        id = item.locationId ?: 0,
                        name = item.locationName ?: "",
                        address = item.locationAddress ?: "",
                        type = item.locationType ?: 0
                    ),
                    id = item.publicationId,
                    message = item.message,
                    attachment = if (item.mediaId == 0) {
                        null
                    } else {
                        AttachmentModel(
                            id = item.mediaId ?: 0,
                            path = item.mediaPath ?: "",
                            width = item.mediaWidth ?: 0,
                            height = item.mediaHeight ?: 0,
                            orientation = item.mediaOrientation ?: 0
                        )
                    },
                    rating = item.rating,
                    comments = item.rating,
                    vote = item.vote,
                    relativeTime = item.relativeTime
                )
            )
        }
        return model
    }

    fun toPublicationDb(params: ArrayList<FeedModel>): ArrayList<Publication> {
        val model = ArrayList<Publication>()
        params.forEach { item ->
            model.add(
                Publication(
                    publicationId = item.id,
                    type = item.viewType.ordinal,
                    locationId = item.location?.id,
                    locationName = item.location?.name,
                    locationAddress = item.location?.address,
                    locationType = item.location?.type,
                    message = item.message,
                    mediaId = item.attachment?.id,
                    mediaPath = item.attachment?.path,
                    mediaWidth = item.attachment?.width,
                    mediaHeight = item.attachment?.height,
                    mediaOrientation = item.attachment?.orientation,
                    rating = item.rating,
                    comments = item.comments,
                    vote = item.vote,
                    relativeTime = item.relativeTime
                )
            )
        }
        return model
    }
}