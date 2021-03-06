package org.gr8conf.trackapp

import org.gr8conf.trackapp.Slot
import org.gr8conf.trackapp.Speaker
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONElement

class AgendaParserService {


    void importAgendaData(data = callGr8conf()) {
        data.days.each { jsonDay ->
            findTracks(jsonDay).each { jsonTrack ->
                def trackName = jsonTrack.name
                addSlots(trackName, jsonTrack)
                addBreaks(jsonDay, jsonTrack)
            }
        }
    }


    private addSlots(trackName, jsonTrack) {
        jsonTrack.schedules.each { jsonSchedule ->
            def speakers = jsonSchedule.presentation.speakers.collect { new Speaker(name: it.name, uri: it.uri) }
            def slot = Slot.findByUri(jsonSchedule.presentation.uri)
            if (!slot) {
                String name = stripTalkName(jsonSchedule.presentation.name)
                slot = new Slot(name: name, room: jsonTrack.room, trackName: trackName, start: jsonSchedule.start.toDate(), end: jsonSchedule.end.toDate(), uri: jsonSchedule.presentation.uri)
            } else {
                new ArrayList(slot.speakers ?: []).each { speaker ->
                    slot.removeFromSpeakers(speaker)
                    speaker.delete()
                }
            }
            speakers.each { speaker ->
                slot.addToSpeakers(speaker)
            }
            slot.save(flush: true, failOnError: true)
        }
    }

    private String stripTalkName(String name) {
        name.replace("*) ", "")
    }

    private List findTracks(jsonDay) {
        def tracks = jsonDay.blocks.collect { jsonBlock -> jsonBlock.tracks }.flatten()
        tracks
    }

    private addBreaks(jsonDay, jsonTrack) {
        jsonDay.breaks.each { brk ->
            def uri = "${brk.uri}_${jsonTrack.room}"
            def pause = Slot.findByUri(uri) ?: new Slot(name: brk.title, room: jsonTrack.room, trackName: jsonTrack.name, pause: true, start: brk.start.toDate(), end: brk.end.toDate(), uri: uri)
            pause.save(flush: true, failOnError: true)
        }
    }

    private JSONElement callGr8conf() {
        def url = "http://gr8conf.org/mobile/eu2013/Agenda"
        JSON.parse(new URL(url).getText("UTF-8"))
    }


}
