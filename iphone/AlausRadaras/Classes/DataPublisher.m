//
//  DataPublisher.m
//  AlausRadaras
//
//  Created by Laurynas R on 2/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "DataPublisher.h"

@implementation DataPublisher

- (void) submitPubBrand: (NSString *) brandId pub:(NSString *) pubId status:(NSString *) status message:(NSString *) message {
	NSString *post = 
		[NSString stringWithFormat:
			@"type=pubBrandInfo&status=%@&brandId=%@&pubId=%@&message=%@",
				status, brandId, pubId, message];
	[self postData:post];
}

- (void) postData:(NSString *) params {
	NSData *postData = [params dataUsingEncoding:NSASCIIStringEncoding allowLossyConversion:YES];
	NSString *postLength = [NSString stringWithFormat:@"%d", [params length]];
	
	NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
	[request setURL:[NSURL URLWithString:@"http://alausradaras.lt/android/subtest.php"]];
	[request setHTTPMethod:@"POST"];
	[request setValue:postLength forHTTPHeaderField:@"Content-Length"];
	[request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
	[request setHTTPBody:postData];
	
	//	NSURLConnection *connection =
	[[NSURLConnection alloc] initWithRequest:request delegate:self];	
}

@end
