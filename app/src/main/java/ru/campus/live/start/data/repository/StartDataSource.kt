package ru.campus.live.start.data.repository

import ru.campus.live.R
import ru.campus.live.start.data.model.StartDataObject
import ru.campus.live.core.data.datasource.StringProvider

class StartDataSource(private val stringProvider: StringProvider) {

    fun execute(): ArrayList<StartDataObject> {
        val model = ArrayList<StartDataObject>()
        model.add(
            StartDataObject(
                id = 1,
                title = stringProvider.get(R.string.onboard_title_one),
                message = stringProvider.get(R.string.onboard_message_one),
                icon = R.drawable.community
            )
        )
        model.add(
            StartDataObject(
                id = 1,
                title = stringProvider.get(R.string.onboard_title_one),
                message = stringProvider.get(R.string.onboard_message_one),
                icon = R.drawable.community
            )
        )
        model.add(
            StartDataObject(
                id = 1,
                title = stringProvider.get(R.string.onboard_title_one),
                message = stringProvider.get(R.string.onboard_message_one),
                icon = R.drawable.community
            )
        )
        model.add(
            StartDataObject(
                id = 1,
                title = stringProvider.get(R.string.onboard_title_one),
                message = stringProvider.get(R.string.onboard_message_one),
                icon = R.drawable.community
            )
        )
        model.add(
            StartDataObject(
                id = 1,
                title = stringProvider.get(R.string.onboard_title_one),
                message = stringProvider.get(R.string.onboard_message_one),
                icon = R.drawable.community
            )
        )
        return model
    }

}