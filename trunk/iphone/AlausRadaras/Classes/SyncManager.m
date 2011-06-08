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


- (void) doSync {
    if (!syncInProgressInd) {
        NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
        
        NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
        [dateFormat setDateFormat:@"yyyy-MM-dd"];
        //Optionally for time zone converstions
        [dateFormat setTimeZone:[NSTimeZone timeZoneWithName:@"..."]];

        NSDate *lastUpdate = [standardUserDefaults objectForKey:@"LastUpdate"];
        NSDate *now = [NSDate date];
        
        NSLog(@"SYNC: Last Update Date: %@, Now is: %@", [dateFormat stringFromDate:lastUpdate], [dateFormat stringFromDate:now]);
        
        if (lastUpdate == nil) {
            // Default date to app release date
            // Update on Database update
            lastUpdate =  [dateFormat dateFromString:@"2011-05-22"];
        }
        
        if (![[dateFormat stringFromDate:lastUpdate]isEqualToString:[dateFormat stringFromDate:now]]) {

            /* ############ DO SYNC ############ */

            syncInProgressInd = YES;
            NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
            newAPI = [standardUserDefaults boolForKey:@"EnableAllFeatures"];

            if (newAPI) {
                [MTStatusBarOverlay sharedInstance].animation = MTStatusBarOverlayAnimationShrink;
                [[MTStatusBarOverlay sharedInstance] postMessage:@"Atnaujinami Duomenys"];
            } else {
                //oldschool
                [UIApplication sharedApplication].networkActivityIndicatorVisible = YES;
            }
            
            
            NSString *stringlastUpdate = [dateFormat stringFromDate:lastUpdate];
                
            responseData = [[NSMutableData data] retain];
            NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
            [request setURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://www.alausradaras.lt/json?lastUpdate=%@T00:00:00",
                                                  stringlastUpdate]]];
            [request setHTTPMethod:@"GET"];
            [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];	
            [[NSURLConnection alloc] initWithRequest:request delegate:self];
        }
        [dateFormat release];
    }
}

- (void) syncSuccessful {
    NSLog(@"syncSuccessful");
    if (newAPI) {
        [[MTStatusBarOverlay sharedInstance]  postImmediateFinishMessage:@"Duomenys Atnaujinti!" duration:2.0 animated:YES];
    } else {
        [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
    }
    syncInProgressInd = NO;
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
    syncInProgressInd = NO;
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



- (void) setEnableAllFeatures:(BOOL)value {
	newAPI = value;
}
@end
