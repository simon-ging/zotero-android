package org.zotero.android.architecture.database.requests

import io.realm.Realm
import io.realm.kotlin.where
import org.zotero.android.architecture.database.DbRequest
import org.zotero.android.architecture.database.objects.FieldKeys
import org.zotero.android.architecture.database.objects.RItem
import org.zotero.android.architecture.database.objects.UpdatableChangeType
import org.zotero.android.sync.LibraryIdentifier

class MarkAttachmentUploadedDbRequest(
    val libraryId: LibraryIdentifier,
    val key: String,
    val version: Int?,
): DbRequest {
    override val needsWrite: Boolean
        get() = true

    override fun process(database: Realm) {
        val attachment = database.where<RItem>().key(this.key, this.libraryId).findFirst()
        if (attachment == null) {
            return
        }
        attachment.attachmentNeedsSync = false
        attachment.changeType = UpdatableChangeType.syncResponse.name
        val md5 = attachment.fields.where().key(FieldKeys.Item.Attachment.md5).findFirst()?.value
        if (md5 != null) {
            attachment.backendMd5 = md5
        }
        val version = this.version
        if (version != null) {
            attachment.version = version
        }
        val parent = attachment.parent
        if (parent != null) {
            parent.version = parent.version
        }
    }


}