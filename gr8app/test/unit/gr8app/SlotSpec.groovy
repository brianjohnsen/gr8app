package gr8app

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@Mock([Day, Track, Slot])
@TestFor(Slot)
class SlotSpec extends Specification {

    def setup() {
        new AgendaParserService().importAgendaData()
    }


    def "track name"() {
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
