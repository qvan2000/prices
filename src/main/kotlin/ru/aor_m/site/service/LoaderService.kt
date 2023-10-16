package ru.aor_m.site.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Service
import ru.aor_m.site.entity.Counterparty
import ru.aor_m.site.entity.FilesCounterparty
import ru.aor_m.site.repository.CounterpartyRepository
import ru.aor_m.site.repository.FilesCounterpartyRepository
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.text.SimpleDateFormat
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.pathString


@Service
@Secured("ROLE_ADMIN")
class LoaderService(private var filesCounterpartyRepository: FilesCounterpartyRepository, private var counterpartyRepository: CounterpartyRepository) {

    @Value("\${aor_m.app.localSettingsTempFolder}")
    private var tempDir: String? = null
    companion object {
        private val logger = LoggerFactory.getLogger(LoaderService::class.java)
        private const val interval = "PT1D"
        private const val delay = "PT01M"
    }

    //@Scheduled(initialDelayString = delay, fixedRateString = interval)*/
    fun loadFromRegardToDB() {
        val dateFormatter = SimpleDateFormat("dd.MM.yy")
        val currentDate = Date()
        val urlFileName = "regard_priceList_new."+dateFormatter.format(currentDate)+".xlsx"
        val url = URL("https://www.regard.ru/api/price/"+urlFileName)

        val counterpartyName = url.host.removePrefix("www.")

        val counterpartyOptional = counterpartyRepository.getByName(counterpartyName)
        val counterparty = if (counterpartyOptional.isPresent) {
            counterpartyOptional.get()
        } else {
            val newCounterparty = Counterparty()
            newCounterparty.name = counterpartyName
            counterpartyRepository.save(newCounterparty)
            newCounterparty
        }

        val filesCounterpartyOptional = filesCounterpartyRepository.findByName(urlFileName)
        val toDownload: Boolean
        val filesCounterparty: FilesCounterparty

        if (!filesCounterpartyOptional.isPresent) {
            filesCounterparty = FilesCounterparty()
            toDownload = true
        } else {
            filesCounterparty = filesCounterpartyOptional.get()
            toDownload = if (!filesCounterparty.isDownloaded) {
                true
            } else {
                false
            }
        }

        if (toDownload == true) {
            val temp = Files.createFile(Path(tempDir!!+urlFileName))
            logger.info(""+url+"/"+temp.pathString)

            val currentDayLong = Calendar.getInstance()
            currentDayLong[Calendar.HOUR_OF_DAY] = 0
            currentDayLong[Calendar.MINUTE] = 0
            currentDayLong[Calendar.SECOND] = 0
            currentDayLong[Calendar.MILLISECOND] = 0

            //download
            if (Files.copy(url.openStream(), temp, StandardCopyOption.REPLACE_EXISTING) > 0) {
                //save
                filesCounterparty.date = currentDayLong.timeInMillis
                filesCounterparty.name = urlFileName
                filesCounterparty.url = url.toString()
                filesCounterparty.counterparty = counterparty
                filesCounterparty.isDownloaded = true
                filesCounterpartyRepository.save(filesCounterparty)
            }
        }
    }

    //parsing xlsx
    //val workbook = WorkbookFactory
    //save
}