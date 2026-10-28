import React, { useState } from 'react';
import { useApp } from '../context/AppContext';
import {
  Sun,
  Moon,
  Globe,
  Video,
  UserCheck,
  ShieldAlert,
  GraduationCap,
  Sparkles,
  ChevronDown,
} from 'lucide-react';
import { AppLanguage } from '../types';

export const Header: React.FC = () => {
  const {
    isDarkMode,
    toggleDarkMode,
    currentLang,
    setLanguage,
    t,
    currentAccount,
    availableAccounts,
    switchAccount,
    currentRole,
    openLiveRoom,
    studentMinutesBalance,
  } = useApp();

  const [showAccountDropdown, setShowAccountDropdown] = useState(false);
  const [showLangDropdown, setShowLangDropdown] = useState(false);

  return (
    <header className="sticky top-0 z-40 w-full glass-panel border-b shadow-sm transition-colors duration-200">
      <div className="max-w-7xl mx-auto px-3 sm:px-6 py-2.5 flex items-center justify-between gap-2 sm:gap-4">
        {/* Logo and Academy Title */}
        <div className="flex items-center gap-2.5 sm:gap-3 min-w-0">
          <div className="relative flex-shrink-0 w-10 h-10 sm:w-11 sm:h-11 rounded-xl bg-gradient-to-br from-gold to-amber-600 flex items-center justify-center text-white shadow-md border border-amber-300">
            <span className="text-xl sm:text-2xl select-none">📖</span>
            <div className="absolute -top-1 -right-1 w-3.5 h-3.5 bg-emerald-500 rounded-full border-2 border-white dark:border-slate-900 animate-pulse" />
          </div>

          <div className="min-w-0">
            <div className="flex items-center gap-1.5 flex-wrap">
              <h1 className="text-sm sm:text-base font-extrabold text-skyBlue-700 dark:text-skyBlue-300 truncate tracking-tight">
                {t('appName')}
              </h1>
              <span className="hidden sm:inline-flex items-center px-1.5 py-0.5 rounded text-[10px] font-bold bg-amber-100 text-amber-800 dark:bg-amber-900/50 dark:text-amber-300 border border-amber-300/60">
                السند المتصل
              </span>
            </div>
            <p className="text-[10px] sm:text-xs text-slate-500 dark:text-slate-400 truncate">
              {t('tagline')}
            </p>
          </div>
        </div>

        {/* Action Controls: Live Room, Account Switcher, Language, Theme */}
        <div className="flex items-center gap-1 sm:gap-2 flex-shrink-0">
          {/* Quick LiveKit Recitation Room Button */}
          <button
            onClick={() => openLiveRoom()}
            className="flex items-center gap-1.5 px-2.5 sm:px-3 py-1.5 rounded-lg bg-gradient-to-r from-emerald-600 to-teal-600 hover:from-emerald-700 hover:to-teal-700 text-white text-xs font-bold shadow-sm transition-transform active:scale-95"
            title="دخول مجلس الإقراء والتسميع المباشر (LiveKit)"
          >
            <Video className="w-3.5 h-3.5 animate-pulse text-amber-300" />
            <span className="hidden md:inline">{t('liveHall')}</span>
            <span className="flex items-center gap-1 text-[10px] bg-emerald-800/80 px-1 rounded text-emerald-100 font-mono">
              Live
            </span>
          </button>

          {/* Quick Minutes Badge for Students */}
          {currentRole === 'STUDENT' && (
            <div className="hidden sm:flex items-center gap-1 px-2 py-1 rounded-lg bg-skyBlue-100 text-skyBlue-800 dark:bg-skyBlue-950 dark:text-skyBlue-300 text-xs font-bold border border-skyBlue-200 dark:border-skyBlue-800">
              <Sparkles className="w-3 h-3 text-gold" />
              <span>{studentMinutesBalance} د</span>
            </div>
          )}

          {/* Account Switcher Badge & Dropdown */}
          <div className="relative">
            <button
              onClick={() => setShowAccountDropdown(!showAccountDropdown)}
              className="flex items-center gap-1.5 px-2 sm:px-2.5 py-1.5 rounded-lg bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 hover:border-gold text-slate-700 dark:text-slate-200 text-xs transition-colors shadow-sm"
              title="تبديل الحساب والصلاحيات"
            >
              <span className="text-sm">{currentAccount.avatar}</span>
              <div className="hidden lg:flex flex-col text-start leading-tight">
                <span className="font-bold text-[11px] truncate max-w-[110px]">
                  {currentAccount.name}
                </span>
                <span className="text-[9px] text-amber-600 dark:text-amber-400 font-medium">
                  {currentRole === 'ADMIN' ? 'المشرف (المدير)' : currentRole === 'TEACHER' ? 'معلم المقرأة' : 'طالب علم'}
                </span>
              </div>
              <ChevronDown className="w-3 h-3 text-slate-400" />
            </button>

            {showAccountDropdown && (
              <div
                className="absolute end-0 mt-1.5 w-64 sm:w-72 bg-white dark:bg-slate-800 rounded-xl shadow-xl border border-slate-200 dark:border-slate-700 p-2 z-50 animate-in fade-in zoom-in-95"
                onClick={() => setShowAccountDropdown(false)}
              >
                <div className="px-2 py-1.5 border-b border-slate-100 dark:border-slate-700 mb-1 text-[11px] font-bold text-slate-500 dark:text-slate-400">
                  تبديل الحساب السريع (Google Account Switcher):
                </div>

                {availableAccounts.map((acc) => (
                  <button
                    key={acc.id}
                    onClick={() => switchAccount(acc)}
                    className={`w-full text-start flex items-center gap-2.5 p-2 rounded-lg text-xs transition-colors mb-1 ${
                      acc.id === currentAccount.id
                        ? 'bg-skyBlue-50 dark:bg-skyBlue-950/60 border border-skyBlue-300 dark:border-skyBlue-800 text-skyBlue-900 dark:text-skyBlue-200 font-bold'
                        : 'hover:bg-slate-100 dark:hover:bg-slate-700/60 text-slate-700 dark:text-slate-300'
                    }`}
                  >
                    <span className="text-xl flex-shrink-0">{acc.avatar}</span>
                    <div className="min-w-0 flex-1">
                      <div className="flex items-center justify-between">
                        <span className="font-bold truncate">{acc.name}</span>
                        {acc.role === 'ADMIN' && (
                          <span className="text-[9px] bg-amber-100 text-amber-800 dark:bg-amber-900 dark:text-amber-300 px-1 rounded">
                            مدير
                          </span>
                        )}
                      </div>
                      <div className="text-[10px] text-slate-400 truncate">{acc.email}</div>
                    </div>
                  </button>
                ))}

                <div className="mt-2 pt-2 border-t border-slate-100 dark:border-slate-700 text-center">
                  <div className="text-[10px] text-emerald-600 dark:text-emerald-400 font-bold flex items-center justify-center gap-1">
                    <UserCheck className="w-3 h-3" />
                    <span>تم الدخول السريع: {currentAccount.email}</span>
                  </div>
                </div>
              </div>
            )}
          </div>

          {/* Language Switcher */}
          <div className="relative">
            <button
              onClick={() => setShowLangDropdown(!showLangDropdown)}
              className="p-1.5 sm:px-2 sm:py-1.5 rounded-lg bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 hover:border-gold text-slate-700 dark:text-slate-200 text-xs flex items-center gap-1 shadow-sm"
              title="تغيير اللغة - Language"
            >
              <Globe className="w-3.5 h-3.5 text-skyBlue-600 dark:text-skyBlue-400" />
              <span className="font-bold text-[11px] uppercase">{currentLang}</span>
            </button>

            {showLangDropdown && (
              <div
                className="absolute end-0 mt-1.5 w-32 bg-white dark:bg-slate-800 rounded-xl shadow-xl border border-slate-200 dark:border-slate-700 p-1.5 z-50"
                onClick={() => setShowLangDropdown(false)}
              >
                <button
                  onClick={() => setLanguage('ar')}
                  className={`w-full text-start px-2 py-1.5 rounded-md text-xs font-bold ${
                    currentLang === 'ar' ? 'bg-skyBlue-50 text-skyBlue-600 dark:bg-skyBlue-950' : 'hover:bg-slate-100 dark:hover:bg-slate-700'
                  }`}
                >
                  العربية (RTL)
                </button>
                <button
                  onClick={() => setLanguage('am')}
                  className={`w-full text-start px-2 py-1.5 rounded-md text-xs font-bold ${
                    currentLang === 'am' ? 'bg-skyBlue-50 text-skyBlue-600 dark:bg-skyBlue-950' : 'hover:bg-slate-100 dark:hover:bg-slate-700'
                  }`}
                >
                  አማርኛ (Amharic)
                </button>
                <button
                  onClick={() => setLanguage('en')}
                  className={`w-full text-start px-2 py-1.5 rounded-md text-xs font-bold ${
                    currentLang === 'en' ? 'bg-skyBlue-50 text-skyBlue-600 dark:bg-skyBlue-950' : 'hover:bg-slate-100 dark:hover:bg-slate-700'
                  }`}
                >
                  English (LTR)
                </button>
              </div>
            )}
          </div>

          {/* Day / Night Mode Toggle */}
          <button
            onClick={toggleDarkMode}
            className="p-1.5 sm:p-2 rounded-lg bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 text-amber-500 dark:text-amber-300 hover:border-gold transition-colors shadow-sm"
            title={isDarkMode ? 'التحويل للوضع النهاري (Day Mode)' : 'التحويل للوضع الليلي (Night Mode)'}
          >
            {isDarkMode ? <Sun className="w-4 h-4" /> : <Moon className="w-4 h-4 text-slate-700" />}
          </button>
        </div>
      </div>
    </header>
  );
};
