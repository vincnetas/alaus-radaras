//
//  AlausRadarasViewController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "AlausRadarasViewController.h"
#import "Pub.h"
#import "TextDatabaseService.h"

@implementation AlausRadarasViewController

@synthesize pintCountLabel;

- (void)dealloc {
	[luckyController release];
	[beerCounterController release];
	[pintCountLabel release];
    [super dealloc];
}

-(IBAction) clickPint:(id) sender {
//	pintCount++;
//	pintCountLabel.text = [NSString stringWithFormat:@"%d",pintCount];
	beerCounterController.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;	
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
	TextDatabaseService *service = [[TextDatabaseService alloc]init];
	[vietosView setPubsOnMap:[service getPubs]];
	vietosView.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;	
	[self presentModalViewController:vietosView animated:YES];
	
	[service release];
	[vietosView release];
}

-(IBAction) clickLucky:(id) sender {
	luckyController.modalTransitionStyle = UIModalTransitionStyleCoverVertical;	
	[self presentModalViewController:luckyController animated:YES];
}




// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	/* Application setup */
    [super viewDidLoad];
	UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	self.view.backgroundColor = background;
	[background release];

	/* Custom initialization */
	pintCount = 0;

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
