//
//  AlausRadarasViewController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "AlausRadarasViewController.h"
#import "Pub.h"
#import "SQLiteManager.h";

@implementation AlausRadarasViewController

@synthesize pintCountLabel;

- (void)dealloc {
//	[mapController release];
	[settingsController release];
	[luckyController release];
	[beerCounterController release];
	[pintCountLabel release];
    [super dealloc];
}

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	/* Application setup */
    [super viewDidLoad];
	UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	self.view.backgroundColor = background;
	[background release];
}

- (void)viewDidAppear:(BOOL)animated {
	/* Get Total Beers from user defaults */
	NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
	beerCount = 0;
	if (standardUserDefaults) {
		beerCount = [standardUserDefaults integerForKey:@"TotalBeers"];
		int currentBeerCount = [standardUserDefaults integerForKey:@"CurrentBeers"];
		beerCount += currentBeerCount;
	}
	pintCountLabel.text = [NSString stringWithFormat:@"%i", beerCount];;
}

-(IBAction) clickPint:(id) sender {
	beerCounterController.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;	
	[self presentModalViewController:beerCounterController animated:YES];
}

-(IBAction) clickBeers:(id) sender {
	BrandsViewController *brandsView = 
		[[BrandsViewController alloc] initWithNibName:nil bundle:nil];
	brandsView.modalTransitionStyle = UIModalTransitionStyleCoverVertical;	
	[self presentModalViewController:brandsView animated:YES];
	[brandsView release];
}

-(IBAction) clickPlaces:(id) sender {
	MapViewController *vietosView = 
			[[MapViewController alloc] initWithNibName:nil bundle:nil];
	[vietosView setPubsOnMap:[[[SQLiteManager sharedManager]getPubs]autorelease]];
	vietosView.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;	
	[self presentModalViewController:vietosView animated:YES];
	[vietosView release];
//	[mapController setPubsOnMap:[[[SQLiteManager sharedManager]getPubs]autorelease]];
//	mapController.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;	
//	[self presentModalViewController:mapController animated:YES];
}

-(IBAction) clickLucky:(id) sender {
	luckyController.modalTransitionStyle = UIModalTransitionStyleCoverVertical;	
	[self presentModalViewController:luckyController animated:YES];
}


-(IBAction) clickSettings:(id) sender {
	settingsController.modalTransitionStyle = UIModalTransitionStylePartialCurl;	
	[self presentModalViewController:settingsController animated:YES];
}


- (void)didReceiveMemoryWarning {
	NSLog(@"AlausRadarasViewController: Recieved a Memory Warning... Oooops..");
	
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}



@end









/*
 // The designated initializer. Override to perform setup that is required before the view is loaded.
 - (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
 self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
 if (self) {
 // Custom initialization
 }
 return self;
 }
 */

/*
 // Implement loadView to create a view hierarchy programmatically, without using a nib.
 - (void)loadView {
 }
 */



/*
 // Override to allow orientations other than the default portrait orientation.
 - (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
 // Return YES for supported orientations
 return (interfaceOrientation == UIInterfaceOrientationPortrait);
 }
 */
