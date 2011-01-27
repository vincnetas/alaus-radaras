//
//  AlausRadarasViewController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "AlausRadarasViewController.h"
#import "Pub.h"

@implementation AlausRadarasViewController

@synthesize pintCountLabel;

- (void)dealloc {
	[pintCountLabel release];
    [super dealloc];
}

-(IBAction) clickPint:(id) sender {
	pintCount++;
	pintCountLabel.text = [NSString stringWithFormat:@"%d",pintCount];
}


-(IBAction) clickAlus:(id) sender {
	BrandsViewController *brandsView = 
		[[BrandsViewController alloc] initWithNibName:nil bundle:nil];
	brandsView.modalTransitionStyle = UIModalTransitionStyleCoverVertical;	
	[self presentModalViewController:brandsView animated:YES];
	[brandsView release];
}

-(IBAction) clickVietos:(id) sender {
	MapViewController *vietosView = 
			[[MapViewController alloc] initWithNibName:nil bundle:nil];

	NSString* path = [[NSBundle mainBundle] pathForResource:@"pubs" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	NSMutableArray *pubs = [[NSMutableArray alloc]init];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];
			Pub *pub = [[Pub alloc]init];

			pub.latitude = [values objectAtIndex:5];
			pub.longitude = [values objectAtIndex:6];
			[pub setPubId:[values objectAtIndex:0]];
			[pub setPubTitle:[values objectAtIndex:1]];
			[pub setPubAddress:[values objectAtIndex:2]];

			[pubs addObject:pub];
			[pub release]; // realising cause navigation problems 
		}
	}
	[vietosView setPubsOnMap:pubs];
	
	vietosView.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;	
	[self presentModalViewController:vietosView animated:YES];
	
	[vietosView release];
	[pubs release];
}






// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	NSLog(@"Pints: %d", pintCount);

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
