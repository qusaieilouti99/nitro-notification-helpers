import Foundation
import NitroModules


public class HybridNotificationHelpers: HybridNitroNotificationHelpersSpec {
    public override init() { super.init() }
    // MARK: - Properties

    // MARK: - Methods

    public func addListener(listener: @escaping (_ notification: String) -> Void) throws -> Void {
        // No-op implementation for iOS
    }

    public func removeListener() throws -> Void {
        // No-op implementation for iOS
    }

    public func getInitialClickedNotification() throws -> String? {
        // No-op implementation for iOS
        return nil
    }

    public func cleanUpStoreNotifications() throws -> Void {
        // No-op implementation for iOS
    }
}
