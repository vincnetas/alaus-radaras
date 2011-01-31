    //
//  BeerCounterController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/28/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "BeerCounterController.h"


@implementation BeerCounterController

@synthesize talkLabel, beerCountLabel;


- (void)dealloc {
	[beerCountLabel release];
	[talkLabel release];
    [super dealloc];
}


- (void)viewDidLoad {
	[super viewDidLoad];
	UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	self.view.backgroundColor = background;
	[background release];
	
	bubbleImage.hidden = YES;
	talkLabel.hidden = YES;
	
	beerCount = 0;
}

- (IBAction) resetCount {
	bubbleImage.hidden = YES;
	talkLabel.hidden = YES;
}

- (IBAction) drinkABeer {	
	bubbleImage.hidden = NO;
	talkLabel.hidden = NO;
	beerCount++;
	
	beerCountLabel.text = [NSString stringWithFormat:@"%i", beerCount];

	if (beerCount >= 11) {
		talkLabel.text = @"Zzz.. zZz..";
		return;
	};
	
	if (beerCount == 5) {
		UIAlertView* alertView = 
		[[UIAlertView alloc] initWithTitle:@"Tai gal jau taksi? A?"
								   message:nil 
								  delegate:self 
						 cancelButtonTitle:@"Užteks,važiuoju"
						 otherButtonTitles:@"Dar vieną!", nil];
		[alertView show];
		[alertView release];	
	}
	
	TextDatabaseService *service = [[TextDatabaseService alloc]init];
	talkLabel.text = [service getQuote:beerCount];
	[service release];
}

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
	if(buttonIndex == 0) {
		// TODO show taxi list?
		NSLog(@"Calling taxi");
	}
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












// The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
/*
 - (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
 self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
 if (self) {
 // Custom initialization.
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
 // Implement viewDidLoad to do additional setup after loading the view, typically from a nib.

 */

/*
 // Override to allow orientations other than the default portrait orientation.
 - (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
 // Return YES for supported orientations.
 return (interfaceOrientation == UIInterfaceOrientationPortrait);
 }
 */

