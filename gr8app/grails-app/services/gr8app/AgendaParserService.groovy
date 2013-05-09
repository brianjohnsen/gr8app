package gr8app

import grails.converters.JSON

class AgendaParserService {

    def call() {
        def url = "http://gr8conf.org/mobile/eu2013/Agenda"
        def data = JSON.parse( new URL( url ).text )
        println data
        def days = data.days

        println "day0 = ${days[0]}"

        days.each {
            new Day()
        }

    }
}
