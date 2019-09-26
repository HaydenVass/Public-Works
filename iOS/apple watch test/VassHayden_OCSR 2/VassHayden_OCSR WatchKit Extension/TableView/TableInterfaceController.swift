//
//  TableInterfaceController.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/28/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import WatchKit
import Foundation


class TableInterfaceController: WKInterfaceController {
    @IBOutlet weak var table: WKInterfaceTable!
    
    var dataSet: [(String, Int)] = []
    override func awake(withContext context: Any?) {
        super.awake(withContext: context)
        if let details = context as? [(String, Int)]{
            dataSet = details
            table.setNumberOfRows(details.count, withRowType: "RowController")
            for(index, _) in details.enumerated(){
                if let rowController = table.rowController(at: index) as? Cell{
                    rowController.spotNameLabel.setText(details[index].0)
                }
            }
        }else{
            print("no deets")
        }
    }
    
    override func table(_ table: WKInterfaceTable, didSelectRowAt rowIndex: Int) {
        let urlBeginning: String = "http://api.spitcast.com/api/spot/forecast/"
        let end: String = "/"
        let middle: String = String(dataSet[rowIndex].1)
        getForecast(urlString: urlBeginning + middle + end)
    }
}
