export type AppLanguage = 'ar' | 'am' | 'en';

export type UserRole = 'STUDENT' | 'TEACHER' | 'ADMIN';

export interface AcademyAccount {
  id: string;
  name: string;
  email: string;
  role: UserRole;
  avatar: string;
  title: string;
}

export interface QuranProgram {
  id: string;
  title: string;
  category: string;
  description: string;
  howToUse: string;
  requirements: string;
  durationText: string;
  isRegistrationOpen: boolean;
  enrolledCount: number;
}

export type GroupTiming = 'MORNING' | 'EVENING' | 'NIGHT' | 'GENERAL';
export type GroupGender = 'MALE' | 'FEMALE' | 'ALL';

export interface StudyGroup {
  id: string;
  name: string;
  timing: GroupTiming;
  gender: GroupGender;
  description: string;
  sheikhSupervisor: string;
  membersCount: number;
  isLiveBroadcastActive: boolean;
  liveRoomId?: string;
  unreadCount?: number;
}

export type FriendStatus = 'NONE' | 'REQUEST_SENT' | 'REQUEST_RECEIVED' | 'FRIENDS';

export interface GroupMember {
  id: string;
  name: string;
  role: UserRole;
  isOnline: boolean;
  qiraah: string;
  friendStatus: FriendStatus;
  avatar?: string;
}

export interface ChatMessage {
  id: string;
  conversationId: string;
  senderName: string;
  text: string;
  timestamp: string;
  isMine: boolean;
  isAudio?: boolean;
  audioDurationSec?: number;
  isRead?: boolean;
}

export interface ChatConversation {
  id: string;
  title: string;
  subtitle: string;
  lastMessageTime: string;
  unreadCount: number;
  isGroup?: boolean;
  isChannel?: boolean;
  isVerified?: boolean;
  avatarUrl?: string;
  liveRoomId?: string;
  hasAudioLastMessage?: boolean;
}

export interface StoryItem {
  id: string;
  title: string;
  author: string;
  isLiveNow: boolean;
  imageUrl?: string;
}

export interface LiveRoomInfo {
  id: string;
  nameAr: string;
  nameAm: string;
  nameEn: string;
  teacherName: string;
  riwayah: string;
  currentSurah: string;
  participantsCount: number;
  isLive: boolean;
  activeReciter: string;
}

export type QueueStatus = 'WAITING' | 'RECITING' | 'COMPLETED';

export interface StudentQueueItem {
  id: string;
  studentName: string;
  riwayah: string;
  surah: string;
  minutesBalance: number;
  status: QueueStatus;
}

export interface MinutePackage {
  id: string;
  nameAr: string;
  nameAm: string;
  nameEn: string;
  minutes: number;
  priceUsd: number;
  priceSar: number;
  priceEtb: number;
  description: string;
  isPopular?: boolean;
}

export type PaymentRequestStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export interface PaymentRequest {
  id: string;
  studentName: string;
  packageName: string;
  minutes: number;
  amount: number;
  method: string;
  transactionId: string;
  phone: string;
  status: PaymentRequestStatus;
  date: string;
}

export interface BankPaymentInfo {
  id: string;
  bankName: string;
  accountHolder: string;
  accountNumber: string;
  ibanOrIdentifier: string;
  currency: string;
  instructions: string;
}

export interface MinuteLoanRequest {
  id: string;
  studentName: string;
  minutesRequested: number;
  reason: string;
  date: string;
  status: PaymentRequestStatus;
}
