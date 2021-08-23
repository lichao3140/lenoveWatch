package cn.ycgo.base.medial.router

/**
 *Author:Kgstt
 *Time: 2020/11/24
 */
object PathConstants {
    const val ACTIVITY_MAIN = "/app/MainActivity"
    const val ACTIVITY_PREVIEW_PHOTO = "/medial/PreviewPhotoActivity"
    const val ACTIVITY_WEB = "/browser/WebActivity"
    const val ACTIVITY_WEB_GAME = "/browser/WebGameActivity"
    const val ACTIVITY_DEBUG = "/medial/DebugActivity"
    const val ACTIVITY_DYNAMIC_POST_IMAGE = "/piazza/PiazzaPostForImageActivity"
    const val ACTIVITY_DYNAMIC_POST_VIDEO = "/piazza/PiazzaPostForVideoActivity"
    const val ACTIVITY_DYNAMIC_POST_TOPIC= "/piazza/PiazzaPostTopicActivity"
    const val ACTIVITY_DYNAMIC_POST_LOCATION= "/piazza/PiazzaPostLocationActivity"
    const val ACTIVITY_DYNAMIC_DETAIL_IMAGE = "/piazza/DynamicDetailForImageActivity"
    const val ACTIVITY_DYNAMIC_DETAIL_VIDEO = "/piazza/VideoActivity"
    const val ACTIVITY_USER_PIAZZA= "/piazza/UserPiazzaActivity"
    const val ACTIVITY_DOCTOR_LIST = "/medicine/DoctorListActivity"
    const val ACTIVITY_MAQ_DETAIL = "/medicine/MedicalAdviceQuestionsDetailActivity"
    const val ACTIVITY_ASK_DOCTOR_FAST = "/medicine/AskDoctorQuicklyActivity"
    const val ACTIVITY_ASK_DOCTOR_CLIENT_LIST = "/medicine/DoctorClientAskListActivity"
    const val ACTIVITY_APPLY_DOCTOR = "/medicine/ApplyDoctorActivity"
    const val ACTIVITY_APPLY_DOCTOR_VERIFY = "/medicine/ApplyDoctorVerifyActivity"
    const val ACTIVITY_APPLY_ADD_HOSPITAL = "/medicine/ApplyDoctorAddHospitalActivity"
    const val ACTIVITY_APPLY_DOCTOR_PERMIT= "/medicine/ApplySelectPermitActivity"
    const val ACTIVITY_ASK_EXPERT = "/medicine/AskExpertActivity"
    const val ACTIVITY_DOCTOR_DETAIL = "/medicine/DoctorDetailActivity"
    const val ACTIVITY_DOCTOR_DETAIL_INTRO = "/medicine/DoctorDetailIntroActivity"
    const val ACTIVITY_DOCTOR_EVALUATE = "/medicine/DoctorEvaluateListActivity"
    const val ACTIVITY_HOSPITAL_DETAIL = "/medicine/HospitalDetailActivity"
    const val ACTIVITY_MINE_EXTEND = "/mine/MineExtendActivity"
    const val ACTIVITY_MINE_QR_CODE = "/mine/MineQRCodeActivity"
    const val ACTIVITY_MINE_ADDRESS_LIST = "/mine/MineAddressListActivity"
    const val ACTIVITY_MINE_EDIT_INFO = "/mine/EditUserInfoActivity"
    const val ACTIVITY_MY_USERS = "/mine/MyUsersActivity"
    const val ACTIVITY_EXTEND_USER_DETAILS = "/mine/ExtendUserDetailsActivity"
    const val ACTIVITY_WITHDRAWAL = "/mine/WithdrawalActivity"
    const val ACTIVITY_WITHDRAWAL_RECORD = "/mine/WithdrawalRecordActivity"
    const val ACTIVITY_PROFIT_BANK = "/mine/ProfitBankActivity"
    const val ACTIVITY_ADD_BANK_CARD = "/mine/AddBankCardActivity"
    const val ACTIVITY_MINE_ADDRESS_ADD = "/mine/AddAddressActivity"
    const val ACTIVITY_MINE_ADDRESS_UPDATE = "/mine/UpdateAddressActivity"
    const val ACTIVITY_INFORMATION_CARD = "/information/InformationCardActivity"
    const val ACTIVITY_MINE_DYNAMIC_MSG = "/information/MineDynamicMsgActivity"
    const val ACTIVITY_VERIFY_INFORMATION = "/information/VerifyInfoActivity"
    const val ACTIVITY_MSG_CENTER = "/information/MsgCenterActivity"
    const val ACTIVITY_CONSULT = "/information/MineConsultActivity"
    const val ACTIVITY_CONSULT_DOCTOR = "/information/DoctorConsultActivity"
    const val ACTIVITY_LIKE_MSG = "/information/LikeMsgActivity"
    const val ACTIVITY_COMMENT_MSG = "/information/CommentMsgActivity"
    const val ACTIVITY_INFORMATION_PREVIEW = "/information/InformationPreviewActivity"
    const val ACTIVITY_MINE_INDENT = "/store/MineIndentActivity"
    const val ACTIVITY_STORE_GOOD_CART= "/store/GoodsCartActivity"
    const val ACTIVITY_STORE_GOODS_APPLY_ORDER= "/store/GoodsApplyOrderActivity"
    const val ACTIVITY_STORE_GOOD_ORDER_DETAIL = "/store/GoodsOrderDetailActivity"
    const val ACTIVITY_STORE_GOOD_ORDER_SEARCH = "/store/GoodsOrderSearchActivity"
    const val ACTIVITY_STORE_GOODS_HOME = "/store/StoreGoodsHomeActivity"
    const val ACTIVITY_STORE_GOOD_DETAIL = "/store/GoodsDetailActivity"
    const val ACTIVITY_STORE_GOOD_POST_COMMENT = "/store/GoodsPostCommentActivity"
    const val ACTIVITY_STORE_RENTING_POST_COMMENT = "/store/PostRentingCommentActivity"
    const val ACTIVITY_STORE_GOOD_SEARCH = "/store/GoodsSearchActivity"
    const val ACTIVITY_STORE_GOOD_COMMENT= "/store/GoodsCommentListActivity"
    const val ACTIVITY_STORE_MY_ORDER = "/store/StoreMyOrderActivity"
    const val ACTIVITY_STORE_RENTING_DETAIL = "/store/StoreRentingDetailActivity"
    const val ACTIVITY_STORE_RENTING_BOOK = "/store/StoreRentingBookActivity"
    const val ACTIVITY_STORE_RENTING_LIST = "/store/RentingListActivity"
    const val ACTIVITY_STORE_RENTING_COMMENT_LIST= "/store/RentingCommentListActivity"
    const val ACTIVITY_STORE_RENTING_ORDER_DETAIL = "/store/StoreRentingOrderDetailActivity"
    const val ACTIVITY_STORE_MY_ORDER_LOGISTICS_DETAIL = "/store/GoodsOrderCheckLogisticsDetailActivity"
    const val ACTIVITY_POST_DYNAMIC_SELECT_GOODS = "/store/PostDynamicSelectGoodsActivity"
    const val ACTIVITY_POST_DYNAMIC_SELECTED_GOODS = "/store/PostDynamicSelectedGoodsActivity"
    const val ACTIVITY_PRICING_SERVICE = "/store/PricingServiceActivity"
    const val ACTIVITY_PRICING_SERVICE_PAY_INFO = "/store/PricingPayInfoActivity"
    const val ACTIVITY_PRICING_SERVICE_CREATE_PETITIONER = "/store/PricingCreatePetitionerActivity"
    const val ACTIVITY_PRICING_SERVICE_PETITIONER_LIST = "/store/PricingPetitionerListActivity"
    const val ACTIVITY_PRICING_PAY_SUCCESS = "/store/PricingPaySuccessActivity"
    const val ACTIVITY_PRICING_DETAIL = "/store/PricingDetailActivity"
    const val ACTIVITY_POLICY_SERVICE = "/store/PolicyServiceActivity"
    const val ACTIVITY_APPLY_SUCCESS = "/store/ApplySuccessActivity"

    const val ACTIVITY_FOLLOW = "/user/FollowActivity"
    const val ACTIVITY_FOLLOWED = "/user/FollowedActivity"
    const val ACTIVITY_FOLLOW_RECOMMEND = "/user/RecommendFollowActivity"
    const val ACTIVITY_LOGIN = "/user/UserLoginActivity"


    const val SERVICE_HOME = "/home/HomeService"
    const val SERVICE_INFORMATION = "/information/InformationService"
    const val SERVICE_IM = "/easemob/ImService"
    const val SERVICE_PIAZZA = "/piazza/PiazzaService"
    const val SERVICE_MEDICINE = "/medicine/MedicineService"
    const val SERVICE_MINE = "/mine/MineService"
    const val SERVICE_RELATIVES = "/relatives/RelativesFragment"
    const val SERVICE_STORE = "/store/StoreService"
    const val SERVICE_SHARE = "/share/ShareService"
    const val SERVICE_AD = "/ad/AdService"
    const val SERVICE_USER = "/user/UserService"
    const val SERVICE_BROWSER = "/browser/BrowserService"
    const val SERVICE_CIRCLE = "/circle/CircleService"

}