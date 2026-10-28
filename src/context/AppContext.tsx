import React, { createContext, useContext, useState, useEffect } from 'react';
import {
  AcademyAccount,
  AppLanguage,
  BankPaymentInfo,
  ChatConversation,
  ChatMessage,
  GroupGender,
  GroupMember,
  GroupTiming,
  LiveRoomInfo,
  MinuteLoanRequest,
  MinutePackage,
  PaymentRequest,
  QuranProgram,
  StoryItem,
  StudentQueueItem,
  StudyGroup,
  UserRole,
} from '../types';
import {
  INITIAL_ACCOUNTS,
  INITIAL_BANNER,
  INITIAL_ABOUT,
  INITIAL_PROGRAMS,
  INITIAL_STUDY_GROUPS,
  INITIAL_GROUP_MEMBERS,
  INITIAL_BANK_DETAILS,
  INITIAL_CONVERSATIONS,
  INITIAL_MESSAGES,
  INITIAL_STORIES,
  INITIAL_LIVE_ROOMS,
  INITIAL_QUEUE,
  INITIAL_PACKAGES,
  INITIAL_PAYMENTS,
  INITIAL_LOANS,
} from '../data/initialData';
import { TRANSLATIONS } from '../data/translations';

interface AppContextType {
  isDarkMode: boolean;
  toggleDarkMode: () => void;
  currentLang: AppLanguage;
  setLanguage: (lang: AppLanguage) => void;
  t: (key: string) => string;

  // Accounts & Role
  currentAccount: AcademyAccount;
  availableAccounts: AcademyAccount[];
  switchAccount: (account: AcademyAccount) => void;
  currentRole: UserRole;

  // Tabs & Navigation
  currentTab: 'home' | 'chats' | 'groups' | 'account' | 'settings';
  setCurrentTab: (tab: 'home' | 'chats' | 'groups' | 'account' | 'settings') => void;

  // CMS Content (Supervisor Editable)
  bannerText: string;
  updateBanner: (text: string) => void;
  aboutText: string;
  updateAbout: (text: string) => void;
  programs: QuranProgram[];
  addProgram: (program: QuranProgram) => void;
  updateProgram: (program: QuranProgram) => void;
  deleteProgram: (id: string) => void;

  // Study Groups
  studyGroups: StudyGroup[];
  activeGroup: StudyGroup | null;
  openGroupChat: (group: StudyGroup) => void;
  closeGroupChat: () => void;
  groupMembers: GroupMember[];
  sendFriendRequest: (memberId: string) => void;
  acceptFriendRequest: (memberId: string) => void;

  // Messaging & Telegram UI
  conversations: ChatConversation[];
  activeConversation: ChatConversation | null;
  openConversation: (conv: ChatConversation) => void;
  closeConversation: () => void;
  messages: Record<string, ChatMessage[]>;
  sendMessage: (convId: string, text: string, isAudio?: boolean, audioDurationSec?: number) => void;
  stories: StoryItem[];

  // LiveKit Recitation Rooms
  liveRooms: LiveRoomInfo[];
  activeLiveRoom: LiveRoomInfo | null;
  openLiveRoom: (room?: LiveRoomInfo) => void;
  closeLiveRoom: () => void;
  studentQueue: StudentQueueItem[];
  callStudentInQueue: (studentId: string) => void;
  completeRecitation: (deductMin?: number) => void;

  // Minutes & Finance
  studentMinutesBalance: number;
  packages: MinutePackage[];
  bankDetails: BankPaymentInfo[];
  saveBankDetail: (bank: BankPaymentInfo) => void;
  deleteBankDetail: (bankId: string) => void;
  payments: PaymentRequest[];
  submitPayment: (pkgName: string, mins: number, amount: number, method: string, txId: string, phone: string, name: string) => void;
  approvePayment: (paymentId: string) => void;
  rejectPayment: (paymentId: string) => void;
  loans: MinuteLoanRequest[];
  requestMinuteLoan: (studentName: string, mins: number, reason: string) => void;
  approveLoan: (loanId: string) => void;
  rejectLoan: (loanId: string) => void;

  // Registration Chatbot
  registrationProgram: QuranProgram | null;
  openRegistrationBot: (program: QuranProgram) => void;
  closeRegistrationBot: () => void;
  enrollStudentInGroup: (name: string, gender: GroupGender, timing: GroupTiming, qiraah: string, program: QuranProgram) => StudyGroup;

  // Alerts & Permissions
  permissionAlert: string | null;
  setPermissionAlert: (msg: string | null) => void;
  dismissPermissionAlert: () => void;
  triggerCallingOrBroadcast: (actionName: string, onAuthorized: () => void) => void;
}

const AppContext = createContext<AppContextType | undefined>(undefined);

export const AppProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [isDarkMode, setIsDarkMode] = useState<boolean>(() => {
    return localStorage.getItem('jazari_theme') === 'dark';
  });

  const [currentLang, setCurrentLangState] = useState<AppLanguage>(() => {
    return (localStorage.getItem('jazari_lang') as AppLanguage) || 'ar';
  });

  const [currentAccount, setCurrentAccount] = useState<AcademyAccount>(INITIAL_ACCOUNTS[0]);
  const [currentTab, setCurrentTab] = useState<'home' | 'chats' | 'groups' | 'account' | 'settings'>('home');

  // CMS
  const [bannerText, setBannerText] = useState(INITIAL_BANNER);
  const [aboutText, setAboutText] = useState(INITIAL_ABOUT);
  const [programs, setPrograms] = useState<QuranProgram[]>(INITIAL_PROGRAMS);

  // Groups
  const [studyGroups, setStudyGroups] = useState<StudyGroup[]>(INITIAL_STUDY_GROUPS);
  const [activeGroup, setActiveGroup] = useState<StudyGroup | null>(null);
  const [groupMembers, setGroupMembers] = useState<GroupMember[]>(INITIAL_GROUP_MEMBERS);

  // Messages
  const [conversations, setConversations] = useState<ChatConversation[]>(INITIAL_CONVERSATIONS);
  const [activeConversation, setActiveConversation] = useState<ChatConversation | null>(null);
  const [messages, setMessages] = useState<Record<string, ChatMessage[]>>(INITIAL_MESSAGES);
  const [stories, setStories] = useState<StoryItem[]>(INITIAL_STORIES);

  // Live Rooms
  const [liveRooms, setLiveRooms] = useState<LiveRoomInfo[]>(INITIAL_LIVE_ROOMS);
  const [activeLiveRoom, setActiveLiveRoom] = useState<LiveRoomInfo | null>(null);
  const [studentQueue, setStudentQueue] = useState<StudentQueueItem[]>(INITIAL_QUEUE);

  // Minutes & Finance
  const [studentMinutesBalance, setStudentMinutesBalance] = useState<number>(45);
  const [packages] = useState<MinutePackage[]>(INITIAL_PACKAGES);
  const [bankDetails, setBankDetails] = useState<BankPaymentInfo[]>(INITIAL_BANK_DETAILS);
  const [payments, setPayments] = useState<PaymentRequest[]>(INITIAL_PAYMENTS);
  const [loans, setLoans] = useState<MinuteLoanRequest[]>(INITIAL_LOANS);

  // Registration Bot
  const [registrationProgram, setRegistrationProgram] = useState<QuranProgram | null>(null);

  // Alerts
  const [permissionAlert, setPermissionAlert] = useState<string | null>(null);

  useEffect(() => {
    if (isDarkMode) {
      document.documentElement.classList.add('dark');
      localStorage.setItem('jazari_theme', 'dark');
    } else {
      document.documentElement.classList.remove('dark');
      localStorage.setItem('jazari_theme', 'light');
    }
  }, [isDarkMode]);

  useEffect(() => {
    document.documentElement.lang = currentLang;
    document.documentElement.dir = currentLang === 'ar' ? 'rtl' : 'ltr';
    localStorage.setItem('jazari_lang', currentLang);
  }, [currentLang]);

  const toggleDarkMode = () => setIsDarkMode((prev) => !prev);

  const setLanguage = (lang: AppLanguage) => {
    setCurrentLangState(lang);
  };

  const t = (key: string): string => {
    return TRANSLATIONS[currentLang]?.[key] || TRANSLATIONS.ar[key] || key;
  };

  const switchAccount = (account: AcademyAccount) => {
    setCurrentAccount(account);
  };

  const currentRole = currentAccount.role;

  // CMS Methods
  const updateBanner = (text: string) => setBannerText(text);
  const updateAbout = (text: string) => setAboutText(text);
  const addProgram = (prog: QuranProgram) => setPrograms((prev) => [prog, ...prev]);
  const updateProgram = (prog: QuranProgram) =>
    setPrograms((prev) => prev.map((p) => (p.id === prog.id ? prog : p)));
  const deleteProgram = (id: string) =>
    setPrograms((prev) => prev.filter((p) => p.id !== id));

  // Groups
  const openGroupChat = (group: StudyGroup) => {
    setActiveGroup(group);
    // Mark unread as 0
    setStudyGroups((prev) =>
      prev.map((g) => (g.id === group.id ? { ...g, unreadCount: 0 } : g))
    );
  };

  const closeGroupChat = () => setActiveGroup(null);

  const sendFriendRequest = (memberId: string) => {
    setGroupMembers((prev) =>
      prev.map((m) => (m.id === memberId ? { ...m, friendStatus: 'REQUEST_SENT' } : m))
    );
  };

  const acceptFriendRequest = (memberId: string) => {
    setGroupMembers((prev) =>
      prev.map((m) => (m.id === memberId ? { ...m, friendStatus: 'FRIENDS' } : m))
    );
  };

  // Messaging
  const openConversation = (conv: ChatConversation) => {
    setActiveConversation(conv);
    setConversations((prev) =>
      prev.map((c) => (c.id === conv.id ? { ...c, unreadCount: 0 } : c))
    );
  };

  const closeConversation = () => setActiveConversation(null);

  const sendMessage = (
    convId: string,
    text: string,
    isAudio = false,
    audioDurationSec = 0
  ) => {
    if (!text.trim() && !isAudio) return;
    const timeNow = new Date().toLocaleTimeString('ar-EG', {
      hour: '2-digit',
      minute: '2-digit',
    });

    const newMsg: ChatMessage = {
      id: `msg_${Date.now()}`,
      conversationId: convId,
      senderName: currentAccount.name,
      text: isAudio ? `تلاوة صوتية (${audioDurationSec} ث)` : text,
      timestamp: timeNow,
      isMine: true,
      isAudio,
      audioDurationSec,
      isRead: true,
    };

    setMessages((prev) => {
      const existing = prev[convId] || [];
      return { ...prev, [convId]: [...existing, newMsg] };
    });

    setConversations((prev) =>
      prev.map((c) =>
        c.id === convId
          ? {
              ...c,
              subtitle: isAudio ? `تلاوة صوتية (${audioDurationSec} ث)` : text,
              lastMessageTime: timeNow,
              hasAudioLastMessage: isAudio,
            }
          : c
      )
    );
  };

  // Live Rooms
  const openLiveRoom = (room?: LiveRoomInfo) => {
    setActiveLiveRoom(room || liveRooms[0]);
  };

  const closeLiveRoom = () => setActiveLiveRoom(null);

  const callStudentInQueue = (studentId: string) => {
    setStudentQueue((prev) =>
      prev.map((q) => {
        if (q.id === studentId) return { ...q, status: 'RECITING' };
        if (q.status === 'RECITING') return { ...q, status: 'COMPLETED' };
        return q;
      })
    );
  };

  const completeRecitation = (deductMin = 15) => {
    setStudentQueue((prev) =>
      prev.map((q) => (q.status === 'RECITING' ? { ...q, status: 'COMPLETED' } : q))
    );
    setStudentMinutesBalance((prev) => Math.max(0, prev - deductMin));
  };

  // Finance
  const saveBankDetail = (bank: BankPaymentInfo) => {
    setBankDetails((prev) => {
      const idx = prev.findIndex((b) => b.id === bank.id);
      if (idx >= 0) {
        const updated = [...prev];
        updated[idx] = bank;
        return updated;
      }
      return [...prev, bank];
    });
  };

  const deleteBankDetail = (bankId: string) => {
    setBankDetails((prev) => prev.filter((b) => b.id !== bankId));
  };

  const submitPayment = (
    pkgName: string,
    mins: number,
    amount: number,
    method: string,
    txId: string,
    phone: string,
    name: string
  ) => {
    const newReq: PaymentRequest = {
      id: `pay_${Date.now()}`,
      studentName: name.trim() || currentAccount.name,
      packageName: pkgName,
      minutes: mins,
      amount,
      method,
      transactionId: txId.trim() || `TXN-${Math.floor(100000 + Math.random() * 900000)}`,
      phone: phone.trim() || '+96650000000',
      status: 'PENDING',
      date: new Date().toISOString().split('T')[0],
    };
    setPayments((prev) => [newReq, ...prev]);
  };

  const approvePayment = (paymentId: string) => {
    setPayments((prev) =>
      prev.map((p) => {
        if (p.id === paymentId) {
          setStudentMinutesBalance((bal) => bal + p.minutes);
          return { ...p, status: 'APPROVED' };
        }
        return p;
      })
    );
  };

  const rejectPayment = (paymentId: string) => {
    setPayments((prev) =>
      prev.map((p) => (p.id === paymentId ? { ...p, status: 'REJECTED' } : p))
    );
  };

  const requestMinuteLoan = (studentName: string, mins: number, reason: string) => {
    const newLoan: MinuteLoanRequest = {
      id: `loan_${Date.now()}`,
      studentName: studentName.trim() || currentAccount.name,
      minutesRequested: mins,
      reason,
      date: new Date().toISOString().split('T')[0],
      status: 'PENDING',
    };
    setLoans((prev) => [newLoan, ...prev]);
  };

  const approveLoan = (loanId: string) => {
    setLoans((prev) =>
      prev.map((l) => {
        if (l.id === loanId) {
          setStudentMinutesBalance((bal) => bal + l.minutesRequested);
          return { ...l, status: 'APPROVED' };
        }
        return l;
      })
    );
  };

  const rejectLoan = (loanId: string) => {
    setLoans((prev) =>
      prev.map((l) => (l.id === loanId ? { ...l, status: 'REJECTED' } : l))
    );
  };

  // Bot Registration & Enrollment
  const openRegistrationBot = (program: QuranProgram) => {
    setRegistrationProgram(program);
  };

  const closeRegistrationBot = () => {
    setRegistrationProgram(null);
  };

  const enrollStudentInGroup = (
    name: string,
    gender: GroupGender,
    timing: GroupTiming,
    qiraah: string,
    program: QuranProgram
  ): StudyGroup => {
    // Determine the exact group ID from the 7 groups
    let targetGroupId = 'grp_general';
    if (gender === 'MALE') {
      if (timing === 'MORNING') targetGroupId = 'grp_m_morning';
      else if (timing === 'EVENING') targetGroupId = 'grp_m_evening';
      else if (timing === 'NIGHT') targetGroupId = 'grp_m_night';
    } else if (gender === 'FEMALE') {
      if (timing === 'MORNING') targetGroupId = 'grp_f_morning';
      else if (timing === 'EVENING') targetGroupId = 'grp_f_evening';
      else if (timing === 'NIGHT') targetGroupId = 'grp_f_night';
    }

    // 1. Increment member counts
    setStudyGroups((prev) =>
      prev.map((g) => {
        if (g.id === targetGroupId || (g.id === 'grp_general' && targetGroupId !== 'grp_general')) {
          return { ...g, membersCount: g.membersCount + 1 };
        }
        return g;
      })
    );

    // 2. Increment program enrolled count
    setPrograms((prev) =>
      prev.map((p) => (p.id === program.id ? { ...p, enrolledCount: p.enrolledCount + 1 } : p))
    );

    // 3. Grant +30 welcome bonus minutes
    setStudentMinutesBalance((prev) => prev + 30);

    // 4. Add member
    const newMember: GroupMember = {
      id: `gm_std_${Date.now()}`,
      name: name.trim() || 'طالب المقرأة الجديد',
      role: 'STUDENT',
      isOnline: true,
      qiraah,
      friendStatus: 'NONE',
    };
    setGroupMembers((prev) => [...prev, newMember]);

    // 5. Add welcome message into target group
    const targetGroup = studyGroups.find((g) => g.id === targetGroupId) || studyGroups[0];
    const timeNow = new Date().toLocaleTimeString('ar-EG', { hour: '2-digit', minute: '2-digit' });
    const welcomeMsg: ChatMessage = {
      id: `wmsg_${Date.now()}`,
      conversationId: targetGroupId,
      senderName: 'بوت التسجيل والقبول الآلي 🤖',
      text: `🎉 نرحب بالطالب المبارك: (${name.trim() || 'طالب علم'}) الملتحق بحلقة [${targetGroup.name}] في مسار [${program.title}] برواية (${qiraah}). مبارك انضمامك وتم تفعيل رصيدك المجاني!`,
      timestamp: timeNow,
      isMine: false,
      isRead: true,
    };

    setMessages((prev) => {
      const existing = prev[targetGroupId] || [];
      return { ...prev, [targetGroupId]: [...existing, welcomeMsg] };
    });

    return targetGroup;
  };

  // Role Protection
  const dismissPermissionAlert = () => setPermissionAlert(null);

  const triggerCallingOrBroadcast = (actionName: string, onAuthorized: () => void) => {
    if (currentRole === 'STUDENT') {
      setPermissionAlert(
        `تنبيه الصلاحيات: بدء ${actionName} مخصص للمعلم والمشرف فقط. بصفتك طالباً، يمكنك الاستماع إلى البث والمشاركة في التسميع عند استدعائك من الشيخ في الطابور.`
      );
    } else {
      onAuthorized();
    }
  };

  return (
    <AppContext.Provider
      value={{
        isDarkMode,
        toggleDarkMode,
        currentLang,
        setLanguage,
        t,
        currentAccount,
        availableAccounts: INITIAL_ACCOUNTS,
        switchAccount,
        currentRole,
        currentTab,
        setCurrentTab,
        bannerText,
        updateBanner,
        aboutText,
        updateAbout,
        programs,
        addProgram,
        updateProgram,
        deleteProgram,
        studyGroups,
        activeGroup,
        openGroupChat,
        closeGroupChat,
        groupMembers,
        sendFriendRequest,
        acceptFriendRequest,
        conversations,
        activeConversation,
        openConversation,
        closeConversation,
        messages,
        sendMessage,
        stories,
        liveRooms,
        activeLiveRoom,
        openLiveRoom,
        closeLiveRoom,
        studentQueue,
        callStudentInQueue,
        completeRecitation,
        studentMinutesBalance,
        packages,
        bankDetails,
        saveBankDetail,
        deleteBankDetail,
        payments,
        submitPayment,
        approvePayment,
        rejectPayment,
        loans,
        requestMinuteLoan,
        approveLoan,
        rejectLoan,
        registrationProgram,
        openRegistrationBot,
        closeRegistrationBot,
        enrollStudentInGroup,
        permissionAlert,
        setPermissionAlert,
        dismissPermissionAlert,
        triggerCallingOrBroadcast,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};

export const useApp = () => {
  const context = useContext(AppContext);
  if (!context) {
    throw new Error('useApp must be used within an AppProvider');
  }
  return context;
};
