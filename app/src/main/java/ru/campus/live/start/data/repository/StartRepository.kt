package ru.campus.live.start.data.repository

import ru.campus.live.start.data.model.StartModel
import javax.inject.Inject

interface IStartRepository {
    fun start(): ArrayList<StartModel>
}

class StartRepository @Inject constructor(
    private val startDataSource: StartDataSource,
) : IStartRepository {

    override fun start(): ArrayList<StartModel> {
        return startDataSource.execute()
    }

}