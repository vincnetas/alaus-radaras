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
}

@property (nonatomic, retain) IBOutlet UILabel *beerCountLabel;
@property (nonatomic, retain) IBOutlet UILabel *talkLabel;
@property (nonatomic, retain) IBOutlet UIButton *beerButton;

- (IBAction) resetCount;
- (IBAction) drinkABeer;
- (IBAction) gotoPreviousView;

@end
