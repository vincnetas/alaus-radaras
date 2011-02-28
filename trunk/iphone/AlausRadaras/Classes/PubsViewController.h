//
//  PubsViewController.h
//  AlausRadaras
//
//  Created by Laurynas R on 2/28/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface PubsViewController : UIViewController <UITableViewDelegate, UITableViewDataSource> {

	NSMutableArray *pubList;

	//UI
	UITableView *pubTable;
}

@property (nonatomic, retain) NSMutableArray *pubList;
@property (nonatomic, retain) IBOutlet 	UITableView *pubTable;

- (IBAction) gotoPreviousView:(id)sender;

@end
