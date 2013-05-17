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

        days.each {d->
            def day = new Day(start: Date.parseJSON(d.start), end: Date.parseJSON(d.end))
            println day
            def tracks = d.blocks.collect{b-> b.tracks}
            println tracks.size()
            tracks.each {t-> println t.name}
        }

    }
}
