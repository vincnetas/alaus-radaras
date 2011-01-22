#ifndef MAKECALL_H
#define MAKECALL_H

#ifdef Q_OS_SYMBIAN
#include <etel3rdparty.h>
#endif
// link to etel3rdparty.lib

//class MDialObserver
//{
//    public:
//        virtual void CallDialedL(TInt aError) = 0;
//};

//class CCallDialer : public CActive
//{
//    public:
//        static CCallDialer* NewL(MDialObserver& aCallBack, const TDesC& aNumber);
//        static CCallDialer* NewLC(MDialObserver& aCallBack, const TDesC& aNumber);
//        ~CCallDialer();

//    protected:
//        CCallDialer(MDialObserver& aObserver);
//        void ConstructL(const TDesC& aNumber);

//    private:
//        void RunL();
//        void DoCancel();

//    private:
//        MDialObserver&                iObserver;
//        CTelephony*                   iTelephony;
//        CTelephony::TCallId           iCallId;
//        CTelephony::TCallParamsV1     iCallParams;
//        CTelephony::TCallParamsV1Pckg iCallParamsPckg;
//};
#endif // MAKECALL_H
