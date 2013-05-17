package gr8app

import grails.converters.JSON

class AgendaParserService {

    def call() {
        Date.metaClass.'static'.parseJSON = {String date->
            Date.parse("yyyy-MM-dd'T'HH:mm:ss", date)
        }



        def url = "http://gr8conf.org/mobile/eu2013/Agenda"
        def data = JSON.parse( new URL( url ).text )
//        println data.toString(2)
        def days = data.days

        println "day0= ${days[0]}"

        days.each {
//            println it.start//.toString(2)
            def day = new Day(start: Date.parseJSON(it.start), end: Date.parseJSON(it.end))
            println day
        }

    }
}
