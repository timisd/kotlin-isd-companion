import UIKit
import SwiftUI

extension UIViewController {
    func configureSystemBars() {
        // Set status bar style to light for dark mode
        UIApplication.shared.statusBarStyle = .lightContent
        
        // Make the navigation bar transparent and match system background
        if let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene {
            windowScene.windows.forEach { window in
                // Set window background to black/dark in dark mode
                window.backgroundColor = .black
                
                // Make the navigation bar transparent
                window.overrideUserInterfaceStyle = .dark
            }
        }
    }
} 