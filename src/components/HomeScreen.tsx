import React, { useState } from 'react';
import { useApp } from '../context/AppContext';
import {
  Bell,
  Sparkles,
  BookOpen,
  HelpCircle,
  Clock,
  CheckCircle,
  Plus,
  Edit3,
  Trash2,
  X,
  ArrowRight,
  ShieldAlert,
  Flame,
} from 'lucide-react';
import { QuranProgram } from '../types';

export const HomeScreen: React.FC = () => {
  const {
    t,
    bannerText,
    updateBanner,
    aboutText,
    updateAbout,
    programs,
    addProgram,
    deleteProgram,
    currentRole,
    openRegistrationBot,
    openLiveRoom,
  } = useApp();

  const [showEditBanner, setShowEditBanner] = useState(false);
  const [editedBanner, setEditedBanner] = useState(bannerText);

  const [showEditAbout, setShowEditAbout] = useState(false);
  const [editedAbout, setEditedAbout] = useState(aboutText);

  const [showAddProgram, setShowAddProgram] = useState(false);
  const [newProgram, setNewProgram] = useState<Partial<QuranProgram>>({
    title: '',
    category: 'مسار قرآني جديد',
    description: '',
    howToUse: '',
    requirements: '',
    durationText: '3 أشهر (جلسات يومية)',
    isRegistrationOpen: true,
  });

  const canEdit = currentRole === 'ADMIN';

  const handleSaveBanner = (e: React.FormEvent) => {
    e.preventDefault();
    updateBanner(editedBanner);
    setShowEditBanner(false);
  };

  const handleSaveAbout = (e: React.FormEvent) => {
    e.preventDefault();
    updateAbout(editedAbout);
    setShowEditAbout(false);
  };

  const handleCreateProgram = (e: React.FormEvent) => {
    e.preventDefault();
    if (!newProgram.title) return;

    addProgram({
      id: `prog_${Date.now()}`,
      title: newProgram.title,
      category: newProgram.category || 'مسار قرآني تخصصي',
      description: newProgram.description || 'وصف تفصيلي للبرنامج القرآني وطريقة مدارسة الآيات والتحريرات.',
      howToUse: newProgram.howToUse || 'الانضمام للحلقات السبع ومتابعة الشيخ في المجلس الصوتي المباشر.',
      requirements: newProgram.requirements || 'الجدية والالتزام بمواعيد التسميع اليومية.',
      durationText: newProgram.durationText || 'مستمر',
      isRegistrationOpen: true,
      enrolledCount: 1,
    });

    setNewProgram({
      title: '',
      category: 'مسار قرآني جديد',
      description: '',
      howToUse: '',
      requirements: '',
      durationText: '',
      isRegistrationOpen: true,
    });
    setShowAddProgram(false);
  };

  return (
    <div className="space-y-4 pb-20 max-w-4xl mx-auto px-3 sm:px-4 pt-3">
      {/* 1. Urgent Notifications Bar (إشارات المقرأة العاجلة) */}
      <div className="glass-panel rounded-2xl p-3 border border-skyBlue-200 dark:border-skyBlue-900 bg-gradient-to-r from-skyBlue-50/80 to-amber-50/60 dark:from-slate-900/80 dark:to-slate-950/80 shadow-sm flex items-start gap-2.5">
        <div className="w-8 h-8 rounded-xl bg-amber-500/20 text-amber-600 dark:text-amber-400 flex items-center justify-center flex-shrink-0 mt-0.5">
          <Bell className="w-4 h-4 animate-bounce" />
        </div>
        <div className="min-w-0 flex-1">
          <div className="flex items-center gap-2">
            <span className="text-xs font-extrabold text-amber-700 dark:text-amber-400">
              {t('urgentAlerts')}:
            </span>
            <span className="px-1.5 py-0.2 rounded text-[9px] font-bold bg-emerald-100 text-emerald-800 dark:bg-emerald-950 dark:text-emerald-300">
              مباشر الآن
            </span>
          </div>
          <p className="text-xs text-slate-700 dark:text-slate-300 mt-0.5 leading-relaxed">
            {currentRole === 'ADMIN' &&
              'تنبيه المدير: يمكنك تعديل لوحة الإعلانات، نبذة المقرأة، وإضافة مسارات جديدة مباشرة من هذه الصفحة دون الرجوع لقاعدة البيانات.'}
            {currentRole === 'TEACHER' &&
              'تنبيه المعلم: الحلقات السبع جاهزة لبدء البث والمكالمات، وقائمة التسميع بانتظار استدعاء الطلاب.'}
            {currentRole === 'STUDENT' &&
              'إشارة الطالب: مجالس التسميع المباشرة منعقدة الآن في الحلقات السبع، والتسجيل في المسارات متاح عبر البوت الآلي.'}
          </p>
        </div>
      </div>

      {/* 2. Big Announcement Banner (لوحة الإعلانات الكبيرة - يعدله المشرف) */}
      <div className="relative rounded-2xl p-4 sm:p-5 bg-gradient-to-br from-skyBlue-600 via-skyBlue-700 to-islamic-midnight text-white shadow-lg overflow-hidden border border-skyBlue-400/30">
        <div className="absolute top-0 right-0 -mr-12 -mt-12 w-48 h-48 rounded-full bg-gold/15 blur-2xl pointer-events-none" />

        <div className="relative z-10">
          <div className="flex items-center justify-between mb-2">
            <div className="flex items-center gap-2">
              <span className="px-2 py-0.5 rounded-full text-[10px] font-extrabold bg-gold text-slate-950 tracking-wider">
                إعلان رسمي معتمد
              </span>
              <span className="text-xs text-skyBlue-200">مقرأة الإمام الجزري</span>
            </div>

            {canEdit && (
              <button
                onClick={() => {
                  setEditedBanner(bannerText);
                  setShowEditBanner(true);
                }}
                className="flex items-center gap-1 px-2.5 py-1 rounded-lg bg-white/20 hover:bg-white/30 text-white text-xs font-bold transition-colors"
                title="تعديل لوحة الإعلانات بصفتك المشرف"
              >
                <Edit3 className="w-3.5 h-3.5 text-amber-300" />
                <span className="hidden sm:inline">تعديل الإعلان (المدير)</span>
              </button>
            )}
          </div>

          <p className="text-sm sm:text-base font-bold leading-relaxed text-slate-50 mt-1">
            {bannerText}
          </p>

          <div className="mt-3.5 flex flex-wrap items-center gap-2">
            <button
              onClick={() => openLiveRoom()}
              className="px-3.5 py-1.5 rounded-xl bg-gold hover:bg-amber-400 text-slate-950 font-extrabold text-xs flex items-center gap-1.5 shadow transition-transform active:scale-95"
            >
              <span>دخول المجلس الصوتي المباشر</span>
              <ArrowRight className="w-3.5 h-3.5" />
            </button>
            <span className="text-[11px] text-skyBlue-200">
              الحلقات السبع: رجال وأخوات • فترات صباحية، مسائية، وليلية
            </span>
          </div>
        </div>
      </div>

      {/* 3. About Maqraah (عالمقرأة - يعدله المشرف) */}
      <div className="glass-panel rounded-2xl p-4 sm:p-5 border border-slate-200 dark:border-slate-800 shadow-sm">
        <div className="flex items-center justify-between mb-2">
          <div className="flex items-center gap-2">
            <BookOpen className="w-4 h-4 text-gold" />
            <h2 className="text-sm sm:text-base font-extrabold text-skyBlue-700 dark:text-skyBlue-300">
              {t('aboutTitle')}
            </h2>
          </div>

          {canEdit && (
            <button
              onClick={() => {
                setEditedAbout(aboutText);
                setShowEditAbout(true);
              }}
              className="flex items-center gap-1 px-2 py-1 rounded-lg bg-slate-100 hover:bg-slate-200 dark:bg-slate-800 dark:hover:bg-slate-700 text-slate-700 dark:text-slate-300 text-xs font-bold transition-colors"
            >
              <Edit3 className="w-3 h-3 text-gold" />
              <span>تعديل النبذة</span>
            </button>
          )}
        </div>

        <p className="text-xs sm:text-sm text-slate-700 dark:text-slate-300 leading-relaxed whitespace-pre-line">
          {aboutText}
        </p>
      </div>

      {/* 4. Programs & Pathways Section (برامج ومسارات المقرأة المعتمدة) */}
      <div className="space-y-3">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-2">
            <Sparkles className="w-4 h-4 text-gold" />
            <h2 className="text-sm sm:text-base font-extrabold text-slate-900 dark:text-slate-100">
              {t('programsTitle')} ({programs.length})
            </h2>
          </div>

          {canEdit && (
            <button
              onClick={() => setShowAddProgram(true)}
              className="flex items-center gap-1 px-3 py-1.5 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white text-xs font-bold shadow-sm transition-transform active:scale-95"
            >
              <Plus className="w-3.5 h-3.5" />
              <span>+ مسار جديد (المدير)</span>
            </button>
          )}
        </div>

        {/* Programs Cards Grid */}
        <div className="grid grid-cols-1 gap-3">
          {programs.map((prog) => (
            <div
              key={prog.id}
              className="glass-panel rounded-2xl p-4 border border-slate-200 dark:border-slate-800 hover:border-skyBlue-300 dark:hover:border-skyBlue-700 transition-all duration-200 shadow-sm"
            >
              <div className="flex items-start justify-between gap-2">
                <div>
                  <span className="inline-block px-2 py-0.5 rounded text-[10px] font-bold bg-skyBlue-100 dark:bg-skyBlue-950 text-skyBlue-700 dark:text-skyBlue-300 mb-1 border border-skyBlue-200 dark:border-skyBlue-800">
                    {prog.category}
                  </span>
                  <h3 className="text-sm sm:text-base font-extrabold text-slate-900 dark:text-slate-100">
                    {prog.title}
                  </h3>
                </div>

                <div className="flex items-center gap-1 flex-shrink-0">
                  <span className="text-[11px] font-semibold text-slate-500 dark:text-slate-400">
                    {prog.enrolledCount} دارس
                  </span>
                  {canEdit && (
                    <button
                      onClick={() => deleteProgram(prog.id)}
                      className="p-1 rounded text-rose-500 hover:bg-rose-50 dark:hover:bg-rose-950/50"
                      title="حذف هذا المسار (المدير)"
                    >
                      <Trash2 className="w-3.5 h-3.5" />
                    </button>
                  )}
                </div>
              </div>

              <p className="text-xs sm:text-sm text-slate-600 dark:text-slate-300 mt-2 leading-relaxed">
                {prog.description}
              </p>

              {/* How to use & requirements box */}
              <div className="mt-3 p-2.5 rounded-xl bg-skyBlue-50/60 dark:bg-skyBlue-950/40 border border-skyBlue-100 dark:border-skyBlue-900 text-xs space-y-1.5">
                <div className="flex items-start gap-1.5 text-skyBlue-800 dark:text-skyBlue-200">
                  <HelpCircle className="w-3.5 h-3.5 text-skyBlue-600 dark:text-skyBlue-400 mt-0.5 flex-shrink-0" />
                  <div>
                    <strong className="font-bold">{t('howToUse')}</strong>{' '}
                    <span>{prog.howToUse}</span>
                  </div>
                </div>

                <div className="flex items-start gap-1.5 text-amber-800 dark:text-amber-200">
                  <CheckCircle className="w-3.5 h-3.5 text-amber-600 dark:text-amber-400 mt-0.5 flex-shrink-0" />
                  <div>
                    <strong className="font-bold">{t('requirements')}</strong>{' '}
                    <span>{prog.requirements}</span>
                  </div>
                </div>
              </div>

              {/* Footer info & Registration button */}
              <div className="mt-3 pt-2.5 border-t border-slate-100 dark:border-slate-800 flex items-center justify-between flex-wrap gap-2">
                <div className="flex items-center gap-1.5 text-xs text-slate-500 dark:text-slate-400">
                  <Clock className="w-3.5 h-3.5 text-gold" />
                  <span>{t('duration')} <strong>{prog.durationText}</strong></span>
                </div>

                {/* Launch Registration Chatbot Button */}
                <button
                  onClick={() => openRegistrationBot(prog)}
                  className="px-4 py-2 rounded-xl bg-gradient-to-r from-gold to-amber-600 hover:from-amber-500 hover:to-amber-600 text-slate-950 font-extrabold text-xs flex items-center gap-1.5 shadow-md transition-transform active:scale-95"
                >
                  <span>{t('registerInPath')} (البوت الآلي)</span>
                  <Sparkles className="w-3.5 h-3.5" />
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Edit Banner Modal (Director CMS) */}
      {showEditBanner && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-3 animate-in fade-in">
          <div className="bg-white dark:bg-slate-900 rounded-2xl max-w-lg w-full p-4 sm:p-5 border border-slate-200 dark:border-slate-800 shadow-2xl">
            <div className="flex items-center justify-between mb-3">
              <h3 className="font-extrabold text-sm sm:text-base text-slate-900 dark:text-slate-100">
                تعديل لوحة الإعلانات الكبيرة (خاص بالمشرف):
              </h3>
              <button onClick={() => setShowEditBanner(false)}>
                <X className="w-5 h-5 text-slate-400" />
              </button>
            </div>

            <form onSubmit={handleSaveBanner} className="space-y-3">
              <textarea
                value={editedBanner}
                onChange={(e) => setEditedBanner(e.target.value)}
                rows={4}
                className="w-full p-3 text-xs sm:text-sm rounded-xl border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-slate-100 focus:ring-2 focus:ring-skyBlue-500 outline-none"
              />
              <div className="flex justify-end gap-2">
                <button
                  type="button"
                  onClick={() => setShowEditBanner(false)}
                  className="px-3 py-1.5 rounded-lg text-xs font-bold text-slate-500 hover:bg-slate-100 dark:hover:bg-slate-800"
                >
                  إلغاء
                </button>
                <button
                  type="submit"
                  className="px-4 py-1.5 rounded-lg bg-skyBlue-600 text-white font-bold text-xs shadow hover:bg-skyBlue-700"
                >
                  حفظ التعديل الفوري
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Edit About Modal (Director CMS) */}
      {showEditAbout && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-3 animate-in fade-in">
          <div className="bg-white dark:bg-slate-900 rounded-2xl max-w-lg w-full p-4 sm:p-5 border border-slate-200 dark:border-slate-800 shadow-2xl">
            <div className="flex items-center justify-between mb-3">
              <h3 className="font-extrabold text-sm sm:text-base text-slate-900 dark:text-slate-100">
                تعديل نبذة «عن المقرأة» (خاص بالمشرف):
              </h3>
              <button onClick={() => setShowEditAbout(false)}>
                <X className="w-5 h-5 text-slate-400" />
              </button>
            </div>

            <form onSubmit={handleSaveAbout} className="space-y-3">
              <textarea
                value={editedAbout}
                onChange={(e) => setEditedAbout(e.target.value)}
                rows={5}
                className="w-full p-3 text-xs sm:text-sm rounded-xl border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-slate-100 focus:ring-2 focus:ring-skyBlue-500 outline-none"
              />
              <div className="flex justify-end gap-2">
                <button
                  type="button"
                  onClick={() => setShowEditAbout(false)}
                  className="px-3 py-1.5 rounded-lg text-xs font-bold text-slate-500 hover:bg-slate-100 dark:hover:bg-slate-800"
                >
                  إلغاء
                </button>
                <button
                  type="submit"
                  className="px-4 py-1.5 rounded-lg bg-skyBlue-600 text-white font-bold text-xs shadow hover:bg-skyBlue-700"
                >
                  حفظ التعديل
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Add Program Modal (Director CMS) */}
      {showAddProgram && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-3 animate-in fade-in">
          <div className="bg-white dark:bg-slate-900 rounded-2xl max-w-lg w-full p-4 sm:p-5 border border-slate-200 dark:border-slate-800 shadow-2xl max-h-[90vh] overflow-y-auto">
            <div className="flex items-center justify-between mb-3">
              <h3 className="font-extrabold text-sm sm:text-base text-slate-900 dark:text-slate-100">
                إضافة مسار / برنامج قرآني جديد (المشرف):
              </h3>
              <button onClick={() => setShowAddProgram(false)}>
                <X className="w-5 h-5 text-slate-400" />
              </button>
            </div>

            <form onSubmit={handleCreateProgram} className="space-y-3">
              <div>
                <label className="block text-xs font-bold text-slate-700 dark:text-slate-300 mb-1">
                  عنوان المسار القرآني:
                </label>
                <input
                  type="text"
                  required
                  value={newProgram.title}
                  onChange={(e) => setNewProgram({ ...newProgram, title: e.target.value })}
                  placeholder="مثال: مسار إتقان تلاوة سورة البقرة برواية الدوري"
                  className="w-full px-3 py-2 text-xs rounded-xl border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none"
                />
              </div>

              <div>
                <label className="block text-xs font-bold text-slate-700 dark:text-slate-300 mb-1">
                  تصنيف المسار:
                </label>
                <input
                  type="text"
                  value={newProgram.category}
                  onChange={(e) => setNewProgram({ ...newProgram, category: e.target.value })}
                  placeholder="مثال: تصحيح التلاوة / الإجازة بالسند"
                  className="w-full px-3 py-2 text-xs rounded-xl border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none"
                />
              </div>

              <div>
                <label className="block text-xs font-bold text-slate-700 dark:text-slate-300 mb-1">
                  الوصف والمنهجية:
                </label>
                <textarea
                  value={newProgram.description}
                  onChange={(e) => setNewProgram({ ...newProgram, description: e.target.value })}
                  rows={2}
                  placeholder="شرح موجز لأهداف المسار وطريقة التسميع..."
                  className="w-full px-3 py-2 text-xs rounded-xl border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none"
                />
              </div>

              <div>
                <label className="block text-xs font-bold text-slate-700 dark:text-slate-300 mb-1">
                  كيفية الاستخدام وطريقة الحضور:
                </label>
                <input
                  type="text"
                  value={newProgram.howToUse}
                  onChange={(e) => setNewProgram({ ...newProgram, howToUse: e.target.value })}
                  placeholder="طريقة الحضور في المجلس الصوتي..."
                  className="w-full px-3 py-2 text-xs rounded-xl border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none"
                />
              </div>

              <div>
                <label className="block text-xs font-bold text-slate-700 dark:text-slate-300 mb-1">
                  شروط الانضمام:
                </label>
                <input
                  type="text"
                  value={newProgram.requirements}
                  onChange={(e) => setNewProgram({ ...newProgram, requirements: e.target.value })}
                  placeholder="شروط التسجيل..."
                  className="w-full px-3 py-2 text-xs rounded-xl border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none"
                />
              </div>

              <div>
                <label className="block text-xs font-bold text-slate-700 dark:text-slate-300 mb-1">
                  المدة المقررة:
                </label>
                <input
                  type="text"
                  value={newProgram.durationText}
                  onChange={(e) => setNewProgram({ ...newProgram, durationText: e.target.value })}
                  placeholder="مثال: شهرين (جلسات أسبوعية)"
                  className="w-full px-3 py-2 text-xs rounded-xl border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none"
                />
              </div>

              <div className="flex justify-end gap-2 pt-2 border-t border-slate-100 dark:border-slate-800">
                <button
                  type="button"
                  onClick={() => setShowAddProgram(false)}
                  className="px-3 py-1.5 rounded-lg text-xs font-bold text-slate-500 hover:bg-slate-100 dark:hover:bg-slate-800"
                >
                  إلغاء
                </button>
                <button
                  type="submit"
                  className="px-4 py-1.5 rounded-lg bg-emerald-600 text-white font-bold text-xs shadow hover:bg-emerald-700"
                >
                  إضافة المسار فوراً
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
