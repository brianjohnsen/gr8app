package gr8app

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@Mock([Day, Track, Slot])
@TestFor(AgendaParserService)
class AgendaParserServiceSpec extends Specification {

    def setup(){
    }

    def "imports agenda data from gr8conf site"() {
        when:
        service.importAgendaData()

        then:
        Day.count() == 3
        def days = Day.list()
        days.first().tracks.size() == 3

        and:
        def track1 = days.first().tracks.first()
        track1.name == "University Basic Trac"
        def firstSlot = track1.slots.first()
        firstSlot.room == "AUD-1"
        firstSlot.name == "*) Getting Groovy Workshop"
        firstSlot.speakers.size() == 1
        firstSlot.speakers.first() == "Hubert Klein Ikkink (Mr.HaKi)"
        firstSlot.start == new Date("2013/05/22 09:00")
    }

    def "imports breaks"() {
        when:
        service.importAgendaData()
        def pauser = Slot.findAllByPause(true)

        then:
        pauser.size() > 5
        "Lunch" in pauser*.name
        "Coffee break" in pauser*.name
    }
}
