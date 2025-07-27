import { NitroModules } from 'react-native-nitro-modules'
import type {
  NitroNotificationHelpers as NitroModule,
  NotificationClickedListener,
} from './specs/notification-helpers.nitro'

const NitroNotificationHelpers = NitroModules.createHybridObject<NitroModule>(
  'NitroNotificationHelpers'
)

export default NitroNotificationHelpers

export type { NotificationClickedListener }
