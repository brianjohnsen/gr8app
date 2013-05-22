import gr8app.AgendaParserService
import gr8app.StringMetaHelper
import grails.converters.JSON
import grails.util.Environment

class BootStrap {

    AgendaParserService agendaParserService

    def init = { servletContext ->
        StringMetaHelper.addToDateToStringMetaClass()
        if (Environment.current == Environment.DEVELOPMENT) {
            def data = JSON.parse(new File("data/gr8.json").text)
            agendaParserService.importAgendaData(data)
        } else {
            agendaParserService.importAgendaData()
        }

    }
    def destroy = {
    }
}
