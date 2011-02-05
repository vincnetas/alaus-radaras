    //
//  BeerCounterController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/28/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "BeerCounterController.h"
#import "AlausRadarasAppDelegate.h"

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
	
	currentBeerCount = 0;
	NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
	if (standardUserDefaults) {
		currentBeerCount = [standardUserDefaults integerForKey:@"CurrentBeers"];
	}
	
	beerCountLabel.text = [NSString stringWithFormat:@"%i", currentBeerCount];
	AlausRadarasAppDelegate *appDelegate = (AlausRadarasAppDelegate *)[[UIApplication sharedApplication] delegate];
	talkLabel.text = [appDelegate getQuote:currentBeerCount];

	NSLog(@"BeerCounterController viewDidLoad");
}

- (IBAction) resetCount {
	NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
	if (standardUserDefaults) {
		int beerCount = 0;
		beerCount = [standardUserDefaults integerForKey:@"TotalBeers"];
		beerCount += currentBeerCount;
		[standardUserDefaults setInteger:beerCount forKey:@"TotalBeers"];
		[standardUserDefaults setInteger:0 forKey:@"CurrentBeers"];
		[standardUserDefaults synchronize];
	}
	currentBeerCount = 0;
	
	beerCountLabel.text = [NSString stringWithFormat:@"%i", currentBeerCount];
	AlausRadarasAppDelegate *appDelegate = (AlausRadarasAppDelegate *)[[UIApplication sharedApplication] delegate];
	talkLabel.text = [appDelegate getQuote:currentBeerCount];
}

- (IBAction) drinkABeer {	
	currentBeerCount++;
	
	beerCountLabel.text = [NSString stringWithFormat:@"%i", currentBeerCount];

	NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
	if (standardUserDefaults) {
		[standardUserDefaults setInteger:currentBeerCount forKey:@"CurrentBeers"];
		[standardUserDefaults synchronize];
	}
	
	if (currentBeerCount >= 11) {
		talkLabel.text = @"Zzz.. zZz..";
		return;
	};


	if (currentBeerCount == 5) {
		UIAlertView* alertView = 
		[[UIAlertView alloc] initWithTitle:@"Tai gal jau taksi? A?"
								   message:nil 
								  delegate:self 
						 cancelButtonTitle:@"Užteks,važiuoju"
						 otherButtonTitles:@"Dar vieną!", nil];
		[alertView show];
		[alertView release];	
	}
	
	AlausRadarasAppDelegate *appDelegate = (AlausRadarasAppDelegate *)[[UIApplication sharedApplication] delegate];
	NSString* quote = [appDelegate getQuote:currentBeerCount];
	talkLabel.text = quote;
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

