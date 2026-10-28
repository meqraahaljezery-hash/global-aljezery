import React, { useState } from 'react';
import { useApp } from '../context/AppContext';
import {
  Wallet,
  Sparkles,
  CreditCard,
  Building2,
  Clock,
  CheckCircle2,
  AlertCircle,
  PlusCircle,
  Send,
  X,
  Copy,
  Check,
  ShieldCheck,
  History,
} from 'lucide-react';
import { MinutePackage } from '../types';

export const MyAccountScreen: React.FC = () => {
  const {
    t,
    studentMinutesBalance,
    packages,
    bankDetails,
    payments,
    submitPayment,
    loans,
    requestMinuteLoan,
    currentAccount,
  } = useApp();

  const [selectedPackage, setSelectedPackage] = useState<MinutePackage>(packages[1]);
  const [selectedBankId, setSelectedBankId] = useState<string>(bankDetails[0]?.id || '');
  const [senderName, setSenderName] = useState(currentAccount.name || '');
  const [senderPhone, setSenderPhone] = useState('');
  const [transactionId, setTransactionId] = useState('');
  const [copiedBankId, setCopiedBankId] = useState<string | null>(null);

  // Minute Loan Modal
  const [showLoanModal, setShowLoanModal] = useState(false);
  const [loanMins, setLoanMins] = useState(20);
  const [loanReason, setLoanReason] = useState('');
  const [paymentSubmittedToast, setPaymentSubmittedToast] = useState(false);

  const selectedBank = bankDetails.find((b) => b.id === selectedBankId) || bankDetails[0];

  const handleCopyAccount = (bankId: string, text: string) => {
    navigator.clipboard.writeText(text);
    setCopiedBankId(bankId);
    setTimeout(() => setCopiedBankId(null), 2000);
  };

  const handlePaymentSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!transactionId.trim()) return;

    submitPayment(
      selectedPackage.nameAr,
      selectedPackage.minutes,
      selectedPackage.priceUsd,
      selectedBank ? selectedBank.bankName : 'تحويل بنكي يدوي',
      transactionId,
      senderPhone,
      senderName
    );

    setTransactionId('');
    setPaymentSubmittedToast(true);
    setTimeout(() => setPaymentSubmittedToast(false), 4000);
  };

  const handleLoanSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!loanReason.trim()) return;

    requestMinuteLoan(senderName || currentAccount.name, loanMins, loanReason);
    setLoanReason('');
    setShowLoanModal(false);
  };

  return (
    <div className="space-y-4 pb-20 max-w-4xl mx-auto px-3 sm:px-4 pt-3">
      {/* 1. Minutes Balance & Loan Action Hero Card */}
      <div className="rounded-2xl p-5 bg-gradient-to-br from-skyBlue-600 via-skyBlue-700 to-islamic-midnight text-white shadow-lg border border-skyBlue-400/30">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <div>
            <span className="text-xs font-bold text-skyBlue-200">
              {t('minutesBalance')}
            </span>
            <div className="flex items-baseline gap-2 mt-1">
              <span className="text-4xl sm:text-5xl font-black text-amber-300 font-mono">
                {studentMinutesBalance}
              </span>
              <span className="text-sm sm:text-base font-bold text-slate-100">
                {t('minutesUnit')}
              </span>
            </div>
            <p className="text-[11px] text-skyBlue-200 mt-1">
              يُخصم الرصيد تلقائياً عند انتهاء جلسات التسميع المباشرة مع الشيخ في الحلقات السبع.
            </p>
          </div>

          <div className="flex sm:flex-col gap-2">
            <button
              onClick={() => setShowLoanModal(true)}
              className="flex-1 sm:flex-initial px-4 py-2.5 rounded-xl bg-gold hover:bg-amber-400 text-slate-950 font-extrabold text-xs flex items-center justify-center gap-1.5 shadow transition-transform active:scale-95"
            >
              <Sparkles className="w-4 h-4" />
              <span>{t('requestLoan')}</span>
            </button>
          </div>
        </div>
      </div>

      {paymentSubmittedToast && (
        <div className="p-3 rounded-xl bg-emerald-500 text-white text-xs font-bold flex items-center gap-2 animate-in fade-in shadow">
          <CheckCircle2 className="w-4 h-4 flex-shrink-0" />
          <span>
            تم استلام بيانات التحويل بنجاح! سيقوم المشرف العام بمراجعة العملية وتفعيل الدقائق في رصيدك فوراً.
          </span>
        </div>
      )}

      {/* 2. Packages System (باقات شحن دقائق التسميع) */}
      <div className="space-y-2.5">
        <div className="flex items-center gap-2">
          <CreditCard className="w-4 h-4 text-gold" />
          <h2 className="text-sm sm:text-base font-extrabold text-slate-900 dark:text-slate-100">
            {t('packagesTitle')}
          </h2>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-3">
          {packages.map((pkg) => {
            const isSelected = selectedPackage.id === pkg.id;

            return (
              <div
                key={pkg.id}
                onClick={() => setSelectedPackage(pkg)}
                className={`relative rounded-2xl p-4 border cursor-pointer transition-all ${
                  isSelected
                    ? 'border-gold bg-amber-50/70 dark:bg-amber-950/30 shadow-md ring-2 ring-gold/40'
                    : 'glass-panel border-slate-200 dark:border-slate-800 hover:border-skyBlue-300'
                }`}
              >
                {pkg.isPopular && (
                  <span className="absolute -top-2.5 start-4 px-2 py-0.5 rounded-full text-[9px] font-extrabold bg-gold text-slate-950 uppercase tracking-tight shadow">
                    الأكثر طلباً
                  </span>
                )}

                <div className="flex items-start justify-between">
                  <h3 className="font-extrabold text-xs sm:text-sm text-slate-900 dark:text-slate-100">
                    {pkg.nameAr}
                  </h3>
                  <span className="text-base font-black text-skyBlue-700 dark:text-skyBlue-300 font-mono">
                    {pkg.minutes} د
                  </span>
                </div>

                <p className="text-[11px] text-slate-500 dark:text-slate-400 mt-1 line-clamp-2">
                  {pkg.description}
                </p>

                <div className="mt-3 pt-2 border-t border-slate-200 dark:border-slate-800 flex items-center justify-between text-xs">
                  <div className="font-extrabold text-slate-900 dark:text-slate-100">
                    <span>${pkg.priceUsd}</span> / <span>{pkg.priceSar} ر.س</span>
                  </div>
                  <span className="text-[10px] text-slate-400 font-mono">{pkg.priceEtb} ETB</span>
                </div>
              </div>
            );
          })}
        </div>
      </div>

      {/* 3. Official Bank Accounts (الحسابات البنكية المعتمدة التي يعدلها المشرف) */}
      <div className="space-y-2.5">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-2">
            <Building2 className="w-4 h-4 text-gold" />
            <h2 className="text-sm sm:text-base font-extrabold text-slate-900 dark:text-slate-100">
              {t('bankAccountsTitle')}
            </h2>
          </div>
          <span className="text-[10px] text-slate-400">
            تُعدل من قبل المشرف في الإعدادات
          </span>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
          {bankDetails.map((bank) => (
            <div
              key={bank.id}
              onClick={() => setSelectedBankId(bank.id)}
              className={`rounded-2xl p-3.5 border transition-all cursor-pointer ${
                selectedBankId === bank.id
                  ? 'border-skyBlue-500 bg-skyBlue-50/80 dark:bg-skyBlue-950/40 ring-1 ring-skyBlue-500'
                  : 'glass-panel border-slate-200 dark:border-slate-800'
              }`}
            >
              <div className="flex items-center justify-between">
                <span className="font-extrabold text-xs text-skyBlue-800 dark:text-skyBlue-300">
                  {bank.bankName}
                </span>
                <span className="text-[10px] bg-slate-200 dark:bg-slate-700 px-1.5 py-0.5 rounded font-mono">
                  {bank.currency}
                </span>
              </div>

              <div className="mt-2 text-xs space-y-1">
                <div>
                  <span className="text-slate-400 text-[10px]">المستفيد:</span>{' '}
                  <strong className="text-slate-700 dark:text-slate-200">
                    {bank.accountHolder}
                  </strong>
                </div>

                <div className="flex items-center justify-between bg-white dark:bg-slate-900 p-1.5 rounded-lg border border-slate-200 dark:border-slate-700 font-mono text-xs">
                  <span className="truncate select-all">{bank.accountNumber}</span>
                  <button
                    onClick={(e) => {
                      e.stopPropagation();
                      handleCopyAccount(bank.id, bank.accountNumber);
                    }}
                    className="p-1 hover:text-gold text-slate-400"
                    title="نسخ رقم الحساب"
                  >
                    {copiedBankId === bank.id ? (
                      <Check className="w-3.5 h-3.5 text-emerald-500" />
                    ) : (
                      <Copy className="w-3.5 h-3.5" />
                    )}
                  </button>
                </div>

                {bank.instructions && (
                  <p className="text-[10px] text-amber-600 dark:text-amber-400 mt-1 italic">
                    {bank.instructions}
                  </p>
                )}
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* 4. Manual Payment Confirmation Form (طريقة الدفع بإرسال يدوي) */}
      <div className="glass-panel rounded-2xl p-4 sm:p-5 border border-slate-200 dark:border-slate-800 shadow-sm">
        <div className="flex items-center gap-2 mb-3">
          <ShieldCheck className="w-4 h-4 text-emerald-600" />
          <h3 className="text-sm sm:text-base font-extrabold text-slate-900 dark:text-slate-100">
            {t('submitProof')} (تأكيد الحوالة للمشرف):
          </h3>
        </div>

        <p className="text-xs text-slate-500 dark:text-slate-400 mb-3">
          {t('paymentInstructions')}
        </p>

        <form onSubmit={handlePaymentSubmit} className="space-y-3">
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
            <div>
              <label className="block text-xs font-bold text-slate-700 dark:text-slate-300 mb-1">
                الباقة المختارة:
              </label>
              <div className="p-2 rounded-xl bg-slate-100 dark:bg-slate-800 text-xs font-bold text-skyBlue-700 dark:text-skyBlue-300 border border-slate-200 dark:border-slate-700">
                {selectedPackage.nameAr} ({selectedPackage.minutes} دقيقة) - ${selectedPackage.priceUsd}
              </div>
            </div>

            <div>
              <label className="block text-xs font-bold text-slate-700 dark:text-slate-300 mb-1">
                الحساب المحول إليه:
              </label>
              <div className="p-2 rounded-xl bg-slate-100 dark:bg-slate-800 text-xs font-bold text-slate-700 dark:text-slate-300 border border-slate-200 dark:border-slate-700 truncate">
                {selectedBank ? selectedBank.bankName : 'حدد البنك من القائمة أعلاه'}
              </div>
            </div>
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-3 gap-3">
            <div>
              <label className="block text-xs font-bold text-slate-700 dark:text-slate-300 mb-1">
                {t('senderName')}:
              </label>
              <input
                type="text"
                required
                value={senderName}
                onChange={(e) => setSenderName(e.target.value)}
                placeholder="اسم صاحب الحساب..."
                className="w-full px-3 py-2 text-xs rounded-xl border border-slate-300 dark:border-slate-700 bg-white dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none"
              />
            </div>

            <div>
              <label className="block text-xs font-bold text-slate-700 dark:text-slate-300 mb-1">
                {t('senderPhone')}:
              </label>
              <input
                type="text"
                required
                value={senderPhone}
                onChange={(e) => setSenderPhone(e.target.value)}
                placeholder="+966... / +251..."
                className="w-full px-3 py-2 text-xs rounded-xl border border-slate-300 dark:border-slate-700 bg-white dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none"
              />
            </div>

            <div>
              <label className="block text-xs font-bold text-slate-700 dark:text-slate-300 mb-1">
                {t('txNumber')}:
              </label>
              <input
                type="text"
                required
                value={transactionId}
                onChange={(e) => setTransactionId(e.target.value)}
                placeholder="رقم العملية / المرجع البنكي..."
                className="w-full px-3 py-2 text-xs rounded-xl border border-slate-300 dark:border-slate-700 bg-white dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none font-mono"
              />
            </div>
          </div>

          <div className="flex justify-end pt-1">
            <button
              type="submit"
              className="px-5 py-2.5 rounded-xl bg-gradient-to-r from-emerald-600 to-teal-600 hover:from-emerald-700 hover:to-teal-700 text-white font-extrabold text-xs flex items-center gap-1.5 shadow-md transition-transform active:scale-95"
            >
              <span>{t('sendPaymentRequest')}</span>
              <Send className="w-3.5 h-3.5" />
            </button>
          </div>
        </form>
      </div>

      {/* 5. Payments & Loans History (سجل العمليات والطلبات) */}
      <div className="glass-panel rounded-2xl p-4 border border-slate-200 dark:border-slate-800 shadow-sm">
        <div className="flex items-center gap-2 mb-3">
          <History className="w-4 h-4 text-gold" />
          <h3 className="font-extrabold text-xs sm:text-sm text-slate-900 dark:text-slate-100">
            سجل طلبات التحويل وسلف الدقائق:
          </h3>
        </div>

        <div className="space-y-2">
          {payments.map((p) => (
            <div
              key={p.id}
              className="p-2.5 rounded-xl bg-slate-50 dark:bg-slate-800/60 border border-slate-200 dark:border-slate-700 flex items-center justify-between gap-2 text-xs"
            >
              <div>
                <div className="font-bold text-slate-900 dark:text-slate-100">
                  {p.packageName}
                </div>
                <div className="text-[10px] text-slate-400">
                  المرجع: <span className="font-mono">{p.transactionId}</span> • {p.date} • {p.method}
                </div>
              </div>

              <div>
                {p.status === 'APPROVED' && (
                  <span className="px-2 py-0.5 rounded-full text-[10px] font-bold bg-emerald-100 text-emerald-800 dark:bg-emerald-950 dark:text-emerald-300">
                    معتمد ومضاف (+{p.minutes} د)
                  </span>
                )}
                {p.status === 'PENDING' && (
                  <span className="px-2 py-0.5 rounded-full text-[10px] font-bold bg-amber-100 text-amber-800 dark:bg-amber-950 dark:text-amber-300">
                    قيد مراجعة المشرف
                  </span>
                )}
                {p.status === 'REJECTED' && (
                  <span className="px-2 py-0.5 rounded-full text-[10px] font-bold bg-rose-100 text-rose-800 dark:bg-rose-950 dark:text-rose-300">
                    مرفوض
                  </span>
                )}
              </div>
            </div>
          ))}

          {loans.map((loan) => (
            <div
              key={loan.id}
              className="p-2.5 rounded-xl bg-skyBlue-50/60 dark:bg-skyBlue-950/30 border border-skyBlue-100 dark:border-skyBlue-900 flex items-center justify-between gap-2 text-xs"
            >
              <div>
                <div className="font-bold text-skyBlue-900 dark:text-skyBlue-200">
                  سلفة دقائق: +{loan.minutesRequested} دقيقة
                </div>
                <div className="text-[10px] text-slate-500 dark:text-slate-400">
                  السبب: {loan.reason} • {loan.date}
                </div>
              </div>

              <span
                className={`px-2 py-0.5 rounded-full text-[10px] font-bold ${
                  loan.status === 'APPROVED'
                    ? 'bg-emerald-100 text-emerald-800 dark:bg-emerald-950 dark:text-emerald-300'
                    : 'bg-amber-100 text-amber-800 dark:bg-amber-950 dark:text-amber-300'
                }`}
              >
                {loan.status === 'APPROVED' ? 'تمت الموافقة' : 'قيد التدقيق'}
              </span>
            </div>
          ))}
        </div>
      </div>

      {/* Minute Loan Modal */}
      {showLoanModal && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-3 animate-in fade-in">
          <div className="bg-white dark:bg-slate-900 rounded-2xl max-w-md w-full p-4 sm:p-5 border border-slate-200 dark:border-slate-800 shadow-2xl">
            <div className="flex items-center justify-between mb-3">
              <h3 className="font-extrabold text-sm sm:text-base text-slate-900 dark:text-slate-100">
                طلب سلفة دقائق لحفظ أو تسميع طارئ:
              </h3>
              <button onClick={() => setShowLoanModal(false)}>
                <X className="w-5 h-5 text-slate-400" />
              </button>
            </div>

            <form onSubmit={handleLoanSubmit} className="space-y-3">
              <div>
                <label className="block text-xs font-bold text-slate-700 dark:text-slate-300 mb-1">
                  عدد الدقائق المطلوبة:
                </label>
                <select
                  value={loanMins}
                  onChange={(e) => setLoanMins(Number(e.target.value))}
                  className="w-full px-3 py-2 text-xs rounded-xl border border-slate-300 dark:border-slate-700 bg-white dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none font-bold"
                >
                  <option value={15}>15 دقيقة (تسميع ربع)</option>
                  <option value={20}>20 دقيقة (تسميع حزب)</option>
                  <option value={30}>30 دقيقة (تسميع جزء كامل)</option>
                </select>
              </div>

              <div>
                <label className="block text-xs font-bold text-slate-700 dark:text-slate-300 mb-1">
                  سبب طلب السلفة:
                </label>
                <textarea
                  required
                  value={loanReason}
                  onChange={(e) => setLoanReason(e.target.value)}
                  rows={3}
                  placeholder="مثال: أحتاج إتمام تسميع سورة الكهف اليوم قبل موعد تجديد اشتراكي..."
                  className="w-full px-3 py-2 text-xs rounded-xl border border-slate-300 dark:border-slate-700 bg-white dark:bg-slate-800 text-slate-900 dark:text-slate-100 outline-none"
                />
              </div>

              <div className="flex justify-end gap-2 pt-2">
                <button
                  type="button"
                  onClick={() => setShowLoanModal(false)}
                  className="px-3 py-1.5 rounded-lg text-xs font-bold text-slate-500"
                >
                  إلغاء
                </button>
                <button
                  type="submit"
                  className="px-4 py-1.5 rounded-lg bg-gold text-slate-950 font-bold text-xs shadow hover:bg-amber-400"
                >
                  إرسال طلب السلفة للمشرف
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
