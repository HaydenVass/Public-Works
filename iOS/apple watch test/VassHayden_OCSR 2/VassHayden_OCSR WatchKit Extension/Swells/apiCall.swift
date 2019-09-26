//
//  apiCall.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/19/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
//api call for swells

extension InterfaceController{
    func configureURL(urlString: String){
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
                                    
                                    if let zero = thirdLevel["0"] as? [String: Any]{
                                        let dir = zero["dir"] as? Int
                                        let hs = zero["hs"] as? Double
                                        let tp = zero["tp"] as? Double
                                        let minute = "0"
                                        self.swellCall.append(SwellReport(_hour: hour ?? "0", _min: minute, _dir: dir, _hs: hs ?? 0.0, _tp: tp ?? 0.0))
                                    }
                                    
                                    if let one = thirdLevel["1"] as? [String: Any]{
                                        let dir = one["dir"] as? Int
                                        let hs = one["hs"] as? Double
                                        let tp = one["tp"] as? Double
                                        let minute = "1"
                                        self.swellCall.append(SwellReport(_hour: hour ?? "0", _min: minute, _dir: dir, _hs: hs ?? 0.0, _tp: tp ?? 0.0))
                                    }

                                    if let two = thirdLevel["2"] as? [String: Any]{
                                        let dir = two["dir"] as? Int
                                        let hs = two["hs"] as? Double
                                        let tp = two["tp"] as? Double
                                        let minute = "2"
                                        self.swellCall.append(SwellReport(_hour: hour ?? "0", _min: minute, _dir: dir, _hs: hs ?? 0.0, _tp: tp ?? 0.0))
                                    }

                                    if let three = thirdLevel["3"] as? [String: Any]{
                                        let dir = three["dir"] as? Int
                                        let hs = three["hs"] as? Double
                                        let tp = three["tp"] as? Double
                                        let minute = "3"
                                        self.swellCall.append(SwellReport(_hour: hour ?? "0", _min: minute, _dir: dir, _hs: hs ?? 0.0, _tp: tp ?? 0.0))
                                    }
                                    if let four = thirdLevel["4"] as? [String: Any]{
                                        let dir = four["dir"] as? Int
                                        let hs = four["hs"] as? Double
                                        let tp = four["tp"] as? Double
                                        let minute = "4"
                                        self.swellCall.append(SwellReport(_hour: hour ?? "0", _min: minute, _dir: dir, _hs: hs ?? 0.0, _tp: tp ?? 0.0))
                                    }

                                    if let five = thirdLevel["5"] as? [String: Any]{
                                        let dir = five["dir"] as? Int
                                        let hs = five["hs"] as? Double
                                        let tp = five["tp"] as? Double
                                        let minute = "5"
                                        self.swellCall.append(SwellReport(_hour: hour ?? "0", _min: minute, _dir: dir, _hs: hs ?? 0.0, _tp: tp ?? 0.0))
                                    }
                                    
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
                    print(self.swellCall.count)
                    var reportToSend : SwellReport?
                    for report in self.swellCall{
                        if report.minutes == self.getCurrentMinutes(){
                            reportToSend = report
                            break
                        }
                    }
                    self.pushController(withName: "SwellDetails", context: reportToSend)
                }
            }
            task.resume()
        }
    }
    
}


