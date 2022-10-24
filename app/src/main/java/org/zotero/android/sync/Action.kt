package org.zotero.android.sync;

sealed class Action {
    object loadKeyPermissions : Action()
    object syncGroupVersions : Action()
    data class createLibraryActions(
        val librarySyncType: LibrarySyncType,
        val createLibraryActionsOptions: CreateLibraryActionsOptions
    ) : Action()

    data class resolveDeletedGroup(val a: Int, val b: String) : Action()

    data class syncGroupToDb(val a: Int) : Action()

    data class resolveGroupMetadataWritePermission(val groupId: Int, val libraryDataName: String): Action()

    data class performWebDavDeletions(override val libraryId: LibraryIdentifier): Action()

    data class submitDeleteBatch(val deleteBatch: DeleteBatch): Action()

    data class createUploadActions(override val libraryId: LibraryIdentifier, val hadOtherWriteActions: Boolean): Action()

    data class syncVersions(
        override val libraryId: LibraryIdentifier,
        val objectS: SyncObject,
        val version: Int,
        val checkRemote: Boolean
    ): Action()

    data class submitWriteBatch(val writeBatch: WriteBatch): Action()

    data class syncDeletions(override val libraryId: LibraryIdentifier, val int: Int): Action()

    data class storeDeletionVersion(override val libraryId: LibraryIdentifier, val version: Int): Action()

    data class syncBatchesToDb(val batches: List<DownloadBatch>) : Action()

    data class syncSettings(override val libraryId: LibraryIdentifier, val int: Int): Action()

    data class storeVersion(val version: Int, override val libraryId: LibraryIdentifier, val syncObject: SyncObject): Action()

        open val libraryId: LibraryIdentifier?
        get() {
            val q = this
            return when (q) {
                is loadKeyPermissions, is createLibraryActions, is syncGroupVersions ->
                    return null
                is resolveDeletedGroup -> return LibraryIdentifier.group(q.a)
                is syncGroupToDb -> return LibraryIdentifier.group(q.a)
                is createUploadActions -> q.libraryId
                is performWebDavDeletions -> q.libraryId
                is resolveGroupMetadataWritePermission -> return LibraryIdentifier.group(q.groupId)
                is storeDeletionVersion -> q.libraryId
                is submitDeleteBatch -> q.libraryId
                is submitWriteBatch -> q.libraryId
                is syncDeletions -> q.libraryId
                is syncSettings -> q.libraryId
                is syncVersions -> q.libraryId
                is storeVersion -> q.libraryId
                is syncBatchesToDb -> q.batches.firstOrNull()?.libraryId
            }
        }

    val requiresConflictReceiver: Boolean
        get() {
            return when (this) {
                is loadKeyPermissions, is createLibraryActions, is syncGroupVersions ->
                    return false
                is resolveDeletedGroup -> true
                is syncGroupToDb -> false
                is createUploadActions -> false
                is performWebDavDeletions -> false
                is resolveGroupMetadataWritePermission -> true
                    is storeDeletionVersion -> false
                    is submitDeleteBatch -> false
                is submitWriteBatch -> false
                is syncDeletions -> true
                is syncSettings -> false
                is syncVersions -> false
                is storeVersion -> false
                is syncBatchesToDb -> false
            }
        }

}
