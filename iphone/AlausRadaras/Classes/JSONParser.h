//
//  JSONParser.h
//  AlausRadaras
//
//  Created by Laurynas R on 5/3/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface JSONParser : NSOperation {
    
    NSString *response;
}

@property(retain) NSString *response;

- (id)initWithResponse:(NSString*)response;


//+ (void) parse:(NSString *) response;

@end
