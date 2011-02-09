//
//  BrandsTableCell.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/24/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface BrandsTableCell : UITableViewCell {
	UILabel *labelText;
	UIImageView *brandIcon;
}

@property (nonatomic, retain) IBOutlet UILabel *labelText;
@property (nonatomic, retain) IBOutlet UIImageView *brandIcon;

@end
