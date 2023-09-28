package ru.aor_m.site.entity

import jakarta.persistence.*
import java.util.UUID

@Entity(name = "files")
class FilesCounterparty {

    @Id
    var id: UUID? = null

    @Column(name = "name")
    var name: String = ""

    @ManyToOne(cascade = [CascadeType.ALL])
    var counterparty: Counterparty? = null

    @Column(name = "date")
    var date: Long? = null

    @Column(name = "url")
    var url: String = ""

    @Column(name = "is_downloaded")
    var isDownloaded: Boolean = false

    @Column(name = "is_counted")
    var isCounted: Boolean = false

}