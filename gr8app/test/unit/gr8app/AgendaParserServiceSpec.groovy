package gr8app

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(AgendaParserService)
class AgendaParserServiceSpec extends Specification {

    def setup(){
    }

    def "testCallGr8conf"() {
        expect:
        service.call()

    }
}
