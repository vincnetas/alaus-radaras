//
//  CodeValue.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/28/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CodeValue : NSObject {
	NSString *code;
	NSString *displayValue; 
}

@property (nonatomic, retain) NSString *code;
@property (nonatomic, retain) NSString *displayValue;

@end
