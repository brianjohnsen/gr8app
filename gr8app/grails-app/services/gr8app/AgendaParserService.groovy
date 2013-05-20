package gr8app

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONElement

class AgendaParserService {


    def call() {
        String.metaClass.'static'.toDate = {
            def d = Date.parse("yyyy-MM-dd'T'HH:mm:ss", delegate)
            d.hours += 2
            d
        }

        def data = callGr8conf()
        data.days.each { jsonDay ->
            def day = new Day(start: jsonDay.start.toDate(), end: jsonDay.end.toDate()).save(flush: true, failOnError: true)
            findTracks(jsonDay).each { jsonTrack ->
                def track = createTrackAndAddSlots(jsonTrack)
                day.addToTracks(track)
            }
            println day
        }

    }

    private Track createTrackAndAddSlots(jsonTrack) {
        def track = new Track(name: jsonTrack.name, room: jsonTrack.room)
        jsonTrack.schedules.each { jsonSchedule ->
            def speakers = jsonSchedule.presentation.speakers.collect { it.name }
            def slot = new Slot(name: jsonSchedule.presentation.name, speakers: speakers, start: jsonSchedule.start.toDate(), end: jsonSchedule.end.toDate())
            track.addToSlots(slot)
        }
        track
    }

    private List findTracks(jsonDay) {
        def tracks = jsonDay.blocks.collect { jsonBlock -> jsonBlock.tracks }.flatten()
        tracks
    }

    private JSONElement callGr8conf() {
        def url = "http://gr8conf.org/mobile/eu2013/Agenda"
        def data = JSON.parse(new URL(url).text)
        data
    }
}
