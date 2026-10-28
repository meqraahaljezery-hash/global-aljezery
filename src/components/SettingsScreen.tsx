import React, { useState } from 'react';
import { useApp } from '../context/AppContext';
import {
  Settings,
  ShieldCheck,
  Building2,
  CheckCircle,
  XCircle,
  Plus,
  Edit2,
  Trash2,
  Moon,
  Sun,
  Globe,
  Bell,
  User,
  Sparkles,
  Save,
  Check,
} from 'lucide-react';
import { BankPaymentInfo, QuranProgram } from '../types';

export const SettingsScreen: React.FC = () => {
  const {
    t,
    currentAccount,
    availableAccounts,
    switchAccount,
    currentRole,
    isDarkMode,
    toggleDarkMode,
    currentLang,
    setLanguage,
    bannerText,
    updateBanner,
    aboutText,
    updateAbout,
    programs,
    deleteProgram,
    bankDetails,
    saveBankDetail,
    deleteBankDetail,
    payments,
    approvePayment,
    rejectPayment,
    loans,
    approveLoan,
    rejectLoan,
  } = useApp();

  const isAdmin = currentRole === 'ADMIN';

  // CMS states
  const [cmsBanner, setCmsBanner] = useState(bannerText);
  const [cmsAbout, setCmsAbout] = useState(aboutText);
  const [savedBannerSuccess, setSavedBannerSuccess] = useState(false);
  const [savedAboutSuccess, setSavedAboutSuccess] = useState(false);

  // Bank editing state
  const [editingBank, setEditingBank] = useState<Partial<BankPaymentInfo> | null>(null);

  const handleSaveBanner = (e: React.FormEvent) => {
    e.preventDefault();
    updateBanner(cmsBanner);
    setSavedBannerSuccess(true);
    setTimeout(() => setSavedBannerSuccess(false), 2500);
  };

  const handleSaveAbout = (e: React.FormEvent) => {
    e.preventDefault();
    updateAbout(cmsAbout);
    setSavedAboutSuccess(true);
    setTimeout(() => setSavedAboutSuccess(false), 2500);
  };

  const handleSaveBank = (e: React.FormEvent) => {
    e.preventDefault();
    if (!editingBank || !editingBank.bankName || !editingBank.accountNumber) return;

    saveBankDetail({
      id: editingBank.id || `bank_${Date.now()}`,
      bankName: editingBank.bankName,
      accountHolder: editingBank.accountHolder || 'مقرأة الإمام الجزري',
      accountNumber: editingBank.accountNumber,
      ibanOrIdentifier: editingBank.ibanOrIdentifier || '',
      currency: editingBank.currency || 'SAR / USD',
      instructions: editingBank.instructions || '',
    });
    setEditingBank(null);
  };

  return (
    <div className="space-y-4 pb-20 max-w-4xl mx-auto px-3 sm:px-4 pt-3">
      {/* Settings Header */}
      <div className="glass-panel rounded-2xl p-4 border border-skyBlue-200 dark:border-skyBlue-900 bg-gradient-to-r from-skyBlue-50 to-white dark:from-slate-900 dark:to-slate-950 flex items-center justify-between">
        <div className="flex items-center gap-2.5">
          <Settings className="w-5 h-5 text-gold" />
          <div>
            <h2 className="text-base sm:text-lg font-extrabold text-skyBlue-800 dark:text-skyBlue-300">
              {t('tabSettings')}
            </h2>
            <p className="text-xs text-slate-500 dark:text-slate-400">
              {isAdmin
                ? 'لوحة إدارة المقرأة الشاملة للمشرف العام والتحكم بدون داتابيس'
                : 'تفضيلات الطالب والحساب واللغة والمظهر'}
            </p>
          </div>
        </div>

        <span
          className={`px-2.5 py-1 rounded-full text-xs font-bold ${
            isAdmin
              ? 'bg-amber-100 text-amber-800 dark:bg-amber-900 dark:text-amber-300 border border-amber-300'
              : 'bg-skyBlue-100 text-skyBlue-800 dark:bg-skyBlue-900 dark:text-skyBlue-200'
          }`}
        >
          {isAdmin ? 'صلاحيات المدير الكاملة' : 'حساب مستخدم'}
        </span>
      </div>

      {/* 1. Account Switcher & Permissions Section */}
      <div className="glass-panel rounded-2xl p-4 border border-slate-200 dark:border-slate-800 shadow-sm space-y-3">
        <div className="flex items-center gap-2">
          <User className="w-4 h-4 text-gold" />
          <h3 className="font-extrabold text-sm text-slate-900 dark:text-slate-100">
            الحساب الحالي وصلاحيات الدخول:
          </h3>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-3 gap-2">
          {availableAccounts.map((acc) => {
            const isSelected = acc.id === currentAccount.id;
            return (
              <button
                key={acc.id}
                onClick={() => switchAccount(acc)}
                className={`p-3 rounded-xl border text-start transition-all ${
                  isSelected
                    ? 'border-skyBlue-500 bg-skyBlue-50/80 dark:bg-skyBlue-950/60 ring-1 ring-skyBlue-500 font-bold'
                    : 'border-slate-200 dark:border-slate-800 hover:bg-slate-50 dark:hover:bg-slate-850'
                }`}
              >
                <div className="flex items-center gap-2">
                  <span className="text-xl">{acc.avatar}</span>
                  <div className="min-w-0">
                    <div className="text-xs font-bold text-slate-900 dark:text-slate-100 truncate">
                      {acc.name}
                    </div>
                    <div className="text-[10px] text-amber-600 dark:text-amber-400 font-medium">
                      {acc.role === 'ADMIN' ? 'مدير ومشرف' : acc.role === 'TEACHER' ? 'معلم مقرأة' : 'طالب علم'}
                    </div>
                  </div>
                </div>
                <div className="text-[10px] text-slate-400 font-mono mt-1 truncate">
                  {acc.email}
                </div>
              </button>
            );
          })}
        </div>
      </div>

      {/* 2. Theme & Language Preferences (نهاري / ليلي + اللغات) */}
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
        {/* Day / Night Mode */}
        <div className="glass-panel rounded-2xl p-4 border border-slate-200 dark:border-slate-800 shadow-sm">
          <div className="flex items-center justify-between mb-2">
            <span className="font-extrabold text-xs text-slate-900 dark:text-slate-100">
              المظهر (Day / Night Mode):
            </span>
            <span className="text-xs text-slate-400 font-bold">
              {isDarkMode ? 'الوضع الليلي' : 'الوضع النهاري'}
            </span>
          </div>

          <div className="grid grid-cols-2 gap-2 mt-2">
            <button
              onClick={() => isDarkMode && toggleDarkMode()}
              className={`p-2.5 rounded-xl border text-xs font-bold flex items-center justify-center gap-1.5 transition-all ${
                !isDarkMode
                  ? 'bg-amber-100 border-amber-400 text-amber-900 shadow-sm'
                  : 'border-slate-200 dark:border-slate-700 hover:bg-slate-100 text-slate-600'
              }`}
            >
              <Sun className="w-4 h-4 text-amber-600" />
              <span>الوضع النهاري (أزرق وأبيض)</span>
            </button>

            <button
              onClick={() => !isDarkMode && toggleDarkMode()}
              className={`p-2.5 rounded-xl border text-xs font-bold flex items-center justify-center gap-1.5 transition-all ${
                isDarkMode
                  ? 'bg-skyBlue-950 border-skyBlue-600 text-skyBlue-300 shadow-sm'
                  : 'border-slate-200 dark:border-slate-700 hover:bg-slate-800 text-slate-400'
              }`}
            >
              <Moon className="w-4 h-4 text-amber-300" />
              <span>الوضع الليلي (كحلي وذهبي)</span>
            </button>
          </div>
        </div>

        {/* Language Selection */}
        <div className="glass-panel rounded-2xl p-4 border border-slate-200 dark:border-slate-800 shadow-sm">
          <div className="flex items-center justify-between mb-2">
            <span className="font-extrabold text-xs text-slate-900 dark:text-slate-100">
              لغة التطبيق (i18n Multi-Language):
            </span>
            <Globe className="w-4 h-4 text-skyBlue-600" />
          </div>

          <div className="grid grid-cols-3 gap-1.5 mt-2">
            <button
              onClick={() => setLanguage('ar')}
              className={`p-2 rounded-xl text-xs font-bold border ${
                currentLang === 'ar'
                  ? 'bg-skyBlue-600 text-white border-skyBlue-600 shadow-sm'
                  : 'border-slate-200 dark:border-slate-700 hover:bg-slate-100 dark:hover:bg-slate-800 text-slate-700 dark:text-slate-300'
              }`}
            >
              العربية (RTL)
            </button>
            <button
              onClick={() => setLanguage('am')}
              className={`p-2 rounded-xl text-xs font-bold border ${
                currentLang === 'am'
                  ? 'bg-skyBlue-600 text-white border-skyBlue-600 shadow-sm'
                  : 'border-slate-200 dark:border-slate-700 hover:bg-slate-100 dark:hover:bg-slate-800 text-slate-700 dark:text-slate-300'
              }`}
            >
              አማርኛ (AM)
            </button>
            <button
              onClick={() => setLanguage('en')}
              className={`p-2 rounded-xl text-xs font-bold border ${
                currentLang === 'en'
                  ? 'bg-skyBlue-600 text-white border-skyBlue-600 shadow-sm'
                  : 'border-slate-200 dark:border-slate-700 hover:bg-slate-100 dark:hover:bg-slate-800 text-slate-700 dark:text-slate-300'
              }`}
            >
              English (EN)
            </button>
          </div>
        </div>
      </div>

      {/* ============================================================== */}
      {/* SUPERVISOR CMS PANEL (لوحة الإدارة والتحكم بدون داتابيس) */}
      {/* ============================================================== */}
      {isAdmin ? (
        <div className="space-y-4 pt-2">
          <div className="flex items-center gap-2 p-2.5 rounded-xl bg-amber-500/10 border border-amber-500/30 text-amber-800 dark:text-amber-300 text-xs font-bold">
            <ShieldCheck className="w-4 h-4 text-gold flex-shrink-0" />
            <span>
              لوحة تحكم المشرف العام (khdahd241@gmail.com): جميع التعديلات أدناه تُحفظ فوراً في التطبيق دون الحاجة لقاعدة بيانات.
            </span>
          </div>

          {/* CMS 1: Edit Announcement Banner */}
          <div className="glass-panel rounded-2xl p-4 border border-slate-200 dark:border-slate-800 shadow-sm">
            <h3 className="font-extrabold text-xs sm:text-sm text-slate-900 dark:text-slate-100 mb-2">
              1. تعديل لوحة الإعلانات الكبيرة الرئيسية:
            </h3>
            <form onSubmit={handleSaveBanner} className="space-y-2">
              <textarea
                value={cmsBanner}
                onChange={(e) => setCmsBanner(e.target.value)}
                rows={3}
                className="w-full p-2.5 text-xs rounded-xl border border-slate-300 dark:border-slate-700 bg-white dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none focus:ring-2 focus:ring-skyBlue-500"
              />
              <div className="flex items-center justify-between">
                <span className="text-[10px] text-slate-400">
                  يظهر هذا الإعلان في أعلى الصفحة الرئيسية لجميع الطلاب والزوار.
                </span>
                <button
                  type="submit"
                  className="px-3 py-1.5 rounded-xl bg-skyBlue-600 hover:bg-skyBlue-700 text-white font-bold text-xs flex items-center gap-1 shadow"
                >
                  {savedBannerSuccess ? <Check className="w-3.5 h-3.5" /> : <Save className="w-3.5 h-3.5" />}
                  <span>{savedBannerSuccess ? 'تم الحفظ!' : 'حفظ الإعلان'}</span>
                </button>
              </div>
            </form>
          </div>

          {/* CMS 2: Edit About Maqraah */}
          <div className="glass-panel rounded-2xl p-4 border border-slate-200 dark:border-slate-800 shadow-sm">
            <h3 className="font-extrabold text-xs sm:text-sm text-slate-900 dark:text-slate-100 mb-2">
              2. تعديل نبذة «عن مقرأة الإمام الجزري»:
            </h3>
            <form onSubmit={handleSaveAbout} className="space-y-2">
              <textarea
                value={cmsAbout}
                onChange={(e) => setCmsAbout(e.target.value)}
                rows={4}
                className="w-full p-2.5 text-xs rounded-xl border border-slate-300 dark:border-slate-700 bg-white dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none focus:ring-2 focus:ring-skyBlue-500"
              />
              <div className="flex items-center justify-between">
                <span className="text-[10px] text-slate-400">
                  شرح رؤية المقرأة وأسانيد المشايخ المتصلة.
                </span>
                <button
                  type="submit"
                  className="px-3 py-1.5 rounded-xl bg-skyBlue-600 hover:bg-skyBlue-700 text-white font-bold text-xs flex items-center gap-1 shadow"
                >
                  {savedAboutSuccess ? <Check className="w-3.5 h-3.5" /> : <Save className="w-3.5 h-3.5" />}
                  <span>{savedAboutSuccess ? 'تم الحفظ!' : 'حفظ النبذة'}</span>
                </button>
              </div>
            </form>
          </div>

          {/* CMS 3: Manage Bank Accounts (وسائل الدفع البنكية) */}
          <div className="glass-panel rounded-2xl p-4 border border-slate-200 dark:border-slate-800 shadow-sm space-y-3">
            <div className="flex items-center justify-between">
              <div>
                <h3 className="font-extrabold text-xs sm:text-sm text-slate-900 dark:text-slate-100">
                  3. إدارة الحسابات البنكية والمحافظ الرقمية ({bankDetails.length}):
                </h3>
                <p className="text-[10px] text-slate-400">
                  تعديل اسم البنك ورقم الحساب واسم المستفيد التي تظهر للطلاب في شاشة «حسابي».
                </p>
              </div>

              <button
                onClick={() =>
                  setEditingBank({
                    bankName: '',
                    accountHolder: 'مقرأة الإمام الجزري',
                    accountNumber: '',
                    ibanOrIdentifier: '',
                    currency: 'SAR / USD',
                    instructions: '',
                  })
                }
                className="px-2.5 py-1.5 rounded-xl bg-emerald-600 text-white text-xs font-bold flex items-center gap-1"
              >
                <Plus className="w-3.5 h-3.5" />
                <span>+ حساب جديد</span>
              </button>
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 gap-2.5">
              {bankDetails.map((bank) => (
                <div
                  key={bank.id}
                  className="p-3 rounded-xl bg-slate-50 dark:bg-slate-800/60 border border-slate-200 dark:border-slate-700 text-xs"
                >
                  <div className="flex items-start justify-between">
                    <div>
                      <div className="font-bold text-skyBlue-800 dark:text-skyBlue-300">
                        {bank.bankName}
                      </div>
                      <div className="text-[10px] text-slate-400 mt-0.5">
                        المستفيد: <strong>{bank.accountHolder}</strong>
                      </div>
                    </div>

                    <div className="flex items-center gap-1">
                      <button
                        onClick={() => setEditingBank(bank)}
                        className="p-1 text-skyBlue-600 hover:bg-skyBlue-50 dark:hover:bg-slate-700 rounded"
                      >
                        <Edit2 className="w-3 h-3" />
                      </button>
                      <button
                        onClick={() => deleteBankDetail(bank.id)}
                        className="p-1 text-rose-500 hover:bg-rose-50 dark:hover:bg-slate-700 rounded"
                      >
                        <Trash2 className="w-3 h-3" />
                      </button>
                    </div>
                  </div>

                  <div className="font-mono text-[11px] bg-white dark:bg-slate-900 p-1 rounded mt-1.5 border border-slate-200 dark:border-slate-700">
                    {bank.accountNumber} ({bank.currency})
                  </div>
                </div>
              ))}
            </div>

            {/* Bank Edit Form Modal */}
            {editingBank && (
              <div className="p-3 rounded-xl bg-skyBlue-50/80 dark:bg-slate-800 border border-skyBlue-200 dark:border-slate-700 space-y-2 text-xs">
                <span className="font-bold text-skyBlue-900 dark:text-skyBlue-200 block">
                  {editingBank.id ? 'تعديل الحساب البنكي:' : 'إضافة حساب بنكي جديد:'}
                </span>

                <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
                  <input
                    type="text"
                    value={editingBank.bankName || ''}
                    onChange={(e) => setEditingBank({ ...editingBank, bankName: e.target.value })}
                    placeholder="اسم البنك (مثال: مصرف الراجحي)"
                    className="px-2.5 py-1.5 rounded-lg border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-900"
                  />
                  <input
                    type="text"
                    value={editingBank.accountHolder || ''}
                    onChange={(e) => setEditingBank({ ...editingBank, accountHolder: e.target.value })}
                    placeholder="اسم المستفيد الكامل"
                    className="px-2.5 py-1.5 rounded-lg border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-900"
                  />
                </div>

                <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
                  <input
                    type="text"
                    value={editingBank.accountNumber || ''}
                    onChange={(e) => setEditingBank({ ...editingBank, accountNumber: e.target.value })}
                    placeholder="رقم الحساب / الآيبان"
                    className="px-2.5 py-1.5 rounded-lg border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-900 font-mono"
                  />
                  <input
                    type="text"
                    value={editingBank.currency || ''}
                    onChange={(e) => setEditingBank({ ...editingBank, currency: e.target.value })}
                    placeholder="العملة (SAR / USD / ETB)"
                    className="px-2.5 py-1.5 rounded-lg border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-900"
                  />
                </div>

                <div className="flex justify-end gap-2 pt-1">
                  <button
                    type="button"
                    onClick={() => setEditingBank(null)}
                    className="px-2.5 py-1 text-slate-500 font-bold"
                  >
                    إلغاء
                  </button>
                  <button
                    onClick={handleSaveBank}
                    className="px-4 py-1 rounded-lg bg-emerald-600 text-white font-bold"
                  >
                    حفظ الحساب
                  </button>
                </div>
              </div>
            )}
          </div>

          {/* CMS 4: Review Manual Payments (مراجعة التحويلات اليدوية للطلاب) */}
          <div className="glass-panel rounded-2xl p-4 border border-slate-200 dark:border-slate-800 shadow-sm space-y-2">
            <h3 className="font-extrabold text-xs sm:text-sm text-slate-900 dark:text-slate-100">
              4. مراجعة وتأكيد طلبات الدفع اليدوية (إضافة الدقائق للطالب):
            </h3>

            <div className="space-y-2">
              {payments.map((p) => (
                <div
                  key={p.id}
                  className="p-3 rounded-xl bg-slate-50 dark:bg-slate-800/60 border border-slate-200 dark:border-slate-700 flex flex-col sm:flex-row sm:items-center justify-between gap-2 text-xs"
                >
                  <div>
                    <div className="font-bold text-slate-900 dark:text-slate-100">
                      الطالب: {p.studentName} • {p.packageName}
                    </div>
                    <div className="text-[11px] text-slate-500 dark:text-slate-400 mt-0.5">
                      رقم العملية: <strong className="font-mono text-skyBlue-600">{p.transactionId}</strong> • هاتف: {p.phone} • الحساب: {p.method}
                    </div>
                  </div>

                  <div className="flex items-center gap-2 flex-shrink-0">
                    {p.status === 'PENDING' ? (
                      <>
                        <button
                          onClick={() => approvePayment(p.id)}
                          className="px-3 py-1.5 rounded-xl bg-emerald-600 text-white font-bold text-xs flex items-center gap-1 shadow hover:bg-emerald-700"
                        >
                          <CheckCircle className="w-3.5 h-3.5" />
                          <span>تأكيد وشحن (+{p.minutes} د)</span>
                        </button>
                        <button
                          onClick={() => rejectPayment(p.id)}
                          className="px-2.5 py-1.5 rounded-xl bg-rose-600 text-white font-bold text-xs flex items-center gap-1 hover:bg-rose-700"
                        >
                          <XCircle className="w-3.5 h-3.5" />
                          <span>رفض</span>
                        </button>
                      </>
                    ) : (
                      <span
                        className={`px-2 py-0.5 rounded-full text-[10px] font-bold ${
                          p.status === 'APPROVED'
                            ? 'bg-emerald-100 text-emerald-800 dark:bg-emerald-950 dark:text-emerald-300'
                            : 'bg-rose-100 text-rose-800 dark:bg-rose-950 dark:text-rose-300'
                        }`}
                      >
                        {p.status === 'APPROVED' ? 'تم الشحن والاعتماد' : 'مرفوض'}
                      </span>
                    )}
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* CMS 5: Review Minute Loans (سلف الدقائق) */}
          <div className="glass-panel rounded-2xl p-4 border border-slate-200 dark:border-slate-800 shadow-sm space-y-2">
            <h3 className="font-extrabold text-xs sm:text-sm text-slate-900 dark:text-slate-100">
              5. مراجعة طلبات سلف الدقائق للطلاب:
            </h3>

            <div className="space-y-2">
              {loans.map((loan) => (
                <div
                  key={loan.id}
                  className="p-3 rounded-xl bg-slate-50 dark:bg-slate-800/60 border border-slate-200 dark:border-slate-700 flex flex-col sm:flex-row sm:items-center justify-between gap-2 text-xs"
                >
                  <div>
                    <div className="font-bold text-slate-900 dark:text-slate-100">
                      الطالب: {loan.studentName} (طلب: +{loan.minutesRequested} دقيقة)
                    </div>
                    <div className="text-[11px] text-slate-500 dark:text-slate-400 mt-0.5">
                      السبب: {loan.reason} • التاريخ: {loan.date}
                    </div>
                  </div>

                  <div className="flex items-center gap-2 flex-shrink-0">
                    {loan.status === 'PENDING' ? (
                      <>
                        <button
                          onClick={() => approveLoan(loan.id)}
                          className="px-3 py-1.5 rounded-xl bg-emerald-600 text-white font-bold text-xs flex items-center gap-1 shadow hover:bg-emerald-700"
                        >
                          <CheckCircle className="w-3.5 h-3.5" />
                          <span>منح السلفة</span>
                        </button>
                        <button
                          onClick={() => rejectLoan(loan.id)}
                          className="px-2.5 py-1.5 rounded-xl bg-rose-600 text-white font-bold text-xs hover:bg-rose-700"
                        >
                          <XCircle className="w-3.5 h-3.5" />
                          <span>رفض</span>
                        </button>
                      </>
                    ) : (
                      <span className="text-emerald-600 font-bold text-[10px]">
                        معتمد
                      </span>
                    )}
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      ) : (
        /* Student / Teacher General Preferences */
        <div className="glass-panel rounded-2xl p-4 border border-slate-200 dark:border-slate-800 shadow-sm space-y-3">
          <h3 className="font-extrabold text-sm text-slate-900 dark:text-slate-100">
            تنبيهات وإشعارات الطالب:
          </h3>
          <div className="space-y-2 text-xs text-slate-600 dark:text-slate-300">
            <label className="flex items-center gap-2 cursor-pointer">
              <input type="checkbox" defaultChecked className="rounded text-skyBlue-600" />
              <span>إشعار عند بدء مجالس البث المباشر في الحلقات السبع</span>
            </label>
            <label className="flex items-center gap-2 cursor-pointer">
              <input type="checkbox" defaultChecked className="rounded text-skyBlue-600" />
              <span>إشعار عند وصول الدور في طابور التسميع مع الشيخ</span>
            </label>
            <label className="flex items-center gap-2 cursor-pointer">
              <input type="checkbox" defaultChecked className="rounded text-skyBlue-600" />
              <span>تنبيه عند اقتراب نفاد رصيد دقائق التسميع</span>
            </label>
          </div>
        </div>
      )}
    </div>
  );
};
