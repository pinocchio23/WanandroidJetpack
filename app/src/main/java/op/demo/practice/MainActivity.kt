package op.demo.practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import pk.utils.lib.CardConfig
import pk.utils.lib.CardItemTouchHelperCallback
import pk.utils.lib.CardLayoutManager
import pk.utils.lib.OnSwipeListener

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    var list = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        GlobalScope.launch {
//            val time = measureTimeMillis {
//                val one = async { doSomethingOne() }
//                val two = async { doSomethingTwo() }2
//                println("The answer is ${one.await() + two.await()}")
//            }
//            println("Completed in $time ms")
//        }
//        println("Completed")

        initData()
        initView()
    }

    private fun launch(
        block: () -> Unit,
        complete: (Int) -> String
    ) {
        block.invoke()
        complete(3)
    }

    private fun initData() {
        list.add(R.drawable.img_avatar_01)
        list.add(R.drawable.img_avatar_02)
        list.add(R.drawable.img_avatar_03)
        list.add(R.drawable.img_avatar_04)
        list.add(R.drawable.img_avatar_05)
        list.add(R.drawable.img_avatar_06)
        list.add(R.drawable.img_avatar_07)

        launch(
            {
                println("block")
            },
            {
                it.toString()
            }
        )
    }

    private fun initView() {
        recyclerView = findViewById(R.id.rv)
        recyclerView.run {
            itemAnimator = DefaultItemAnimator()
            adapter = MyAdapter()
        }
        val cardCallback = CardItemTouchHelperCallback(recyclerView.adapter!!, list,
            object : OnSwipeListener<Int> {
                override fun onSwiping(
                    viewHolder: RecyclerView.ViewHolder?,
                    ratio: Float,
                    direction: Int
                ) {
                    val myHolder = viewHolder as MyViewHolder
                    viewHolder.itemView.alpha = 1 - Math.abs(ratio) * 0.2f
                    if (direction == CardConfig.SWIPING_LEFT) {
                        myHolder.dislikeImageView!!.alpha = Math.abs(ratio)
                    } else if (direction == CardConfig.SWIPING_RIGHT) {
                        myHolder.likeImageView!!.alpha = Math.abs(ratio)
                    } else {
                        myHolder.dislikeImageView!!.alpha = 0f
                        myHolder.likeImageView!!.alpha = 0f
                    }
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder?,
                    t: Int?,
                    direction: Int
                ) {
                    val myHolder = viewHolder as MyViewHolder
                    viewHolder.itemView.alpha = 1f
                    myHolder.dislikeImageView!!.alpha = 0f
                    myHolder.likeImageView!!.alpha = 0f
                    Toast.makeText(
                        this@MainActivity,
                        if (direction == CardConfig.SWIPED_LEFT) "swiped left" else "swiped right",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onSwipedClear() {
                    Toast.makeText(this@MainActivity, "data clear", Toast.LENGTH_SHORT).show()
                    recyclerView.postDelayed({
                        initData()
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }, 3000L)
                }

            })
        val itemTouchHelper = ItemTouchHelper(cardCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView.layoutManager = CardLayoutManager(recyclerView, itemTouchHelper)
    }

    private inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val avatarImageView = holder.avatarImageView
            avatarImageView?.setImageResource(list[position])
        }

        override fun getItemCount(): Int = list.size

    }

    private inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatarImageView: ImageView? = null
        var likeImageView: ImageView? = null
        var dislikeImageView: ImageView? = null

        init {
            avatarImageView = view.findViewById(R.id.iv_avatar)
            likeImageView = view.findViewById(R.id.iv_like)
            dislikeImageView = view.findViewById(R.id.iv_dislike)
        }
    }


}

