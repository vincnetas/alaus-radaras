//
//  LuckyController.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/29/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FeelingLucky.h";


@interface LuckyController : UIViewController {
	FeelingLucky *feelingLucky;
	UILabel *pubLabel;
	UILabel *brandLabel;
	UIImageView *brandImage;
	
	NSString *pubId;
}
@property (nonatomic, retain) FeelingLucky *feelingLucky;
@property (nonatomic, retain) IBOutlet UILabel *pubLabel;
@property (nonatomic, retain) IBOutlet UILabel *brandLabel;
@property (nonatomic, retain) IBOutlet UIImageView *brandImage;

- (IBAction) gotoPreviousView;
- (IBAction) showPubDetailView ;

@end
