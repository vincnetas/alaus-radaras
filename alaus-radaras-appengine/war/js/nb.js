		var jsonService = new JsonRpc.ServiceProxy("/jsonrpc", {
			asynchronous : true,
			methods : [ 
				"nb.hello", 
				"nb.savePlace", 
				"nb.getPlaceTags",
				"nb.getPlaces"
			]
		});		  
		JsonRpc.setAsynchronous(jsonService, true);
