package gr8app

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONElement

class AgendaParserService {


    void importAgendaData(data = callGr8conf()) {
        //FIXME: move to bootstrap
        String.metaClass.'static'.toDate = {
            def d = Date.parse("yyyy-MM-dd'T'HH:mm:ss", delegate)
            d.hours += 2
            d
        }

        data.days.each { jsonDay ->
            def day = Day.findByDay(jsonDay.day) ?: new Day(start: jsonDay.start.toDate(), end: jsonDay.end.toDate(), day: jsonDay.day).save(flush: true, failOnError: true)
            findTracks(jsonDay).each { jsonTrack ->
                def track = Track.findByUri(jsonTrack.uri) ?: new Track(name: jsonTrack.name, uri: jsonTrack.uri)
                addSlots(track, jsonTrack)
                findBreaks(jsonDay, jsonTrack).each { brk ->
                    track.addToSlots(brk)
                }
                day.addToTracks(track)
            }
            day.save(flush: true)
        }
    }


    private Track addSlots(track, jsonTrack) {
        jsonTrack.schedules.each { jsonSchedule ->
            def speakers = jsonSchedule.presentation.speakers.collect { new Speaker(name: it.name, uri: it.uri) }
            def slot = Slot.findByUri(jsonSchedule.presentation.uri) ?: new Slot(name: jsonSchedule.presentation.name, room: jsonTrack.room, start: jsonSchedule.start.toDate(), end: jsonSchedule.end.toDate(), uri: jsonSchedule.presentation.uri)
            speakers.each { speaker ->
                slot.addToSpeakers(speaker)
            }
            track.addToSlots(slot)
        }
        track
    }

    private List findTracks(jsonDay) {
        def tracks = jsonDay.blocks.collect { jsonBlock -> jsonBlock.tracks }.flatten()
        tracks
    }

    private List<Slot> findBreaks(jsonDay, jsonTrack) {
        jsonDay.breaks.collect { brk -> new Slot(name: brk.title, room: jsonTrack.room, pause: true, start: brk.start.toDate(), end: brk.end.toDate(), uri: brk.uri) }
    }

    private JSONElement callGr8conf() {
        def url = "http://gr8conf.org/mobile/eu2013/Agenda"
        def data = JSON.parse(new URL(url).text)
        data
    }


}
