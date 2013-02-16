package gr8app

import grails.converters.JSON

class AgendaParserService {

    def call() {
        def url = "http://gr8conf.org/mobile/eu2012/Agenda"
        def data = JSON.parse( new URL( url ).text )
        println data
        println data.days[1].blocks.size()

    }
}
