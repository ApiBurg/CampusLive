package ru.campus.live.discussion.domain.usecase

import ru.campus.live.discussion.data.model.DiscussionObject

class UserAvatarUseCase(private val uid: Int) {

    fun execute(model: ArrayList<DiscussionObject>): Int {
        val icon = search(model)
        if (icon == 0) return generate(model)
        return icon
    }

    private fun search(model: ArrayList<DiscussionObject>): Int {
        model.forEach { item -> if (item.author == uid) return item.icon_id }
        return 0
    }

    private fun generateArrayListIcon(): ArrayList<Int> {
        val model = ArrayList<Int>()
        for (i in 1..50) {
            model.add(i)
        }
        return model
    }

    private fun generate(model: ArrayList<DiscussionObject>): Int {
        val icon = generateArrayListIcon()
        model.forEach { item ->
            val index = icon.indexOfLast { it == item.icon_id }
            if (index != -1) icon.removeAt(index)
        }

        if (icon.size != 0) {
            val index = icon.random()
            return icon[index]
        }
        return 0
    }

}