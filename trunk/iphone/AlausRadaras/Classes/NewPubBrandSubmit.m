//
//  NewPubBrandSubmit.m
//  AlausRadaras
//
//  Created by Laurynas R on 3/2/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "NewPubBrandSubmit.h"
#import "DataPublisher.h"
#import "LocationManager.h";

@implementation NewPubBrandSubmit

@synthesize msgTextView, submitBtn, pubId;

- (void)dealloc {
	[pubId release];
	[msgTextView release];
	[submitBtn release];
    [super dealloc];
}

- (void)viewDidLoad {
	 /* Application setup */
	 [super viewDidLoad];
	 UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	 self.view.backgroundColor = background;
	 [background release];

	 msgTextView.delegate = self;
	 
	 [submitBtn setEnabled:NO];
}

- (void)viewWillAppear:(BOOL)animated {
	[msgTextView becomeFirstResponder];
	[super viewDidAppear:animated];
}

- (void)textViewDidChange:(UITextView *)textView {
	if (msgTextView.text.length > 0) {
		[submitBtn setEnabled:YES];
	} else {
		[submitBtn setEnabled:NO];
	}
}

- (IBAction) sendNewPubBrandSubmit {
	UIAlertView* alertView = 
	[[UIAlertView alloc] initWithTitle:@"Pranešk apie alų"
							   message:nil 
							  delegate:self 
					 cancelButtonTitle:@"Tiek to"
					 otherButtonTitles:@"Esu blaivas!!", nil];
	[alertView show];
	[alertView release];	
}

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
	if(buttonIndex == 1) {
		// check if submited in 12 hours?		
		NSString *uniqueIdentifier = [[UIDevice currentDevice] uniqueIdentifier];
		
//		DataPublisher *dataPublisher = [[DataPublisher alloc]init];
		BOOL success = [[DataPublisher sharedManager] submitPubBrand:@"" 
												  pub:pubId 
											   status:@"NEW_BRAND" 
											  message:@""
											 validate:NO];
	
		if (success) {
			NSLog(@"Data can be published");
			CLLocationCoordinate2D coords = [[LocationManager sharedManager]getLocationCoordinates];
			NSString *post = 
				[NSString stringWithFormat:
					@"type=pubBrandInfo&status=NEW_BRAND&brandId=NEW_BRAND&pubId=%@&message=%@&location.latitude=%.8f&location.longitude=%.8f",
					pubId, [NSString stringWithFormat:@"UID: %@ Message: %@", uniqueIdentifier, msgTextView.text], coords.latitude, coords.longitude];
			
			[self.parentViewController postData:post msg:@"+500 taškų už pilietiškumą. Dėkui! :)"];
		}

		msgTextView.text = @"";
		[submitBtn setEnabled:NO];

		[self.parentViewController dismissModalViewControllerAnimated:YES];
	}
}



- (IBAction) gotoPreviousView {
	[self.parentViewController dismissModalViewControllerAnimated:YES];	
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation) interfaceOrientation {
	if (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown) {
		return YES;	
	} else {
		return NO;
	}
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

