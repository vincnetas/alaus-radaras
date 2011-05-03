//
//  SyncManager.m
//  AlausRadaras
//
//  Created by Laurynas R on 5/3/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SyncManager.h"

@implementation SyncManager

- (void)dealloc {
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
    
    //	NSString *params = 
    //		[NSString stringWithFormat:
    //		 @"lastUpdate=2011-04-01"];
    //	NSData *postData = [params dataUsingEncoding:NSASCIIStringEncoding allowLossyConversion:YES];
    //	NSString *postLength = [NSString stringWithFormat:@"%d", [params length]];
    
	UIView *statusView = [[UIView alloc] initWithFrame:CGRectMake(290.0f, 0.0f, 30.0f, 20.0f)];
	[statusView setBackgroundColor:[UIColor redColor]];
	UIImage *image  = [UIImage imageNamed:@"atnaujinimasStatus.png"];
	UIImageView *statusImg = [[UIImageView alloc] initWithImage:image];
    
	UIView *statusView2 = [[UIView alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 320.0f, 20.0f)];
	[statusView2 setBackgroundColor:[UIColor redColor]];
	UIImage *image2  = [UIImage imageNamed:@"atnaujinimas.png"];
	UIImageView *statusImg2 = [[UIImageView alloc] initWithImage:image2];
	
	UILabel *statusLabel = [[[UILabel alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 320.0f, 20.0f)] autorelease];
	statusLabel.text = @"Atnaujinami svarbūs duomenys!";
	statusLabel.textColor = [UIColor whiteColor];
	statusLabel.backgroundColor = [UIColor clearColor];
	statusLabel.textAlignment = UITextAlignmentCenter;
	statusLabel.font = [UIFont fontWithName:@"ArialMT" size:14]; 
	
	[statusView2 addSubview:statusImg2];	
	[statusView addSubview:statusImg];	
    
	topWindow = [[UIWindow alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 320.0f, 20.0f)];
	[topWindow setBackgroundColor:[UIColor blackColor]];
	[topWindow setAlpha:1.0f];
	[topWindow setWindowLevel:10000.0f];
	[topWindow setHidden:NO];
    
    //	[topWindow addSubview:statusLabel];
	[topWindow addSubview:statusView];
	[topWindow addSubview:statusView2];
    
	responseData = [[NSMutableData data] retain];
    
	
	NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
	[request setURL:[NSURL URLWithString:@"http://www.alausradaras.lt/json?lastUpdate=2011-04-01"]];
	[request setHTTPMethod:@"GET"];
    //	[request setValue:postLength forHTTPHeaderField:@"Content-Length"];
	[request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    //	[request setHTTPBody:postData];
	
	[[NSURLConnection alloc] initWithRequest:request delegate:self];
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
    //	[responseData release];
	
	NSDictionary *results = [responseString JSONValue];
	NSArray *brands = [[results objectForKey:@"update"] objectForKey:@"brands"];
    
	NSLog(@"VISO: %i", [brands count]);
	
	for (NSDictionary *brand in brands){		
		NSLog(@"%@\n", [brand objectForKey:@"title"]);
	}
	
//	[topWindow setHidden:YES];
    
	//NSLog(@"%@",responseString);
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data {
    NSLog(@"JSON didReceiveData: %i", 	[data length]);	
	[responseData appendData:data];
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response {
	NSLog(@"JSON didReceiveResponse");	
	[responseData setLength:0];
    
}

@end
