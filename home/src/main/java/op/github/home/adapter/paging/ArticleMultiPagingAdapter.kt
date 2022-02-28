package op.github.home.adapter.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alibaba.android.arouter.launcher.ARouter
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import op.github.common.support.Constants
import op.github.home.R
import op.github.home.bean.ArticleData
import op.github.home.bean.BannerData
import op.github.home.databinding.ItemBannerBinding
import op.github.home.databinding.ItemRvArticleBinding

/**
 * Author: pk
 * Time: 2022/2/9  6:24 下午
 * Description:  首页多typeAdapter，包含Banner和文章列表
 */
class ArticleMultiPagingAdapter :
    PagingDataAdapter<ArticleData, RecyclerView.ViewHolder>(differCallback) {


    //banner 数据源
    private var bannerList: List<BannerData> = ArrayList()

    fun addBannerList(list: List<BannerData>) {
        this.bannerList = list
    }

    companion object {
        const val TYPE_BANNER = 0
        const val TYPE_ARTICLE = 1

        val differCallback = object : DiffUtil.ItemCallback<ArticleData>() {

            override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_BANNER) {
            (holder as BannerVH).bindData(bannerList)
        } else {
            getItem(position - 1)?.let {
                (holder as ArticleVH).bindData(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_BANNER) {
            return BannerVH(
                ItemBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        val articleVH =
            ArticleVH(
                ItemRvArticleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        articleVH.itemView.setOnClickListener {
            val data = getItem(articleVH.layoutPosition - 1)
            //跳转到webView 文章详情
            ARouter.getInstance()
                .build(Constants.PATH_WEBVIEW)
                .withString(Constants.KEY_WEBVIEW_PATH, data?.link)
                .withString(Constants.KEY_WEBVIEW_TITLE, data?.title)
                .navigation()
        }

        return articleVH
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_BANNER else TYPE_ARTICLE
    }


    internal class BannerVH(val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //绑定banner数据
        fun bindData(data: List<BannerData>) {
            binding.bannerArticle.adapter = object : BannerImageAdapter<BannerData>(data) {
                /**
                 * 绑定布局数据
                 *
                 * @param holder   XViewHolder
                 * @param data     数据实体
                 * @param position 当前位置
                 * @param size     总数
                 */
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerData?,
                    position: Int,
                    size: Int
                ) {
                    holder!!.imageView.load(data?.imagePath) {
                        placeholder(R.mipmap.img_placeholder)
                    }
                }

            }
        }
    }

    internal class ArticleVH(val binding: ItemRvArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: ArticleData) {
            setText(binding.tvArticleTitle, data.title)
            setText(binding.btHealthInfoType, data.superChapterName)
            setText(binding.tvHomeInfoTime, data.niceDate)

            if (data.author.isEmpty()) {
                setText(binding.tvArticleAuthor, data.shareUser)
            } else {
                setText(binding.tvArticleAuthor, data.author)
            }
        }

        private fun setText(view: TextView, text: String) {
            view.text = text
        }
    }
}