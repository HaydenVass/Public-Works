//
//  Musician.swift
//  VassHayden_CE04
//
//  Created by Hayden Vass on 5/23/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
import UIKit

class Musician: NSObject {
    
    let name: String?
    let descrip: String
    let homeTown: String
    let img: UIImage?
    let category: String?
    
    init(_name: String, _description: String, _img: UIImage, _category: String?, _hT: String) {
        name = _name
        descrip = _description
        img = _img
        category = _category
        homeTown = _hT
    }
    
    
}
