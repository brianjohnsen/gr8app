package gr8app

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@Mock([Day, Track, Slot])
@TestFor(AgendaParserService)
class AgendaParserServiceSpec extends Specification {

    def setup(){
    }

    def "testCallGr8conf"() {
        when:
        service.call()

        then:
        Day.count() == 3
        def days = Day.list()
        days.first().tracks.size() == 3
        def track1 = days.first().tracks.first()
        track1.name == "University Basic Trac"
        def firstSlot = track1.slots.first()
        firstSlot.room == "AUD-1"
        firstSlot.name == "*) Getting Groovy Workshop"
        firstSlot.speakers.size() == 1
        firstSlot.speakers.first() == "Hubert Klein Ikkink (Mr.HaKi)"
        firstSlot.start == new Date("2013/05/22 09:00")
    }
}
