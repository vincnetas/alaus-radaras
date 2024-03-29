//
//  JSONParser.m
//  AlausRadaras
//
//  Created by Laurynas R on 5/3/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "JSONParser.h"
#import "SQLiteManager.h"
#import "SyncManager.h"

@implementation JSONParser

@synthesize response;

- (id)initWithResponse:(NSString*)response {
    if (![super init]) return nil;
    [self setResponse:response];
    return self;
}


- (void) main {
	NSDictionary *results = [response JSONValue];
    
    //1. DELETE
    //1.1. Pubs
    //1.2. Beers
    //1.3. Brands
    
    [[SQLiteManager updateManager] initializeDatabase];        

    //2. UPDATE
    NSArray *update = [results objectForKey:@"update"];
    //2.1. Brands
    NSArray *brands = [update objectForKey:@"brands"];
    NSLog(@"SYNC: Total Brands: %i", [brands count]);
        
    for (NSDictionary *brand in brands){
        [[SQLiteManager updateManager] updateBrand:brand];
	}
    
    //2.2. Beers
    NSArray *beers = [update objectForKey:@"beers"];
    NSLog(@"SYNC: Total Beers: %i", [beers count]);

    for (NSDictionary *beer in beers){
        [[SQLiteManager updateManager] updateBeer:beer];
	}
    
    //2.3. Pubs
    NSArray *pubs = [update objectForKey:@"pubs"];
    NSLog(@"SYNC: Total Pubs: %i", [pubs count]);
    
    for (NSDictionary *pub in pubs){
        NSLog(@"SYNC: Pub [%@]", [pub objectForKey:@"title"]);
        [[SQLiteManager updateManager] updatePub:pub];
    }
    
    NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
	[standardUserDefaults setObject:[NSDate date] forKey:@"LastUpdate"];
    
    [[SyncManager sharedManager]syncSuccessful];
}

@end
