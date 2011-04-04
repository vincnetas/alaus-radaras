//
//  DataPublisher.m
//  AlausRadaras
//
//  Created by Laurynas R on 2/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "DataPublisher.h"
#import "SQLiteManager.h"

@implementation DataPublisher

- (void)dealloc {
	[submits release];
//	[currentBrandId release];
//	[currentPubId release];
    [super dealloc];
}

#pragma mark -
#pragma mark Singleton stuff

static DataPublisher *sharedManager = nil;

+ (DataPublisher*) sharedManager {
    if (sharedManager == nil) {
        sharedManager = [[super allocWithZone:NULL] init];
    }
    return sharedManager;
}

+ (id)allocWithZone:(NSZone *)zone {
    return [[self sharedManager] retain];
}

- (void) initializeManager {
	submits = [[NSMutableDictionary alloc]init];
}

#pragma mark -
#pragma mark Methods


- (BOOL) submitPubBrand: (NSString *) brandId 
					pub:(NSString *) pubId 
				 status:(NSString *) status 
				message:(NSString *) message 
			   validate:(BOOL) validate {
//	NSString *post = 
//		[NSString stringWithFormat:
//			@"type=pubBrandInfo&status=%@&brandId=%@&pubId=%@&message=%@",
//				status, brandId, pubId, message];
//	
	currentBrandId = [brandId copy];
	currentPubId = [pubId copy];
	
	if (validate) {
		NSMutableDictionary *pubBrands = [submits objectForKey:currentPubId];
		
		if (pubBrands != nil) {
			NSDate *lastSubmitTime = [pubBrands objectForKey:currentBrandId];
			
			if (lastSubmitTime != nil) {
				//pub already has this brand submited, checking time
				NSDate *now = [[NSDate alloc]init];
				double delta = [now timeIntervalSinceDate:lastSubmitTime];
				
				NSLog(@"Time since last submit: %g",delta);
				
				if (delta < 600) {
					// Too soon
					NSLog(@"Sorry, too soon");
					
					NSString *brandName = [[SQLiteManager sharedManager] getBrandsLabelById:currentBrandId];
					
					UIAlertView *alert = [[UIAlertView alloc] 
							initWithTitle:[NSString stringWithFormat:@"Ar tikrai blaivas?\nApie %@ alų jau pranešei ☺", brandName] 
							message: @""
							delegate:self 
							cancelButtonTitle:@"Oj.." 
							otherButtonTitles:nil, nil];
					[alert show];
					[alert release];
					[brandName release];
					
					return NO;
				}
				[now release];
			}
			[lastSubmitTime release];
		}
	//	[pubBrands release];
	}
	return YES;
//	[self postData:post];
}


- (void) addSubmittedBrand {
	NSLog(@"+addSubmittedBrand: %@, Pub: %@",currentBrandId, currentPubId);
	NSMutableDictionary *pubBrands = [submits objectForKey:currentPubId];
	NSLog(@"+2");
	if (pubBrands == nil) {
		pubBrands = [[NSMutableDictionary alloc]init];
	}
	NSLog(@"+3");
	NSDate *now = [[NSDate alloc]init];
	[pubBrands setObject:now forKey:currentBrandId];
	NSLog(@"+4");

	[submits setObject:pubBrands forKey:currentPubId];
	
//	[pubBrands release];
	NSLog(@"-addSubmittedBrand");
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
	
	[[NSURLConnection alloc] initWithRequest:request delegate:self];
}

#pragma mark -
#pragma mark NSURLConnection stuff

//- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
//    // release the connection, and the data object
//    [connection release];
//    // receivedData is declared as a method instance elsewhere
//    
//	currentBrandId = @"";
//	currentPubId = @"";
//
//    // inform the user
//    NSLog(@"Connection failed! Error - %@ %@",
//          [error localizedDescription],
//          [[error userInfo] objectForKey:NSURLErrorFailingURLStringErrorKey]);
//	
//	//Connection error occured
//	UIAlertView* alertView = 
//		[[UIAlertView alloc] initWithTitle:@"Nepavyko nusiųsti... gal dar alaus?"
//							   message:nil 
//							  delegate:self 
//					 cancelButtonTitle:@"Meginsiu vėliau"
//					 otherButtonTitles:nil];
//	[alertView show];
//	[alertView release];
//}
//
//- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
//    NSLog(@"Pub Brands successfully sent");
//	
//	//TODO thank user for sending data
//
//	NSMutableDictionary *pubBrands = [submits objectForKey:currentPubId];
//	NSLog(@"Submital records: %i", [submits count]);
//	 
//	if (pubBrands == nil) {
//		pubBrands = [[NSMutableDictionary alloc]init];
//	}
//	[pubBrands setObject:[[NSDate alloc]init] forKey:currentBrandId];
//	[submits setObject:pubBrands forKey:currentPubId];
//		
//	currentBrandId = @"";
//	currentPubId = @"";
//	 
//    // release the connection, and the data object
//	[pubBrands release];
//    [connection release];
//}

@end
