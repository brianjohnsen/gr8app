package org.gr8conf.gr8app

import gr8app.Slot
import groovy.time.*

class IntermissionService {

    Map<String, List<Slot>> getUpcomingSlotsByRoom(Date time = new Date()) {
        Date endTime
        use([groovy.time.TimeCategory]) {
            endTime = (time + 3.hours)
        }
        def restOfDaySlots = Slot.findAllByEndBetweenOrStartBetween(time, endTime, time, endTime)
        def roomMappedSlots = restOfDaySlots.groupBy { it.room }
        roomMappedSlots.each { room, slots ->
            def sortedSlots = slots.sort { it.start }
            ArrayList keepSlots = getTwoEarliestTalksAndAllBreaksInBetween(sortedSlots)
            roomMappedSlots."$room" = keepSlots
        }
        return roomMappedSlots.findAll {it.value}
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

    def calculateRemainingTime(long slotId, now = new Date()) {
        use([groovy.time.TimeCategory]) {

            Slot slot = Slot.get(slotId)
            if (!slot) return 0
            Date end = slot.end

            BigInteger remainderInMinutes = (end - now).toMilliseconds() /(60*1000)

            def adaptedRemainder = Math.max(remainderInMinutes, new BigInteger(0))
            return adaptedRemainder
        }


    }


}
