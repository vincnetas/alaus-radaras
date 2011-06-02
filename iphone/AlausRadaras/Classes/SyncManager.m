//
//  SyncManager.m
//  AlausRadaras
//
//  Created by Laurynas R on 5/3/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SyncManager.h"
#import "JSONParser.h"
@implementation SyncManager

- (void)dealloc {
    [queue release];
    [super dealloc];
}

#pragma mark -
#pragma mark Singleton stuff

static SyncManager *sharedManager = nil;

+ (SyncManager*) sharedManager {
    if (sharedManager == nil) {
        sharedManager = [[super allocWithZone:NULL] init];
    }
    return sharedManager;
}

+ (id)allocWithZone:(NSZone *)zone {
    return [[self sharedManager] retain];
}

- (void) initializeManager {
    //init stuff goes here
}


- (void) doSync: (NSDate *) lastUpdate{
    [MTStatusBarOverlay sharedInstance].animation = MTStatusBarOverlayAnimationShrink;//MTStatusBarOverlayAnimationFallDown;  // MTStatusBarOverlayAnimationShrink
//    overlay.detailViewMode = MTDetailViewModeHistory;         // enable automatic history-tracking and show in detail-view
//    overlay.delegate = self;
//    overlay.progress = 0.0;
    [[MTStatusBarOverlay sharedInstance] postMessage:@"Atnaujinami Duomenys"];
    
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy-MM-dd"];    
    //Optionally for time zone converstions
    [dateFormatter setTimeZone:[NSTimeZone timeZoneWithName:@"..."]];
    
    NSString *stringlastUpdate = [dateFormatter stringFromDate:lastUpdate];
        
	responseData = [[NSMutableData data] retain];
	NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
	[request setURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://www.alausradaras.lt/json?lastUpdate=%@T00:00:00",
                                          stringlastUpdate]]];
	[request setHTTPMethod:@"GET"];
	[request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];	
	[[NSURLConnection alloc] initWithRequest:request delegate:self];
    
    [dateFormatter release];
}

- (void) syncSuccessful {
    NSLog(@"syncSuccessful");
    
    [[MTStatusBarOverlay sharedInstance]  postImmediateFinishMessage:@"Duomenys Atnaujinti!" duration:2.0 animated:YES];
}


#pragma mark -
#pragma mark Request handling stuff


- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
    // release the connection, and the data object
    [connection release];
	
    NSLog(@"Connection failed! Error - %@ %@",
          [error localizedDescription],
          [[error userInfo] objectForKey:NSURLErrorFailingURLStringErrorKey]);
    
    [[MTStatusBarOverlay sharedInstance]  postErrorMessage:@"Nepavyko Atnaujinti Duomenų" duration:2.0 animated:YES];
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    NSLog(@"JSON connectionDidFinishLoading");
	[connection release];
	
	NSString *responseString = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
    
    queue = [[NSOperationQueue alloc] init];
    JSONParser *json = [[JSONParser alloc] initWithResponse:responseString];
    [queue addOperation:json];
    [json release];
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data {
	[responseData appendData:data];
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response {
	[responseData setLength:0];    
}

@end
