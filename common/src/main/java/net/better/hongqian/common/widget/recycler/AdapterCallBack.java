package net.better.hongqian.common.widget.recycler;

/**
 * Created by HongQian.Wang on 2018/2/9.
 */

public interface AdapterCallBack<Data> {
    void update(Data data ,RecyclerAdapter.ViewHolder<Data> holder);
}
