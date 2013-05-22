package gr8app

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@Mock([Slot, Speaker])
@TestFor(Slot)
class SlotSpec extends Specification {

    def setup() {
        StringMetaHelper.addToDateToStringMetaClass()
    }


    def "track name"() {
        setup:
        new AgendaParserService().importAgendaData()

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
