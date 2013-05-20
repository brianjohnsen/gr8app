package gr8app

import grails.converters.JSON

class AgendaParserService {

    def call() {
        Date.metaClass.'static'.parseJSON = { String date ->
            Date.parse("yyyy-MM-dd'T'HH:mm:ss", date)
        }



        def url = "http://gr8conf.org/mobile/eu2013/Agenda"
        def data = JSON.parse(new URL(url).text)
//        println data.toString(2)
        def days = data.days

        days.each { d ->
            def day = new Day(start: Date.parseJSON(d.start), end: Date.parseJSON(d.end)).save(flush: true, failOnError: true)
            def tracks = d.blocks.collect { b -> b.tracks }.flatten()
            tracks.each { t ->
                def track = new Track(name: t.name)
                t.schedules.each {s->
                    def speakers = s.presentation.speakers.collect{it.name}
                    def slot = new Slot(name: s.presentation.name, speakers: speakers, start: Date.parseJSON(s.start), end: Date.parseJSON(s.end), room: t.room)
                    track.addToSlots(slot)
                }
                day.addToTracks(track)
            }
            println day
        }

    }
}
