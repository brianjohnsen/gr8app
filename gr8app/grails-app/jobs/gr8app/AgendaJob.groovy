package gr8app



class AgendaJob {

    AgendaParserService agendaParserService

    static triggers = {
        simple name: 'TriggerAgendaParser', startDelay: 60000, repeatInterval: 60000
    }

    def execute() {
        println "Calling agendaParserService..."
        agendaParserService.importAgendaData()
    }
}
