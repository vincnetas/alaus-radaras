//
//  Brand.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/22/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Beer : NSObject {
	NSString *id;
	NSString *icon; 
	NSString *label;
}

@property (nonatomic, retain) NSString *id;
@property (nonatomic, retain) NSString *icon;
@property (nonatomic, retain) NSString *label;

@end
