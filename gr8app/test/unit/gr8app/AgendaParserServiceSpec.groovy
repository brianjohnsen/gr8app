package gr8app

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@Mock([Day, Track, Slot, Speaker])
@TestFor(AgendaParserService)
class AgendaParserServiceSpec extends Specification {

    def data

    def setup() {
        data = JSON.parse(new File("data/gr8.json").text)
        service.importAgendaData(data)
    }


//    def "not duplicating the domain"(){
//
//        when:
//        service.importAgendaData()
//
//        then:
//        Day.count() == old(Day.count())
//        Track.count() == old(Track.count())
//        Slot.count() == old(Slot.count())
//
//    }

    def "imports agenda data from gr8conf site"() {
        expect:
        Day.count() == 3

        when:
        def days = Day.list()

        then:
        days.first().tracks.size() == 3

        and:
        def track1 = days.first().tracks.first()
        track1.name == "University Basic Trac"

        and:
        def firstSlot = track1.slots.first()
        firstSlot.room == "AUD-1"
        firstSlot.name == "*) Getting Groovy Workshop"
        firstSlot.speakers.size() == 1
        firstSlot.speakers.first().name == "Hubert Klein Ikkink (Mr.HaKi)"
        firstSlot.start == new Date("2013/05/22 09:00")
    }

    def "multiple speakers"() {
        when:
        def slot = Slot.findByName("*) Unleashing the power of AST transformations workshop")

        then:
        slot
        slot.speakers.size() == 2
        "Cédric Champeau" in slot.speakers*.name
        "Andres Almiray" in slot.speakers*.name
    }

    def "imports breaks"() {
        when:
        def pauser = Slot.findAllByPause(true)

        then:
        pauser.size() > 5
        "Lunch" in pauser*.name
        "Coffee break" in pauser*.name
    }
}
