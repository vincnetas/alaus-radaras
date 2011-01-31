    //
//  LuckyController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/29/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "LuckyController.h"
#import "TextDatabaseService.h"
#import "PubDetailViewController.h"

@implementation LuckyController

@synthesize feelingLucky;
@synthesize pubLabel, brandLabel, brandImage;

- (void)viewDidLoad {
	NSLog(@"LuckyController viewDidLoad");
	[super viewDidLoad];
	
	UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	self.view.backgroundColor = background;
	[background release];
}

- (IBAction) gotoPreviousView {
	[self.parentViewController dismissModalViewControllerAnimated:YES];	
	feelingLucky = nil;
}

- (void)viewDidAppear:(BOOL)animated {
	NSLog(@"viewDidAppear");
	if (feelingLucky == nil) {
		TextDatabaseService *service = [[TextDatabaseService alloc]init];
		feelingLucky = [service feelingLucky];

		pubLabel.text = feelingLucky.pub.pubTitle;
		brandLabel.text = feelingLucky.brand.label;
		brandImage.image = [UIImage imageNamed:feelingLucky.brand.icon];
		if (brandImage.image == nil) {
			brandImage.image = [UIImage imageNamed:@"brand_default.png"];		
		}
		[service release];
	}
}

- (void)viewDidDisappear:(BOOL)animated {
	NSLog(@"viewDidDisappear");
}


- (IBAction) showPubDetailView {
	PubDetailViewController *pubDetailView = 
		[[PubDetailViewController alloc] initWithNibName:nil bundle:nil];
	
	pubDetailView.currentPub = feelingLucky.pub;	
	pubDetailView.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;	
	[self presentModalViewController:pubDetailView animated:YES];
	[pubDetailView release];
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


- (void)dealloc {
    [super dealloc];
}


@end
