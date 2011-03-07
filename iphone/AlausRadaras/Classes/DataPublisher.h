//
//  DataPublisher.h
//  AlausRadaras
//
//  Created by Laurynas R on 2/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface DataPublisher : NSObject {	
	NSMutableDictionary *submits;
	
	NSString *currentBrandId;
	NSString *currentPubId;
}

+ (DataPublisher*) sharedManager;
+ (id)allocWithZone:(NSZone *)zone;

- (void) initializeManager;

- (void) submitPubBrand: (NSString *) brandId pub:(NSString *) pubId status:(NSString *) status message:(NSString *) message validate:(BOOL) validate;
- (void) addSubmittedBrand:  (NSString *) brandId forPub:(NSString *) pubId;
- (void) postData:(NSString *) params;

@end
