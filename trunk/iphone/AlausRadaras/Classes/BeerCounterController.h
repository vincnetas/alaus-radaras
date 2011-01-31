//
//  BeerCounterController.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/28/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TextDatabaseService.h"

@interface BeerCounterController : UIViewController {
	UILabel *beerCountLabel;
	UILabel *talkLabel;
	IBOutlet UIImageView *bubbleImage;
	
	int beerCount;
}

@property (nonatomic, retain) IBOutlet UILabel *beerCountLabel;
@property (nonatomic, retain) IBOutlet UILabel *talkLabel;

- (IBAction) resetCount;
- (IBAction) drinkABeer;
- (IBAction) gotoPreviousView;

@end
