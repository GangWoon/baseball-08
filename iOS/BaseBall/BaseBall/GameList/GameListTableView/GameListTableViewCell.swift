//
//  GameListTableViewCell.swift
//  BaseBall
//
//  Created by Cloud on 2020/05/12.
//  Copyright © 2020 Cloud. All rights reserved.
//

import UIKit

final class GameListTableViewCell: UITableViewCell {
    
    // MARK: - IBOutlets
    @IBOutlet weak var gameNumberLabel: UILabel!
    @IBOutlet weak var homeTeamLabel: UILabel!
    @IBOutlet weak var awayTeamLabel: UILabel!
    
    // MARK: - Properties
    static let identifier: String = "GameListTableViewCell"
    static let height: CGFloat = 110
    
    // MARK: - Methods
    func configure(_ game: Gameable) {
        gameNumberLabel.text = "Game \(game.game)"
        homeTeamLabel.text = game.home
        awayTeamLabel.text = game.away
    }
}
