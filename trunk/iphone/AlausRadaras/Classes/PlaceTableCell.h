//
//  PlaceTableCell.h
//  AlausRadaras
//
//  Created by Laurynas R on 9/15/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface PlaceTableCell : UITableViewCell {
	UILabel *titleText;
	UILabel *addressText;
    UILabel *distanceText;
	UIImageView *icon;
}

@property (nonatomic, retain) IBOutlet UILabel *titleText;
@property (nonatomic, retain) IBOutlet UILabel *addressText;
@property (nonatomic, retain) IBOutlet UILabel *distanceText;

@property (nonatomic, retain) IBOutlet UIImageView *icon;

@end