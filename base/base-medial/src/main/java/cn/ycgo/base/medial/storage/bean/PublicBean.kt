package cn.ycgo.base.medial.storage.bean

/**
 *Author:Kgstt
 *Time: 2020/12/4
 */
interface IBannerBean {
    fun getBannerImage(): String
    fun getBannerLink(): String
    fun getBannerId(): Int
    fun getBannerTitle(): String
}

interface IVideoBean {
    fun getVideoCover(): String?
    fun getVideoName(): String?
    fun getVideoId(): Int?
    fun getVideoUrl(): String?
}

interface IUserBean {
    fun getUserId(): Int
    fun getAvatarUrl(): String
    fun getUserName(): String
    fun getAddressText(): String
    fun getIsFollowed(): Boolean
}

data class UserBean(
    private val user_id: Int = 0,
    private val id: Int = 0,
    val is_doctor: Int = 0,
    val role_type: Int = 0,
    private val avatar: String,
    private val nickname: String,
    private val addressTextName: String? = "",
    val token: String? = null,
    val im: ImBean,
    private val star_already: Int? = 0
) : IUserBean {
    override fun getUserId() = if (user_id >= 0) id else user_id
    override fun getAvatarUrl() = avatar
    override fun getUserName() = nickname
    override fun getAddressText() = addressTextName ?: ""
    override fun getIsFollowed() = (star_already == 1)
}

data class ImBean(
    val username: String,
    val password: String
)
data class ProfitBean(
    val today_profit: String,
    val total_profit: String
)
data class UserDetailBean(
    private val star_already: Int? = 0,
    private val avatar: String,
    val city_name: String,
    val fan_count: Int,
    private val id: Int,
    val like_count: Int,
    val is_doctor: Int,
    val mobile: String,
    val nickname: String,
    val im_username: String?,
    val im_password: String?,
    val province_name: String,
    val qrcode: String,
    val signature: String,
    val role_type: Int,
    val profit:ProfitBean,
    val star_count: Int
) : IUserBean {
    override fun getUserId() = id
    override fun getAvatarUrl() = avatar
    override fun getUserName() = nickname
    override fun getAddressText() = if (province_name == city_name) {
        city_name
    } else {
        "$province_name$city_name"
    }

    override fun getIsFollowed() = (star_already == 1)
}

data class BannerBean(
    private val id: Int,
    private val link: String,
    private val picture: String,
    private val title: String
) : IBannerBean {
    override fun getBannerImage() = picture
    override fun getBannerLink() = link
    override fun getBannerId() = id
    override fun getBannerTitle() = title
}

data class SpreadQrcode(
    val qrcode: String
)