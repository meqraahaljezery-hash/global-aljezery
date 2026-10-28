import React from 'react';
import { useApp } from './context/AppContext';
import { Header } from './components/Header';
import { BottomNavigation } from './components/BottomNavigation';
import { HomeScreen } from './components/HomeScreen';
import { ChatsScreen } from './components/ChatsScreen';
import { GroupsScreen } from './components/GroupsScreen';
import { MyAccountScreen } from './components/MyAccountScreen';
import { SettingsScreen } from './components/SettingsScreen';
import { RegistrationBotModal } from './components/RegistrationBotModal';
import { LiveKitRecitationRoom } from './components/LiveKitRecitationRoom';
import { ShieldAlert, X } from 'lucide-react';

export const AppContent: React.FC = () => {
  const {
    currentTab,
    permissionAlert,
    dismissPermissionAlert,
    activeLiveRoom,
  } = useApp();

  return (
    <div className="min-h-screen flex flex-col bg-skyBlue-50/50 dark:bg-slate-950 text-slate-800 dark:text-slate-100 transition-colors duration-200">
      {/* Top Header */}
      <Header />

      {/* Main Tab Content */}
      <main className="flex-1 w-full">
        {currentTab === 'home' && <HomeScreen />}
        {currentTab === 'chats' && <ChatsScreen />}
        {currentTab === 'groups' && <GroupsScreen />}
        {currentTab === 'account' && <MyAccountScreen />}
        {currentTab === 'settings' && <SettingsScreen />}
      </main>

      {/* Floating Bottom Navigation */}
      <BottomNavigation />

      {/* Interactive Telegram Registration Chatbot */}
      <RegistrationBotModal />

      {/* LiveKit Recitation Audio/Video Room */}
      {activeLiveRoom && <LiveKitRecitationRoom />}

      {/* Permissions Restriction Warning Alert Dialog */}
      {permissionAlert && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-4 animate-in fade-in">
          <div className="bg-white dark:bg-slate-900 rounded-2xl max-w-sm w-full p-5 border border-slate-200 dark:border-slate-800 shadow-2xl text-center space-y-3">
            <div className="w-12 h-12 rounded-full bg-rose-100 dark:bg-rose-950/60 text-rose-600 dark:text-rose-400 flex items-center justify-center mx-auto">
              <ShieldAlert className="w-6 h-6" />
            </div>

            <h3 className="font-extrabold text-base text-slate-900 dark:text-slate-100">
              تنبيه صلاحيات البث والمكالمات
            </h3>

            <p className="text-xs text-slate-600 dark:text-slate-300 leading-relaxed">
              {permissionAlert}
            </p>

            <button
              onClick={dismissPermissionAlert}
              className="w-full py-2.5 rounded-xl bg-skyBlue-600 hover:bg-skyBlue-700 text-white font-extrabold text-xs shadow-md transition-transform active:scale-95"
            >
              حسناً، فهمت
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default function App() {
  return <AppContent />;
}
