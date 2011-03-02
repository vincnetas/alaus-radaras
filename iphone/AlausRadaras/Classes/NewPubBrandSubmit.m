    //
//  NewPubBrandSubmit.m
//  AlausRadaras
//
//  Created by Laurynas R on 3/2/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "NewPubBrandSubmit.h"
#import "DataPublisher.h"

@implementation NewPubBrandSubmit

@synthesize msgTextView,submitControl,pubId;

- (void)dealloc {
	[pubId release];
	[submitControl release];
	[msgTextView release];

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
	[msgTextView becomeFirstResponder];
}


- (IBAction) actionSelected {
	switch (self.submitControl.selectedSegmentIndex) {
		case 0:
			[self sendNewPubBrandSubmit];
			self.submitControl.selectedSegmentIndex = -1;
			break;
			
		case 1:
			[self.parentViewController dismissModalViewControllerAnimated:YES];
			break;

		default:
			break;
	}
}


- (void) sendNewPubBrandSubmit {
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
		[dataPublisher submitPubBrand:@"" pub:pubId status:@"NEW_BRAND" message:msgTextView.text];
		
		[self.parentViewController dismissModalViewControllerAnimated:YES];
	}
}



- (void) viewDidDisappear:(BOOL)animated {
	self.submitControl.selectedSegmentIndex = -1;
	msgTextView.text = @"";
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

