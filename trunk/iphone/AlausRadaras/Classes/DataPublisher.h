//
//  DataPublisher.h
//  AlausRadaras
//
//  Created by Laurynas R on 2/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface DataPublisher : NSObject {

}

- (void) submitPubBrand: (NSString *) brandId pub:(NSString *) pubId status:(NSString *) status message:(NSString *) message;

@end
