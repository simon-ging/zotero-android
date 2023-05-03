package org.zotero.android.pdf

import android.graphics.PointF
import android.graphics.RectF
import org.zotero.android.database.objects.AnnotationType
import java.util.Date

data class DocumentAnnotation(
    val key: String,
    val type: AnnotationType,
    val page: Int,
    val pageLabel: String,
    val rects: List<RectF>,
    val paths: List<List<PointF>>,
    val lineWidth: Float?,
    val color: String,
    val comment: String,
    val text: String?,
    val dateModified: Date,
) {
    val readerKey: AnnotationKey
        get() {
            return AnnotationKey(key = this.key, type = AnnotationKey.Kind.document)
        }

//    fun isAuthor(currentUserId: Int): Boolean {
//        return this.isAuthor
//    }
//
//    fun author(displayName: String, username: String): String {
//        return this.author
//    }
}