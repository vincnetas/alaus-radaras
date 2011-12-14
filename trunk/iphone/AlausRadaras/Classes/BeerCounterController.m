    //
//  BeerCounterController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/28/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "BeerCounterController.h"
#import "SQLiteManager.h"
#import <AudioToolbox/AudioServices.h>
#import "TaxiViewController.h"

@implementation BeerCounterController

@synthesize touchTimer, talkLabel, beerCountLabel, beerButton;

- (void)dealloc {
	[touchTimer release];
	
	[beerButton release];
	[beerCountLabel release];
	[talkLabel release];
    [super dealloc];
}


- (void)viewDidLoad {
	[super viewDidLoad];
	UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	self.view.backgroundColor = background;
	[background release];
	
	/*
	UILongPressGestureRecognizer *longPressGR;	
	UIGestureRecognizer *recognizer;
	recognizer = [[ UILongPressGestureRecognizer alloc] initWithTarget:self action:@selector(handleLongPress:)];
	recognizer.delegate = self;
	
	longPressGR = (UILongPressGestureRecognizer *)recognizer;
	longPressGR.minimumPressDuration = 1.0;
	longPressGR.allowableMovement = 50.0;
	[beerButton addGestureRecognizer:longPressGR];
	*/
	currentBeerCount = 0;
	
	NSLog(@"BeerCounterController viewDidLoad");
}

-(void) handleLongPress:(UILongPressGestureRecognizer *)recognizer  {
	if (currentBeerCount != 0) {
		[self resetCount];
	}
}

- (void)viewDidAppear:(BOOL)animated {
	/* Get Total Beers from user defaults */
	if (currentBeerCount == 0) {
		NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
		if (standardUserDefaults) {
			currentBeerCount = [standardUserDefaults integerForKey:@"CurrentBeers"];
		}
		if (currentBeerCount > 10) {
			talkLabel.text = [[SQLiteManager sharedManager]getQuote:10];
		} else {
			talkLabel.text = [[SQLiteManager sharedManager]getQuote:currentBeerCount];
		}
		beerCountLabel.text = [NSString stringWithFormat:@"%i", currentBeerCount];
	}
	counterReset = FALSE;
}

- (IBAction) resetCount {
	if ([touchTimer isValid]) [touchTimer invalidate];

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
	
	AudioServicesPlaySystemSound(kSystemSoundID_Vibrate);
	counterReset = TRUE;
	beerCountLabel.text = [NSString stringWithFormat:@"%i", currentBeerCount];
	talkLabel.text = [[SQLiteManager sharedManager]getQuote:currentBeerCount];
}

- (IBAction) drinkABeer {
	if (!counterReset) {
		[touchTimer invalidate];

		currentBeerCount++;
		
		beerCountLabel.text = [NSString stringWithFormat:@"%i", currentBeerCount];

		NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
		if (standardUserDefaults) {
			[standardUserDefaults setInteger:currentBeerCount forKey:@"CurrentBeers"];
			[standardUserDefaults synchronize];
		}

		if (currentBeerCount == 5) {
			UIAlertView* alertView = 
			[[UIAlertView alloc] initWithTitle:@"Tai gal jau taksi? A?"
									   message:nil 
									  delegate:self 
							 cancelButtonTitle:@"Dar vieną!"
							 otherButtonTitles:@"Jau kviečiu", nil];
			[alertView show];
			[alertView release];
		}
		
		if (currentBeerCount == 10) {
			UIAlertView* alertView = 
			[[UIAlertView alloc] initWithTitle:@"Sveikiname!. Pasiekiete naują lygį - galite ropoti!"
									   message:nil 
									  delegate:self 
							 cancelButtonTitle:@"Valio!"
							 otherButtonTitles:nil];
			[alertView show];
			[alertView release];	
		}
		if (currentBeerCount > 10) {
			//talkLabel.text = @"Zzz.. zZz..";
			return;
		};
		
		NSString* quote = [[SQLiteManager sharedManager]getQuote:currentBeerCount];
		talkLabel.text = quote;
	}
	counterReset = FALSE;
}

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
	if(buttonIndex == 1) {
		// TODO show taxi list?
		NSLog(@"Calling taxi");
		
		TaxiViewController *taxiView = 
			[[TaxiViewController alloc] initWithNibName:nil bundle:nil];
		taxiView.modalTransitionStyle = UIModalTransitionStyleCoverVertical;	
		[self presentModalViewController:taxiView animated:YES];
		[taxiView release];
	}
}

- (IBAction) gotoPreviousView {
	[self dismissModalViewControllerAnimated:YES];	
}


- (IBAction) pintTouchDown:(id) sender {
	NSLog(@"touchDown");
	[self startTouchTimer:1.00];
}

- (void)startTouchTimer:(float)delay {
	if (!counterReset) {
		touchTimer = [NSTimer scheduledTimerWithTimeInterval:delay target:self selector:@selector(resetCount) userInfo:nil repeats:NO];
		[touchTimer retain];
	}
}

- (void)touchHeld:(NSTimer*)timer {
	NSLog(@"Touch Held");	
	[self resetCount];
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

