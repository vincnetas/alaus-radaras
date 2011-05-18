//
//  PubsViewController.m
//  AlausRadaras
//
//  Created by Laurynas R on 2/28/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PubsViewController.h"


@implementation PubsViewController

@synthesize pubList, pubTable;

- (void)dealloc {
//	[pubList release];
	[pubTable release];
    [super dealloc];
}

- (void)viewDidLoad {
	[super viewDidLoad];
	
	UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	self.view.backgroundColor = background;
	[background release];
	
	/* Transparent Background */
	pubTable.backgroundColor = [UIColor clearColor];
	pubTable.opaque = NO;
	
	/* Separator color */ 
	pubTable.separatorColor = [UIColor grayColor];

}

- (IBAction) gotoPreviousView:(id)sender {
	[self dismissModalViewControllerAnimated:YES];	
}


#pragma mark -
#pragma mark Table view methods

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	return [pubList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {	
	static NSString *CellIdentifier = @"PubCell";

	UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
	if (cell == nil) {
		cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
	cell.textColor = [UIColor whiteColor];
	cell.textLabel.text = [[pubList objectAtIndex:indexPath.row] pubTitle];
	
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	[tableView deselectRowAtIndexPath:indexPath animated:YES];	
}

- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
    return NO;
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
