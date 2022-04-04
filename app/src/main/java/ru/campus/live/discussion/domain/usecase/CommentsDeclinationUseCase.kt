package ru.campus.live.discussion.domain.usecase

import android.content.Context
import ru.campus.live.R

class CommentsDeclinationUseCase(private val context: Context) {

    fun getTextComments(count: Int): String {
        val decline = arrayOf(
            context.getString(R.string.s_comment_one),
            context.getString(R.string.s_comments_two),
            context.getString(R.string.s_comments_3),
            context.getString(R.string.s_comments_4),
            context.getString(R.string.s_comments_5)
        )
        val commentsCount = count % 10
        return if (commentsCount > 4) {
            "$count " + context.getString(R.string.s_comment_one)
        } else {
            if (count in 12..19) {
                "$count " + context.getString(R.string.s_comment_one)
            } else {
                if (count == 0) {
                    context.getString(R.string.none_comments)
                } else {
                    "$count " + decline[commentsCount]
                }
            }
        }
    }

}