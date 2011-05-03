//
//  JSONParser.m
//  AlausRadaras
//
//  Created by Laurynas R on 5/3/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "JSONParser.h"
#import "SQLiteManager.h"

@implementation JSONParser

+ (void) parse:(NSString *) response {
	NSDictionary *results = [response JSONValue];
    
    //1. DELETE
    //1.1. Pubs
    //1.2. Beers
    //1.3. Brands
    
    //2. UPDATE
    NSArray *update = [results objectForKey:@"update"];
    //2.1. Brands
    NSArray *brands = [update objectForKey:@"brands"];
    NSLog(@"UPDATE: Sync Brands: %i", [brands count]);
    
    for (NSDictionary *brand in brands){
       // [[SQLiteManager sharedManager] getBrandsLocationBased];
        NSLog(@"%@\n", [brand objectForKey:@"title"]);
	}
    
    //2.2. Beers
    NSArray *beers = [update objectForKey:@"beers"];
    NSLog(@"UPDATE: Sync Beers: %i", [beers count]);

    for (NSDictionary *beer in beers){
        //	NSLog(@"%@\n", [brand objectForKey:@"title"]);
	}
    
    
    //2.3. Pubs
    NSArray *pubs = [update objectForKey:@"pubs"];
    NSLog(@"UPDATE: Sync Pubs: %i", [pubs count]);
    
    for (NSDictionary *pub in pubs){
        //	NSLog(@"%@\n", [brand objectForKey:@"title"]);
	}
    
    


    
}

@end
