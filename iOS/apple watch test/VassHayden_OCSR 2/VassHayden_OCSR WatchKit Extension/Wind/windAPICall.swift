//
//  apiCall.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/19/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
//api call for wind
var selectedReport: WindReport?
extension InterfaceController{
    func configureWindURL(urlString: String){
        let config = URLSessionConfiguration.default
        let session = URLSession(configuration: config)
        if let validURL = URL (string: urlString){
            let task = session.dataTask(with: validURL) { (data, response, error) in
                if error != nil {return}
                guard let response = response as? HTTPURLResponse,
                    response.statusCode == 200,
                    let data = data
                    else {return}
                do{
                    if let json = try JSONSerialization.jsonObject(with: data) as? [Any]
                    {
                        for data in json{
                            if let thirdLevel = data as? [String: Any]{
                                let hour = thirdLevel["hour"] as? String
                                let chour = InterfaceController.getCurrentHour()
                                // checks the current hour and pulls the data from API relevant to that hour
                                if hour == chour{
                                    let knots = thirdLevel["speed_kts"] as? Double
                                    let mph = thirdLevel["speed_mph"] as? Double
                                    let directionStr = thirdLevel["direction_text"] as? String
                                    let directionDegrees = thirdLevel["direction_degrees"] as? Double
                                    
                                    selectedReport = WindReport(_knots: knots ?? 0.0, _mph: mph ?? 0.0, _direction: directionDegrees ?? 0.0, dStr: directionStr ?? "n/a")
                                }
                            }
                        }
                    }
                }catch{
                    print ("Error: \(error.localizedDescription)")
                }
                // get back on the main thread - perform segue -
                //checks against most recent minute (by 10s) to present most relevant ata
                DispatchQueue.main.async {
                    if selectedReport != nil{
                        self.pushController(withName: "WindDetails", context: selectedReport)
                    }
                }
            }
            task.resume()
        }
    }
    
}


