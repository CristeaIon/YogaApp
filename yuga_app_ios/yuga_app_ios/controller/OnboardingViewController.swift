//
//  OnboardingViewController.swift
//  yuga_app_ios
//
//  Created by Ion Cristea on 10.06.2023.
//

import UIKit

class OnboardingViewController: UIViewController {
    
    @IBOutlet weak var onboardingCollectionView: UICollectionView!
    @IBOutlet weak var pageControl: UIPageControl!
    var slides: [OnboardingSlide] = []
    override func viewDidLoad() {
        super.viewDidLoad()
        slides = [
            OnboardingSlide(image: UIImage(imageLiteralResourceName: "onboarding1"), title: "Yoga", subtitle: "Daily Yoga", desctription: "Do you practice of physical exercice and relaxation make healty"),OnboardingSlide(image: UIImage(imageLiteralResourceName: "onboarding2"), title: "Meditation", subtitle: "Yoga Classes", desctription: "Meditation is the key to Productivity. Happiness & Longevity"),OnboardingSlide(image: UIImage(imageLiteralResourceName: "onboarding3"), title: "Meets", subtitle: "Community", desctription: "Do you practice of physical exercice and relaxation make healty")
        ]
        // Do any additional setup after loading the view.
    }
    
    @IBAction func skipBtnClicked(_ sender: UIBarButtonItem) {
    }
    @IBAction func nextBtnClicked(_ sender: UIButton) {
    }
}
extension OnboardingViewController: UICollectionViewDelegate,UICollectionViewDataSource,UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return slides.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: OnboardingCollectionViewCell.identifier, for: indexPath) as! OnboardingCollectionViewCell
        cell.setup(slides[indexPath.row])
        
        return cell
    }
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: collectionView.frame.width, height: collectionView.frame.height)
    }
}

