package org.gr8conf.trackapp

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.gr8conf.trackapp.AgendaParserService
import spock.lang.Specification

@Mock([Slot, Speaker])
@TestFor(Slot)
class SlotSpec extends Specification {

    private final data = JSON.parse(new File("data/gr8.json").text)

    def setup() {
        StringMetaHelper.addToDateToStringMetaClass()
    }


    def "track name"() {
        setup:
        new AgendaParserService().importAgendaData(data)
//        new AgendaParserService().importAgendaData()

        expect:
        Slot.findByName(slotName).trackName == trackName
        Slot.findByName(slotName).offtrack == offtrack

        where:
        slotName                  | trackName      | offtrack
        "Grails Goodness"         | "Grails Track" | false
        "The next generation MOP" | "Groovy Track" | false
        "Meet'n'greet"            | "Offtrack"     | true
        "Hackergarten on Tour"    | "Offtrack"     | true
    }

}
