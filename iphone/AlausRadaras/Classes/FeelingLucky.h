//
//  FeelingLucky.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/31/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Brand.h"
#import "Pub.h"

@interface FeelingLucky : NSObject {
	Brand *brand;
	Pub *pub;
}

@property (nonatomic, retain) Brand *brand;
@property (nonatomic, retain) Pub *pub;

@end
