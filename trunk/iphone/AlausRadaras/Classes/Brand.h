//
//  Brand.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/22/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Brand : NSObject {
	NSString *brandId;
	NSString *icon; 
	NSString *label;
	NSString *pubsString;
    NSMutableArray *tags;
    NSString *tagsAsString;
}

@property (nonatomic, retain) NSString *brandId;
@property (nonatomic, retain) NSString *icon;
@property (nonatomic, retain) NSString *label;
@property (nonatomic, retain) NSString *pubsString;
@property (nonatomic, retain) NSString *tagsAsString;

@end
