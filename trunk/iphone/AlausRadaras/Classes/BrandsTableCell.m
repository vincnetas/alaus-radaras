//
//  BrandsTableCell.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/24/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "BrandsTableCell.h"

@implementation BrandsTableCell

@synthesize labelText, label2Text, brandIcon, iconSmall;

- (void)dealloc {
	[labelText release];
	[label2Text release];
	[brandIcon release];
	[iconSmall release];
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
