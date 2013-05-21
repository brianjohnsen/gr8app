package gr8app

class Day {

    String day //identifier
    Date start
    Date end

    static hasMany = [tracks: Track]

    static constraints = {
        start()
        end()
    }


    @Override
    String toString() {
        def date = start.format('yyyy-MM-dd')
        def s = start.format('HH:mm')
        def e = end.format('HH:mm')
        "$date $s to $e"
    }
}
