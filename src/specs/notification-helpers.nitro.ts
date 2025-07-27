import type { HybridObject } from 'react-native-nitro-modules'

export type NotificationClickedListener = (notification: string) => void

export interface NitroNotificationHelpers
  extends HybridObject<{ ios: 'swift'; android: 'kotlin' }> {
  addListener(listener: NotificationClickedListener): () => void // the JS can register by calling this func
  removeListeners(): () => void // the JS can register by calling this func
  getInitialClickedNotification(): string | undefined // the JS can fetch the initial notification by calling this func
  storeNotification(notification: string): void // the NotificationService.kt can call this method to store a notification so we can pass it to JS
}
