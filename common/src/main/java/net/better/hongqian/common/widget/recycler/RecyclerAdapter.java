package net.better.hongqian.common.widget.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.better.hongqian.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by HongQian.Wang on 2018/2/9.
 */

public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>> implements View.OnClickListener, View.OnLongClickListener, AdapterCallBack<Data> {
    private List<Data> mDatalist=new ArrayList<>();

    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<Data> adapterListener) {
        this(null, adapterListener);
    }

    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> adapterListener) {
        this.mDatalist = dataList;
        this.adapterListener = adapterListener;
    }

    /**
     * 约定ViewType的值为layout布局的int值  把布局id为ViewType的文件初始化为一个root View
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);

        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        root.setTag(R.id.tag_recycler_holder);
        holder.callBack = this;

        holder.unbinder = ButterKnife.bind(this, root);
        return holder;
    }

    //   得到布局类型,返回的都是xml文件的id
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDatalist.get(position));
    }

    protected abstract int getItemViewType(int position, Data data);

    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
        Data data = mDatalist.get(position);
        holder.bind(data); //出发holder的绑定方法 触发绑定

    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    public void add(Data data) {
        mDatalist.add(data);
        notifyItemInserted(mDatalist.size() - 1);
    }

    public void add(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mDatalist.size();
            Collections.addAll(mDatalist, dataList);
            notifyItemRangeChanged(startPos, dataList.length);
        }
    }

    public void add(Collection<Data> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = mDatalist.size();
            mDatalist.addAll(dataList);
            notifyItemRangeChanged(startPos, dataList.size());
        }
    }

    public void clear() {
        mDatalist.clear();
        notifyDataSetChanged();
    }

    public void replace(Collection<Data> dataList) {
        mDatalist.clear();
        if (dataList != null || dataList.size() == 0) {
            return;
        }
        mDatalist.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);
        if (adapterListener != null) {
            int adapterPosition = holder.getAdapterPosition();
            adapterListener.onItemClick(holder, mDatalist.get(adapterPosition));
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);
        if (adapterListener != null) {
            int adapterPosition = holder.getAdapterPosition();
            adapterListener.onItemLongClick(holder, mDatalist.get(adapterPosition));
            return true;
        }
        return false;
    }

    @Override
    public void update(Data data, ViewHolder<Data> holder) {

    }


    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
        protected Data mData;
        private Unbinder unbinder;

        private AdapterCallBack<Data> callBack;

        public ViewHolder(View itemView) {
            super(itemView);

        }

        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候的回调
         *
         * @param data
         */
        protected abstract void onBind(Data data);

        public void updateData(Data data) {
            if (callBack != null) {
                callBack.update(data, this);
            }
        }
    }

    private AdapterListener<Data> adapterListener;

    public void setListener(AdapterListener<Data> adapterListener) {
        this.adapterListener = adapterListener;
    }

    public interface AdapterListener<Data> {
        void onItemClick(RecyclerAdapter.ViewHolder holder, Data data);

        void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data);
    }
}
