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

@synthesize brand, pubId;
@synthesize pubBrandStatusControl, brandLabel, brandImage, sendBtn;

- (void)dealloc {
	[status release];
	[brand release];
	[pubId release];
	
	[pubBrandStatusControl release];
	[brandLabel release];
	[brandImage release];
	[sendBtn release];
	
    [super dealloc];
}

- (void)viewDidLoad {
	/* Application setup */
    [super viewDidLoad];
	UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"black-background.png"]];
	self.view.backgroundColor = background;
	[background release];
	
	sendBtn.enabled = NO;
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
			status = @"EXISTS";
			sendBtn.enabled = YES;
			[self sendPubBrandSubmit];
			break;
			
		case 1:
			status = @"DISCONTINUED";
			sendBtn.enabled = YES;
			[self sendPubBrandSubmit];
			break;
			
		case 2:
			status = @"TEMPORARY_SOLD_OUT";
			sendBtn.enabled = YES;
			[self sendPubBrandSubmit];
			break;
		
		default:
			break;
	}
	self.pubBrandStatusControl.selectedSegmentIndex = -1;
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
		// check if submited in 12 hours?		
		NSString *uniqueIdentifier = [[UIDevice currentDevice] uniqueIdentifier];
		NSLog(@"GUID: %@", uniqueIdentifier);
		
		DataPublisher *dataPublisher = [[DataPublisher alloc]init];
		[dataPublisher submitPubBrand:brand.brandId pub:pubId status:status message:uniqueIdentifier];
		
		[self.parentViewController dismissModalViewControllerAnimated:YES];
	}
}



- (void) viewDidDisappear:(BOOL)animated {
	// Clear everything
	brandLabel.text = @"";
	brandImage.image = nil;
	status = @"";
	sendBtn.enabled = NO;
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
