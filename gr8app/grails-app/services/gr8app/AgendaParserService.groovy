package gr8app

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONElement

class AgendaParserService {


    void importAgendaData() {
        //FIXME: move to bootstrap
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
                findBreaks(jsonDay, jsonTrack).each { brk ->
                    track.addToSlots(brk)
                }
                day.addToTracks(track)
            }
            day.save(flush: true)
        }
    }

    private Track createTrackAndAddSlots(jsonTrack) {
        def track = new Track(name: jsonTrack.name)
        jsonTrack.schedules.each { jsonSchedule ->
            def speakers = jsonSchedule.presentation.speakers.collect { it.name }
            def slot = new Slot(name: jsonSchedule.presentation.name, room: jsonTrack.room, start: jsonSchedule.start.toDate(), end: jsonSchedule.end.toDate())
            speakers.each {speaker->
                slot.addToSpeakers(name: speaker)
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
        jsonDay.breaks.collect { brk -> new Slot(name: brk.title, room: jsonTrack.room, pause: true, start: brk.start.toDate(), end: brk.end.toDate()) }
    }

    private JSONElement callGr8conf() {
        def url = "http://gr8conf.org/mobile/eu2013/Agenda"
        def data = JSON.parse(new URL(url).text)
        data
    }
}
