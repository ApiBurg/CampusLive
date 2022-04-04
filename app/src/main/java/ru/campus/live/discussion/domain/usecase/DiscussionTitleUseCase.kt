package ru.campus.live.discussion.domain.usecase

import ru.campus.live.R
import ru.campus.live.core.data.datasource.StringProvider
import javax.inject.Inject

class DiscussionTitleUseCase @Inject constructor(private val stringProvider: StringProvider) {

    fun execute(count: Int): String {
        val decline = arrayOf(
            stringProvider.get(R.string.s_comment_one),
            stringProvider.get(R.string.s_comments_two),
            stringProvider.get(R.string.s_comments_3),
            stringProvider.get(R.string.s_comments_4),
            stringProvider.get(R.string.s_comments_5)
        )

        val commentsCount = count % 10
        return if (commentsCount > 4) {
            "$count " + stringProvider.get(R.string.s_comment_one)
        } else {
            if (count in 12..19) {
                "$count " + stringProvider.get(R.string.s_comment_one)
            } else {
                if (count == 0) {
                    stringProvider.get(R.string.none_comments)
                } else {
                    "$count " + decline[commentsCount]
                }
            }
        }
    }

}