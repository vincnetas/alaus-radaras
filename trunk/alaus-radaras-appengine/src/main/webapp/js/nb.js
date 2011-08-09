		var jsonService = new JsonRpc.ServiceProxy("/jsonrpc", {
			asynchronous : true,
			methods : [ 
				"nb.acPlace", 
			]
		});		  
		JsonRpc.setAsynchronous(jsonService, true);
