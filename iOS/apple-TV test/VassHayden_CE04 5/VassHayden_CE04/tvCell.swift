//
//  tvCell.swift
//  VassHayden_CE04
//
//  Created by Hayden Vass on 5/23/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit

class tvCell: UITableViewCell {

    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var categoryLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
