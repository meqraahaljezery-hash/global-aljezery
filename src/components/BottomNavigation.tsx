import React from 'react';
import { useApp } from '../context/AppContext';
import { Home, MessageSquare, Users, Wallet, Settings } from 'lucide-react';

export const BottomNavigation: React.FC = () => {
  const { currentTab, setCurrentTab, t, conversations, studyGroups, currentRole, studentMinutesBalance } = useApp();

  const totalChatUnread = conversations.reduce((acc, c) => acc + c.unreadCount, 0);
  const totalGroupUnread = studyGroups.reduce((acc, g) => acc + (g.unreadCount || 0), 0);

  const tabs = [
    {
      id: 'home',
      label: t('tabHome'),
      icon: Home,
    },
    {
      id: 'chats',
      label: t('tabChats'),
      icon: MessageSquare,
      badge: totalChatUnread > 0 ? totalChatUnread : undefined,
    },
    {
      id: 'groups',
      label: t('tabGroups'),
      icon: Users,
      badge: totalGroupUnread > 0 ? totalGroupUnread : undefined,
    },
    {
      id: 'account',
      label: t('tabAccount'),
      icon: Wallet,
      badge: currentRole === 'STUDENT' ? `${studentMinutesBalance}د` : undefined,
      badgeColor: 'bg-emerald-500 text-white',
    },
    {
      id: 'settings',
      label: t('tabSettings'),
      icon: Settings,
      badge: currentRole === 'ADMIN' ? 'CMS' : undefined,
      badgeColor: 'bg-amber-500 text-white',
    },
  ];

  return (
    <nav className="fixed bottom-0 inset-x-0 z-40 glass-panel border-t shadow-lg safe-bottom">
      <div className="max-w-md mx-auto px-2 py-1 flex items-center justify-around">
        {tabs.map((tab) => {
          const Icon = tab.icon;
          const isActive = currentTab === tab.id;

          return (
            <button
              key={tab.id}
              onClick={() => setCurrentTab(tab.id as any)}
              className={`relative flex flex-col items-center justify-center py-1.5 px-3 rounded-xl transition-all duration-200 ${
                isActive
                  ? 'text-skyBlue-600 dark:text-skyBlue-400 font-extrabold scale-105'
                  : 'text-slate-500 dark:text-slate-400 hover:text-slate-700 dark:hover:text-slate-200'
              }`}
            >
              <div className="relative">
                <Icon className={`w-5 h-5 transition-transform ${isActive ? 'stroke-[2.5px]' : 'stroke-2'}`} />
                {tab.badge && (
                  <span
                    className={`absolute -top-1.5 -end-2.5 text-[9px] font-bold px-1 min-w-[15px] h-[15px] rounded-full flex items-center justify-center leading-none ${
                      tab.badgeColor || 'bg-rose-500 text-white'
                    }`}
                  >
                    {tab.badge}
                  </span>
                )}
              </div>
              <span className="text-[11px] mt-0.5 tracking-tight">{tab.label}</span>
              {isActive && (
                <span className="w-1.5 h-1.5 rounded-full bg-gold mt-0.5 animate-pulse" />
              )}
            </button>
          );
        })}
      </div>
    </nav>
  );
};
