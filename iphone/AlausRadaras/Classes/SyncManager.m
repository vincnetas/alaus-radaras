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
    [self showSyncInd];
    
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy-MM-dd"];    
    //Optionally for time zone converstions
    [dateFormatter setTimeZone:[NSTimeZone timeZoneWithName:@"..."]];
    
    NSString *stringlastUpdate = [dateFormatter stringFromDate:lastUpdate];
    
    NSLog(@"SYNC: Last Update Date: %@", stringlastUpdate);
    
	responseData = [[NSMutableData data] retain];
	NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
	[request setURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://www.alausradaras.lt/json?lastUpdate=%@T00:00:00",
                                          stringlastUpdate]]];
	[request setHTTPMethod:@"GET"];
	[request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];	
	[[NSURLConnection alloc] initWithRequest:request delegate:self];
    
    [dateFormatter release];
}

- (void) showSyncInd {
	UIView *statusView2 = [[UIView alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 320.0f, 20.0f)];
	[statusView2 setBackgroundColor:[UIColor clearColor]];
	UIImage *image2  = [UIImage imageNamed:@"atnaujinimas.png"];
	UIImageView *statusImg2 = [[UIImageView alloc] initWithImage:image2];
	
	UILabel *statusLabel = [[[UILabel alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 320.0f, 20.0f)] autorelease];
	statusLabel.text = @"Atnaujinami svarbūs duomenys!";
	statusLabel.textColor = [UIColor whiteColor];
	statusLabel.backgroundColor = [UIColor clearColor];
	statusLabel.textAlignment = UITextAlignmentCenter;
	statusLabel.font = [UIFont fontWithName:@"ArialMT" size:14]; 
	
	[statusView2 addSubview:statusImg2];
    
	topWindow = [[UIWindow alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 320.0f, 20.0f)];
    //	[topWindow setBackgroundColor:[UIColor blackColor]];
	[topWindow setAlpha:1.0f];
	[topWindow setWindowLevel:10000.0f];
	[topWindow setHidden:NO];
    
    //   [topWindow addSubview:statusLabel];
	[topWindow addSubview:statusView2];
}


- (void) removeSyncInd {
    [topWindow setHidden:YES];
}



#pragma mark -
#pragma mark Request handling stuff


- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
    // release the connection, and the data object
    [connection release];
	
    NSLog(@"Connection failed! Error - %@ %@",
          [error localizedDescription],
          [[error userInfo] objectForKey:NSURLErrorFailingURLStringErrorKey]);
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
