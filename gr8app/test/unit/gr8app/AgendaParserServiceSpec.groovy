package gr8app

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@Mock([Slot, Speaker])
@TestFor(AgendaParserService)
class AgendaParserServiceSpec extends Specification {

    def data

    def setup() {
        StringMetaHelper.addToDateToStringMetaClass()
        data = JSON.parse(new File("data/gr8.json").text)
        service.importAgendaData(data)
    }


    def "not duplicating the domain"() {
        when:
        service.importAgendaData(data)

        then:
        Slot.count() == old(Slot.count())
    }

    def "strip name"() {
        expect:
        service.stripTalkName(name) == stripped

        where:
        name                                      | stripped
        "*) Getting Groovy Workshop"              | "Getting Groovy Workshop"
        "*) Web Development with Grails Workshop" | "Web Development with Grails Workshop"
    }


    def "imports agenda data from gr8conf site"() {
        when:
        def firstSlot = Slot.get(1)

        then:
        firstSlot.trackName == "University Basic Trac"
        firstSlot.room == "AUD-1"
        firstSlot.name == "Getting Groovy Workshop"
        firstSlot.speakers.size() == 1
        firstSlot.speakers.first().name == "Hubert Klein Ikkink (Mr.HaKi)"
        firstSlot.start == new Date("2013/05/22 09:00")
    }

    def "multiple speakers"() {
        when:
        def slot = Slot.findByName("Unleashing the power of AST transformations workshop")

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
