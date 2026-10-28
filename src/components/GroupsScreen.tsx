import React, { useState } from 'react';
import { useApp } from '../context/AppContext';
import {
  Users,
  Radio,
  Phone,
  Video,
  Info,
  UserPlus,
  MessageSquare,
  ShieldCheck,
  Send,
  X,
  Sparkles,
  ArrowRight,
  Clock,
  Mic,
  Smile,
  Paperclip,
  Check,
  CheckCheck,
  UserCheck,
} from 'lucide-react';
import { GroupMember, StudyGroup, UserRole } from '../types';

export const GroupsScreen: React.FC = () => {
  const {
    studyGroups,
    activeGroup,
    openGroupChat,
    closeGroupChat,
    groupMembers,
    sendFriendRequest,
    acceptFriendRequest,
    messages,
    sendMessage,
    openLiveRoom,
    openConversation,
    conversations,
    setCurrentTab,
    currentRole,
    triggerCallingOrBroadcast,
  } = useApp();

  const [showProfileModal, setShowProfileModal] = useState(false);
  const [inputText, setInputText] = useState('');

  // The 7 groups list view
  if (!activeGroup) {
    return (
      <div className="space-y-4 pb-20 max-w-4xl mx-auto px-3 sm:px-4 pt-3">
        {/* Header Title */}
        <div className="glass-panel rounded-2xl p-4 border border-skyBlue-200 dark:border-skyBlue-900 bg-gradient-to-r from-skyBlue-50 to-white dark:from-slate-900 dark:to-slate-950 flex items-center justify-between">
          <div>
            <div className="flex items-center gap-2">
              <Users className="w-5 h-5 text-gold" />
              <h2 className="text-base sm:text-lg font-extrabold text-skyBlue-800 dark:text-skyBlue-300">
                الحلقات السبع الدراسية (مجموعات التليجرام)
              </h2>
            </div>
            <p className="text-xs text-slate-500 dark:text-slate-400 mt-0.5">
              حلقات الرجال والنساء مقسمة حسب الفترات: صباحي، مسائي، ليلي، والملتقى العام
            </p>
          </div>

          <span className="px-2.5 py-1 rounded-full text-xs font-bold bg-amber-100 text-amber-800 dark:bg-amber-900 dark:text-amber-200 border border-amber-300">
            7 مجموعات
          </span>
        </div>

        {/* The 7 Study Groups Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
          {studyGroups.map((group) => {
            const isLive = group.isLiveBroadcastActive;
            const timingIcon =
              group.timing === 'MORNING'
                ? '🌅 صباحي'
                : group.timing === 'EVENING'
                ? '🌇 مسائي'
                : group.timing === 'NIGHT'
                ? '🌙 ليلي'
                : '🌍 عام';

            return (
              <div
                key={group.id}
                onClick={() => openGroupChat(group)}
                className="glass-panel rounded-2xl p-4 border border-slate-200 dark:border-slate-800 hover:border-gold dark:hover:border-gold/60 cursor-pointer transition-all duration-200 shadow-sm hover:shadow-md flex flex-col justify-between"
              >
                <div>
                  <div className="flex items-start justify-between gap-2">
                    <div className="flex items-center gap-2.5">
                      <div className="w-10 h-10 rounded-xl bg-gradient-to-br from-skyBlue-500 to-skyBlue-700 flex items-center justify-center text-white font-extrabold text-sm shadow">
                        {group.name.slice(0, 2)}
                      </div>
                      <div>
                        <h3 className="font-extrabold text-sm sm:text-base text-slate-900 dark:text-slate-100">
                          {group.name}
                        </h3>
                        <span className="text-[11px] text-slate-500 dark:text-slate-400">
                          {group.sheikhSupervisor}
                        </span>
                      </div>
                    </div>

                    {isLive && (
                      <span className="flex items-center gap-1 px-2 py-0.5 rounded-full text-[10px] font-extrabold bg-rose-500 text-white animate-pulse">
                        <Radio className="w-3 h-3" />
                        بث مباشر
                      </span>
                    )}
                  </div>

                  <p className="text-xs text-slate-600 dark:text-slate-300 mt-2.5 line-clamp-2 leading-relaxed">
                    {group.description}
                  </p>
                </div>

                <div className="mt-3.5 pt-2.5 border-t border-slate-100 dark:border-slate-800 flex items-center justify-between text-xs">
                  <div className="flex items-center gap-2 text-slate-500 dark:text-slate-400">
                    <span className="font-bold text-skyBlue-700 dark:text-skyBlue-300">
                      {timingIcon}
                    </span>
                    <span>•</span>
                    <span>{group.membersCount} عضو</span>
                  </div>

                  <span className="text-xs font-bold text-gold hover:underline flex items-center gap-1">
                    <span>فتح المحادثة</span>
                    <ArrowRight className="w-3 h-3" />
                  </span>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    );
  }

  // Active Group Chat View (Telegram Style)
  const groupMsgs = messages[activeGroup.id] || [];

  const handleSend = (e?: React.FormEvent) => {
    if (e) e.preventDefault();
    if (!inputText.trim()) return;
    sendMessage(activeGroup.id, inputText);
    setInputText('');
  };

  const handleStartBroadcast = () => {
    triggerCallingOrBroadcast('البث المباشر للحلقة', () => {
      openLiveRoom();
    });
  };

  const handleStartCall = (isVideo: boolean) => {
    triggerCallingOrBroadcast(isVideo ? 'المكالمة المرئية' : 'المكالمة الصوتية', () => {
      openLiveRoom();
    });
  };

  return (
    <div className="fixed inset-0 top-[60px] bottom-[56px] z-30 bg-slate-100 dark:bg-slate-950 flex flex-col max-w-4xl mx-auto shadow-2xl">
      {/* Telegram Group Top Bar */}
      <div className="px-3 py-2 glass-panel border-b border-slate-200 dark:border-slate-800 flex items-center justify-between gap-2 shadow-sm">
        <div className="flex items-center gap-2.5 min-w-0">
          <button
            onClick={closeGroupChat}
            className="p-1 rounded-lg hover:bg-slate-200 dark:hover:bg-slate-800 text-slate-600 dark:text-slate-300"
            title="رجوع للحلقات"
          >
            <ArrowRight className="w-5 h-5 rtl:rotate-180" />
          </button>

          {/* Group Avatar and Profile click */}
          <div
            onClick={() => setShowProfileModal(true)}
            className="flex items-center gap-2 cursor-pointer hover:opacity-85 transition-opacity min-w-0"
            title="عرض بروفايل ووصف الحلقة وقائمة الأعضاء"
          >
            <div className="w-9 h-9 rounded-xl bg-gradient-to-br from-skyBlue-500 to-skyBlue-700 flex items-center justify-center text-white font-bold text-xs shadow flex-shrink-0">
              {activeGroup.name.slice(0, 2)}
            </div>
            <div className="min-w-0">
              <div className="flex items-center gap-1">
                <h3 className="font-extrabold text-sm text-slate-900 dark:text-slate-100 truncate">
                  {activeGroup.name}
                </h3>
                <Info className="w-3.5 h-3.5 text-gold flex-shrink-0" />
              </div>
              <p className="text-[10px] text-slate-500 dark:text-slate-400 truncate">
                {activeGroup.membersCount} عضو • {activeGroup.sheikhSupervisor}
              </p>
            </div>
          </div>
        </div>

        {/* Action Controls: Voice Call, Video Call, Live Broadcast */}
        <div className="flex items-center gap-1 sm:gap-2 flex-shrink-0">
          {/* Audio Call */}
          <button
            onClick={() => handleStartCall(false)}
            className="p-2 rounded-xl bg-slate-200/80 hover:bg-slate-300 dark:bg-slate-800 dark:hover:bg-slate-700 text-slate-700 dark:text-slate-200 transition-colors shadow-sm"
            title={
              currentRole === 'STUDENT'
                ? 'المكالمات محصورة بالمعلم والمشرف'
                : 'بدء مكالمة صوتية جماعية'
            }
          >
            <Phone className="w-4 h-4 text-skyBlue-600 dark:text-skyBlue-400" />
          </button>

          {/* Video Call */}
          <button
            onClick={() => handleStartCall(true)}
            className="p-2 rounded-xl bg-slate-200/80 hover:bg-slate-300 dark:bg-slate-800 dark:hover:bg-slate-700 text-slate-700 dark:text-slate-200 transition-colors shadow-sm"
            title={
              currentRole === 'STUDENT'
                ? 'المكالمات محصورة بالمعلم والمشرف'
                : 'بدء مكالمة مرئية'
            }
          >
            <Video className="w-4 h-4 text-emerald-600 dark:text-emerald-400" />
          </button>

          {/* Live Broadcast Badge / Button */}
          <button
            onClick={handleStartBroadcast}
            className={`flex items-center gap-1 px-2.5 py-1.5 rounded-xl font-bold text-xs shadow-sm transition-transform active:scale-95 ${
              activeGroup.isLiveBroadcastActive
                ? 'bg-rose-600 hover:bg-rose-700 text-white animate-pulse'
                : 'bg-gold hover:bg-amber-400 text-slate-950'
            }`}
            title="بدء / انضمام للبث المباشر (المعلم والمشرف)"
          >
            <Radio className="w-3.5 h-3.5" />
            <span className="hidden sm:inline">
              {activeGroup.isLiveBroadcastActive ? 'البث شغال' : 'بدء البث'}
            </span>
          </button>
        </div>
      </div>

      {/* Messages Feed */}
      <div className="flex-1 overflow-y-auto p-3 sm:p-4 space-y-3 bg-skyBlue-50/40 dark:bg-slate-950/80">
        <div className="text-center my-2">
          <span className="inline-block px-3 py-1 rounded-full text-[10px] font-bold bg-white/80 dark:bg-slate-800/80 text-slate-500 dark:text-slate-400 border border-slate-200 dark:border-slate-700 shadow-sm">
            حلقة «{activeGroup.name}» المعتمدة • السند المتصل
          </span>
        </div>

        {groupMsgs.map((msg) => {
          const isMine = msg.isMine;
          return (
            <div
              key={msg.id}
              className={`flex flex-col ${isMine ? 'items-end' : 'items-start'}`}
            >
              <div
                className={`max-w-[85%] rounded-2xl px-3.5 py-2.5 text-xs sm:text-sm shadow-sm whitespace-pre-line ${
                  isMine
                    ? 'bg-skyBlue-600 text-white rounded-br-none'
                    : 'bg-white dark:bg-slate-800 text-slate-900 dark:text-slate-100 border border-slate-200 dark:border-slate-700 rounded-bl-none'
                }`}
              >
                {!isMine && (
                  <span className="block text-[10px] font-bold text-amber-600 dark:text-amber-400 mb-0.5">
                    {msg.senderName}
                  </span>
                )}

                <p className="leading-relaxed">{msg.text}</p>

                <div
                  className={`flex items-center justify-end gap-1 text-[9px] mt-1 ${
                    isMine ? 'text-skyBlue-200' : 'text-slate-400'
                  }`}
                >
                  <span>{msg.timestamp}</span>
                  {isMine && <CheckCheck className="w-3 h-3 text-skyBlue-200" />}
                </div>
              </div>
            </div>
          );
        })}
      </div>

      {/* Bottom Message Input Field */}
      <form
        onSubmit={handleSend}
        className="p-2 sm:p-3 glass-panel border-t border-slate-200 dark:border-slate-800 flex items-center gap-2"
      >
        <button
          type="button"
          onClick={() => sendMessage(activeGroup.id, 'تسجيل صوتي لتسميع الآيات', true, 24)}
          className="p-2 rounded-xl bg-slate-100 hover:bg-slate-200 dark:bg-slate-800 dark:hover:bg-slate-700 text-slate-600 dark:text-slate-300"
          title="إرسال تسجيل صوتي سريع للتسميع"
        >
          <Mic className="w-4 h-4 text-rose-500" />
        </button>

        <input
          type="text"
          value={inputText}
          onChange={(e) => setInputText(e.target.value)}
          placeholder="اكتب رسالتك أو استفسارك في الحلقة..."
          className="flex-1 px-3 py-2 text-xs sm:text-sm rounded-xl border border-slate-300 dark:border-slate-700 bg-white dark:bg-slate-800 text-slate-900 dark:text-slate-100 focus:outline-none focus:ring-2 focus:ring-skyBlue-500"
        />

        <button
          type="submit"
          disabled={!inputText.trim()}
          className="p-2.5 rounded-xl bg-skyBlue-600 hover:bg-skyBlue-700 disabled:opacity-50 text-white font-bold transition-transform active:scale-95 shadow"
        >
          <Send className="w-4 h-4" />
        </button>
      </form>

      {/* Group Profile & Members Sheet Modal */}
      {showProfileModal && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-3 animate-in fade-in">
          <div className="bg-white dark:bg-slate-900 rounded-2xl max-w-md w-full p-4 sm:p-5 border border-slate-200 dark:border-slate-800 shadow-2xl max-h-[85vh] overflow-y-auto">
            <div className="flex items-center justify-between pb-3 border-b border-slate-100 dark:border-slate-800">
              <div className="flex items-center gap-2">
                <ShieldCheck className="w-5 h-5 text-gold" />
                <h3 className="font-extrabold text-base text-slate-900 dark:text-slate-100">
                  بيانات حلقة «{activeGroup.name}»
                </h3>
              </div>
              <button onClick={() => setShowProfileModal(false)}>
                <X className="w-5 h-5 text-slate-400" />
              </button>
            </div>

            {/* Description & Supervisor */}
            <div className="mt-3 space-y-2 text-xs">
              <div className="p-3 rounded-xl bg-skyBlue-50/70 dark:bg-skyBlue-950/40 border border-skyBlue-100 dark:border-skyBlue-900">
                <span className="font-bold text-skyBlue-800 dark:text-skyBlue-300 block mb-1">
                  وصف الحلقة ومنهجيتها:
                </span>
                <p className="text-slate-700 dark:text-slate-300 leading-relaxed">
                  {activeGroup.description}
                </p>
                <div className="mt-2 text-amber-700 dark:text-amber-300 font-bold">
                  المشرف على التسميع: {activeGroup.sheikhSupervisor}
                </div>
              </div>
            </div>

            {/* Members List with Interaction Rules */}
            <div className="mt-4">
              <div className="flex items-center justify-between mb-2">
                <h4 className="font-extrabold text-xs text-slate-700 dark:text-slate-300">
                  قائمة حسابات وأعضاء الحلقة ({groupMembers.length}):
                </h4>
                <span className="text-[10px] text-slate-400">
                  {currentRole === 'STUDENT' ? 'طلب صداقة متاح' : 'محادثة فورية للمشرف'}
                </span>
              </div>

              <div className="space-y-2">
                {groupMembers.map((member) => {
                  const isTeacherOrAdmin = member.role === 'ADMIN' || member.role === 'TEACHER';

                  return (
                    <div
                      key={member.id}
                      className="p-2.5 rounded-xl bg-slate-50 dark:bg-slate-800/60 border border-slate-200 dark:border-slate-700 flex items-center justify-between gap-2"
                    >
                      <div className="flex items-center gap-2 min-w-0">
                        <div className="w-8 h-8 rounded-full bg-skyBlue-100 dark:bg-skyBlue-900 text-skyBlue-700 dark:text-skyBlue-300 font-bold flex items-center justify-center text-xs flex-shrink-0">
                          {isTeacherOrAdmin ? '👑' : '🎓'}
                        </div>
                        <div className="min-w-0">
                          <div className="flex items-center gap-1.5">
                            <span className="font-bold text-xs text-slate-900 dark:text-slate-100 truncate">
                              {member.name}
                            </span>
                            {isTeacherOrAdmin && (
                              <span className="text-[9px] px-1 rounded bg-amber-100 text-amber-800 dark:bg-amber-900 dark:text-amber-200 font-bold">
                                {member.role === 'ADMIN' ? 'مدير' : 'معلم'}
                              </span>
                            )}
                          </div>
                          <span className="text-[10px] text-slate-400 block truncate">
                            رواية: {member.qiraah}
                          </span>
                        </div>
                      </div>

                      {/* Interaction Buttons based on role */}
                      <div className="flex items-center gap-1 flex-shrink-0">
                        {/* If Current User is Teacher or Admin -> Instant Chat & Call */}
                        {currentRole !== 'STUDENT' ? (
                          <>
                            <button
                              onClick={() => {
                                setShowProfileModal(false);
                                closeGroupChat();
                                const defaultConv = conversations[0];
                                openConversation(defaultConv);
                                setCurrentTab('chats');
                              }}
                              className="p-1.5 rounded-lg bg-skyBlue-600 hover:bg-skyBlue-700 text-white text-xs font-bold flex items-center gap-1"
                              title="محادثة تلقائية مع الطالب"
                            >
                              <MessageSquare className="w-3.5 h-3.5" />
                              <span className="text-[10px]">محادثة</span>
                            </button>

                            <button
                              onClick={() => {
                                setShowProfileModal(false);
                                openLiveRoom();
                              }}
                              className="p-1.5 rounded-lg bg-emerald-600 hover:bg-emerald-700 text-white text-xs font-bold"
                              title="مكالمة وتسميع فوري"
                            >
                              <Phone className="w-3.5 h-3.5" />
                            </button>
                          </>
                        ) : (
                          /* If Current User is Student: Friend Request logic */
                          <>
                            {member.friendStatus === 'FRIENDS' ? (
                              <button
                                onClick={() => {
                                  setShowProfileModal(false);
                                  closeGroupChat();
                                  const defaultConv = conversations[0];
                                  openConversation(defaultConv);
                                  setCurrentTab('chats');
                                }}
                                className="px-2 py-1 rounded-lg bg-emerald-100 text-emerald-800 dark:bg-emerald-950 dark:text-emerald-300 font-bold text-[10px] flex items-center gap-1"
                              >
                                <MessageSquare className="w-3 h-3" />
                                <span>محادثة</span>
                              </button>
                            ) : member.friendStatus === 'REQUEST_SENT' ? (
                              <span className="text-[10px] text-slate-400 font-medium px-2 py-1 bg-slate-100 dark:bg-slate-700 rounded-lg">
                                تم الطلب
                              </span>
                            ) : (
                              <button
                                onClick={() => sendFriendRequest(member.id)}
                                className="px-2.5 py-1 rounded-lg bg-skyBlue-100 hover:bg-skyBlue-200 dark:bg-skyBlue-950 dark:hover:bg-skyBlue-900 text-skyBlue-700 dark:text-skyBlue-300 font-bold text-[10px] flex items-center gap-1 transition-colors"
                              >
                                <UserPlus className="w-3 h-3" />
                                <span>طلب صداقة</span>
                              </button>
                            )}
                          </>
                        )}
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
