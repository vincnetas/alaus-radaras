//
//  PubBrandSubmit.m
//  AlausRadaras
//
//  Created by Laurynas R on 2/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PubBrandSubmit.h"
#import "DataPublisher.h"
#import "LocationManager.h";

@implementation PubBrandSubmit

@synthesize brand, pubId;
@synthesize pubBrandStatusControl, brandLabel, brandImage;

- (void)dealloc {
	[status release];
	[brand release];
	[pubId release];
	
	[pubBrandStatusControl release];
	[brandLabel release];
	[brandImage release];
	
    [super dealloc];
}

- (void)viewDidLoad {
	NSLog(@"PubBrandSubmit viewDidLoad");

	/* Application setup */
    [super viewDidLoad];
	
	/* iOS3.1 Support */
	NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
	enableAllFeatures = [standardUserDefaults boolForKey:@"EnableAllFeatures"];
	UIColor *background;
	if (enableAllFeatures) {
		background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"black-background.png"]];
	} else {
		background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"black-background-small.png"]];
		pubBrandStatusControl.segmentedControlStyle = UISegmentedControlStyleBar;
		
		CGRect rect = CGRectMake(0, 0, 320, 260);
		UIButton *button=[[UIButton alloc] initWithFrame:rect];
		[button setFrame:rect];
		[button setBackgroundColor:[UIColor clearColor]];
		//	[button setBackgroundImage:buttonImageNormal forState:UIControlStateNormal];
		[button setContentMode:UIViewContentModeCenter];
		[button addTarget:self action:@selector(hidePubBrandSubmit:) forControlEvents:UIControlEventTouchUpInside];
		[self.view addSubview:button];
		[button release];
	}
	
	self.view.backgroundColor = background;
	self.view.opaque = NO;
	[background release];

}


-(IBAction) hidePubBrandSubmit:(id) sender {
	if (enableAllFeatures)
	NSLog(@"hidePubBrandSubmit");
	[self.view removeFromSuperview];
	self.view.alpha = 0.0;
	[self viewDidDisappear:NO];
}

- (void) viewDidAppear:(BOOL)animated {
	NSLog(@"PubBrandSubmit viewDidAppear");
	brandLabel.text = brand.label;
	brandImage.image = [UIImage imageNamed:brand.icon];
	if (brandImage.image == nil) {
		brandImage.image = [UIImage imageNamed:@"brand_default.png"];		
	}
	[super viewDidAppear:animated];
}

- (IBAction) pubBrandStatusChanged {
	switch (self.pubBrandStatusControl.selectedSegmentIndex) {
		case 0:
			status = @"EXISTS";
			[self sendPubBrandSubmit];
			break;
			
		case 1:
			status = @"DISCONTINUED";
			[self sendPubBrandSubmit];
			break;
			
		case 2:
			status = @"TEMPORARY_SOLD_OUT";
			[self sendPubBrandSubmit];
			break;
		
		default:
			break;
	}
//	self.pubBrandStatusControl.selectedSegmentIndex = -1;
}

- (void) sendPubBrandSubmit {
	UIAlertView* alertView = 
		[[UIAlertView alloc] initWithTitle:@"Pranešk apie alų"
							   message:nil 
							  delegate:self 
					 cancelButtonTitle:@"Esu blaivas!!"
					 otherButtonTitles:@"Tiek to", nil];
	[alertView show];
	[alertView release];	
}

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
	if(buttonIndex == 0) {
		NSString *uniqueIdentifier = [[UIDevice currentDevice] uniqueIdentifier];
		NSLog(@"GUID: %@\nPub: %@\nBrand: %@\nStatus: %@", uniqueIdentifier, pubId, brand.brandId, status);
		
		BOOL success = 
			[[DataPublisher sharedManager] submitPubBrand:brand.brandId pub:pubId status:status message:uniqueIdentifier validate:YES];
		if (success) {
			NSLog(@"Data can be published");
			CLLocationCoordinate2D coords= [[LocationManager sharedManager]getLocationCoordinates];
			NSString *post = 
				[NSString stringWithFormat:
					@"type=pubBrandInfo&status=%@&brandId=%@&pubId=%@&message=%@&location.latitude=%.8f&location.longitude=%.8f",
					status, brand.brandId, pubId, uniqueIdentifier, coords.latitude, coords.longitude];
			
			[self.parentViewController postData:post msg:@"Tik alus išgelbės mus!"];
		}
	}
	if (enableAllFeatures) {
		[self.parentViewController dismissModalViewControllerAnimated:YES];
	} else {
		[self hidePubBrandSubmit:nil];
	}
}


- (void) viewDidDisappear:(BOOL)animated {
	// Clear everything
	brandLabel.text = @"";
	brandImage.image = nil;
	status = @"";
	self.pubBrandStatusControl.selectedSegmentIndex = -1;
}

- (IBAction) gotoPreviousView {
	[self.parentViewController dismissModalViewControllerAnimated:YES];	
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
