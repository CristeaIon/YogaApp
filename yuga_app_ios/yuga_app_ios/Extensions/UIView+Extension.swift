//
//  UIView+Extension.swift
//  yuga_app_ios
//
//  Created by Ion Cristea on 10.06.2023.
//

import UIKit

extension UIView {
   @IBInspectable var cornerRadius: CGFloat {
       get { return self.cornerRadius}
        set {self.layer.cornerRadius = newValue}
    }
}
