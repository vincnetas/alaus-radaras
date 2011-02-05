    //
//  LuckyController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/29/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "LuckyController.h"
#import "PubDetailViewController.h"
#import "AlausRadarasAppDelegate.h"

@implementation LuckyController

@synthesize feelingLucky;
@synthesize pubLabel, brandLabel, brandImage;


- (void)dealloc {
	[feelingLucky release];
	[pubLabel release];
	[brandLabel release];
	[brandImage release];
    [super dealloc];
}

- (void)viewDidLoad {
	[super viewDidLoad];
	
	UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	self.view.backgroundColor = background;
	[background release];
	
	NSLog(@"LuckyController viewDidLoad");
}

- (IBAction) gotoPreviousView {
	[self.parentViewController dismissModalViewControllerAnimated:YES];	
	feelingLucky = nil;
}

- (void)viewDidAppear:(BOOL)animated {

	if (feelingLucky == nil){	
		AlausRadarasAppDelegate *appDelegate = (AlausRadarasAppDelegate *)[[UIApplication sharedApplication] delegate];		
		feelingLucky = [appDelegate feelingLucky];
		
		pubId = [feelingLucky.pub.pubId copy];
		pubLabel.text = feelingLucky.pub.pubTitle;
		brandLabel.text = feelingLucky.brand.label;
		brandImage.image = [UIImage imageNamed:feelingLucky.brand.icon];
		if (brandImage.image == nil) {
			brandImage.image = [UIImage imageNamed:@"brand_default.png"];		
		}
	}	
}

- (void) viewDidDisappear:(BOOL)animated {
	NSLog(@"viewDidDisappear");
}


- (IBAction) showPubDetailView {
	PubDetailViewController *pubDetailView = 
		[[PubDetailViewController alloc] initWithNibName:nil bundle:nil];
	
	AlausRadarasAppDelegate *appDelegate = (AlausRadarasAppDelegate *)[[UIApplication sharedApplication] delegate];

	pubDetailView.currentPub =  [appDelegate getPubById:pubId];//feelingLucky.pub;
	
	pubDetailView.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;	
	[self presentModalViewController:pubDetailView animated:YES];
	//[pubDetailView release];
}

- (void)didReceiveMemoryWarning {
	NSLog(@"LuckyController didReceiveMemoryWarning");
	
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
