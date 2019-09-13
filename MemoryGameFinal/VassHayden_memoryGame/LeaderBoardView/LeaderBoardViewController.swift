//
//  LeaderBoardViewController.swift
//  VassHayden_memoryGame
//
//  Created by Hayden Vass on 2/23/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit
import CoreData



class LeaderBoardViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, UISearchBarDelegate, UISearchResultsUpdating, UISearchControllerDelegate, UITextFieldDelegate {
    
    
    private let appDelegate = UIApplication.shared.delegate as! AppDelegate
    private var managedContext: NSManagedObjectContext!
    private var entityDescription: NSEntityDescription!
    private var player: NSManagedObject!
    
    @IBOutlet var tableView: UITableView!
    
    var mainArray = [NSManagedObject]()
    var filteredArray = [NSManagedObject]()
    var searchController = UISearchController(searchResultsController: nil)


    override func viewDidLoad() {
        super.viewDidLoad()
        // gets core data information
        managedContext = appDelegate.managedObjectContext

        fetchLeaderBoard()
        // search functionality
        craftSearchFunction()
        // ui elements
        modifyUI()
        // filtred array for search bar - main array pulled from core data
        filteredArray = mainArray
        tableView.layer.cornerRadius = 10
        definesPresentationContext = true

    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return filteredArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "C2", for: indexPath) as! LBCellTableViewCell
        cell.playerClicksLabel.text = (filteredArray[indexPath.row].value(forKey: "turnsTaken") as! Int).description
        cell.playerTimeLabel.text = timeFormatted(totalTime: (filteredArray[indexPath.row].value(forKey: "time") as! Int))
        cell.playerNameLabel.text = filteredArray[indexPath.row].value(forKey: "name") as? String
        cell.playDateLabel.text = String ((filteredArray[indexPath.row].value(forKey: "date") as! Date).description.dropLast(9))
        //cell.placeLabel.text = mainArray.firstIndex(of: filteredArray[indexPath.row])?.description
        // stores the actual place the searched user is in. So if a user searches for a name, their proper position in the leaderboard will persist.
        let num = ((mainArray.firstIndex(of: filteredArray[indexPath.row]))!) + 1
        cell.placeLabel.text = num.description
        return cell
    }
    //pulls data from core data
    func fetchLeaderBoard(){
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Player")
        do {
            let results:[NSManagedObject] = try managedContext.fetch(fetchRequest)
            mainArray = results.sorted(by: { (a, b) -> Bool in
                ( a.value(forKeyPath: "time") as? Int ?? 0) < (b.value(forKeyPath: "time") as? Int ?? 0)
            })
        }catch {
            //test
        }
        tableView.reloadData()
    }
    // updates table view while the user is searching
    func updateSearchResults(for searchController: UISearchController) {
        let searchText = searchController.searchBar.text
        filteredArray = mainArray
        if searchText != ""{
            filteredArray = filteredArray.filter({ (player) -> Bool in
                return (player.value(forKey: "name") as? String)?.lowercased().range(of: searchText!.lowercased()) != nil
            })
        }
        tableView.reloadData()
    }
    
    func searchBar(_ searchBar: UISearchBar, selectedScopeButtonIndexDidChange selectedScope: Int) {
        updateSearchResults(for: searchController)
    }
    
    
    func modifyUI(){
        navigationController?.navigationBar.isHidden = false
        navigationController?.navigationBar.barTintColor = Colors.mutedGreen
        navigationItem.title = "Leader Board"
        navigationController?.navigationBar.tintColor = Colors.mutedWhite
        navigationController?.navigationBar.titleTextAttributes = [NSAttributedString.Key.foregroundColor: Colors.white]
        UINavigationBar.appearance().tintColor = Colors.mutedWhite
    }
    // sets search functionality
    func craftSearchFunction(){
        searchController.dimsBackgroundDuringPresentation = false
        searchController.definesPresentationContext = true
        searchController.searchResultsUpdater = self
        searchController.searchBar.placeholder = "Search for a players Initals."
        searchController.searchBar.delegate = self
        navigationItem.searchController = searchController
        searchController.searchBar.tintColor = Colors.mutedWhite
        searchController.searchBar.barTintColor = Colors.brownSugar
        
        let searchTextColor = [NSAttributedString.Key.foregroundColor: Colors.mutedWhite]
        UITextField.appearance(whenContainedInInstancesOf: [UISearchBar.self]).defaultTextAttributes = searchTextColor
    }
    //formats time to minutes and seconds
    func timeFormatted(totalTime: Int) -> String {
        let seconds: Int = totalTime % 60
        let minutes: Int = (totalTime / 60) % 60
        if seconds < 10{
            return "\(minutes):0\(seconds)"
        }else{
            return "\(minutes):\(seconds)"
        }
    }
}
