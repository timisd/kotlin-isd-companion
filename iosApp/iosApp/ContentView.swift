import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let controller = MainViewControllerKt.MainViewController()
        controller.view.backgroundColor = .init(displayP3Red: 26/255, green: 26/255, blue: 26/255, alpha: 1)  // #1A1A1A
        return controller
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ZStack {
            Color(UIColor(displayP3Red: 26/255, green: 26/255, blue: 26/255, alpha: 1))
                .ignoresSafeArea()
            
            ComposeView()
                .ignoresSafeArea(.all, edges: .bottom)
        }
        .preferredColorScheme(.dark)
    }
}



