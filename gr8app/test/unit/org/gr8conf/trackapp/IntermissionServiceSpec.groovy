package org.gr8conf.trackapp

import org.gr8conf.trackapp.Slot

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(IntermissionService)
@Mock([Slot])
class IntermissionServiceSpec extends Specification {

    def setup() {
        createAndSaveSlot("Aud1", new Date("5/22-2013 13:55"), new Date("5/22-2013 14:00"), 1, true)
        createAndSaveSlot("Aud1", new Date("5/22-2013 14:00"), new Date("5/22-2013 14:55"), 1)
        createAndSaveSlot("Aud1", new Date("5/22-2013 14:55"), new Date("5/22-2013 15:00"), 1, true)
        createAndSaveSlot("Aud1", new Date("5/22-2013 15:00"), new Date("5/22-2013 15:55"), 2)
        createAndSaveSlot("Aud1", new Date("5/22-2013 15:55"), new Date("5/22-2013 16:00"), 2, true)
        createAndSaveSlot("Aud1", new Date("5/22-2013 16:00"), new Date("5/22-2013 16:55"), 3)
        createAndSaveSlot("Aud2", new Date("5/22-2013 14:00"), new Date("5/22-2013 14:55"), 4)
        createAndSaveSlot("Aud2", new Date("5/22-2013 14:55"), new Date("5/22-2013 15:00"), 4, true)
        createAndSaveSlot("Aud2", new Date("5/22-2013 15:00"), new Date("5/22-2013 15:55"), 5)
        createAndSaveSlot("Aud2", new Date("5/22-2013 15:55"), new Date("5/22-2013 16:00"), 5, true)
        createAndSaveSlot("Aud2", new Date("5/22-2013 16:00"), new Date("5/22-2013 16:55"), 6)
    }

    def "get upcoming slots returns the next 2 talks for all rooms"() {
        when:
        Map upcomingSlots = service.getUpcomingSlotsByRoom(new Date("5/22-2013 14:00"))

        then:
        upcomingSlots
        upcomingSlots.size() == 2
        upcomingSlots."Aud1".size() == 3
        upcomingSlots."Aud2".size() == 3
        upcomingSlots.values().flatten().findAll { it.pause }.size() == 2
    }

    def "calculateRemainder returns 0 when past ending time and remaining time in minutes otherwise"() {
        expect:
        service.calculateRemainingTime(1, new Date("5/22-2013 14:55")) == 0

        where:
        slotId | date              | result
        1      | "5/22-2013 14:55" | 0
        1      | "5/22-2013 13:55" | 5
        1      | "5/22-2013 12:55" | 65
        -1     | "5/22-2013 12:55" | 0

    }

    def void createAndSaveSlot(String room, Date start, Date end, int index, pause = false) {
        Slot slot = new Slot(trackName: "sometrack", room: room, start: start, end: end, name: pause ? "pause${index}" : "talk${index}", speaker: pause ? "" : "speaker${index}", pause: pause, uri: "uri$index")
        slot.save(failOnError: true, flush: true)
    }

}
