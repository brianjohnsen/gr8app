package org.gr8conf.gr8app

import gr8app.AgendaParserService
import gr8app.Slot
import grails.converters.JSON

class TalkController {

    IntermissionService intermissionService

    def index() {
        redirect(action: "intermission")
    }

    def counter(long slotId, String room) {
        [slotId: slotId, room: room]
    }

    def slotRemainingTimeAJAX(long slotId) {
        Map slotInformation = [:]
        slotInformation.remaining = intermissionService.calculateRemainingTime(slotId)
        render slotInformation as JSON
    }

    def select() {
        def rooms = Slot.list().groupBy { it.room }.keySet()
        [rooms: rooms]
    }

    def intermission(String room) {
        def date = params.date('date', 'yyyyMMddHHmm') ?: new Date()
        def slotsByRoom = intermissionService.getUpcomingSlotsByRoom(date)
        room = room ?: (slotsByRoom.keySet() as List).first()
        def mainRoomSlots = slotsByRoom."$room"
        def remainingRooms = slotsByRoom.findAll { entry -> entry.key != room && !entry.value?.any { it.offtrack } }
        def offTrack = slotsByRoom.findAll { entry -> entry.value?.any { it.offtrack } }
        [mainRoom: room, mainRoomSlots: mainRoomSlots, remainingRooms: remainingRooms, offTrack: offTrack, upcomingSlotId: mainRoomSlots ? mainRoomSlots[0].id : -1]
    }

}
