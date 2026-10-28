import React, { useState } from 'react';
import { useApp } from '../context/AppContext';
import {
  MessageSquare,
  Search,
  Video,
  Mic,
  Send,
  ArrowRight,
  ShieldCheck,
  CheckCheck,
  Sparkles,
  Play,
  Volume2,
} from 'lucide-react';
import { ChatConversation } from '../types';

export const ChatsScreen: React.FC = () => {
  const {
    conversations,
    activeConversation,
    openConversation,
    closeConversation,
    messages,
    sendMessage,
    stories,
    openLiveRoom,
  } = useApp();

  const [activeTab, setActiveTab] = useState<'ALL' | 'DIRECT' | 'GROUPS' | 'CHANNELS' | 'UNREAD'>('ALL');
  const [searchQuery, setSearchQuery] = useState('');
  const [chatInput, setChatInput] = useState('');

  // Filter conversations by tab and search
  const filtered = conversations.filter((conv) => {
    if (searchQuery.trim()) {
      const q = searchQuery.toLowerCase();
      if (!conv.title.toLowerCase().includes(q) && !conv.subtitle.toLowerCase().includes(q)) {
        return false;
      }
    }
    if (activeTab === 'DIRECT') return !conv.isGroup && !conv.isChannel;
    if (activeTab === 'GROUPS') return conv.isGroup;
    if (activeTab === 'CHANNELS') return conv.isChannel;
    if (activeTab === 'UNREAD') return conv.unreadCount > 0;
    return true;
  });

  // Active Direct Conversation view
  if (activeConversation) {
    const convMsgs = messages[activeConversation.id] || [];

    const handleSend = (e?: React.FormEvent) => {
      if (e) e.preventDefault();
      if (!chatInput.trim()) return;
      sendMessage(activeConversation.id, chatInput);
      setChatInput('');
    };

    return (
      <div className="fixed inset-0 top-[60px] bottom-[56px] z-30 bg-slate-100 dark:bg-slate-950 flex flex-col max-w-4xl mx-auto shadow-2xl">
        {/* Header */}
        <div className="px-3 py-2 glass-panel border-b border-slate-200 dark:border-slate-800 flex items-center justify-between shadow-sm">
          <div className="flex items-center gap-2.5 min-w-0">
            <button
              onClick={closeConversation}
              className="p-1 rounded-lg hover:bg-slate-200 dark:hover:bg-slate-800 text-slate-600 dark:text-slate-300"
            >
              <ArrowRight className="w-5 h-5 rtl:rotate-180" />
            </button>
            <div className="w-9 h-9 rounded-full bg-gold/20 flex items-center justify-center text-base font-bold text-slate-900 dark:text-slate-100 flex-shrink-0">
              {activeConversation.avatarUrl || '📖'}
            </div>
            <div className="min-w-0">
              <div className="flex items-center gap-1">
                <h3 className="font-extrabold text-sm text-slate-900 dark:text-slate-100 truncate">
                  {activeConversation.title}
                </h3>
                {activeConversation.isVerified && (
                  <ShieldCheck className="w-3.5 h-3.5 text-skyBlue-500 flex-shrink-0" />
                )}
              </div>
              <span className="text-[10px] text-emerald-600 dark:text-emerald-400 font-bold block">
                متصل الآن • يجيب خلال دقائق
              </span>
            </div>
          </div>

          {activeConversation.liveRoomId && (
            <button
              onClick={() => openLiveRoom()}
              className="px-2.5 py-1.5 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-bold text-xs flex items-center gap-1 shadow-sm"
            >
              <Video className="w-3.5 h-3.5" />
              <span className="hidden sm:inline">دخول المجلس</span>
            </button>
          )}
        </div>

        {/* Messages Body */}
        <div className="flex-1 overflow-y-auto p-3 sm:p-4 space-y-3 bg-skyBlue-50/30 dark:bg-slate-950/70">
          {convMsgs.map((msg) => (
            <div
              key={msg.id}
              className={`flex flex-col ${msg.isMine ? 'items-end' : 'items-start'}`}
            >
              <div
                className={`max-w-[85%] rounded-2xl px-3.5 py-2.5 text-xs sm:text-sm shadow-sm ${
                  msg.isMine
                    ? 'bg-skyBlue-600 text-white rounded-br-none'
                    : 'bg-white dark:bg-slate-800 text-slate-900 dark:text-slate-100 border border-slate-200 dark:border-slate-700 rounded-bl-none'
                }`}
              >
                {!msg.isMine && (
                  <span className="block text-[10px] font-bold text-gold mb-0.5">
                    {msg.senderName}
                  </span>
                )}

                {msg.isAudio ? (
                  <div className="flex items-center gap-3 p-1">
                    <button className="w-8 h-8 rounded-full bg-white/20 flex items-center justify-center text-white">
                      <Play className="w-4 h-4 fill-white" />
                    </button>
                    <div>
                      <div className="font-bold text-xs">{msg.text}</div>
                      <div className="flex items-center gap-1 text-[10px] opacity-80 mt-0.5">
                        <Volume2 className="w-3 h-3" />
                        <span>0:42 / {msg.audioDurationSec || 18} ثانية</span>
                      </div>
                    </div>
                  </div>
                ) : (
                  <p className="leading-relaxed whitespace-pre-line">{msg.text}</p>
                )}

                <div
                  className={`flex items-center justify-end gap-1 text-[9px] mt-1 ${
                    msg.isMine ? 'text-skyBlue-200' : 'text-slate-400'
                  }`}
                >
                  <span>{msg.timestamp}</span>
                  {msg.isMine && <CheckCheck className="w-3 h-3 text-skyBlue-200" />}
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Input Bar */}
        <form
          onSubmit={handleSend}
          className="p-2 sm:p-3 glass-panel border-t border-slate-200 dark:border-slate-800 flex items-center gap-2"
        >
          <button
            type="button"
            onClick={() =>
              sendMessage(
                activeConversation.id,
                'تسجيل صوتي: قراءة سورة الفاتحة للتصحيح',
                true,
                30
              )
            }
            className="p-2 rounded-xl bg-slate-100 hover:bg-slate-200 dark:bg-slate-800 dark:hover:bg-slate-700 text-slate-600 dark:text-slate-300"
            title="إرسال تلاوة صوتية"
          >
            <Mic className="w-4 h-4 text-rose-500" />
          </button>

          <input
            type="text"
            value={chatInput}
            onChange={(e) => setChatInput(e.target.value)}
            placeholder="اكتب رسالتك لفضيلة الشيخ..."
            className="flex-1 px-3 py-2 text-xs sm:text-sm rounded-xl border border-slate-300 dark:border-slate-700 bg-white dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none focus:ring-2 focus:ring-skyBlue-500"
          />

          <button
            type="submit"
            disabled={!chatInput.trim()}
            className="p-2.5 rounded-xl bg-skyBlue-600 hover:bg-skyBlue-700 disabled:opacity-50 text-white font-bold shadow"
          >
            <Send className="w-4 h-4" />
          </button>
        </form>
      </div>
    );
  }

  return (
    <div className="space-y-3 pb-20 max-w-4xl mx-auto px-3 sm:px-4 pt-3">
      {/* 1. Stories Top Bar (حالات وفوائد الشيوخ) */}
      <div className="glass-panel rounded-2xl p-3 border border-slate-200 dark:border-slate-800 shadow-sm overflow-x-auto">
        <div className="flex items-center gap-3 min-w-max">
          {stories.map((story) => (
            <div
              key={story.id}
              onClick={() => story.isLiveNow && openLiveRoom()}
              className="flex flex-col items-center gap-1 cursor-pointer group"
            >
              <div
                className={`relative w-12 h-12 rounded-full p-0.5 flex items-center justify-center transition-transform group-hover:scale-105 ${
                  story.isLiveNow
                    ? 'bg-gradient-to-tr from-rose-500 via-amber-500 to-skyBlue-500 animate-pulse'
                    : 'bg-gradient-to-tr from-gold to-amber-600'
                }`}
              >
                <div className="w-full h-full rounded-full bg-white dark:bg-slate-900 flex items-center justify-center text-xs font-bold text-slate-900 dark:text-slate-100 border border-white/50">
                  📖
                </div>
                {story.isLiveNow && (
                  <span className="absolute -bottom-1 px-1 rounded-full text-[8px] font-extrabold bg-rose-600 text-white uppercase tracking-tighter">
                    Live
                  </span>
                )}
              </div>
              <span className="text-[10px] font-bold text-slate-700 dark:text-slate-300 max-w-[65px] truncate text-center">
                {story.author}
              </span>
            </div>
          ))}
        </div>
      </div>

      {/* 2. Search Field */}
      <div className="relative">
        <Search className="w-4 h-4 absolute start-3 top-3 text-slate-400" />
        <input
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder="ابحث في محادثات المشرف، المعلمين، والإعلانات..."
          className="w-full ps-9 pe-4 py-2 text-xs sm:text-sm rounded-xl border border-slate-200 dark:border-slate-800 bg-white dark:bg-slate-900 text-slate-900 dark:text-slate-100 shadow-sm focus:outline-none focus:ring-2 focus:ring-skyBlue-500"
        />
      </div>

      {/* 3. Telegram Tabs */}
      <div className="flex items-center gap-1.5 overflow-x-auto pb-1 text-xs font-bold">
        {[
          { id: 'ALL', label: 'الكل' },
          { id: 'DIRECT', label: 'محادثات خاصة' },
          { id: 'GROUPS', label: 'المجموعات' },
          { id: 'CHANNELS', label: 'الإعلانات الرسمية' },
          { id: 'UNREAD', label: 'غير مقروءة' },
        ].map((tab) => (
          <button
            key={tab.id}
            onClick={() => setActiveTab(tab.id as any)}
            className={`px-3 py-1.5 rounded-xl whitespace-nowrap transition-colors ${
              activeTab === tab.id
                ? 'bg-skyBlue-600 text-white shadow-sm'
                : 'bg-white dark:bg-slate-800 text-slate-600 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-slate-700 border border-slate-200 dark:border-slate-700'
            }`}
          >
            {tab.label}
          </button>
        ))}
      </div>

      {/* 4. Conversations List */}
      <div className="glass-panel rounded-2xl border border-slate-200 dark:border-slate-800 divide-y divide-slate-100 dark:divide-slate-800 shadow-sm overflow-hidden">
        {filtered.length === 0 ? (
          <div className="p-8 text-center text-slate-400 text-xs">
            لا توجد محادثات تطابق هذا التصنيف
          </div>
        ) : (
          filtered.map((conv) => (
            <div
              key={conv.id}
              onClick={() => openConversation(conv)}
              className="p-3 sm:p-3.5 hover:bg-skyBlue-50/50 dark:hover:bg-slate-800/50 cursor-pointer flex items-center justify-between gap-3 transition-colors"
            >
              <div className="flex items-center gap-3 min-w-0">
                <div className="relative w-11 h-11 rounded-2xl bg-gradient-to-br from-gold/30 to-amber-600/30 flex items-center justify-center text-lg flex-shrink-0 border border-gold/40">
                  {conv.avatarUrl || '💬'}
                  {conv.unreadCount > 0 && (
                    <span className="absolute -top-1 -right-1 w-3 h-3 bg-skyBlue-500 rounded-full border-2 border-white dark:border-slate-900" />
                  )}
                </div>

                <div className="min-w-0">
                  <div className="flex items-center gap-1.5">
                    <h3 className="font-extrabold text-xs sm:text-sm text-slate-900 dark:text-slate-100 truncate">
                      {conv.title}
                    </h3>
                    {conv.isVerified && (
                      <ShieldCheck className="w-3.5 h-3.5 text-skyBlue-600 dark:text-skyBlue-400 flex-shrink-0" />
                    )}
                  </div>
                  <p className="text-xs text-slate-500 dark:text-slate-400 truncate mt-0.5">
                    {conv.subtitle}
                  </p>
                </div>
              </div>

              <div className="flex flex-col items-end gap-1 flex-shrink-0">
                <span className="text-[10px] text-slate-400">{conv.lastMessageTime}</span>
                {conv.unreadCount > 0 && (
                  <span className="px-1.5 py-0.5 rounded-full text-[10px] font-bold bg-skyBlue-600 text-white min-w-[18px] text-center">
                    {conv.unreadCount}
                  </span>
                )}
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};
