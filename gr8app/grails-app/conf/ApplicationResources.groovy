modules = {
    application {
        resource url: 'js/application.js'
    }

    'style' {
        resource url: 'css/main.css'
        resource url: 'css/mobile.css'
        resource url: 'less/gr8app.less', attrs: [rel: "stylesheet/less", type: 'css']
    }

    'gr8app-style' {
        dependsOn 'nexa-fonts'
        resource url: 'less/gr8app.less', attrs: [rel: "stylesheet/less", type: 'css']
    }

    'logo-resizer' {
        dependsOn 'jquery'
        resource url: 'js/logo-resizer.js'
    }

    'nexa-fonts' {
        resource url: 'css/NexaWebfontsKit.css'
    }

    'jquery-fittext' {
        resource url:'js/jquery/plugins/jquery.fittext.js'
    }

    'jquery-fullscreen' {
        resource url:'js/jquery/plugins/jquery.fullscreen.js'
    }
}
