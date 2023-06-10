//
//  OnboardingCollectionViewCell.swift
//  yuga_app_ios
//
//  Created by Ion Cristea on 10.06.2023.
//

import UIKit

class OnboardingCollectionViewCell: UICollectionViewCell {
    static let identifier = String(describing: OnboardingCollectionViewCell.self)
  
    @IBOutlet weak var slideImageView: UIImageView!
    @IBOutlet weak var slideTitleLbl: UILabel!
    @IBOutlet weak var slideSubtitleLbl: UILabel!
    @IBOutlet weak var slideDescriptionLbl: UILabel!
    
    func setup(_ slide: OnboardingSlide) {
        slideImageView.image = slide.image
        slideTitleLbl.text = slide.title
        slideSubtitleLbl.text = slide.subtitle
        slideDescriptionLbl.text = slide.desctription
    }
}
