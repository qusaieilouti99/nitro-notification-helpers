import type { HybridObject } from 'react-native-nitro-modules'

export type NotificationClickedListener = (notification: string) => void

export interface NitroNotificationHelpers
  extends HybridObject<{ ios: 'swift'; android: 'kotlin' }> {
  addListener(listener: NotificationClickedListener): void // add or overwrite existing listener
  removeListener(): void // remove the existing listener
  getInitialClickedNotification(): string | undefined // the JS can fetch the initial notification by calling this func
  cleanUpStoreNotifications(): void // this should just clear the in-memory stored notifications
}
