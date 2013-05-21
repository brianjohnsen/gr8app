class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

//		"/"(view:"/index")
		"/"(controller: "talk", action: "select")
		"500"(view:'/error')
	}
}
