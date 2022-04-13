package ru.campus.live.start.data.repository

import ru.campus.live.R
import ru.campus.live.core.data.datasource.StringProvider
import ru.campus.live.start.data.model.StartDataObject
import javax.inject.Inject

class PresentationDataSource @Inject constructor(private val stringProvider: StringProvider) {

    fun execute(): ArrayList<StartDataObject> {
        val model = ArrayList<StartDataObject>()
        model.add(
            StartDataObject(
                id = 1,
                title = stringProvider.get(R.string.start_title_two),
                message = stringProvider.get(R.string.start_message_two),
                icon = R.drawable.college
            )
        )
        model.add(
            StartDataObject(
                id = 1,
                title = stringProvider.get(R.string.start_title_three),
                message = stringProvider.get(R.string.start_message_three),
                icon = R.drawable.alarm
            )
        )
        model.add(
            StartDataObject(
                id = 1,
                title = stringProvider.get(R.string.start_title_four),
                message = stringProvider.get(R.string.start_message_four),
                icon = R.drawable.forum
            )
        )
        return model
    }

}