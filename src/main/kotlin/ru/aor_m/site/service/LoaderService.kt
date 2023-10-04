package ru.aor_m.site.service

import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Service
import ru.aor_m.site.entity.Counterparty
import ru.aor_m.site.entity.FilesCounterparty
import ru.aor_m.site.repository.CounterpartyRepository
import ru.aor_m.site.repository.FilesCounterpartyRepository
import java.net.URL
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.*
import kotlin.io.path.pathString


@Service
@Secured("ROLE_ADMIN")
class LoaderService(private var filesCounterpartyRepository: FilesCounterpartyRepository, private var counterpartyRepository: CounterpartyRepository) {
    /*companion object {
        private const val interval = "PT1D"
        private const val delay = "PT01M"
    }*/

    //@Scheduled(initialDelayString = delay, fixedRateString = interval)*/
    fun loadFromRegardToDB() {
        val dateFormatter = SimpleDateFormat("dd.MM.yy")
        val currentDate = Date()
        val temp = Files.createTempFile("", ".xlsx")
        val urlFileName = "regard_priceList_new."+dateFormatter.format(currentDate)+".xlsx"
        val url = URL("https://www.regard.ru/api/price/"+urlFileName)
        println(""+url+"/"+temp.pathString)

        val counterpartyName = url.host.removePrefix("www.")

        val counterpartyOptional = counterpartyRepository.findByName(counterpartyName)
        val counterparty = if (counterpartyOptional.isPresent) {
            counterpartyOptional.get()
        } else {
            val newCounterparty = Counterparty()
            newCounterparty.name = counterpartyName
            counterpartyRepository.save(newCounterparty)
            newCounterparty
        }

        if (!filesCounterpartyRepository.findByName(urlFileName).isPresent) {
            val currentDayLong = Calendar.getInstance()
            currentDayLong[Calendar.HOUR_OF_DAY] = 0
            currentDayLong[Calendar.MINUTE] = 0
            currentDayLong[Calendar.SECOND] = 0
            currentDayLong[Calendar.MILLISECOND] = 0

            //download
            if (Files.copy(url.openStream(),temp)>0) {
                //save
                val filesCounterparty = FilesCounterparty()
                filesCounterparty.date = currentDayLong.timeInMillis
                filesCounterparty.name = urlFileName
                filesCounterparty.url = url.toString()
                filesCounterparty.counterparty = counterparty
                filesCounterpartyRepository.save(filesCounterparty)
            }
        }
    }

    //parsing xlsx
    //val workbook = WorkbookFactory
    //save
}