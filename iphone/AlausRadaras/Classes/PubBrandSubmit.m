//
//  PubBrandSubmit.m
//  AlausRadaras
//
//  Created by Laurynas R on 2/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PubBrandSubmit.h"
#import "DataPublisher.h"

@implementation PubBrandSubmit

@synthesize brand, pubId, pubBrandStatusControl, brandLabel, brandImage;

- (void)dealloc {
	[brand release];
	[pubId release];
	
	[pubBrandStatusControl release];
	[brandLabel release];
	[brandImage release];
    [super dealloc];
}

- (void)viewDidLoad {
	/* Application setup */
    [super viewDidLoad];
	UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"black-background.png"]];
	self.view.backgroundColor = background;
	[background release];
}

- (void) viewDidAppear:(BOOL)animated {
	brandLabel.text = brand.label;
	brandImage.image = [UIImage imageNamed:brand.icon];
	if (brandImage.image == nil) {
		brandImage.image = [UIImage imageNamed:@"brand_default.png"];		
	}
}

- (IBAction) pubBrandStatusChanged {
	switch (self.pubBrandStatusControl.selectedSegmentIndex) {
		case 0:
			
			break;
		case 1:

			break;
		case 2:
			
			break;
		
		default:
			break;
	}
}


- (IBAction) sendPubBrandSubmit: (id)sender {
	DataPublisher *dataPublisher = [[DataPublisher alloc]init];
	[dataPublisher submitPubBrand:brand.brandId pub:pubId status:@"iPhoneTestStatus" message:@""];
}

- (void) viewDidDisappear:(BOOL)animated {

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
