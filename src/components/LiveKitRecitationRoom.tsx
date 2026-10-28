import React, { useState, useEffect } from 'react';
import { useApp } from '../context/AppContext';
import {
  Mic,
  MicOff,
  Video,
  VideoOff,
  Hand,
  PhoneOff,
  Users,
  Radio,
  Sparkles,
  Volume2,
  CheckCircle,
  Play,
  Clock,
  ShieldCheck,
  AlertCircle,
} from 'lucide-react';
import { LiveRoomInfo } from '../types';

export const LiveKitRecitationRoom: React.FC = () => {
  const {
    activeLiveRoom,
    closeLiveRoom,
    studentQueue,
    callStudentInQueue,
    completeRecitation,
    currentRole,
    currentAccount,
    studentMinutesBalance,
  } = useApp();

  const [isMicMuted, setIsMicMuted] = useState(false);
  const [isCameraOff, setIsCameraOff] = useState(false);
  const [isHandRaised, setIsHandRaised] = useState(false);
  const [recitationSeconds, setRecitationSeconds] = useState(145);

  useEffect(() => {
    const timer = setInterval(() => {
      setRecitationSeconds((prev) => prev + 1);
    }, 1000);
    return () => clearInterval(timer);
  }, []);

  if (!activeLiveRoom) return null;

  const isTeacherOrAdmin = currentRole === 'ADMIN' || currentRole === 'TEACHER';
  const activeStudent = studentQueue.find((q) => q.status === 'RECITING') || studentQueue[0];

  const formatTime = (secs: number) => {
    const m = Math.floor(secs / 60);
    const s = secs % 60;
    return `${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}`;
  };

  return (
    <div className="fixed inset-0 z-50 bg-slate-950 text-white flex flex-col animate-in fade-in">
      {/* Top Bar */}
      <div className="px-3 sm:px-6 py-2.5 bg-slate-900/90 border-b border-slate-800 flex items-center justify-between gap-2">
        <div className="flex items-center gap-2.5 min-w-0">
          <div className="flex items-center gap-1.5 px-2 py-0.5 rounded-full bg-rose-600 text-white text-[11px] font-extrabold animate-pulse">
            <Radio className="w-3.5 h-3.5" />
            <span>بث حي LiveKit</span>
          </div>
          <div className="min-w-0">
            <h2 className="font-extrabold text-xs sm:text-sm text-slate-100 truncate">
              {activeLiveRoom.nameAr}
            </h2>
            <p className="text-[10px] text-amber-400 truncate">
              الشيخ المقرئ: {activeLiveRoom.teacherName} • رواية {activeLiveRoom.riwayah}
            </p>
          </div>
        </div>

        <div className="flex items-center gap-2">
          <div className="hidden sm:flex items-center gap-1 text-xs text-slate-300 bg-slate-800 px-2.5 py-1 rounded-lg">
            <Users className="w-3.5 h-3.5 text-skyBlue-400" />
            <span>{activeLiveRoom.participantsCount} مشارك</span>
          </div>

          <button
            onClick={closeLiveRoom}
            className="px-3 py-1.5 rounded-xl bg-rose-600 hover:bg-rose-700 text-white font-bold text-xs flex items-center gap-1 shadow transition-transform active:scale-95"
          >
            <PhoneOff className="w-3.5 h-3.5" />
            <span>مغادرة المجلس</span>
          </button>
        </div>
      </div>

      {/* Main Video Stage & Recitation Area */}
      <div className="flex-1 p-2 sm:p-4 overflow-y-auto grid grid-cols-1 lg:grid-cols-3 gap-3">
        {/* Left 2 Cols: Main Video Frames */}
        <div className="lg:col-span-2 flex flex-col gap-3 justify-center">
          {/* Sheikh Video Frame */}
          <div className="relative rounded-2xl bg-slate-900 border border-slate-800 overflow-hidden aspect-video max-h-[300px] sm:max-h-[360px] flex items-center justify-center shadow-lg">
            <div className="text-center p-4">
              <div className="w-16 h-16 sm:w-20 sm:h-20 rounded-full bg-gradient-to-tr from-gold to-amber-600 text-slate-950 font-black text-2xl sm:text-3xl flex items-center justify-center mx-auto shadow-md border-2 border-white/80">
                👑
              </div>
              <h3 className="font-black text-sm sm:text-base mt-2 text-slate-100">
                {activeLiveRoom.teacherName}
              </h3>
              <p className="text-xs text-amber-300">
                مجلس الإجازة وتصحيح التلاوة بالسند المتصل
              </p>
              <div className="inline-flex items-center gap-1 mt-2 px-2.5 py-0.5 rounded-full text-[10px] bg-emerald-500/20 text-emerald-300 border border-emerald-500/40">
                <Volume2 className="w-3 h-3 animate-pulse" />
                <span>المايك مفتوح • يستمع ويدقق أوجه الرواية</span>
              </div>
            </div>

            <div className="absolute top-2 start-2 px-2 py-0.5 rounded bg-black/60 text-[10px] font-mono text-slate-300">
              المعلم المشرف
            </div>
          </div>

          {/* Active Student Recitation Frame */}
          <div className="relative rounded-2xl bg-gradient-to-r from-skyBlue-950/40 to-slate-900 border border-skyBlue-900/60 p-3 sm:p-4 flex items-center justify-between gap-3 shadow-md">
            <div className="flex items-center gap-3">
              <div className="w-12 h-12 rounded-xl bg-skyBlue-600/30 border border-skyBlue-400 text-skyBlue-300 font-bold flex items-center justify-center text-xl">
                🎓
              </div>
              <div>
                <div className="flex items-center gap-2">
                  <span className="text-xs text-amber-400 font-bold">المقرئ الحالي في المجلس:</span>
                  <span className="px-1.5 py-0.2 rounded text-[9px] bg-skyBlue-900 text-skyBlue-200 font-mono">
                    {formatTime(recitationSeconds)}
                  </span>
                </div>
                <h4 className="font-extrabold text-sm sm:text-base text-slate-100">
                  {activeStudent ? activeStudent.studentName : 'عبد الرحمن السعيد'}
                </h4>
                <p className="text-xs text-slate-400">
                  المقرر: {activeStudent ? activeStudent.surah : 'سورة النساء آية 1-10'} • رواية{' '}
                  {activeStudent ? activeStudent.riwayah : 'حفص عن عاصم'}
                </p>
              </div>
            </div>

            {/* Teacher control to finish recitation & deduct minutes */}
            {isTeacherOrAdmin && (
              <button
                onClick={() => completeRecitation(15)}
                className="px-3 py-1.5 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-bold text-xs flex items-center gap-1 shadow transition-transform active:scale-95 flex-shrink-0"
              >
                <CheckCircle className="w-3.5 h-3.5" />
                <span className="hidden sm:inline">إنهاء التسميع وخصم 15 د</span>
                <span className="sm:hidden">إنهاء (15د)</span>
              </button>
            )}
          </div>
        </div>

        {/* Right Col: Student Recitation Queue (طابور التسميع) */}
        <div className="rounded-2xl bg-slate-900/80 border border-slate-800 p-3 sm:p-4 flex flex-col h-full max-h-[460px]">
          <div className="flex items-center justify-between pb-2 border-b border-slate-800 mb-2">
            <div className="flex items-center gap-1.5">
              <Clock className="w-4 h-4 text-gold" />
              <h3 className="font-extrabold text-xs sm:text-sm text-slate-200">
                طابور التسميع أمام الشيخ ({studentQueue.length}):
              </h3>
            </div>
            <span className="text-[10px] text-slate-400">نظام تلقائي</span>
          </div>

          <div className="flex-1 overflow-y-auto space-y-2 pr-1">
            {studentQueue.map((student, idx) => {
              const isReciting = student.status === 'RECITING';

              return (
                <div
                  key={student.id}
                  className={`p-2.5 rounded-xl border text-xs transition-all ${
                    isReciting
                      ? 'bg-skyBlue-950/70 border-skyBlue-500 shadow-sm'
                      : 'bg-slate-800/60 border-slate-700'
                  }`}
                >
                  <div className="flex items-start justify-between gap-1">
                    <div className="min-w-0">
                      <div className="flex items-center gap-1.5">
                        <span className="w-4 h-4 rounded-full bg-slate-700 text-[10px] font-mono flex items-center justify-center font-bold">
                          {idx + 1}
                        </span>
                        <span className="font-bold text-slate-100 truncate">
                          {student.studentName}
                        </span>
                      </div>
                      <span className="text-[10px] text-slate-400 block mt-0.5 truncate">
                        {student.surah} • {student.riwayah}
                      </span>
                    </div>

                    <div className="flex flex-col items-end gap-1 flex-shrink-0">
                      {isReciting ? (
                        <span className="px-1.5 py-0.5 rounded text-[9px] font-extrabold bg-emerald-500 text-slate-950 animate-pulse">
                          يسمع الآن
                        </span>
                      ) : (
                        <span className="text-[10px] text-slate-400 font-mono">
                          رصيد: {student.minutesBalance} د
                        </span>
                      )}

                      {/* Call student button for Sheikh */}
                      {isTeacherOrAdmin && !isReciting && (
                        <button
                          onClick={() => callStudentInQueue(student.id)}
                          className="px-2 py-0.5 rounded bg-skyBlue-600 hover:bg-skyBlue-700 text-white font-bold text-[10px] flex items-center gap-0.5"
                        >
                          <Play className="w-2.5 h-2.5" />
                          <span>استدعاء</span>
                        </button>
                      )}
                    </div>
                  </div>
                </div>
              );
            })}
          </div>

          {/* Student Raise Hand Button */}
          {!isTeacherOrAdmin && (
            <div className="pt-2 border-t border-slate-800 mt-2">
              <button
                onClick={() => setIsHandRaised(!isHandRaised)}
                className={`w-full py-2 px-3 rounded-xl font-bold text-xs flex items-center justify-center gap-1.5 transition-colors ${
                  isHandRaised
                    ? 'bg-amber-500 text-slate-950'
                    : 'bg-slate-800 hover:bg-slate-700 text-slate-200 border border-slate-700'
                }`}
              >
                <Hand className={`w-4 h-4 ${isHandRaised ? 'animate-bounce' : ''}`} />
                <span>{isHandRaised ? 'تم رفع يدك (بانتظار دورك)' : 'رفع اليد لطلب التسميع'}</span>
              </button>
            </div>
          )}
        </div>
      </div>

      {/* In-Call Bottom Controls Bar */}
      <div className="px-3 sm:px-6 py-3 bg-slate-900 border-t border-slate-800 flex items-center justify-center gap-3 sm:gap-4">
        {/* Toggle Mic */}
        <button
          onClick={() => setIsMicMuted(!isMicMuted)}
          className={`p-3 rounded-2xl flex items-center justify-center transition-all ${
            isMicMuted
              ? 'bg-rose-600 text-white hover:bg-rose-700'
              : 'bg-slate-800 text-slate-200 hover:bg-slate-700 border border-slate-700'
          }`}
          title={isMicMuted ? 'تشغيل المايك' : 'كتم المايك'}
        >
          {isMicMuted ? <MicOff className="w-5 h-5" /> : <Mic className="w-5 h-5" />}
        </button>

        {/* Toggle Camera */}
        <button
          onClick={() => setIsCameraOff(!isCameraOff)}
          className={`p-3 rounded-2xl flex items-center justify-center transition-all ${
            isCameraOff
              ? 'bg-rose-600 text-white hover:bg-rose-700'
              : 'bg-slate-800 text-slate-200 hover:bg-slate-700 border border-slate-700'
          }`}
          title={isCameraOff ? 'تشغيل الكاميرا' : 'إيقاف الكاميرا'}
        >
          {isCameraOff ? <VideoOff className="w-5 h-5" /> : <Video className="w-5 h-5" />}
        </button>

        {/* Raise Hand */}
        <button
          onClick={() => setIsHandRaised(!isHandRaised)}
          className={`p-3 rounded-2xl flex items-center justify-center transition-all ${
            isHandRaised
              ? 'bg-amber-500 text-slate-950'
              : 'bg-slate-800 text-slate-200 hover:bg-slate-700 border border-slate-700'
          }`}
          title="رفع اليد"
        >
          <Hand className="w-5 h-5" />
        </button>

        {/* End Call / Leave */}
        <button
          onClick={closeLiveRoom}
          className="px-5 py-3 rounded-2xl bg-rose-600 hover:bg-rose-700 text-white font-extrabold text-xs flex items-center gap-2 shadow-lg transition-transform active:scale-95"
        >
          <PhoneOff className="w-4 h-4" />
          <span>مغادرة المجلس</span>
        </button>
      </div>
    </div>
  );
};
