//
//  PlaceTableCell.m
//  AlausRadaras
//
//  Created by Laurynas R on 9/15/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PlaceTableCell.h"


@implementation PlaceTableCell

@synthesize titleText, addressText, distanceText, icon;

- (void)dealloc {
	[titleText release];
	[addressText release];
	[distanceText release];
    
    [icon release];
    
    [super dealloc];
}

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc. that aren't in use.
}

- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


@end
