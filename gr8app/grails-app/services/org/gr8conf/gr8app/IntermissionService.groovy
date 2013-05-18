package org.gr8conf.gr8app

import gr8app.Slot


class IntermissionService {

    Map<String, List<Slot>> getUpcomingSlotsByRoom(Date time) {
        def restOfDaySlots = Slot.findAllByStartBetween(time, (time + 1).clearTime())
        def roomMappedSlots = restOfDaySlots.groupBy { it.room }
        roomMappedSlots.each { room, slots ->
            def sortedSlots = slots.sort { it.start }
            ArrayList keepSlots = getTwoEarliestTalksAndAllBreaksInBetween(sortedSlots)
            roomMappedSlots."$room" = keepSlots
        }
        return roomMappedSlots
    }

    private ArrayList getTwoEarliestTalksAndAllBreaksInBetween(List<Slot> sortedSlots) {
        def keepSlots = []
        for (int i = 0; i < sortedSlots.size(); i++) {
            def slot = sortedSlots[i]
            def talkAmount = keepSlots.findAll { !it.pause }.size()
            if (talkAmount == 2) {
                break
            }
            if (slot.pause) {
                if (talkAmount > 0) {
                    keepSlots << slot
                }
            } else {
                keepSlots << slot
            }
        }
        keepSlots
    }


}
