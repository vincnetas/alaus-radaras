//
//  BeerCounterController.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/28/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BeerCounterController : UIViewController<UIGestureRecognizerDelegate> {
	UILabel *beerCountLabel;
	UILabel *talkLabel;
	
	int currentBeerCount;
	
	// iOS3.1 support
	NSTimer *touchTimer;
	BOOL counterReset;
}

@property (nonatomic, retain) IBOutlet UILabel *beerCountLabel;
@property (nonatomic, retain) IBOutlet UILabel *talkLabel;
@property (nonatomic, retain) IBOutlet UIButton *beerButton;

// iOS3.1 support
@property (nonatomic, retain) NSTimer *touchTimer;

- (IBAction) resetCount;
- (IBAction) drinkABeer;
- (IBAction) gotoPreviousView;

- (IBAction) pintTouchDown:(id) sender;
- (IBAction) pintTouchEnded:(id) sender;
- (IBAction) pintTouchMoved:(id) sender;

- (void)startTouchTimer:(float)delay;

@end
