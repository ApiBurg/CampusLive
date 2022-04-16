package ru.campus.live.ribbon.domain

import ru.campus.live.core.data.datasource.DisplayMetrics
import ru.campus.live.core.data.datasource.IUserDataSource
import ru.campus.live.core.data.model.ErrorObject
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteObject
import ru.campus.live.core.data.repository.IUploadMediaRepository
import ru.campus.live.discussion.domain.usecase.DiscussionTitleUseCase
import ru.campus.live.gallery.data.model.GalleryDataObject
import ru.campus.live.gallery.data.model.UploadMediaObject
import ru.campus.live.location.data.model.LocationModel
import ru.campus.live.ribbon.data.model.PublicationPostObject
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonViewType
import ru.campus.live.ribbon.data.repository.IRibbonRepository
import javax.inject.Inject

class RibbonInteractor @Inject constructor(
    private val repository: IRibbonRepository,
    private val userDataSource: IUserDataSource,
    private val uploadRepository: IUploadMediaRepository,
    private val displayMetrics: DisplayMetrics,
    private val titleUseCase: DiscussionTitleUseCase,
) {

    fun get(model: ArrayList<RibbonModel>): ArrayList<RibbonModel> {
        when (val result = repository.get(offset = getOffset(model))) {
            is ResponseObject.Success -> model.addAll(result.data)
            is ResponseObject.Failure -> {
                if(model.size != 0 && result.error.code != 404) {
                    model.add(1, error(result.error))
                }
            }
        }
        return model.preparation().header()
    }

    fun complaint(item: RibbonModel) {
        repository.complaint(item.id)
    }

    fun vote(voteObject: VoteObject) {
        repository.vote(voteObject)
    }

    fun post(params: PublicationPostObject): ResponseObject<RibbonModel> {
        return repository.post(params)
    }

    fun upload(params: GalleryDataObject): UploadMediaObject {
        val result = uploadRepository.upload(params)
        val error = result !is ResponseObject.Success
        val id = if (result is ResponseObject.Success) result.data.id else 0
        return UploadMediaObject(
            id = id,
            fullPath = params.fullPath,
            upload = false,
            error = error,
            animation = false
        )
    }

    fun error(params: ErrorObject): RibbonModel {
        return RibbonModel(viewType = RibbonViewType.ERROR, message = params.message)
    }

    private fun getOffset(model: ArrayList<RibbonModel>): Int {
        return model.count{ it.viewType == RibbonViewType.PUBLICATION }
    }

    private fun ArrayList<RibbonModel>.preparation(): ArrayList<RibbonModel> {
        val model = this
        model.forEachIndexed { index, item ->
            if (item.commentsString.isEmpty())
                model[index].commentsString = titleUseCase.execute(item.comments)
            if (item.viewType == RibbonViewType.PUBLICATION) {
                if (item.attachment != null) {
                    val params = displayMetrics.get(item.attachment.width, item.attachment.height)
                    model[index].mediaWidth = params[0]
                    model[index].mediaHeight = params[1]
                }
            }
        }
        return model
    }

    private fun ArrayList<RibbonModel>.header(): ArrayList<RibbonModel> {
        val model = this
        if (model.size != 0 && model[0].viewType != RibbonViewType.HEADING) {
            val location = LocationModel(
                id = userDataSource.location().id,
                name = userDataSource.location().name,
                address = userDataSource.location().name,
                type = userDataSource.location().type
            )
            model.add(0, RibbonModel(viewType = RibbonViewType.HEADING, location = location))
        }
        return model
    }

}
