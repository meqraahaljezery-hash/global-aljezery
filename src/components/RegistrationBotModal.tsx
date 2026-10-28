import React, { useState, useEffect, useRef } from 'react';
import { useApp } from '../context/AppContext';
import {
  X,
  Send,
  Bot,
  CheckCircle2,
  Sparkles,
  Users,
  Video,
  ShieldCheck,
  ArrowRight,
  Sun,
  Sunset,
  Moon,
} from 'lucide-react';
import { GroupGender, GroupTiming, StudyGroup } from '../types';
import confetti from 'canvas-confetti';

interface BotMessage {
  id: string;
  sender: 'bot' | 'user';
  text: string;
  time: string;
}

export const RegistrationBotModal: React.FC = () => {
  const {
    registrationProgram,
    closeRegistrationBot,
    enrollStudentInGroup,
    openGroupChat,
    openLiveRoom,
    setCurrentTab,
    currentAccount,
  } = useApp();

  const [messages, setMessages] = useState<BotMessage[]>([]);
  const [step, setStep] = useState<'NAME' | 'GENDER' | 'TIMING' | 'QIRAAH' | 'COMPLETED'>('NAME');
  const [studentName, setStudentName] = useState(currentAccount.name || 'عبد الرحمن السعيد');
  const [gender, setGender] = useState<GroupGender | null>(null);
  const [timing, setTiming] = useState<GroupTiming | null>(null);
  const [qiraah, setQiraah] = useState<string>('');
  const [enrolledGroup, setEnrolledGroup] = useState<StudyGroup | null>(null);
  const [isTyping, setIsTyping] = useState<boolean>(false);

  const messagesEndRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (registrationProgram) {
      setMessages([
        {
          id: 'b1',
          sender: 'bot',
          text: `السلام عليكم ورحمة الله وبركاته يا طالب القرآن الكريم 🌿\nأنا بوت القبول والتسجيل الآلي لمقرأة الإمام الجزري (@JazariRegistrationBot).\nلقد اخترت مسار:\n«${registrationProgram.title}».\nسأقوم الآن بجمع بياناتك لتوزيعك فورياً على حلقتك المناسبة من الحلقات السبع وتفعيل رصيدك المجاني (+30 دقيقة).`,
          time: 'الآن',
        },
        {
          id: 'b2',
          sender: 'bot',
          text: 'ما هو اسمك الكريم المسجل في المقرأة؟',
          time: 'الآن',
        },
      ]);
      setStep('NAME');
    }
  }, [registrationProgram]);

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages, isTyping]);

  if (!registrationProgram) return null;

  const handleNameSubmit = (e?: React.FormEvent) => {
    if (e) e.preventDefault();
    const finalName = studentName.trim() || 'طالب مقرأة';

    setMessages((prev) => [
      ...prev,
      {
        id: `u_${Date.now()}`,
        sender: 'user',
        text: `اسمي: ${finalName}`,
        time: 'الآن',
      },
    ]);

    setIsTyping(true);
    setTimeout(() => {
      setIsTyping(false);
      setMessages((prev) => [
        ...prev,
        {
          id: `b_${Date.now()}`,
          sender: 'bot',
          text: `أهلاً بك يا ${finalName}! 🌸\nيرجى تحديد القسم المناسب وفق الضوابط الشرعية والأكاديمية:`,
          time: 'الآن',
        },
      ]);
      setStep('GENDER');
    }, 500);
  };

  const handleGenderSelect = (selectedGender: GroupGender) => {
    setGender(selectedGender);
    const genderLabel = selectedGender === 'MALE' ? 'قسم الذكور (رجال وشباب) 👨' : 'قسم الإناث (أخوات وبنات) 🧕';

    setMessages((prev) => [
      ...prev,
      {
        id: `u_${Date.now()}`,
        sender: 'user',
        text: genderLabel,
        time: 'الآن',
      },
    ]);

    setIsTyping(true);
    setTimeout(() => {
      setIsTyping(false);
      setMessages((prev) => [
        ...prev,
        {
          id: `b_${Date.now()}`,
          sender: 'bot',
          text: 'ممتاز! حدد الآن الفترة الزمنية المناسبة لحضورك المجالس اليومية في الحلقات السبع:',
          time: 'الآن',
        },
      ]);
      setStep('TIMING');
    }, 500);
  };

  const handleTimingSelect = (selectedTiming: GroupTiming) => {
    setTiming(selectedTiming);
    const timingLabels: Record<GroupTiming, string> = {
      MORNING: '🌅 الفترة الصباحية (بعد الفجر)',
      EVENING: '🌇 الفترة المسائية (بعد العصر)',
      NIGHT: '🌙 الفترة الليلية (بعد العشاء)',
      GENERAL: 'ملتقى عام',
    };

    setMessages((prev) => [
      ...prev,
      {
        id: `u_${Date.now()}`,
        sender: 'user',
        text: timingLabels[selectedTiming],
        time: 'الآن',
      },
    ]);

    setIsTyping(true);
    setTimeout(() => {
      setIsTyping(false);
      setMessages((prev) => [
        ...prev,
        {
          id: `b_${Date.now()}`,
          sender: 'bot',
          text: 'ما هي الرواية القرآنية التي ترغب في التسميع والإقراء بها أمام الشيخ؟',
          time: 'الآن',
        },
      ]);
      setStep('QIRAAH');
    }, 500);
  };

  const handleQiraahSelect = (selectedQiraah: string) => {
    setQiraah(selectedQiraah);

    setMessages((prev) => [
      ...prev,
      {
        id: `u_${Date.now()}`,
        sender: 'user',
        text: `أرغب برواية: ${selectedQiraah}`,
        time: 'الآن',
      },
    ]);

    setIsTyping(true);
    setTimeout(() => {
      setIsTyping(false);

      // Perform enrollment in AppContext
      const group = enrollStudentInGroup(
        studentName,
        gender || 'MALE',
        timing || 'MORNING',
        selectedQiraah,
        registrationProgram
      );

      setEnrolledGroup(group);
      setStep('COMPLETED');

      // Trigger celebration confetti
      try {
        confetti({
          particleCount: 80,
          spread: 70,
          origin: { y: 0.6 },
          colors: ['#D4AF37', '#38BDF8', '#10B981'],
        });
      } catch (err) {
        // Safe fallback
      }

      setMessages((prev) => [
        ...prev,
        {
          id: `b_final_${Date.now()}`,
          sender: 'bot',
          text: `🎉 مبارك يا طالب العلم!\nتم قبولك رسمياً وتوزيعك آلياً إلى:\n` +
            `📌 حلقة: «${group.name}»\n` +
            `📌 «الملتقى العام»\n` +
            `🎁 تم شحن رصيدك فوراً بـ: +30 دقيقة ترحيبية مجانية!\n` +
            `يمكنك الآن التوجه مباشرة لحلقتك للتواصل مع فضيلة الشيخ وزملائك، أو دخول مجالس البث المباشر.`,
          time: 'الآن',
        },
      ]);
    }, 700);
  };

  return (
    <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-2 sm:p-4 animate-in fade-in">
      <div className="relative w-full max-w-lg bg-white dark:bg-slate-900 rounded-2xl shadow-2xl border border-slate-200 dark:border-slate-800 flex flex-col h-[90vh] max-h-[680px] overflow-hidden">
        {/* Telegram Bot Top Bar */}
        <div className="flex items-center justify-between px-4 py-3 bg-gradient-to-r from-skyBlue-600 to-skyBlue-700 text-white shadow-md">
          <div className="flex items-center gap-3 min-w-0">
            <div className="relative w-10 h-10 rounded-full bg-gold flex items-center justify-center text-slate-900 font-bold border-2 border-white shadow">
              <Bot className="w-5 h-5 text-slate-950" />
              <div className="absolute -bottom-0.5 -right-0.5 w-3.5 h-3.5 bg-emerald-400 rounded-full border border-white" />
            </div>
            <div className="min-w-0">
              <div className="flex items-center gap-1.5">
                <h3 className="font-extrabold text-sm sm:text-base leading-tight truncate">
                  بوت التسجيل والقبول الآلي
                </h3>
                <ShieldCheck className="w-4 h-4 text-amber-300 flex-shrink-0" />
              </div>
              <p className="text-[11px] text-skyBlue-100 font-mono">
                {isTyping ? 'يكتب الآن...' : '@JazariRegistrationBot • متصل دائماً'}
              </p>
            </div>
          </div>

          <button
            onClick={closeRegistrationBot}
            className="p-1.5 rounded-full hover:bg-white/20 transition-colors"
            title="إغلاق"
          >
            <X className="w-5 h-5 text-white" />
          </button>
        </div>

        {/* Selected Program Banner */}
        <div className="px-3 py-1.5 bg-amber-50 dark:bg-amber-950/40 border-b border-amber-200 dark:border-amber-900 flex items-center justify-between text-xs text-amber-900 dark:text-amber-200 font-medium">
          <span className="truncate">
            المسار: <strong className="text-amber-700 dark:text-amber-300">{registrationProgram.title}</strong>
          </span>
          <span className="text-[10px] bg-amber-200 dark:bg-amber-900 px-1.5 py-0.5 rounded text-amber-900 dark:text-amber-100 flex-shrink-0">
            قبول فوري
          </span>
        </div>

        {/* Chat Messages Body */}
        <div className="flex-1 overflow-y-auto p-3 sm:p-4 space-y-3 bg-slate-50/70 dark:bg-slate-950/40">
          {messages.map((msg) => {
            const isBot = msg.sender === 'bot';
            return (
              <div
                key={msg.id}
                className={`flex gap-2 items-end ${isBot ? 'justify-start' : 'justify-end'}`}
              >
                {isBot && (
                  <div className="w-7 h-7 rounded-full bg-gold/20 flex items-center justify-center text-gold text-xs flex-shrink-0 mb-1 border border-gold/40">
                    <Bot className="w-4 h-4" />
                  </div>
                )}
                <div
                  className={`max-w-[85%] rounded-2xl px-3.5 py-2.5 text-xs sm:text-sm leading-relaxed shadow-sm whitespace-pre-line ${
                    isBot
                      ? 'bg-white dark:bg-slate-800 text-slate-800 dark:text-slate-100 border border-slate-200 dark:border-slate-700 rounded-bl-none'
                      : 'bg-skyBlue-600 text-white rounded-br-none'
                  }`}
                >
                  {isBot && (
                    <span className="block text-[10px] font-bold text-skyBlue-600 dark:text-skyBlue-400 mb-0.5">
                      بوت المقرأة الآلي 🤖
                    </span>
                  )}
                  {msg.text}
                  <span
                    className={`block text-[9px] mt-1 text-end ${
                      isBot ? 'text-slate-400' : 'text-skyBlue-200'
                    }`}
                  >
                    {msg.time}
                  </span>
                </div>
              </div>
            );
          })}

          {isTyping && (
            <div className="flex gap-2 items-center text-slate-400 text-xs py-1">
              <Bot className="w-4 h-4 animate-bounce text-gold" />
              <span>جاري تدقيق البيانات وكتابة الرد...</span>
            </div>
          )}

          <div ref={messagesEndRef} />
        </div>

        {/* Telegram Interactive Keyboard Area */}
        <div className="p-3 bg-white dark:bg-slate-900 border-t border-slate-200 dark:border-slate-800">
          {step === 'NAME' && (
            <form onSubmit={handleNameSubmit} className="flex gap-2">
              <input
                type="text"
                value={studentName}
                onChange={(e) => setStudentName(e.target.value)}
                placeholder="اكتب اسمك الثلاثي للتسجيل..."
                className="flex-1 px-3 py-2 text-xs sm:text-sm rounded-xl border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-slate-100 focus:outline-none focus:ring-2 focus:ring-skyBlue-500"
                autoFocus
              />
              <button
                type="submit"
                className="px-4 py-2 rounded-xl bg-skyBlue-600 hover:bg-skyBlue-700 text-white font-bold text-xs flex items-center gap-1.5 transition-transform active:scale-95 shadow"
              >
                <span>إرسال</span>
                <Send className="w-3.5 h-3.5" />
              </button>
            </form>
          )}

          {step === 'GENDER' && (
            <div className="space-y-1.5">
              <p className="text-[11px] text-slate-500 dark:text-slate-400 font-medium">
                اختر القسم المناسب لتوزيعك على إحدى الحلقات السبع:
              </p>
              <div className="grid grid-cols-2 gap-2">
                <button
                  onClick={() => handleGenderSelect('MALE')}
                  className="py-2.5 px-3 rounded-xl bg-skyBlue-600 hover:bg-skyBlue-700 text-white font-bold text-xs sm:text-sm flex items-center justify-center gap-1.5 shadow transition-all active:scale-95"
                >
                  <span>👨 قسم الذكور</span>
                </button>
                <button
                  onClick={() => handleGenderSelect('FEMALE')}
                  className="py-2.5 px-3 rounded-xl bg-gradient-to-r from-amber-500 to-amber-600 hover:from-amber-600 hover:to-amber-700 text-white font-bold text-xs sm:text-sm flex items-center justify-center gap-1.5 shadow transition-all active:scale-95"
                >
                  <span>🧕 قسم الإناث</span>
                </button>
              </div>
            </div>
          )}

          {step === 'TIMING' && (
            <div className="space-y-1.5">
              <p className="text-[11px] text-slate-500 dark:text-slate-400 font-medium">
                اختر الفترة اليومية المناسبة لمواعيد فراغك:
              </p>
              <div className="grid grid-cols-3 gap-2">
                <button
                  onClick={() => handleTimingSelect('MORNING')}
                  className="py-2.5 px-2 rounded-xl bg-skyBlue-500 hover:bg-skyBlue-600 text-white font-bold text-xs flex flex-col items-center justify-center gap-1 shadow transition-all active:scale-95"
                >
                  <Sun className="w-4 h-4 text-amber-200" />
                  <span>🌅 صباحي</span>
                </button>
                <button
                  onClick={() => handleTimingSelect('EVENING')}
                  className="py-2.5 px-2 rounded-xl bg-skyBlue-700 hover:bg-skyBlue-800 text-white font-bold text-xs flex flex-col items-center justify-center gap-1 shadow transition-all active:scale-95"
                >
                  <Sunset className="w-4 h-4 text-amber-300" />
                  <span>🌇 مسائي</span>
                </button>
                <button
                  onClick={() => handleTimingSelect('NIGHT')}
                  className="py-2.5 px-2 rounded-xl bg-slate-800 hover:bg-slate-900 text-amber-300 font-bold text-xs flex flex-col items-center justify-center gap-1 shadow transition-all active:scale-95 border border-amber-500/30"
                >
                  <Moon className="w-4 h-4 text-amber-300" />
                  <span>🌙 ليلي</span>
                </button>
              </div>
            </div>
          )}

          {step === 'QIRAAH' && (
            <div className="space-y-1.5">
              <p className="text-[11px] text-slate-500 dark:text-slate-400 font-medium">
                اختر الرواية القرآنية التي تود التسميع بها:
              </p>
              <div className="flex flex-wrap gap-1.5">
                {[
                  'حفص عن عاصم',
                  'ورش عن نافع',
                  'قالون عن نافع',
                  'الدوري عن أبي عمرو',
                  'القراءات السبع',
                ].map((q) => (
                  <button
                    key={q}
                    onClick={() => handleQiraahSelect(q)}
                    className="py-1.5 px-3 rounded-lg bg-slate-100 hover:bg-gold/20 dark:bg-slate-800 dark:hover:bg-gold/30 border border-slate-300 dark:border-slate-700 text-slate-800 dark:text-slate-200 font-bold text-xs transition-colors"
                  >
                    {q}
                  </button>
                ))}
              </div>
            </div>
          )}

          {step === 'COMPLETED' && enrolledGroup && (
            <div className="space-y-2">
              <div className="flex items-center gap-1.5 text-xs font-bold text-emerald-600 dark:text-emerald-400 justify-center">
                <CheckCircle2 className="w-4 h-4" />
                <span>تم التسجيل والفرز الآلي بنجاح (+30 دقيقة)</span>
              </div>

              <div className="grid grid-cols-2 gap-2">
                <button
                  onClick={() => {
                    closeRegistrationBot();
                    openGroupChat(enrolledGroup);
                    setCurrentTab('groups');
                  }}
                  className="py-2.5 px-3 rounded-xl bg-gradient-to-r from-emerald-600 to-teal-600 hover:from-emerald-700 hover:to-teal-700 text-white font-extrabold text-xs flex items-center justify-center gap-1.5 shadow-md active:scale-95 transition-all"
                >
                  <Users className="w-4 h-4" />
                  <span>دخول حلقة «{enrolledGroup.name}»</span>
                </button>

                <button
                  onClick={() => {
                    closeRegistrationBot();
                    openLiveRoom();
                  }}
                  className="py-2.5 px-3 rounded-xl bg-gradient-to-r from-gold to-amber-600 hover:from-amber-600 hover:to-amber-700 text-slate-900 font-extrabold text-xs flex items-center justify-center gap-1.5 shadow-md active:scale-95 transition-all"
                >
                  <Video className="w-4 h-4" />
                  <span>المجلس المباشر</span>
                </button>
              </div>

              <button
                onClick={closeRegistrationBot}
                className="w-full text-center text-xs text-slate-500 dark:text-slate-400 hover:underline py-1"
              >
                العودة للصفحة الرئيسية
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
