package gr8app

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(AgendaParserService)
class AgendaParserServiceSpec extends Specification {

    def "testCallGr8conf"() {
        expect:
        service.call()

    }
}
