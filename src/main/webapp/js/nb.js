		var jsonService = new JsonRpc.ServiceProxy("http://www.alausradaras.lt/jsonrpc", {
			asynchronous : true,
			methods : [ 
				"nb.acPlace",
				"nb.acBeer"
			]
		});		  
		JsonRpc.setAsynchronous(jsonService, true);
