//
//  LBCellTableViewCell.swift
//  VassHayden_memoryGame
//
//  Created by Hayden Vass on 2/23/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit

class LBCellTableViewCell: UITableViewCell {

    @IBOutlet var playDateLabel: UILabel!
    @IBOutlet var playerNameLabel: UILabel!
    @IBOutlet var playerTimeLabel: UILabel!
    @IBOutlet var playerClicksLabel: UILabel!
    @IBOutlet var placeLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
