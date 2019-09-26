//
//  WinConditionViewController.swift
//  VassHayden_memoryGame
//
//  Created by Hayden Vass on 2/13/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit
import CoreData
// todo - validate empty spaces


class WinConditionViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    //ui
    @IBOutlet var gameDataLabel: UILabel!
    @IBOutlet var playAgainButton: UIButton!
    @IBOutlet var tableView: UITableView!
    @IBOutlet var addScoreButton: UIButton!
    
    
    //core data
    private let appDelegate = UIApplication.shared.delegate as! AppDelegate
    private var managedContext: NSManagedObjectContext!
    private var entityDescription: NSEntityDescription!
    private var player: NSManagedObject!
    //counters
    var playersTime: Int = 0
    var turns: Int = 0
    var playerInitials: String = "test"
    //date
    var date = Date()
    let dateFormatter = DateFormatter()
    let calendar = Calendar.current
    
    // core data array - sorted
    var allEntriesArray = [NSManagedObject]()
    var topTenArray = [NSManagedObject]()
    
    //switches
    var scoreAdded: Bool = false
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        definesPresentationContext = true
        managedContext = appDelegate.managedObjectContext
        entityDescription = NSEntityDescription.entity(forEntityName: "Player", in: managedContext)
        
        constructUI()
        dateFormatter.timeZone = TimeZone.current
        dateFormatter.dateFormat = "dd.MM.yyyy HH:mm"
        let currentDate = dateFormatter.string(from: date)
        let dateTimeStamp = dateFormatter.date(from: currentDate)
        date = dateTimeStamp!
        
        fetchLeaderBoard()
    }
    // if player gets in the top 10 - they will be notified
    override func viewDidAppear(_ animated: Bool) {
        gotTopTen()
    }
    
    @IBAction func playAgainBtnPress(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    
    // calculates total time (sconds) into minutes / secons
    func timeFormatted(totalTime: Int) -> String {
        let seconds: Int = totalTime % 60
        let minutes: Int = (totalTime / 60) % 60
        if seconds < 10{
            return "\(minutes):0\(seconds)"
        }else{
            return "\(minutes):\(seconds)"
        }
    }
    // table view
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return topTenArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "C1", for: indexPath) as! LeaderBoardTableViewCell
        cell.layer.cornerRadius = 5
        cell.playerClickLabel.text = (topTenArray[indexPath.row].value(forKey: "turnsTaken") as! Int).description
        cell.playerTimeLabel.text = timeFormatted(totalTime: (topTenArray[indexPath.row].value(forKey: "time") as! Int))
        cell.playerInitalsLabel.text = topTenArray[indexPath.row].value(forKey: "name") as? String
        cell.playerDateLabel.text = String ((topTenArray[indexPath.row].value(forKey: "date") as! Date).description.dropLast(9))
        cell.placeLabel.text = (indexPath.row + 1).description
        return cell
    }
    // grabs core data
    func fetchLeaderBoard(){
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Player")
        do {
            let results:[NSManagedObject] = try managedContext.fetch(fetchRequest)
            allEntriesArray = results.sorted(by: { (a, b) -> Bool in
                ( a.value(forKeyPath: "time") as? Int ?? 0) < (b.value(forKeyPath: "time") as? Int ?? 0)
            })
            if allEntriesArray.count > 10{
                let slice = allEntriesArray.prefix(through: 9)
                topTenArray = Array(slice)
            }else{
                topTenArray = allEntriesArray
            }
        }
        catch {
            //assertionFailure()
        }
        tableView.reloadData()
        
    }
    
    func constructUI(){
        tableView.layer.cornerRadius = 10
        navigationController?.navigationBar.barTintColor = Colors.mutedGreen
        playAgainButton.layer.cornerRadius = 10
        addScoreButton.layer.cornerRadius = 10
        // takes time, converts it to mm:ss and gives it to the user in a string
        gameDataLabel.text = "Your time was \(timeFormatted(totalTime: playersTime )) and it took you \(turns) turns! Play again and try to do better!"
    }
    
    @IBAction func addScorePressed(_ sender: Any) {
        addScoreModal(alertMessage: "Great job! Add your name to the leaderboard. Just put in your name and we'll take care of the rest.")
    }
    
    func gotTopTen(){
        // if there isnt already 10 players then the first 10 are automatically in the top
        if topTenArray.count < 10{
            addScoreModal(alertMessage: "Wow! You placed in the top ten! Add your initals so others can see your accomplishment!")
        }else if playersTime <= topTenArray[9].value(forKey: "time") as! Int{
            // checks to see if the current score is better than the 10th best time
            addScoreModal(alertMessage: "Wow! You placed in the top ten! Add your initals so others can see your accomplishment!")
        }else{
            addScoreModal(alertMessage: "You didn't place in the top ten, but add your name and check out the leader board to see how you stacked up!")
        }
    }
    // modals
    func addScoreModal(alertMessage: String){
        if player == nil{
            player = NSManagedObject(entity: entityDescription, insertInto: managedContext)
        }
        var userInput: String = ""
        let alert: UIAlertController = UIAlertController(title: "Add your legacy!", message: alertMessage, preferredStyle: .alert)
        alert.addTextField { (textField) in
            textField.placeholder = "Put your initals here."
        }
        alert.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: { action in
            // if the text field isnt empty, then the score gets added to core data
            if alert.textFields?[0].text != ""{
                self.scoreAdded = true
                userInput = alert.textFields?[0].text ?? "nil"
                self.player.setValue(self.turns, forKey: "turnsTaken")
                self.player.setValue(self.playersTime, forKey: "time")
                self.player.setValue(userInput, forKey: "name")
                self.player.setValue(self.date, forKey: "date")
                self.appDelegate.saveContext()
                DispatchQueue.main.async {
                    self.fetchLeaderBoard()
                    if self.scoreAdded{
                        self.addScoreButton.isHidden = true
                    }
                }
            }else{
                self.emptyTextFieldModal()
            }
        }))
        present(alert, animated: true, completion: nil)
    }
    // validates empty text field
    func emptyTextFieldModal(){
        let alert: UIAlertController = UIAlertController(title: "Empty name field!", message: "Please enter your name or hit cancel.", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: { action in
            DispatchQueue.main.async {
                self.addScoreModal(alertMessage: "Add your name!")
            }
        }))
        present(alert, animated: true, completion: nil)
    }
    
}
