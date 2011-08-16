		var jsonService = new JsonRpc.ServiceProxy("/jsonrpc", {
			asynchronous : true,
			methods : [ 
				"nb.acPlace",
				"nb.acBeer"
			]
		});		  
		JsonRpc.setAsynchronous(jsonService, true);
