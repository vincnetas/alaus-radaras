//
//  PubAnnotation.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/25/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PubAnnotation.h"


@implementation PubAnnotation

@synthesize coordinate;
@synthesize title;
@synthesize subtitle;
@synthesize pubId;

-init {
	return self;
}

-initWithCoordinate:(CLLocationCoordinate2D)inCoord {
	coordinate = inCoord;
	return self;
}

@end
